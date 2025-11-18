package dev.realkc.semesterinfo.widget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import dev.realkc.semesterinfo.R
import dev.realkc.semesterinfo.weekInfo
import dev.realkc.semesterinfo.widget.composables.TextColorProvider
import dev.realkc.semesterinfo.widget.composables.WeekInfoDisplay

class SemesterWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        val info = weekInfo(context.resources, R.raw.msc)

        provideContent {
            GlanceTheme {
                TextColorProvider { color ->
                    WeekInfoDisplay(info, textColor = color)
                }
            }
        }
    }

    override suspend fun providePreview(
        context: Context,
        widgetCategory: Int,
    ) {
        val info = weekInfo(context.resources, R.raw.msc)

        provideContent {
            GlanceTheme {
                WeekInfoDisplay(info, textColor = Color.Black)
            }
        }
    }
}
