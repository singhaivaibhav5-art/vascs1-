package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.AccountLedgerEntity
import com.example.vascs.data.model.AccountsPayableEntity
import com.example.vascs.data.model.AccountsReceivableEntity
import com.example.vascs.data.model.BalanceSheetReportEntity
import com.example.vascs.data.model.BankBookEntity
import com.example.vascs.data.model.CashBookEntity
import com.example.vascs.data.model.ExpenseRegisterEntity
import com.example.vascs.data.model.GstReportEntity
import com.example.vascs.data.model.ProfitLossReportEntity
import com.example.vascs.data.model.PurchaseRegisterEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FinanceViewModel(private val repository: VascsRepository) : ViewModel() {

    val ledgers: StateFlow<List<AccountLedgerEntity>> = repository.allLedgers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cashBook: StateFlow<List<CashBookEntity>> = repository.allCashBook
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bankBook: StateFlow<List<BankBookEntity>> = repository.allBankBook
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val purchases: StateFlow<List<PurchaseRegisterEntity>> = repository.allPurchases
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expenses: StateFlow<List<ExpenseRegisterEntity>> = repository.allExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val receivables: StateFlow<List<AccountsReceivableEntity>> = repository.allReceivables
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val payables: StateFlow<List<AccountsPayableEntity>> = repository.allPayables
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val gstReports: StateFlow<List<GstReportEntity>> = repository.allGstReports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val profitLoss: StateFlow<List<ProfitLossReportEntity>> = repository.allProfitLossReports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val balanceSheet: StateFlow<List<BalanceSheetReportEntity>> = repository.allBalanceSheetReports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createLedger(ledger: AccountLedgerEntity) {
        viewModelScope.launch {
            repository.createLedger(ledger)
        }
    }

    fun recordPurchase(purchase: PurchaseRegisterEntity) {
        viewModelScope.launch {
            repository.recordPurchase(purchase)
        }
    }

    fun recordExpense(expense: ExpenseRegisterEntity) {
        viewModelScope.launch {
            repository.recordExpense(expense)
        }
    }

    fun recordPayment(dealerId: Long, amount: Double, paymentMode: String, refNumber: String) {
        viewModelScope.launch {
            repository.recordPayment(dealerId, amount, paymentMode, refNumber)
        }
    }

    fun generateProfitLoss(report: ProfitLossReportEntity) {
        viewModelScope.launch {
            repository.generateProfitLoss(report)
        }
    }

    fun generateBalanceSheet(report: BalanceSheetReportEntity) {
        viewModelScope.launch {
            repository.generateBalanceSheet(report)
        }
    }

    fun generateGSTReport(report: GstReportEntity) {
        viewModelScope.launch {
            repository.generateGSTReport(report)
        }
    }

    class Factory(private val repository: VascsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FinanceViewModel::class.java)) {
                return FinanceViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
