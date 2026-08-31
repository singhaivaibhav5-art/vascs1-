package com.example.vascs.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vascs.data.model.DeliveryEntity
import com.example.vascs.data.model.DispatchEntity
import com.example.vascs.data.model.OrderItemEntity
import com.example.vascs.data.model.OrderMasterEntity
import com.example.vascs.data.model.OrderTrackingEntity
import com.example.vascs.data.model.PackingSlipEntity
import com.example.vascs.data.repository.VascsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDispatchViewModel(
    private val repository: VascsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _statusFilter = MutableStateFlow("ALL")
    val statusFilter: StateFlow<String> = _statusFilter.asStateFlow()

    val orders: StateFlow<List<OrderMasterEntity>> = combine(
        repository.allOrderMasters,
        _searchQuery,
        _statusFilter
    ) { all, query, filter ->
        all.filter { order ->
            val matchesQuery = query.isBlank() ||
                    order.orderNumber.contains(query, ignoreCase = true) ||
                    order.dealerName.contains(query, ignoreCase = true) ||
                    order.mobile.contains(query, ignoreCase = true)
            val matchesFilter = filter == "ALL" || order.status.equals(filter, ignoreCase = true)
            matchesQuery && matchesFilter
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendingOrders: StateFlow<List<OrderMasterEntity>> = repository.allOrderMasters.map { list ->
        list.filter { it.status == "PENDING" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val approvedOrders: StateFlow<List<OrderMasterEntity>> = repository.allOrderMasters.map { list ->
        list.filter { it.status == "APPROVED" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val packingOrders: StateFlow<List<OrderMasterEntity>> = repository.allOrderMasters.map { list ->
        list.filter { it.status == "PACKING" || it.status == "PACKED" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dispatchedOrders: StateFlow<List<OrderMasterEntity>> = repository.allOrderMasters.map { list ->
        list.filter { it.status == "DISPATCHED" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val deliveredOrders: StateFlow<List<OrderMasterEntity>> = repository.allOrderMasters.map { list ->
        list.filter { it.status == "DELIVERED" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cancelledOrders: StateFlow<List<OrderMasterEntity>> = repository.allOrderMasters.map { list ->
        list.filter { it.status == "CANCELLED" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedOrder = MutableStateFlow<OrderMasterEntity?>(null)
    val selectedOrder: StateFlow<OrderMasterEntity?> = _selectedOrder.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setStatusFilter(filter: String) {
        _statusFilter.value = filter
    }

    fun selectOrder(order: OrderMasterEntity?) {
        _selectedOrder.value = order
    }

    fun getOrderItems(orderId: Long): Flow<List<OrderItemEntity>> {
        return repository.getOrderItems(orderId)
    }

    fun getPackingSlip(orderId: Long): Flow<PackingSlipEntity?> {
        return repository.getPackingSlip(orderId)
    }

    fun getDispatch(orderId: Long): Flow<DispatchEntity?> {
        return repository.getDispatch(orderId)
    }

    fun getDelivery(orderId: Long): Flow<DeliveryEntity?> {
        return repository.getDelivery(orderId)
    }

    fun trackOrder(orderId: Long): Flow<List<OrderTrackingEntity>> {
        return repository.trackOrder(orderId)
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(Date())
    }

    fun createOrder(
        dealerId: String,
        dealerName: String,
        mobile: String,
        whatsapp: String,
        items: List<OrderItemEntity>,
        remarks: String = ""
    ) {
        viewModelScope.launch {
            val date = getCurrentDate()
            val orderNum = "ORD-${System.currentTimeMillis().toString().takeLast(6)}"
            val totalQty = items.sumOf { it.qty }
            val totalAmount = items.sumOf { it.amount }
            val gstAmount = items.sumOf { it.gst }
            val netAmount = items.sumOf { it.netAmount }

            val order = OrderMasterEntity(
                orderNumber = orderNum,
                dealerId = dealerId,
                dealerName = dealerName,
                mobile = mobile,
                whatsapp = whatsapp,
                orderDate = date,
                totalItems = items.size,
                totalQty = totalQty,
                totalAmount = totalAmount,
                gstAmount = gstAmount,
                netAmount = netAmount,
                status = "PENDING",
                remarks = remarks,
                createdDate = date,
                updatedDate = date
            )

            repository.saveOrder(order, items)
        }
    }

    fun approveOrder(orderId: Long) {
        viewModelScope.launch {
            repository.approveOrder(orderId, getCurrentDate())
        }
    }

    fun createPackingSlip(
        orderId: Long,
        packingNumber: String,
        boxes: Int,
        itemsCount: Int,
        packedBy: String,
        remarks: String
    ) {
        viewModelScope.launch {
            repository.createPackingSlip(
                orderId = orderId,
                packingNumber = packingNumber,
                boxes = boxes,
                itemsCount = itemsCount,
                packedBy = packedBy,
                remarks = remarks,
                currentDate = getCurrentDate()
            )
        }
    }

    fun createDispatch(
        orderId: Long,
        dispatchNumber: String,
        transport: String,
        lrNumber: String,
        vehicleNumber: String,
        expectedDate: String
    ) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            repository.createDispatch(
                orderId = orderId,
                dispatchNumber = dispatchNumber,
                transport = transport,
                lrNumber = lrNumber,
                vehicleNumber = vehicleNumber,
                dispatchDate = currentDate,
                expectedDate = expectedDate
            )
        }
    }

    fun markDelivered(
        orderId: Long,
        receivedBy: String,
        mobile: String,
        remarks: String,
        proofUri: String
    ) {
        viewModelScope.launch {
            repository.markDelivered(
                orderId = orderId,
                deliveredDate = getCurrentDate(),
                receivedBy = receivedBy,
                mobile = mobile,
                remarks = remarks,
                proofUri = proofUri
            )
        }
    }

    fun cancelOrder(orderId: Long, reason: String) {
        viewModelScope.launch {
            repository.cancelOrder(orderId, reason, getCurrentDate())
        }
    }
}

class OrderDispatchViewModelFactory(private val repository: VascsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDispatchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderDispatchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
