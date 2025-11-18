package dev.realkc.semesterinfo

import androidx.compose.ui.graphics.Color
import androidx.glance.appwidget.testing.unit.runGlanceAppWidgetUnitTest
import androidx.glance.testing.unit.assertHasText
import androidx.glance.testing.unit.hasTestTag
import dev.realkc.semesterinfo.model.WeekBlock
import dev.realkc.semesterinfo.model.YearInfo
import dev.realkc.semesterinfo.widget.composables.WeekInfoDisplay
import kotlinx.datetime.LocalDate
import org.junit.Test

class WidgetTest {
    val yearInfo =
        YearInfo(
            semesterOne =
                listOf(
                    WeekBlock.Holiday(
                        LocalDate(2025, 10, 1),
                        end = LocalDate(2025, 10, 31),
                    ),
                    WeekBlock.Classes(
                        LocalDate(2025, 11, 1),
                        end = LocalDate(2025, 11, 28),
                    ),
                    WeekBlock.Exams(
                        LocalDate(2025, 12, 1),
                        end = LocalDate(2025, 12, 28),
                    ),
                    WeekBlock.Reexams(
                        LocalDate(2026, 1, 1),
                        end = LocalDate(2026, 1, 28),
                    ),
                    WeekBlock.Classes(
                        LocalDate(2026, 1, 29),
                        end = LocalDate(2026, 2, 28),
                    ),
                ),
            semesterTwo = listOf(),
        )

    @Test
    fun widget_showsIsHoliday_ifIsHoliday() =
        runGlanceAppWidgetUnitTest {
            provideComposable {
                WeekInfoDisplay(WeekInfo(yearInfo, LocalDate(2025, 10, 17)), Color.Black)
            }

            onNode(hasTestTag("contentArea"))
                .assertHasText("Holiday", ignoreCase = true)
        }

    @Test
    fun widget_showsIsExams_ifIsExams() =
        runGlanceAppWidgetUnitTest {
            provideComposable {
                WeekInfoDisplay(WeekInfo(yearInfo, LocalDate(2025, 12, 17)), Color.Black)
            }

            onNode(hasTestTag("contentArea"))
                .assertHasText("Exams", ignoreCase = true)
        }

    @Test
    fun widget_showsIsReexams_ifIsReexams() =
        runGlanceAppWidgetUnitTest {
            provideComposable {
                WeekInfoDisplay(WeekInfo(yearInfo, LocalDate(2026, 1, 17)), Color.Black)
            }

            onNode(hasTestTag("contentArea"))
                .assertHasText("Restanțe", ignoreCase = true)
        }

    @Test
    fun widget_showsWeekNumber() =
        runGlanceAppWidgetUnitTest {
            provideComposable {
                WeekInfoDisplay(WeekInfo(yearInfo, LocalDate(2025, 11, 8)), Color.Black)
            }

            onNode(hasTestTag("contentArea"))
                .assertHasText("Week 2", ignoreCase = true)
        }
}
