package com.example.inventariosapp.api

import com.example.inventariosapp.model.client.ClientResponseModel
import com.example.inventariosapp.model.login.LoginResponseModel
import com.example.inventariosapp.model.payment.PayModel
import com.example.inventariosapp.model.product.InventarioRseponeModel
import com.example.inventariosapp.model.product.ProductIdResponseModel
import com.example.inventariosapp.model.product.ProductsResponseModel
import com.example.inventariosapp.model.sales.GetPaymentResponseModel
import com.example.inventariosapp.model.sales.GetSalesByIdResponse
import com.example.inventariosapp.model.sales.PostSalesModel
import com.example.inventariosapp.model.sales.SalesModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // region login
<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/usuario/{user},{password}")
=======
    @GET("${Constants.CASA_JORDAN}/Api/usuario/{user},{password}")
>>>>>>> Stashed changes
    suspend fun validateUser(
        @Path("user") user: String,
        @Path("password") password: String
    ): Response<LoginResponseModel>
    // endregion
    // region productos
<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/Producto")
=======
    @GET("${Constants.CASA_JORDAN}/Api/Producto")
>>>>>>> Stashed changes
    suspend fun getProducts(
        @Query("EsActivo") esActivo: Boolean = true
    ): Response<List<ProductsResponseModel>>

<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/Producto/GetByIdData")
=======
    @GET("${Constants.CASA_JORDAN}/Api/Producto/GetByIdData")
>>>>>>> Stashed changes
    suspend fun getProductId(
        @Query("productoId") productId: Int
    ): Response<ProductIdResponseModel>

<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/Inventario")
=======
    @GET("${Constants.CASA_JORDAN}/Api/Inventario")
>>>>>>> Stashed changes
    suspend fun getInventario(
        @Query("EsActivo") esActivo: Boolean = true
    ): Response<List<InventarioRseponeModel>>
    // endregion


    // region clientes
<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/Cliente")
=======
    @GET("${Constants.CASA_JORDAN}/Api/Cliente")
>>>>>>> Stashed changes
    suspend fun getClient(
        @Query("EsActivo") esActivo: Boolean = true
    ): Response<List<ClientResponseModel>>
    // endregion


    // region payment
<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/TipoPago")
=======
    @GET("${Constants.CASA_JORDAN}/Api/TipoPago")
>>>>>>> Stashed changes
    suspend fun getPaymentMethod(
        @Query("EsActivo") esActivo: Boolean = true
    ): Response<List<GetPaymentResponseModel>>

<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/VentaPago/{pagoId}")
=======
    @GET("${Constants.CASA_JORDAN}/Api/VentaPago/{pagoId}")
>>>>>>> Stashed changes
    suspend fun getPaymentById(
        @Path("pagoId") pagoId: String
    ): Response<GetPaymentResponseModel>

<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/VentaPago")
=======
    @GET("${Constants.CASA_JORDAN}/Api/VentaPago")
>>>>>>> Stashed changes
    suspend fun getPayment(
        @Query("VentaID") ventaID: String,
        @Query("EsActivo") esActivo: Boolean = true
    ): Response<List<PayModel>>

<<<<<<< Updated upstream
    @POST("InventariosApi.QA/Api/VentaPago")
=======
    @POST("${Constants.CASA_JORDAN}/Api/VentaPago")
>>>>>>> Stashed changes
    suspend fun setPayment(
        @Query("VentaID") ventaID: String,
        @Query("montoPago") montoPago: String,
        @Query("fecha") fecha: String,
        @Query("observaciones") observaciones: String,
        @Query("origenId") origenId: Int,
        @Query("tipoConexionId") tipoConexionId: Int,
        @Query("usuarioSesionId") usuarioSesionId: Int
    ): Response<Unit>

<<<<<<< Updated upstream
    @DELETE("InventariosApi.QA/Api/VentaPago/{pagoId}")
=======
    @DELETE("${Constants.CASA_JORDAN}/Api/VentaPago/{pagoId}")
>>>>>>> Stashed changes
    suspend fun deletePayment(
        @Path("pagoId") pagoId: Int
    ): Response<Unit>
    // endregion


    // region ventas
<<<<<<< Updated upstream
    @POST("InventariosApi.QA/Api/Venta")
=======
    @POST("${Constants.CASA_JORDAN}/Api/Venta")
>>>>>>> Stashed changes
    suspend fun postSale(
        @Body sales: List<PostSalesModel>
    ): Response<Unit>

<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/Venta/{saleId}")
=======
    @GET("${Constants.CASA_JORDAN}/Api/Venta/{saleId}")
>>>>>>> Stashed changes
    suspend fun getSalesById(
        @Path("saleId") saleId: String
    ): Response<GetSalesByIdResponse>

<<<<<<< Updated upstream
    @PUT("InventariosApi.QA/Api/Venta/{ventaId}")
    suspend fun editSale(
        @Path("ventaId") ventaId: String,
        @Body venta: GetSalesByIdResponse
    ): Response<Unit>

    @GET("InventariosApi.QA/Api/Venta")
=======
    @PUT("${Constants.CASA_JORDAN}/Api/Venta/{ventaId}")
    suspend fun editSale(@Body venta: GetSalesByIdResponse, @Path("ventaId") ventaId: String) : Response<Unit>

    @GET("${Constants.CASA_JORDAN}/Api/Venta")
>>>>>>> Stashed changes
    suspend fun getSalesInProcess(
        @Query("esActivo") esActivo: Boolean = true,
        @Query("EstatusVentaIds") statusSales: String,
        @Query("fechaInicio") startDate: String,
        @Query("fechaFin") endDate: String
    ): Response<List<SalesModel>>

<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/Venta")
=======
    @GET("${Constants.CASA_JORDAN}/Api/Venta")
>>>>>>> Stashed changes
    suspend fun getPendingSales(
        @Query("esActivo") esActivo: Boolean = true,
        @Query("EstatusVentaIds") estatusVentaIds: String = "1,2",
        @Query("fechaInicio") fechaInicio: String,
        @Query("fechaFin") fechaFin: String
    ): Response<List<SalesModel>>

<<<<<<< Updated upstream
    @GET("InventariosApi.QA/Api/VentaPago")
=======
    @GET("${Constants.CASA_JORDAN}/Api/VentaPago")
>>>>>>> Stashed changes
    suspend fun getSalePayments(
        @Query("VentaId") pagoId: String,
        @Query("EsActivo") esActivo: Boolean = true
    ): Response<List<GetPaymentResponseModel>>
    // endregion
}