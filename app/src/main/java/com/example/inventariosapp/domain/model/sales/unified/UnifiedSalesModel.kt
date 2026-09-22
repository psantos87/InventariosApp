package com.example.inventariosapp.domain.model.sales.unified

data class UnifiedSalesModel(
    @JvmField var id: Long = 0,
    @JvmField var clientId: Long? = null,
    @JvmField var clientName: String? = null,
    @JvmField var tableName: String? = null,
    @JvmField var tableNumber: Int? = null,
    @JvmField var waiterId: Long? = null,
    @JvmField var waiterName: String? = null,
    @JvmField var totalProducts: Double? = null,
    @JvmField var discountPercentage: Double? = null,
    @JvmField var discountAmount: Double? = null,
    @JvmField var subtotal: Double? = null,
    @JvmField var taxPercentage: Double? = null,
    @JvmField var taxAmount: Double? = null,
    @JvmField var total: Double? = null,
    @JvmField var paymentMethodId: Long? = null,
    @JvmField var paymentMethodName: String? = null,
    @JvmField var cashReceived: Double? = null,
    @JvmField var changeGiven: Double? = null,
    @JvmField var isActive: Boolean? = null,
    @JvmField var createdAt: String? = null,
    @JvmField var updatedAt: String? = null,

    // From entity - additional database fields
    @JvmField var productId: Long? = null,
    @JvmField var productName: String? = null,
    @JvmField var productPrice: Double? = null,
    @JvmField var quantity: Int? = null,
    @JvmField var category: String? = null,
    @JvmField var departmentId: Long? = null,
    @JvmField var userId: Long? = null,
    @JvmField var userName: String? = null
) {
}