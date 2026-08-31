package com.example.vascs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.ui.theme.Maroon500
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditDialog(
    editingProduct: ProductEntity?,
    onDismiss: () -> Unit,
    onSave: (ProductEntity) -> Unit
) {
    var name by remember { mutableStateOf(editingProduct?.name ?: "") }
    var sku by remember { mutableStateOf(editingProduct?.sku ?: "SKU-${System.currentTimeMillis().toString().takeLast(6)}") }
    var barcode by remember { mutableStateOf(editingProduct?.barcode ?: "890${System.currentTimeMillis().toString().takeLast(9)}") }
    var category by remember { mutableStateOf(editingProduct?.category ?: "Banarasi") }
    var brand by remember { mutableStateOf(editingProduct?.brand ?: "VASCS Signature") }
    var fabric by remember { mutableStateOf(editingProduct?.fabric ?: "Pure Silk") }
    var colour by remember { mutableStateOf(editingProduct?.colour ?: "Red") }
    var size by remember { mutableStateOf(editingProduct?.size ?: "6.3m with Blouse") }
    var hsn by remember { mutableStateOf(editingProduct?.hsn ?: "5407") }
    var gst by remember { mutableStateOf(editingProduct?.gst?.toString() ?: "5.0") }
    var purchasePrice by remember { mutableStateOf(editingProduct?.purchasePrice?.toString() ?: "5000") }
    var wholesalePrice by remember { mutableStateOf(editingProduct?.wholesalePrice?.toString() ?: "6500") }
    var retailPrice by remember { mutableStateOf(editingProduct?.retailPrice?.toString() ?: "8500") }
    var mrp by remember { mutableStateOf(editingProduct?.mrp?.toString() ?: "12000") }
    var stock by remember { mutableStateOf(editingProduct?.stock?.toString() ?: "10") }
    var image by remember { mutableStateOf(editingProduct?.image ?: "") }

    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    val categoriesList = listOf("Banarasi", "Silk", "Chiffon", "Georgette", "Organza", "Cotton", "Paithani", "Kanjeevaram", "Tissue", "Satin")

    var errorMessage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (editingProduct != null) "Edit Saree Product" else "New Saree Entry",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Maroon500
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                if (errorMessage.isNotBlank()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Saree Basic Info
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; errorMessage = "" },
                    label = { Text("Product / Saree Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ExposedDropdownMenuBox(
                        expanded = categoryDropdownExpanded,
                        onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Category *") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = categoryDropdownExpanded,
                            onDismissRequest = { categoryDropdownExpanded = false }
                        ) {
                            categoriesList.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        category = cat
                                        categoryDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = brand,
                        onValueChange = { brand = it },
                        label = { Text("Brand") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = sku,
                        onValueChange = { sku = it },
                        label = { Text("SKU Code") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = barcode,
                        onValueChange = { barcode = it },
                        label = { Text("Barcode Number") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = fabric,
                        onValueChange = { fabric = it },
                        label = { Text("Fabric Type") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = colour,
                        onValueChange = { colour = it },
                        label = { Text("Colour") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = hsn,
                        onValueChange = { hsn = it },
                        label = { Text("HSN Code") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = gst,
                        onValueChange = { gst = it },
                        label = { Text("GST (%)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                // Pricing Section
                Text("PRICING & STOCK (₹)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Maroon500)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = purchasePrice,
                        onValueChange = { purchasePrice = it },
                        label = { Text("Purchase Price") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = wholesalePrice,
                        onValueChange = { wholesalePrice = it },
                        label = { Text("Wholesale Price") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = retailPrice,
                        onValueChange = { retailPrice = it },
                        label = { Text("Retail Price *") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = mrp,
                        onValueChange = { mrp = it },
                        label = { Text("MRP") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock Quantity (Pcs) *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text("Image URL (Optional)") },
                    placeholder = { Text("https://example.com/saree.jpg") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            if (name.isBlank()) {
                                errorMessage = "Product Name is required."
                                return@Button
                            }
                            val parsedPurchase = purchasePrice.toDoubleOrNull() ?: 0.0
                            val parsedRetail = retailPrice.toDoubleOrNull() ?: 0.0
                            val parsedMrp = mrp.toDoubleOrNull() ?: parsedRetail
                            val parsedStock = stock.toIntOrNull() ?: 0

                            val product = ProductEntity(
                                id = editingProduct?.id ?: UUID.randomUUID().toString(),
                                name = name.trim(),
                                sku = sku.trim(),
                                barcode = barcode.trim(),
                                category = category.trim(),
                                brand = brand.trim(),
                                fabric = fabric.trim(),
                                colour = colour.trim(),
                                size = size.trim(),
                                hsn = hsn.trim(),
                                gst = gst.toDoubleOrNull() ?: 5.0,
                                purchasePrice = parsedPurchase,
                                wholesalePrice = wholesalePrice.toDoubleOrNull() ?: parsedPurchase,
                                retailPrice = parsedRetail,
                                mrp = parsedMrp,
                                discount = if (parsedMrp > 0) ((parsedMrp - parsedRetail) / parsedMrp) * 100 else 0.0,
                                stock = parsedStock,
                                image = image.trim(),
                                createdAt = editingProduct?.createdAt ?: System.currentTimeMillis().toString()
                            )

                            onSave(product)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Maroon500),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (editingProduct != null) "Update Saree" else "Save Saree", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
