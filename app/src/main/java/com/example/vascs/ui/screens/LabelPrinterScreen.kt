package com.example.vascs.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import com.example.vascs.util.BarcodeGenerator
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LabelPrinterScreen(
    products: List<ProductEntity>
) {
    var selectedProduct by remember { mutableStateOf<ProductEntity?>(products.firstOrNull()) }
    var storeName by remember { mutableStateOf("VEERANSH SAREE STUDIO") }
    var careInstruction by remember { mutableStateOf("Dry Clean Only") }

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 0
    }

    val barcodeBitmap = remember(selectedProduct?.barcode, selectedProduct?.sku) {
        val code = selectedProduct?.barcode?.ifBlank { null } ?: selectedProduct?.sku ?: "VASCS-001"
        BarcodeGenerator.generateBarcodeBitmap(code, width = 450, height = 120)
    }

    val qrCodeBitmap = remember(selectedProduct?.sku) {
        val sku = selectedProduct?.sku ?: "VASCS-001"
        BarcodeGenerator.generateQrCodeBitmap("VASCS:${sku}", size = 200)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.QrCode, contentDescription = null, tint = Maroon500, modifier = Modifier.height(28.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("PRINT STUDIO", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)
                Text("Saree Barcode Tag & Label Generator", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Product Selector Carousel
        Text("SELECT SAREE FOR PRINT", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)

        if (products.isEmpty()) {
            Text("No sarees available. Please add products to print labels.")
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(products) { product ->
                    val isSelected = selectedProduct?.id == product.id
                    Card(
                        onClick = { selectedProduct = product },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Maroon500 else MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.width(180.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = product.category,
                                fontSize = 11.sp,
                                color = if (isSelected) GoldAccent else Maroon500,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = product.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                maxLines = 1,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currencyFormatter.format(product.retailPrice),
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Maroon500,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // Template Settings
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("LABEL TEMPLATE CONFIGURATION", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)

                OutlinedTextField(
                    value = storeName,
                    onValueChange = { storeName = it },
                    label = { Text("Store Header Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = careInstruction,
                    onValueChange = { careInstruction = it },
                    label = { Text("Washing / Care Note") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        // Printable Saree Tag Preview
        Text("PRINTABLE SAREE TAG PREVIEW", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)

        selectedProduct?.let { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Maroon500, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = storeName.uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Maroon500,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "VEERANSH AI SAREE CATALOGUE",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Category: ${item.category}", fontSize = 12.sp, color = Color.DarkGray)
                        Text("Fabric: ${item.fabric}", fontSize = 12.sp, color = Color.DarkGray)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Colour: ${item.colour}", fontSize = 12.sp, color = Color.DarkGray)
                        Text("HSN: ${item.hsn}", fontSize = 12.sp, color = Color.DarkGray)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("MRP: ${currencyFormatter.format(item.mrp)}", fontSize = 12.sp, color = Color.Gray)
                            Text(
                                text = "OFFER PRICE: ${currencyFormatter.format(item.retailPrice)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Maroon500
                            )
                            Text("(Incl. of all taxes - GST ${item.gst}%)", fontSize = 10.sp, color = Color.Gray)
                        }

                        if (qrCodeBitmap != null) {
                            Image(
                                bitmap = qrCodeBitmap.asImageBitmap(),
                                contentDescription = "QR Code",
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (barcodeBitmap != null) {
                        Image(
                            bitmap = barcodeBitmap.asImageBitmap(),
                            contentDescription = "Barcode",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                        Text(
                            text = if (item.barcode.isNotBlank()) item.barcode else item.sku,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Care: $careInstruction",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Simulated Print Action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Maroon500),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Print, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Print Saree Barcode Tag", fontWeight = FontWeight.Bold)
            }
        }
    }
}
