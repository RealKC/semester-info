package dev.realkc.semesterinfo.widget.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.semantics.semantics
import androidx.glance.semantics.testTag
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import dev.realkc.semesterinfo.WeekInfo

@Composable
fun WeekInfoDisplay(
    week: WeekInfo,
    textColor: Color,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = week.description,
            style =
                TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 32.sp,
                    color = ColorProvider(textColor),
                    fontWeight = FontWeight.Bold,
                ),
            modifier = GlanceModifier.padding(12.dp).semantics { testTag = "contentArea" },
        )
    }
}
