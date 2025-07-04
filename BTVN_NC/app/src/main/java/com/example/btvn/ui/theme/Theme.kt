// Theme.kt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

import com.example.btvn.ui.theme.AppTypography // 👈 import typography tùy chỉnh
import com.example.btvn.ui.theme.Shapes

@Composable
fun BTVNTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme(),
        typography = AppTypography, // ✅ dùng tên rõ ràng hơn
        shapes = Shapes,
        content = content
    )
}
