package com.example.inventariosapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.inventariosapp.database.entity.PostSaleEntity
import com.example.inventariosapp.database.entity.PostSaleProductEntity
import com.example.inventariosapp.database.entity.PostSaleWithProducts

@Dao
interface PostSalesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale: PostSaleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaleProducts(products: List<PostSaleProductEntity>): List<Long>

    @Transaction
    @Query("SELECT * FROM post_sales")
    suspend fun getAllSales(): List<PostSaleWithProducts>

    @Transaction
    @Query("SELECT * FROM post_sales WHERE id = :id")
    suspend fun getSaleById(id: String): PostSaleWithProducts?

    @Query("DELETE FROM post_sales")
    suspend fun clearSales(): Int

    @Query("DELETE FROM post_sales WHERE id = :id")
    suspend fun deleteSaleById(id: String): Int

<<<<<<<< Updated upstream:app/src/main/java/com/example/inventariosapp/database/dao/PostSalesDao.kt
    @Query("UPDATE post_sales SET estatusVentaId = :newStatus WHERE id = :id")
    suspend fun updateSaleStatusById(id: String, newStatus: Int): Int
}
========
    @Query("DELETE FROM post_sale_products")
    suspend fun deleteAllProducts(): Int

    @Query("DELETE FROM post_sales")
    suspend fun deleteAllSales(): Int

    @Query("UPDATE post_sales SET estatusVentaId = :newStatus WHERE id = :id")
    suspend fun updateSaleStatusById(id: String, newStatus: Int): Int

    @Query("""
    SELECT COUNT(*)
    FROM post_sale_products
""")
    suspend fun getTotalProducts(): Int

    @Transaction
    suspend fun deleteAllAndReload(): List<PostSaleWithProducts> {
        deleteAllProducts()
        deleteAllSales()
        return getAllSales()
    }
}

>>>>>>>> Stashed changes:app/src/main/java/com/example/inventariosapp/local/dao/PostSalesDao.kt
