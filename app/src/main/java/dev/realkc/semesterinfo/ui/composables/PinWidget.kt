package dev.realkc.semesterinfo.ui.composables

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.GlanceAppWidgetManager
import dev.realkc.semesterinfo.widget.Receiver
import dev.realkc.semesterinfo.widget.SemesterWidget
import kotlinx.coroutines.launch

@Composable
fun PinWidget() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            coroutineScope.launch {
                GlanceAppWidgetManager(context).requestPinGlanceAppWidget(
                    receiver = Receiver::class.java,
                    preview = SemesterWidget(),
                    previewState = DpSize(245.dp, 115.dp)
                )
            }
        }
    ) {
        Text(text = "Pin widget to home screen")
    }
}