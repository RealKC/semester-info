package dev.realkc.semesterinfo.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import dev.realkc.semesterinfo.R
import dev.realkc.semesterinfo.WeekInfo
import dev.realkc.semesterinfo.weekInfo


class SemesterWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val info = weekInfo(context.resources, R.raw.msc)

        provideContent {
            MyContent(info)
        }
    }

    @Composable
    private fun MyContent(info: WeekInfo) {

        Column(
            modifier = GlanceModifier.fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Is it a holday? ${info.isHoliday}", modifier = GlanceModifier.padding(12.dp))
            Text("Week ${info.currentWeek}", modifier = GlanceModifier.padding(12.dp))
        }
    }
}