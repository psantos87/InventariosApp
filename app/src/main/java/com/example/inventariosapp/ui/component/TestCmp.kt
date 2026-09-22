package com.yourpackage.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.appgeneric.ui.component.TextCmp
import com.example.inventariosapp.ui.component.cards.CardSellProductCmp
import com.example.inventariosapp.ui.theme.PADDING_16
import com.example.inventariosapp.ui.theme.PADDING_4
import com.example.inventariosapp.ui.theme.PADDING_8
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// ─────────────────────────────────────────────
//  GlassCard
// ─────────────────────────────────────────────

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(28.dp),
    contentPadding: PaddingValues = PaddingValues(24.dp),
    content: @Composable BoxScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // ── Animation states ──────────────────────
    var isCracking by remember { mutableStateOf(false) }
    var crackProgress by remember { mutableStateOf(0f) }
    var shakeOffset by remember { mutableStateOf(0f) }

    val scaleAnim = remember { Animatable(1f) }
    val crackAnim = remember { Animatable(0f) }
    val shakeAnim = remember { Animatable(0f) }

    // ── Shimmer ───────────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = -0.4f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerProgress"
    )

    // ── Crack lines (generated once per press) ──
    val crackLines = remember { mutableStateOf<List<CrackLine>>(emptyList()) }

    // ── Tap handler ───────────────────────────
    fun triggerBreak() {
        if (!enabled || isCracking) return
        isCracking = true
        crackLines.value = generateCrackLines()

        coroutineScope.launch {
            // Scale down
            scaleAnim.animateTo(
                0.95f,
                animationSpec = tween(80, easing = FastOutLinearInEasing)
            )
            // Crack reveal
            crackAnim.animateTo(
                1f,
                animationSpec = tween(320, easing = FastOutSlowInEasing)
            )
            // Shake
            repeat(4) {
                shakeAnim.animateTo(
                    if (it % 2 == 0) 6f else -6f,
                    animationSpec = tween(55, easing = LinearEasing)
                )
            }
            shakeAnim.animateTo(0f, animationSpec = tween(55))

            // Hold cracked state briefly
            delay(180)

            // Restore
            crackAnim.animateTo(0f, animationSpec = tween(260, easing = FastOutSlowInEasing))
            scaleAnim.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))

            isCracking = false
            crackLines.value = emptyList()
            onClick()
        }
    }

    // ── Gradient definitions ──────────────────
    val glassBackground = Brush.linearGradient(
        colors = listOf(
            Color(0x33FFFFFF),
            Color(0x1ACCE4FF),
            Color(0x14B8A4FF),
            Color(0x1AFF9DE2),
            Color(0x1A00E5FF),
            Color(0x2200B8FF),
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val borderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0x88FFFFFF),
            Color(0x44A8D8FF),
            Color(0x33C4B5FF),
            Color(0x44FF9DE2),
            Color(0x6600E5FF),
            Color(0x88FFFFFF),
        )
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scaleAnim.value
                scaleY = scaleAnim.value
                translationX = shakeAnim.value
            }
            .clip(shape)
            .background(brush = glassBackground, shape = shape)
            .border(width = 1.2.dp, brush = borderBrush, shape = shape)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()

                    // ── Shimmer sweep ────────────────────────
                    val width = size.width / 10
                    val sweepWidth = width * 0.45f
                    val sweepX = shimmerProgress * (size.width + sweepWidth) - sweepWidth / 2f

                    val shimmerBrush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x1AFFFFFF),
                            Color(0x44FFFFFF),
                            Color(0x1AFFFFFF),
                            Color.Transparent
                        ),
                        start = Offset(sweepX, 0f),
                        end = Offset(sweepX + sweepWidth, size.height)
                    )
                    drawRect(brush = shimmerBrush, size = size)

                    // ── Top-left inner highlight ──────────────
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0x33FFFFFF), Color.Transparent),
                            start = Offset.Zero,
                            end = Offset(size.width * 0.5f, size.height * 0.5f)
                        ),
                        size = size
                    )

                    // ── Crack overlay ─────────────────────────
                    val progress = crackAnim.value
                    if (progress > 0f) {
                        drawCrackEffect(
                            crackLines = crackLines.value,
                            progress = progress,
                            size = size
                        )
                    }
                }
            }
            .pointerInput(enabled) {
                detectTapGestures(onTap = { triggerBreak() })
            },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}

