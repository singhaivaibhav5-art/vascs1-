package com.example.vascs.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface

data class BrandProfile(
    val storeName: String = "VASCS SAREE ENTERPRISE",
    val tagline: String = "Pure Heritage & Artisan Handlooms",
    val phone: String = "+91 98765 43210",
    val whatsApp: String = "+91 98765 43210",
    val website: String = "www.vascssarees.com",
    val watermarkText: String = "VASCS EXCLUSIVE",
    val primaryColorHex: String = "#7A003C",
    val goldColorHex: String = "#D4AF37"
)

object BrandingEngine {

    val defaultProfile = BrandProfile()

    fun drawWatermark(
        canvas: Canvas,
        width: Int,
        height: Int,
        watermarkText: String = defaultProfile.watermarkText,
        colorHex: String = defaultProfile.goldColorHex
    ) {
        val paint = Paint().apply {
            color = Color.parseColor(colorHex)
            alpha = 40
            textSize = (width * 0.05f).coerceAtLeast(36f)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        canvas.save()
        canvas.rotate(-30f, width / 2f, height / 2f)
        canvas.drawText(watermarkText, width / 2f, height / 2f, paint)
        canvas.restore()
    }

    fun generateBrandedQrCode(
        productId: String,
        sku: String?,
        size: Int = 240
    ): Bitmap? {
        val qrContent = "VASCS|PID:$productId|SKU:${sku ?: "N/A"}|WEB:${defaultProfile.website}"
        return BarcodeGenerator.generateQrCodeBitmap(qrContent, size)
    }
}
