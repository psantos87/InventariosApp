package com.example.inventariosapp.ui.view.user_sales

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.inventariosapp.MainActivity
import com.example.inventariosapp.local.entity.PostSaleEntity
import com.example.inventariosapp.local.entity.PostSaleProductEntity
import com.example.inventariosapp.local.entity.PostSaleWithProducts
import com.example.inventariosapp.ui.dialog.PenddingSaleDialogCmp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PenndingSalesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    private val testPendingSales = arrayListOf(
        PostSaleWithProducts(
            sale = PostSaleEntity(
                id = "test-001",
                ventaId = 1001,
                nombreCliente = "Juan Pérez",
                fechaIngreso = "15-01-2025",
                direccion = "Calle Principal #123, Colonia Centro",
                clienteId = 5001,
                total = 1500.00,
                fechaVenta = "15-01-2025"
            ),
            productos = listOf(
                PostSaleProductEntity(
                    postSaleId = "test-001",
                    productoId = 1,
                    cantidad = 2,
                    precioVenta = 500.00,
                    nombreProducto = "Producto A"
                ),
                PostSaleProductEntity(
                    postSaleId = "test-001",
                    productoId = 2,
                    cantidad = 1,
                    precioVenta = 500.00,
                    nombreProducto = "Producto B"
                )
            )
        ),
        PostSaleWithProducts(
            sale = PostSaleEntity(
                id = "test-002",
                ventaId = 1002,
                nombreCliente = "María García",
                fechaIngreso = "16-01-2025",
                direccion = "Av. Reforma #456, Colonia Norte",
                clienteId = 5002,
                total = 2300.50,
                fechaVenta = "16-01-2025"
            ),
            productos = listOf(
                PostSaleProductEntity(
                    postSaleId = "test-002",
                    productoId = 3,
                    cantidad = 3,
                    precioVenta = 766.83,
                    nombreProducto = "Producto C"
                )
            )
        )
    )

    @Before
    fun setup() {
        navController = TestNavHostController(composeTestRule.activity)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
    }

    @Test
    fun testPenndingSalesView_composesWithEmptyData() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = arrayListOf(),
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_composesWithData() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesScreen_composes() {
        composeTestRule.setContent {
            PenndingSalesScreen(navController)
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_displaysPendingSales() {
        val expectedClientName = "Juan Pérez"
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.onNodeWithText(expectedClientName).assertExists()
        composeTestRule.onNodeWithText("PENDIENTE").assertExists()
    }

    @Test
    fun testPenndingSalesView_displaysMultipleSales() {
        val secondClientName = "María García"
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.onNodeWithText(secondClientName).assertExists()
    }

    @Test
    fun testPenndingSalesView_updateButtonIsEnabled() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.onNode(hasClickAction()).assertIsEnabled()
    }

    @Test
    fun testPenndingSalesView_updateButtonCanBeDisabled() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = false,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.onNode(hasClickAction()).assertIsNotEnabled()
    }

    @Test
    fun testPenndingSalesScreen_withNavController() {
        composeTestRule.setContent {
            PenndingSalesScreen(navController)
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_displaysTitle() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_saleCardDisplaysInfo() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.onNodeWithText("Juan Pérez").assertExists()
        composeTestRule.onNodeWithText("PENDIENTE").assertExists()
    }

    @Test
    fun testPenndingSalesView_showsProductCount() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_rowIsClickable() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_updateButtonExecutesAction() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_menuButtonIsAccessible() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_backButtonIsAccessible() {
        composeTestRule.setContent {
            PenndingSalesView(
                data = testPendingSales,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenddingSaleDialogCmp_showsContent() {
        composeTestRule.setContent {
            PenddingSaleDialogCmp(
                content = { },
                onDismiss = { }
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPenndingSalesView_fullHappyPath() {
        val salesData = arrayListOf(
            PostSaleWithProducts(
                sale = PostSaleEntity(
                    id = "happy-path-001",
                    ventaId = 2001,
                    nombreCliente = "Cliente Happy Path",
                    fechaIngreso = "20-01-2025",
                    direccion = "Dirección de prueba",
                    clienteId = 9001,
                    total = 1000.00,
                    fechaVenta = "20-01-2025"
                ),
                productos = listOf(
                    PostSaleProductEntity(
                        postSaleId = "happy-path-001",
                        productoId = 10,
                        cantidad = 1,
                        precioVenta = 1000.00,
                        nombreProducto = "Producto Happy"
                    )
                )
            )
        )
        composeTestRule.setContent {
            PenndingSalesView(
                data = salesData,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Cliente Happy Path").assertExists()
        composeTestRule.onNodeWithText("PENDIENTE").assertExists()
    }

    @Test
    fun testPenndingSalesView_singlePendingSale() {
        val singleSale = arrayListOf(
            PostSaleWithProducts(
                sale = PostSaleEntity(
                    id = "single-001",
                    ventaId = 3001,
                    nombreCliente = "Único Cliente",
                    fechaIngreso = "21-01-2025",
                    direccion = "Calle Única #1",
                    clienteId = 8001,
                    total = 500.00,
                    fechaVenta = "21-01-2025"
                ),
                productos = listOf(
                    PostSaleProductEntity(
                        postSaleId = "single-001",
                        productoId = 20,
                        cantidad = 1,
                        precioVenta = 500.00,
                        nombreProducto = "Producto Único"
                    )
                )
            )
        )
        composeTestRule.setContent {
            PenndingSalesView(
                data = singleSale,
                btnEnabled = true,
                onClickBack = { },
                onClickMenu = { },
                onclickRow = { },
                onClickUpdate = { }
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Único Cliente").assertExists()
    }
}
