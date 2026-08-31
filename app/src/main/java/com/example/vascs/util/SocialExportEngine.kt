package com.example.vascs.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.net.Uri
import com.example.vascs.data.model.ProductEntity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ExportType(
    val code: String,
    val displayName: String,
    val width: Int,
    val height: Int,
    val subfolder: String
) {
    WHATSAPP_CARD("WHATSAPP_CARD", "WhatsApp Card", 1200, 1200, "whatsapp"),
    INSTAGRAM_POST("INSTAGRAM_POST", "Instagram Post", 1080, 1080, "instagram"),
    INSTAGRAM_STORY("INSTAGRAM_STORY", "Instagram Story", 1080, 1920, "instagram"),
    FACEBOOK_POST("FACEBOOK_POST", "Facebook Post", 1200, 630, "facebook"),
    FACEBOOK_COVER("FACEBOOK_COVER", "Facebook Cover", 1640, 624, "facebook"),
    TELEGRAM_POST("TELEGRAM_POST", "Telegram Post", 1200, 900, "telegram"),
    CATALOGUE_CARD("CATALOGUE_CARD", "Catalogue Card", 1200, 1600, "catalogue"),
    CUSTOM_EXPORT("CUSTOM", "Custom Export", 1080, 1080, "custom")
}

object SocialExportEngine {

    fun getExportSubfolder(context: Context, subfolder: String): File {
        val baseDir = File(context.filesDir, "social_exports")
        val destDir = File(baseDir, subfolder)
        if (!destDir.exists()) {
            destDir.mkdirs()
        }
        return destDir
    }

    fun renderSocialExportBitmap(
        context: Context,
        product: ProductEntity,
        exportType: ExportType,
        sourceBitmap: Bitmap?,
        brandProfile: BrandProfile = BrandingEngine.defaultProfile,
        customWidth: Int? = null,
        customHeight: Int? = null
    ): Bitmap {
        val width = customWidth ?: exportType.width
        val height = customHeight ?: exportType.height

        val resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)

