package com.example.vascs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vascs.ui.theme.GoldAccent
import com.example.vascs.ui.theme.Maroon500
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PricingCalculatorScreen() {
    var purchaseCostText by remember { mutableStateOf("5000") }
    var gstRateText by remember { mutableStateOf("5.0") }
    var retailMarginPercent by remember { mutableFloatStateOf(40f) }
    var wholesaleMarginPercent by remember { mutableFloatStateOf(20f) }
    var discountPercent by remember { mutableFloatStateOf(25f) }

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 0
    }

    val purchaseCost = purchaseCostText.toDoubleOrNull() ?: 0.0
    val gstRate = gstRateText.toDoubleOrNull() ?: 0.0

    // Calculations
    val wholesalePrice = purchaseCost * (1 + (wholesaleMarginPercent / 100))
    val retailPrice = purchaseCost * (1 + (retailMarginPercent / 100))

    val gstAmountRetail = retailPrice * (gstRate / 100)
    val totalRetailWithGst = retailPrice + gstAmountRetail

    // Suggested MRP so that after discountPercent, selling price equals retailPrice
    val suggestedMrp = if (discountPercent < 100) retailPrice / (1 - (discountPercent / 100)) else retailPrice

    val netProfitRetail = retailPrice - purchaseCost
    val netProfitWholesale = wholesalePrice - purchaseCost

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Calculate, contentDescription = null, tint = Maroon500, modifier = Modifier.height(28.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("PRICING & MARGIN STUDIO", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)
                Text("Saree Costing & Margin Calculator", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Inputs Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("INPUT COST & TAXES", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Maroon500)

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = purchaseCostText,
                        onValueChange = { purchaseCostText = it },
                        label = { Text("Base Purchase Cost (₹)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = gstRateText,
                        onValueChange = { gstRateText = it },
                        label = { Text("GST Rate (%)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Sliders
                Text("Retail Profit Margin: ${retailMarginPercent.toInt()}%", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Slider(
                    value = retailMarginPercent,
                    onValueChange = { retailMarginPercent = it },
                    valueRange = 5f..150f,
                    colors = SliderDefaults.colors(thumbColor = Maroon500, activeTrackColor = Maroon500)
                )

                Text("Wholesale Profit Margin: ${wholesaleMarginPercent.toInt()}%", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Slider(
                    value = wholesaleMarginPercent,
                    onValueChange = { wholesaleMarginPercent = it },
                    valueRange = 5f..80f,
                    colors = SliderDefaults.colors(thumbColor = Maroon500, activeTrackColor = Maroon500)
                )

                Text("Showroom Tag Discount: ${discountPercent.toInt()}%", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Slider(
                    value = discountPercent,
                    onValueChange = { discountPercent = it },
                    valueRange = 0f..60f,
                    colors = SliderDefaults.colors(thumbColor = GoldAccent, activeTrackColor = GoldAccent)
                )
            }
        }

        // Calculated Output Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("CALCULATED PRICE STRUCTURE", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Maroon500)

                CalcResultRow("Suggested MRP Tag", currencyFormatter.format(suggestedMrp), isHero = true)
                CalcResultRow("Discount Offer (${discountPercent.toInt()}%)", "- ${currencyFormatter.format(suggestedMrp - retailPrice)}")
                HorizontalDivider()
                CalcResultRow("Final Retail Sell Price", currencyFormatter.format(retailPrice), isBold = true)
                CalcResultRow("GST Amount (${gstRate}%)", "+ ${currencyFormatter.format(gstAmountRetail)}")
                CalcResultRow("Customer Payable (Incl. GST)", currencyFormatter.format(totalRetailWithGst), isBold = true)
                HorizontalDivider()
                CalcResultRow("Wholesale Bulk Price", currencyFormatter.format(wholesalePrice))
                CalcResultRow("Retail Net Profit Amount", currencyFormatter.format(netProfitRetail), isHighlight = true)
                CalcResultRow("Wholesale Net Profit Amount", currencyFormatter.format(netProfitWholesale), isHighlight = true)
            }
        }
    }
}

@Composable
fun CalcResultRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    isHighlight: Boolean = false,
    isHero: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = if (isHero) 15.sp else 13.sp,
            fontWeight = if (isBold || isHero || isHighlight) FontWeight.Bold else FontWeight.Normal,
            color = if (isHighlight) Color(0xFF15803D) else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            fontSize = if (isHero) 18.sp else 14.sp,
            fontWeight = FontWeight.Bold,
            color = when {
                isHero -> Maroon500
                isHighlight -> Color(0xFF15803D)
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}
