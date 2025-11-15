package dev.realkc.semesterinfo.widget.composables

import android.app.WallpaperColors
import android.app.WallpaperManager
import android.app.WallpaperManager.FLAG_SYSTEM
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.glance.LocalContext


/**
 * Provides composables with a text color appropriate that will be readable against the wallpaper
 *
 * Based on code from here: https://proandroiddev.com/widgets-with-glance-standing-out-33834eee2dee
 */
@Composable
fun TextColorProvider(
    content: @Composable (Color) -> Unit
) {
    val wallpaperManager = WallpaperManager.getInstance(LocalContext.current)
    val colors = wallpaperManager.getWallpaperColors(FLAG_SYSTEM)
    var useDarkColor by remember {
        mutableStateOf(
            getUseDarkColor(colors)
        )
    }

    DisposableEffect(wallpaperManager) {
        val listener = WallpaperManager.OnColorsChangedListener { colors, _ ->
            getUseDarkColor(colors).let {
                useDarkColor = it
            }
        }

        wallpaperManager.addOnColorsChangedListener(listener,
            android.os.Handler(Looper.getMainLooper())
        )

        onDispose {
            wallpaperManager.removeOnColorsChangedListener(listener)
        }
    }

    content.invoke(if (useDarkColor) Color.Black else Color.White)
}

fun getUseDarkColor(colors: WallpaperColors?): Boolean = colors != null && (colors.colorHints and WallpaperColors.HINT_SUPPORTS_DARK_TEXT) != 0