        // Background - Dark Rich Gradient
        val bgPaint = Paint().apply {
            shader = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                Color.parseColor("#1F000F"),
                Color.parseColor("#42001F"),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Draw Watermark
        BrandingEngine.drawWatermark(canvas, width, height, brandProfile.watermarkText, brandProfile.goldColorHex)

        // Top Header Banner (Brand)
        val headerHeight = (height * 0.12f).coerceAtLeast(100f)
        val headerPaint = Paint().apply {
            color = Color.parseColor("#7A003C")
        }
        canvas.drawRect(0f, 0f, width.toFloat(), headerHeight, headerPaint)

        // Gold line under header
        val goldPaint = Paint().apply {
            color = Color.parseColor("#D4AF37")
            strokeWidth = 6f
        }
        canvas.drawLine(0f, headerHeight, width.toFloat(), headerHeight, goldPaint)

        // Header Text: Store Name & Tagline
        val storePaint = Paint().apply {
            color = Color.parseColor("#D4AF37")
            textSize = (headerHeight * 0.35f).coerceAtLeast(28f)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        canvas.drawText(brandProfile.storeName, 40f, headerHeight * 0.45f, storePaint)

        val taglinePaint = Paint().apply {
            color = Color.WHITE
            textSize = (headerHeight * 0.22f).coerceAtLeast(18f)
            isAntiAlias = true
        }
        canvas.drawText(brandProfile.tagline, 40f, headerHeight * 0.78f, taglinePaint)

        // Product Image Rect
        val imageTop = headerHeight + 30f
        val footerHeight = (height * 0.25f).coerceAtLeast(180f)
        val imageBottom = height - footerHeight - 20f
        val imageMargin = 40f

        val imageRect = RectF(imageMargin, imageTop, width - imageMargin, imageBottom)

        if (sourceBitmap != null) {
            val srcRect = Rect(0, 0, sourceBitmap.width, sourceBitmap.height)
            canvas.drawBitmap(sourceBitmap, srcRect, imageRect, Paint(Paint.FILTER_BITMAP_FLAG))
        } else {
            val placeholderPaint = Paint().apply {
                color = Color.parseColor("#2A2A2A")
            }
            canvas.drawRoundRect(imageRect, 20f, 20f, placeholderPaint)
            val noImgPaint = Paint().apply {
                color = Color.GRAY
                textSize = 32f
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
            }
            canvas.drawText("No Product Image", imageRect.centerX(), imageRect.centerY(), noImgPaint)
        }

        // Draw Gold Border Around Image
        val borderPaint = Paint().apply {
            color = Color.parseColor("#D4AF37")
            style = Paint.Style.STROKE
            strokeWidth = 6f
            isAntiAlias = true
        }
        canvas.drawRoundRect(imageRect, 12f, 12f, borderPaint)

        // Footer Section: Product Info, Price, Contacts, QR Code
        val footerTop = height - footerHeight
        val footerBgPaint = Paint().apply {
            color = Color.parseColor("#15000A")
        }
        canvas.drawRect(0f, footerTop, width.toFloat(), height.toFloat(), footerBgPaint)
        canvas.drawLine(0f, footerTop, width.toFloat(), footerTop, goldPaint)

        val textMargin = 40f
        var currentY = footerTop + 45f

        // Product Title
        val titlePaint = Paint().apply {
            color = Color.WHITE
            textSize = (footerHeight * 0.16f).coerceIn(24f, 42f)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        canvas.drawText(product.name, textMargin, currentY, titlePaint)
        currentY += titlePaint.textSize + 12f

        // Sub-details: SKU, Fabric, Category
        val subPaint = Paint().apply {
            color = Color.parseColor("#E0E0E0")
            textSize = (footerHeight * 0.10f).coerceIn(16f, 26f)
            isAntiAlias = true
        }
        val subDetails = "SKU: ${product.sku} | Fabric: ${product.fabric} | Category: ${product.category}"
        canvas.drawText(subDetails, textMargin, currentY, subPaint)
        currentY += subPaint.textSize + 16f

        // Price Section: Offer Price + MRP + GST
        val pricePaint = Paint().apply {
            color = Color.parseColor("#D4AF37")
            textSize = (footerHeight * 0.20f).coerceIn(28f, 48f)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }
        val formattedPrice = "₹%.2f".format(product.retailPrice)
        val formattedMrp = if (product.mrp > product.retailPrice) "MRP: ₹%.2f".format(product.mrp) else ""
        val priceText = "$formattedPrice  $formattedMrp  (Inc. GST ${product.gst}%)"
        canvas.drawText(priceText, textMargin, currentY, pricePaint)
        currentY += pricePaint.textSize + 16f

        // Contact Info
        val contactPaint = Paint().apply {
            color = Color.WHITE
            textSize = (footerHeight * 0.09f).coerceIn(14f, 22f)
            isAntiAlias = true
        }
        val contactText = "Call/WhatsApp: ${brandProfile.whatsApp} | Web: ${brandProfile.website}"
        canvas.drawText(contactText, textMargin, currentY, contactPaint)

        // Draw QR Code on bottom right
        val qrSize = (footerHeight * 0.70f).toInt().coerceIn(120, 260)
        val qrBitmap = BrandingEngine.generateBrandedQrCode(product.id, product.sku, qrSize)
        if (qrBitmap != null) {
            val qrLeft = width - qrSize - 40f
            val qrTop = footerTop + (footerHeight - qrSize) / 2f
            val qrBgPaint = Paint().apply { color = Color.WHITE }
            val qrRect = RectF(qrLeft - 8f, qrTop - 8f, qrLeft + qrSize + 8f, qrTop + qrSize + 8f)
            canvas.drawRoundRect(qrRect, 8f, 8f, qrBgPaint)
            canvas.drawBitmap(qrBitmap, qrLeft, qrTop, Paint(Paint.FILTER_BITMAP_FLAG))
        }

        return resultBitmap
    }

    fun generateAndSaveExport(
        context: Context,
        product: ProductEntity,
        exportType: ExportType,
        sourceImageUri: String,
        brandProfile: BrandProfile = BrandingEngine.defaultProfile
    ): String? {
        return try {
            val sourceBitmap = try {
                val uri = Uri.parse(sourceImageUri)
                val inputStream = context.contentResolver.openInputStream(uri)
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                null
            }

            val exportBitmap = renderSocialExportBitmap(
                context = context,
                product = product,
                exportType = exportType,
                sourceBitmap = sourceBitmap,
                brandProfile = brandProfile
            )

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(Date())
            val fileName = "export_${exportType.code.lowercase()}_${product.sku.lowercase()}_$timeStamp.jpg"
            val destFile = File(getExportSubfolder(context, exportType.subfolder), fileName)

            val outputStream = FileOutputStream(destFile)
            exportBitmap.compress(Bitmap.CompressFormat.JPEG, 92, outputStream)
            outputStream.flush()
            outputStream.close()

            Uri.fromFile(destFile).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
