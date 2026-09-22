import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.io.path.Path

@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    shape: Shape = RoundedCornerShape(16.dp),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
) {

    val isPressed = false
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        finishedListener = { /* no-op */ }
    )
    val crackAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.5f else 0f,
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier
            .scale(scale)
            .clickable(onClick = onClick,  interactionSource = remember { MutableInteractionSource() })
            .background(Color.Transparent)
            .blur(16.dp) // Background blur for glass effect
    ) {
        Surface(
            shape = shape,
            color = backgroundColor,
            border = BorderStroke(1.5.dp, borderColor),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .semantics { contentDescription = text }
            ) {
                if (icon != null) Icon(icon, contentDescription = null, tint = contentColor)
                Text(text, color = contentColor)
            }
        }

        // Glass crack overlay
        Canvas(modifier = Modifier.matchParentSize()) {
            if (crackAlpha > 0f) {
                val path = Path().apply {
                    moveTo(0f, size.height * 0.3f)
                    lineTo(size.width * 0.2f, size.height * 0.7f)
                    lineTo(size.width * 0.4f, size.height * 0.3f)
                    lineTo(size.width * 0.6f, size.height * 0.7f)
                    lineTo(size.width * 0.8f, size.height * 0.3f)
                }
                drawPath(path, color = contentColor.copy(alpha = crackAlpha), style = Stroke(width = 2.dp.toPx()))
            }
        }
    }
}

@Preview
@Composable
fun GlassButtonPreview2() {
    GlassButton(
        onClick = { /* TODO */ },
        text = "Glass Button",
        icon = Icons.Default.Star,
        shape = RoundedCornerShape(24.dp)
    )
}