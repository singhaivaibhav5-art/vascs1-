package com.example.vascs.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.BroadcastCampaignEntity
import com.example.vascs.data.model.CustomerLeadEntity
import com.example.vascs.data.model.FollowupEntity
import com.example.vascs.data.model.ProductEntity
import com.example.vascs.data.model.QuotationEntity
import com.example.vascs.data.model.WhatsappTemplateEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class SalesDashboardMetrics(
    val newLeadsCount: Int = 0,
    val activeLeadsCount: Int = 0,
    val quotationsCount: Int = 0,
    val confirmedOrdersCount: Int = 0,
    val lostLeadsCount: Int = 0,
    val totalLeadsCount: Int = 0,
    val conversionRate: Double = 0.0,
    val totalQuotationValue: Double = 0.0
)

class WhatsappCommerceViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    val leads: StateFlow<List<CustomerLeadEntity>> = repository.allLeads
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val quotations: StateFlow<List<QuotationEntity>> = repository.allQuotations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val followups: StateFlow<List<FollowupEntity>> = repository.allFollowups
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val templates: StateFlow<List<WhatsappTemplateEntity>> = repository.allTemplates
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val campaigns: StateFlow<List<BroadcastCampaignEntity>> = repository.allCampaigns
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val products: StateFlow<List<ProductEntity>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val salesDashboard: StateFlow<SalesDashboardMetrics> = combine(leads, quotations) { leadList, quoteList ->
        val total = leadList.size
        val newLeads = leadList.count { it.status == "NEW" }
        val active = leadList.count { it.status in listOf("CONTACTED", "FOLLOWUP") }
        val qSent = leadList.count { it.status == "QUOTATION_SENT" }
        val confirmed = leadList.count { it.status == "ORDER_CONFIRMED" }
        val lost = leadList.count { it.status == "LOST" }
        val conversion = if (total > 0) (confirmed.toDouble() / total.toDouble()) * 100.0 else 0.0
        val totalQValue = quoteList.sumOf { it.netAmount }

        SalesDashboardMetrics(
            newLeadsCount = newLeads,
            activeLeadsCount = active,
            quotationsCount = quoteList.size,
            confirmedOrdersCount = confirmed,
            lostLeadsCount = lost,
            totalLeadsCount = total,
            conversionRate = conversion,
            totalQuotationValue = totalQValue
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SalesDashboardMetrics())

    init {
        viewModelScope.launch {
            val currentDate = dateFormat.format(Date())
            repository.seedDefaultTemplatesIfEmpty(currentDate)
        }
    }

    fun saveLead(
        customerName: String,
        mobile: String,
        whatsapp: String,
        city: String,
        state: String,
        source: String,
        interestedProduct: String,
        remarks: String,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            val currentDate = dateFormat.format(Date())
            val lead = CustomerLeadEntity(
                customerName = customerName.ifBlank { "Valued Customer" },
                mobile = mobile,
                whatsapp = whatsapp.ifBlank { mobile },
                city = city,
                state = state,
                source = source.ifBlank { "WhatsApp Studio" },
                interestedProduct = interestedProduct,
                status = "NEW",
                remarks = remarks,
                createdDate = currentDate
            )
            repository.saveLead(lead)
            onSuccess()
        }
    }

    fun updateLeadStatus(leadId: Long, status: String) {
        viewModelScope.launch {
            repository.updateLeadStatus(leadId, status)
        }
    }

    fun generateQuotation(
        leadId: Long,
        customerName: String,
        mobile: String,
        productsJson: String,
        totalQty: Int,
        totalAmount: Double,
        gstRatePercent: Double,
        validityDays: Int,
        onSuccess: (QuotationEntity) -> Unit = {}
    ) {
        viewModelScope.launch {
            val currentDate = dateFormat.format(Date())
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, validityDays)
            val validityDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(cal.time)
            val quotationNo = "QUO-" + (1000..9999).random()

            val gstAmount = totalAmount * (gstRatePercent / 100.0)
            val netAmount = totalAmount + gstAmount

            val quotation = QuotationEntity(
                quotationNo = quotationNo,
                leadId = leadId,
                customerName = customerName,
                mobile = mobile,
                productsJson = productsJson,
                totalQty = totalQty,
                totalAmount = totalAmount,
                gstAmount = gstAmount,
                netAmount = netAmount,
                validityDate = validityDate,
                createdDate = currentDate,
                status = "SENT"
            )

            val id = repository.saveQuotation(quotation)
            val savedQuotation = quotation.copy(quotationId = id)
            onSuccess(savedQuotation)
        }
    }

    fun scheduleFollowup(
        leadId: Long,
        customerName: String,
        mobile: String,
        reminderType: String, // 1 Day, 3 Days, 7 Days, 15 Days
        notes: String,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            val currentDate = dateFormat.format(Date())
            val cal = Calendar.getInstance()
            val daysToAdd = when (reminderType) {
                "1 Day" -> 1
                "3 Days" -> 3
                "7 Days" -> 7
                "15 Days" -> 15
                else -> 1
            }
            cal.add(Calendar.DAY_OF_YEAR, daysToAdd)
            val dueDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(cal.time)

            val followup = FollowupEntity(
                leadId = leadId,
                customerName = customerName,
                mobile = mobile,
                reminderType = reminderType,
                dueDate = dueDate,
                notes = notes,
                status = "PENDING",
                createdDate = currentDate
            )
            repository.scheduleFollowup(followup)
            repository.updateLeadStatus(leadId, "FOLLOWUP")
            onSuccess()
        }
    }

    fun updateFollowupStatus(followupId: Long, status: String) {
        viewModelScope.launch {
            repository.updateFollowupStatus(followupId, status)
        }
    }

    fun sendBroadcast(
        campaignName: String,
        targetSegment: String,
        targetCount: Int,
        templateUsed: String,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            val currentDate = dateFormat.format(Date())
            val campaign = BroadcastCampaignEntity(
                campaignName = campaignName,
                targetSegment = targetSegment,
                targetCount = targetCount,
                sentCount = targetCount,
                deliveredCount = (targetCount * 0.96).toInt(),
                templateUsed = templateUsed,
                createdDate = currentDate,
                status = "COMPLETED"
            )
            repository.sendBroadcast(campaign)
            onSuccess()
        }
    }

    fun shareWhatsAppMessage(context: Context, phone: String, message: String) {
        try {
            val cleanPhone = phone.replace("+", "").replace(" ", "").trim()
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanPhone&text=${Uri.encode(message)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            context.startActivity(Intent.createChooser(intent, "Share via WhatsApp"))
        }
    }
}

class WhatsappCommerceViewModelFactory(
    private val repository: VascsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WhatsappCommerceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WhatsappCommerceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
