package com.example.vascs.util

import com.example.vascs.data.model.ProductEntity

enum class AiDrapingStyle(val label: String, val description: String) {
    BRIDAL("Bridal Showroom", "Royal grand showroom setting with chandelier ambient lighting and bridal pose"),
    FESTIVE("Festive Collection", "Warm festive celebration ambiance with subtle floral decor backdrop"),
    PARTY_WEAR("Party Wear Glamour", "Modern sleek studio lighting suited for contemporary party sarees"),
    TRADITIONAL("Traditional Heritage", "Classic Indian heritage architectural courtyard setting"),
    OFFICE_WEAR("Office & Casual Wear", "Clean minimalist high-key studio setting for daily and formal wear")
}

enum class AiGenerationStatus {
    IDLE, QUEUED, PROCESSING, SUCCESS, FAILED, CANCELLED
}

data class AiModelConfig(
    val modelId: String,
    val displayName: String,
    val provider: String = "Gemini Vision Enterprise",
    val description: String,
    val supportedStyles: List<AiDrapingStyle>,
    val supportedResolutions: List<String> = listOf("1024x1536", "1024x1024"),
    val enabled: Boolean = true
)

data class AiGenerationRequest(
    val productId: String,
    val sourceImageUri: String,
    val style: AiDrapingStyle = AiDrapingStyle.BRIDAL,
    val modelId: String = "model-standard-01",
    val backgroundStyle: String = "Showroom Studio",
    val pose: String = "Standing Elegance",
    val resolution: String = "1024x1536",
    val customPrompt: String? = null,
    val customNegativePrompt: String? = null
)

data class AiGenerationResult(
    val id: String,
    val productId: String,
    val generatedImageUri: String?,
    val status: AiGenerationStatus,
    val progress: Int = 0,
    val errorMessage: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

interface SareeAiProvider {
    suspend fun generateCatalogueImage(request: AiGenerationRequest): AiGenerationResult
}

object ModelLibrary {
    val defaultModels = listOf(
        AiModelConfig(
            modelId = "model-standard-01",
            displayName = "Standard Indian Catalogue Model 01",
            description = "Balanced ethnic posture ideal for all fabric types and weave patterns.",
            supportedStyles = AiDrapingStyle.entries
        ),
        AiModelConfig(
            modelId = "model-royal-02",
            displayName = "Royal Heritage Model 02",
            description = "Elegant posture specializing in heavy Banarasi and Kanjeevaram silk sarees.",
            supportedStyles = listOf(AiDrapingStyle.BRIDAL, AiDrapingStyle.TRADITIONAL, AiDrapingStyle.FESTIVE)
        ),
        AiModelConfig(
            modelId = "model-modern-03",
            displayName = "Contemporary Glamour Model 03",
            description = "Dynamic fashion pose tailored for Organza, Chiffon, and Georgette printed sarees.",
            supportedStyles = listOf(AiDrapingStyle.PARTY_WEAR, AiDrapingStyle.OFFICE_WEAR)
        )
    )
}

object PromptTemplateBuilder {
    fun buildPrompt(product: ProductEntity?, style: AiDrapingStyle, customPrompt: String?): String {
        val base = customPrompt?.takeIf { it.isNotBlank() } ?: "Professional studio saree catalogue photo"
        val productDetails = if (product != null) {
            "Saree Name: ${product.name}, Fabric: ${product.fabric}, Colour: ${product.colour}, Category: ${product.category}."
        } else ""

        val styleInstruction = when (style) {
            AiDrapingStyle.BRIDAL -> "Grand royal bridal saree draping with intricate zardosi border highlights and chandelier lighting."
            AiDrapingStyle.FESTIVE -> "Festive celebration ambiance with warm golden light accentuating the pallu weave."
            AiDrapingStyle.PARTY_WEAR -> "Sleek high-fashion studio lighting showcasing light fabric fall and pleat flow."
            AiDrapingStyle.TRADITIONAL -> "Traditional temple architecture courtyard backdrop highlighting rich silk texture."
            AiDrapingStyle.OFFICE_WEAR -> "Clean, crisp, modern executive lighting with neat pleat draping."
        }

        return "$base. $productDetails $styleInstruction High resolution, photorealistic, 8k quality saree catalogue showcase."
    }

    fun defaultNegativePrompt(): String {
        return "blurry, low resolution, distorted fabric, unnatural pleats, extra limbs, bad anatomy, disfigured face, oversaturated colours."
    }
}

class SareeCatalogueEngine : SareeAiProvider {

    override suspend fun generateCatalogueImage(request: AiGenerationRequest): AiGenerationResult {
        // Default local client fallback engine abstraction
        return AiGenerationResult(
            id = "ai-gen-${System.currentTimeMillis()}",
            productId = request.productId,
            generatedImageUri = request.sourceImageUri,
            status = AiGenerationStatus.SUCCESS,
            progress = 100,
            errorMessage = null
        )
    }
}
