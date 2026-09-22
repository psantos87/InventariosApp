package com.example.inventariosapp.domain.repository.sales

import android.util.Log
import com.example.inventariosapp.MainActivity
import com.example.inventariosapp.api.ApiService
import com.example.inventariosapp.local.dao.PostSalesDao
import com.example.inventariosapp.domain.model.error.ErrorModel
import com.example.inventariosapp.domain.model.sales.PostSalesModel
import com.example.inventariosapp.domain.model.sales.toEntity
import com.example.inventariosapp.session.SessionManager
import com.example.inventariosapp.util.NetworkMonitor
import com.google.gson.Gson
import javax.inject.Inject

class PostSaleRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val postSalesDao: PostSalesDao,
    private val sessionManager: SessionManager,
    private val networkMonitor: NetworkMonitor
) {

    /**
     * Estrategia: LOCAL-FIRST
     * 1. Guarda SIEMPRE en base de datos primero.
     * 2. Si hay internet, intenta subir a la red.
     * 3. Si la red falla, los datos ya estan seguros en local.
     */
    suspend operator fun invoke(sales: List<PostSalesModel>?, internetUse: Boolean): Pair<Unit?, String?> {
        if (sales.isNullOrEmpty()) {
            return Pair(Unit, null)
        }
        if (networkMonitor.isConnected.value && internetUse) {
            val networkResult = fetchFromNetwork(sales)
            return networkResult
        }
        else{
            val localResult = fetchFromLocal(sales)
            if (localResult.second != null) {
                return localResult
            }
        }
        return Pair(Unit, null)
    }

    /**
     * Inserta los datos en la base de datos local.
     */
    private suspend fun fetchFromLocal(sales: List<PostSalesModel>?): Pair<Unit?, String?> {
        return try {
            sales?.forEach { saleModel ->
                val saleEntity = saleModel.toEntity()
                val productsEntity = saleModel.ventaProductos.map { product ->
                    product.toEntity(parentId = saleEntity.id)
                }
                postSalesDao.insertSaleWithProducts(
                    sale = saleEntity,
                    products = productsEntity
                )
                Log.d("PRODUCTS_BY_SALE", "Total: ${saleEntity.id}")
            }
            Pair(Unit, null)
        } catch (e: Exception) {
            Log.e("PostSaleRepo", "Error crítico al guardar en local", e)
            MainActivity.mainDialogMsg.value = "Error crítico: No se pudo guardar la venta en el dispositivo. ${e.message}"
            MainActivity.mainDialog.value = true
            Pair(null, "Error de almacenamiento local: ${e.message}")
        }
    }

    /**
     * Envía los datos a la red.
     */
    private suspend fun fetchFromNetwork(sales: List<PostSalesModel>?): Pair<Unit?, String?> {
        val payload = ArrayList(sales ?: emptyList())

        if (!sessionManager.isSessionValid()) {
            MainActivity.mainDialogMsg.value = "Sesión expirada, favor de iniciar sesión"
            MainActivity.mainDialog.value = true
            return Pair(null, "Sesión expirada, favor de iniciar sesión")
        }

        return try {
            val response = apiService.postSale(payload)

            if (response.isSuccessful) {
                // Éxito total
                Pair(Unit, null)
            }
            else {
                // Error del servidor (ej. 400, 500)
                val errorJson = response.errorBody()?.string()
                val error = errorJson?.let { Gson().fromJson(it, ErrorModel::class.java) }
                    ?: ErrorModel(error("Error desconocido del servidor"))

                MainActivity.mainDialogMsg.value = error.MsgError.toString()
                MainActivity.mainDialog.value = true

                // Aclaración: Los datos YA están en local gracias al Paso 1.
                Pair(null, "Error en el servidor, pero la venta fue guardada localmente.")
            }
        } catch (e: Exception) {
            // Error de red (Timeout, Sin internet, etc.)
            MainActivity.mainDialogMsg.value = "Error de conexión: ${e.message}. Venta guardada localmente."
            MainActivity.mainDialog.value = true

            // Aclaración: Los datos YA están en local gracias a la Paso 1.
            Pair(null, "Error de conexión, venta guardada localmente.")
        }
    }
}
