package com.example.inventariosapp.ui.component

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.animation.core.animateFloat


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.ui.graphics.graphicsLayer


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.size
import androidx.compose.ui.tooling.preview.Preview
import com.example.appgeneric.ui.component.TextCmp


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*


@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(28.dp),
    contentPadding: PaddingValues = PaddingValues(24.dp),
    content: @Composable BoxScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    var pressed by remember { mutableStateOf(false) }
    var showBreak by remember { mutableStateOf(false) }
    val scaleAnim = remember { Animatable(1f) }
    val shakeOffsetX = remember { Animatable(0f) }

    LaunchedEffect(pressed) {
        if (pressed) {
            scaleAnim.animateTo(
                targetValue = 0.95f,
                animationSpec = tween(durationMillis = 100)
            )
            val shakeDuration = 200L
            repeat(6) { i ->
                val offset = if (i % 2 == 0) -4f else 4f
                launch {
                    shakeOffsetX.animateTo(offset, tween((shakeDuration / 6).toInt()))
                }
                delay(shakeDuration / 6)
            }
            showBreak = true
            delay(300)
            showBreak = false
            scaleAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 100)
            )
            shakeOffsetX.snapTo(0f)
            pressed = false
            onClick()
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scaleAnim.value
                scaleY = scaleAnim.value
                translationX = shakeOffsetX.value
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            pressed = true
                            tryAwaitRelease()
                        },
                        onTap = {}
                    )
                }
            }
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White.copy(alpha = 0.12f), Color.Transparent),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = shape
                )
                .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)), shape = shape)
        )

        ShimmerOverlay(modifier = Modifier.matchParentSize(), shape = shape)

        Box(
            modifier = Modifier
                .padding(contentPadding)
                .matchParentSize(),
            contentAlignment = Alignment.Center,
            content = content
        )

        if (showBreak) {
            GlassBreakEffect(modifier = Modifier.matchParentSize(), shape = shape)
        }
    }
}

@Composable
private fun ShimmerOverlay(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(28.dp)
) {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -2000f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            tween(3000, easing = LinearEasing),
            RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.clip(shape)) {
        val shimmerBrush = Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.15f), Color.Transparent),
            start = Offset(offsetX, 0f),
            end = Offset(offsetX + size.width / 2, size.height)
        )
        drawRect(brush = shimmerBrush)
    }
}

@Composable
private fun GlassBreakEffect(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(28.dp)
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 300, easing = LinearEasing))
    )
    Canvas(modifier = modifier.clip(shape)) {
        val path = Path().apply {
            moveTo(0f, size.height * 0.3f)
            lineTo(size.width * 0.5f, size.height * 0.7f)
            lineTo(size.width, size.height * 0.2f)
        }
        drawPath(
            path = path,
            color = Color.White.copy(alpha = alpha),
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GlassCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
                .background(Color(0xFF222222)),
            contentAlignment = Alignment.Center
        ) {
            GlassCard(
                onClick = { /* Handle click */ }
            ) {
                Text(
                    "Glass Card",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