// ─────────────────────────────────────────────
//  Crack line model
// ─────────────────────────────────────────────

private data class CrackLine(
    val startFraction: Offset,   // 0..1 fractions of canvas size
    val endFraction: Offset,
    val branches: List<Pair<Offset, Offset>>,
    val alpha: Float
)

private fun generateCrackLines(): List<CrackLine> {
    val rng = Random(System.currentTimeMillis())
    val originX = rng.nextFloat() * 0.4f + 0.3f  // bias toward center
    val originY = rng.nextFloat() * 0.4f + 0.3f
    val origin = Offset(originX, originY)

    val primaryCount = rng.nextInt(4, 7)
    return (0 until primaryCount).map { i ->
        val angle = (i.toFloat() / primaryCount) * 360f + rng.nextFloat() * 30f - 15f
        val length = rng.nextFloat() * 0.35f + 0.15f
        val rad = Math.toRadians(angle.toDouble())
        val endX = (originX + cos(rad) * length).toFloat().coerceIn(0f, 1f)
        val endY = (originY + sin(rad) * length).toFloat().coerceIn(0f, 1f)
        val end = Offset(endX, endY)

        // 1–2 branches per primary crack
        val branchCount = rng.nextInt(1, 3)
        val branches = (0 until branchCount).map {
            val t = rng.nextFloat() * 0.5f + 0.3f
            val branchStart = lerp(origin, end, t)
            val branchAngle = angle + rng.nextFloat() * 60f - 30f + if (rng.nextBoolean()) 0f else 180f
            val branchLen = length * (rng.nextFloat() * 0.3f + 0.1f)
            val bRad = Math.toRadians(branchAngle.toDouble())
            val bEndX = (branchStart.x + cos(bRad) * branchLen).toFloat().coerceIn(0f, 1f)
            val bEndY = (branchStart.y + sin(bRad) * branchLen).toFloat().coerceIn(0f, 1f)
            branchStart to Offset(bEndX, bEndY)
        }

        CrackLine(
            startFraction = origin,
            endFraction = end,
            branches = branches,
            alpha = rng.nextFloat() * 0.4f + 0.6f
        )
    }
}

private fun lerp(a: Offset, b: Offset, t: Float) =
    Offset(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t)

// ─────────────────────────────────────────────
//  Crack draw
// ─────────────────────────────────────────────

private fun DrawScope.drawCrackEffect(
    crackLines: List<CrackLine>,
    progress: Float,
    size: Size
) {
    if (crackLines.isEmpty()) return

    val glowPaint = Paint().apply {
        asFrameworkPaint().apply {
            isAntiAlias = true
            color = android.graphics.Color.TRANSPARENT
            setShadowLayer(8f, 0f, 0f, android.graphics.Color.argb(120, 200, 230, 255))
        }
    }

    crackLines.forEach { crack ->
        val revealedProgress = (progress * 1.5f).coerceIn(0f, 1f)
        val crackEnd = lerp(crack.startFraction, crack.endFraction, revealedProgress)

        val start = Offset(crack.startFraction.x * size.width, crack.startFraction.y * size.height)
        val end = Offset(crackEnd.x * size.width, crackEnd.y * size.height)

        // Glow halo
        drawLine(
            color = Color(0x3300E5FF),
            start = start,
            end = end,
            strokeWidth = 5f,
            cap = StrokeCap.Round
        )
        // Core crack line
        drawLine(
            color = Color(0xCCFFFFFF).copy(alpha = crack.alpha * progress),
            start = start,
            end = end,
            strokeWidth = 1.4f,
            cap = StrokeCap.Round
        )

        // Branches
        if (progress > 0.5f) {
            val branchProgress = ((progress - 0.5f) / 0.5f).coerceIn(0f, 1f)
            crack.branches.forEach { (bStart, bEnd) ->
                val bs = Offset(bStart.x * size.width, bStart.y * size.height)
                val be = lerp(
                    bStart,
                    bEnd,
                    branchProgress
                ).let { Offset(it.x * size.width, it.y * size.height) }

                drawLine(
                    color = Color(0x2200E5FF),
                    start = bs,
                    end = be,
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color(0xAAFFFFFF).copy(alpha = crack.alpha * branchProgress * 0.7f),
                    start = bs,
                    end = be,
                    strokeWidth = 0.9f,
                    cap = StrokeCap.Round
                )
            }
        }

        // Fragment glints at crack tips
        if (progress > 0.6f) {
            val glintAlpha = ((progress - 0.6f) / 0.4f).coerceIn(0f, 1f)
            drawCircle(
                color = Color(0xFFB8EEFF).copy(alpha = glintAlpha * crack.alpha),
                radius = 2.5f,
                center = end
            )
        }
    }

    // Central impact point
    val origin = Offset(
        crackLines.first().startFraction.x * size.width,
        crackLines.first().startFraction.y * size.height
    )
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xAAFFFFFF), Color(0x4400E5FF), Color.Transparent),
            center = origin,
            radius = 18f * progress
        ),
        radius = 18f * progress,
        center = origin
    )
}

