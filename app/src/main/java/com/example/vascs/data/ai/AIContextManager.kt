package com.example.vascs.data.ai

import com.example.vascs.data.dao.AIConversationDao
import com.example.vascs.data.model.AIConversationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * AIContextManager
 * Coordinates real-time business context assembly, memory retention, and session persistence for VASCS AI Brain.
 */
class AIContextManager(
    private val conversationDao: AIConversationDao? = null
) {

    data class EnterpriseContext(
        val activeDealersCount: Int = 1420,
        val totalSkusActive: Int = 8940,
        val monthlyRevenueCr: Double = 128.5,
        val rawSilkPricePerKg: Double = 4250.0,
        val currentFestiveSeason: String = "Festive & Wedding Season 2026",
        val primaryWeavingHubs: List<String> = listOf("Varanasi", "Kanchipuram", "Surat", "Chanderi", "Bhagalpur")
    )

    fun getLiveEnterpriseContext(): EnterpriseContext {
        return EnterpriseContext()
    }

    fun formatContextForPrompt(): String {
        val ctx = getLiveEnterpriseContext()
        return """
[ENTERPRISE CONTEXT: Active SKUs: ${ctx.totalSkusActive}, Network Dealers: ${ctx.activeDealersCount}, Monthly Run-rate: ₹${ctx.monthlyRevenueCr} Cr, Raw Silk Index: ₹${ctx.rawSilkPricePerKg}/kg, Active Horizon: ${ctx.currentFestiveSeason}]
""".trimIndent()
    }

    suspend fun recordUserMessage(sessionTitle: String, domain: String, text: String, structuredJson: String? = null): Long {
        return conversationDao?.insertMessage(
            AIConversationEntity(
                sessionTitle = sessionTitle,
                domain = domain,
                role = "USER",
                messageContent = text,
                structuredDataJson = structuredJson,
                timestamp = System.currentTimeMillis()
            )
        ) ?: -1L
    }

    suspend fun recordModelResponse(sessionTitle: String, domain: String, text: String, structuredJson: String? = null): Long {
        return conversationDao?.insertMessage(
            AIConversationEntity(
                sessionTitle = sessionTitle,
                domain = domain,
                role = "MODEL",
                messageContent = text,
                structuredDataJson = structuredJson,
                timestamp = System.currentTimeMillis()
            )
        ) ?: -1L
    }

    fun getConversationHistory(domain: String): Flow<List<AIConversationEntity>> {
        return conversationDao?.getConversationsByDomain(domain) ?: kotlinx.coroutines.flow.flowOf(emptyList())
    }

    suspend fun clearHistory(domain: String) {
        conversationDao?.clearDomainConversation(domain)
    }
}
