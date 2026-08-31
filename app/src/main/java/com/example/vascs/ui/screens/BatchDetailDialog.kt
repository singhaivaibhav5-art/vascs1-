package com.example.vascs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.vascs.data.model.ProductBatchEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.theme.Maroon500
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BatchDetailDialog(
    batch: ProductBatchEntity,
    allProducts: List<ProductEntity>,
    onDismiss: () -> Unit,
    onStatusChange: (ProductBatchEntity, String) -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 0
    }

    // Filter products in this batch
    val batchProducts = allProducts.filter { product ->
        batch.productIdsJson.contains(product.id)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(batch.batchNumber, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)
                        Text(batch.batchName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text("STATUS & MANAGEMENT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { onStatusChange(batch, "ACTIVE") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (batch.status == "ACTIVE") Maroon500 else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Active")
                    }

                    Button(
                        onClick = { onStatusChange(batch, "COMPLETED") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (batch.status == "COMPLETED") Color(0xFF0284C7) else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Completed")
                    }

                    Button(
                        onClick = { onStatusChange(batch, "DRAFT") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (batch.status == "DRAFT") Color(0xFFD97706) else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Draft")
                    }
                }

                HorizontalDivider()

                Text("SAREES IN THIS BATCH (${batchProducts.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Maroon500)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    if (batchProducts.isEmpty()) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No matching product records found for this batch.")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.padding(8.dp)) {
                            items(batchProducts) { product ->
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(product.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(currencyFormatter.format(product.retailPrice), fontWeight = FontWeight.Bold, color = Maroon500)
                                    }
                                    Text("SKU: ${product.sku} • Fabric: ${product.fabric} • Stock: ${product.stock}", fontSize = 11.sp, color = Color.Gray)
                                }
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close Details")
                }
            }
        }
    }
}
