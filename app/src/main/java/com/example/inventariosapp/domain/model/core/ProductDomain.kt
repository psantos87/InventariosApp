package com.example.inventariosapp.domain.model.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDomain(
    val productoId: Int?,
    val codigo: String?,
    val departamento: String?,
    val descripcion: String?,
    val descripcionPresentacion: String?,
    val estatus: String?,
    val esActivo: Boolean?,
    val costo: Double?,
    val precioVenta1: Double?,
    val precioVenta2: Double?,
    val precioVenta3: Double?,
    val precioVenta4: Double?,
    val nombreUnidadMedida: String
) : Parcelable
