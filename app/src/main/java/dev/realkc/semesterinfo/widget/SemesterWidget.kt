package dev.realkc.semesterinfo.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import dev.realkc.semesterinfo.R
import dev.realkc.semesterinfo.WeekInfo
import dev.realkc.semesterinfo.weekInfo
import dev.realkc.semesterinfo.widget.composables.TextColorProvider


class SemesterWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val info = weekInfo(context.resources, R.raw.msc)

        provideContent {
            GlanceTheme {
                TextColorProvider { color ->
                    MyContent(info, textColor = color)
                }
            }
        }
    }

    override suspend fun providePreview(context: Context, widgetCategory: Int) {
        val info = weekInfo(context.resources, R.raw.msc)

        provideContent {
            GlanceTheme {
                MyContent(info, textColor = Color.Black)
            }
        }
    }

    @Composable
    private fun MyContent(info: WeekInfo, textColor: Color) {

        Column(
            modifier = GlanceModifier.fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (info.isHoliday) "Holiday week" else "Week ${info.currentWeek}",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 32.sp,
                    color = ColorProvider(textColor),
                    fontWeight = FontWeight.Bold
                ),
                modifier = GlanceModifier.padding(12.dp)
            )
        }
    }
}