@Preview(showBackground = true)
@Composable
fun GlassCardPreview(){
    GlassCard() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_4)
                .clip(RoundedCornerShape(10.dp))
                //.background(backgroundColor)
            ,
        ) {
            val (product, cantidad, precioVenta, total, btnDelete, btnPrint) = createRefs()
            TextCmp(
                text = "ACEITE PARA MOTO POWER RIDE 2T 10/300ml",
                modifier = Modifier.constrainAs(product){
                    top.linkTo(parent.top, PADDING_8)
                    start.linkTo(parent.start, PADDING_8)
                    end.linkTo(parent.end, PADDING_8)
                },
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                maxLine = 2
            )
            Row(
                modifier = Modifier
                    .constrainAs(cantidad){
                        top.linkTo(product.bottom, PADDING_16)
                        start.linkTo(parent.start, PADDING_8)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextCmp(
                    text = "Cantidad:",
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLine = 1
                )
                TextCmp(
                    text = "$40",
                    modifier = Modifier,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLine = 1
                )
            }
            Row(
                modifier = Modifier
                    .constrainAs(precioVenta){
                        top.linkTo(product.bottom, PADDING_16)
                        start.linkTo(cantidad.end)
                        end.linkTo(total.start)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                TextCmp(
                    text = "Precio:",
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLine = 1
                )
                TextCmp(
                    text = "20.20",
                    modifier = Modifier,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLine = 1
                )
            }
            Row(
                modifier = Modifier
                    .constrainAs(total){
                        top.linkTo(product.bottom, PADDING_16)
                        end.linkTo(btnDelete.start, PADDING_8)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                TextCmp(
                    text = "Total:",
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLine = 1
                )
                val totalValue = 0.0
                val totalFormatted = String.format(
                    Locale.US,
                    "%.2f",
                    totalValue
                )
                TextCmp(
                    text = "${totalFormatted}",
                    modifier = Modifier,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    maxLine = 1
                )
            }
            Card(
                modifier = Modifier.constrainAs(btnDelete){
                    top.linkTo(product.bottom, PADDING_16)
                    end.linkTo(parent.end, PADDING_8)
                    bottom.linkTo(parent.bottom, PADDING_8)

                },
                elevation = CardDefaults.elevatedCardElevation(8.dp)
            ){
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {  }
                )
            }
            /*
            Card(
                modifier = Modifier
                    .constrainAs(btnPrint){
                        top.linkTo(product.bottom, PADDING_16)
                        end.linkTo(parent.end, PADDING_8)
                        bottom.linkTo(parent.bottom, PADDING_8)

                },
                elevation = CardDefaults.elevatedCardElevation(8.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_printer),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable{ onClickPrint() }
                )
            }

             */
        }
    }
}