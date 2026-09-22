package com.example.inventariosapp.domain.model.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SalesDomain(
    val ventaId: Int?,
    val nombreCliente: String?,
    val folio: String?,
    val subtotal: Double?,
    val descuento: Double?, // ✅ Corregido: Int → Double
    val iva: Double?,
    val total: Double?,
    val montoPagado: Double?,
    val montoPorPagar: Double?,
    val fechaVenta: String?,
    val fechaVentaFormato: String?,
    val estatusVentaId: Int?,
    val estatusVenta: String?,
    val direccion: String?,
    val diasCredito: Int?,
    val fechaLimitePago: String?
) : Parcelable
