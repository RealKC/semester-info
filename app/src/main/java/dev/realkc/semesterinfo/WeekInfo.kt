package dev.realkc.semesterinfo

import android.content.res.Resources
import androidx.annotation.RawRes
import dev.realkc.semesterinfo.model.WeekBlock
import dev.realkc.semesterinfo.model.YearInfo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import java.time.temporal.ChronoUnit
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class WeekInfo(
    yearInfo: YearInfo,
    currentDay: LocalDate = now(),
) {
    var isHoliday: Boolean = false
        private set

    var isExams: Boolean = false
        private set

    var isReexams: Boolean = false
        private set

    var currentWeek: Long = 0
        private set

    init {
        val semester =
            if (yearInfo.semesterOneDateRange.contains(currentDay)) {
                yearInfo.semesterOne
            } else if (yearInfo.semesterTwoDateRange.contains(currentDay)) {
                yearInfo.semesterTwo
            } else {
                null
            }

        if (semester == null) {
            isHoliday = true
        } else {
            var weekCount = 1L

            for (weekBlock in semester) {
                if (weekBlock.dateRange.contains(currentDay)) {
                    when (weekBlock) {
                        is WeekBlock.Classes -> {
                            currentWeek = weekCount + weeksBetween(weekBlock.start, currentDay)
                        }

                        is WeekBlock.Holiday -> {
                            isHoliday = true
                        }

                        is WeekBlock.Exams -> {
                            isExams = true
                        }

                        is WeekBlock.Reexams -> {
                            isReexams = true
                        }
                    }
                    break
                } else {
                    if (weekBlock is WeekBlock.Classes) {
                        weekCount += weeksBetween(weekBlock.start, weekBlock.end)
                    }
                }
            }
        }
    }

    private fun weeksBetween(
        a: LocalDate,
        b: LocalDate,
    ): Long {
        val aj = a.toJavaLocalDate()
        val bj = b.toJavaLocalDate()

        return aj.until(bj, ChronoUnit.WEEKS)
    }

    val description: String
        get() =
            if (isHoliday) {
                "Holiday week"
            } else if (isExams) {
                "Exams session :("
            } else if (isReexams) {
                "Restanțe =((("
            } else {
                "Week $currentWeek"
            }
}

@OptIn(ExperimentalTime::class)
private fun now(): LocalDate =
    Clock.System
        .now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

fun weekInfo(
    resources: Resources,
    @RawRes id: Int,
): WeekInfo {
    val rawInfo = String(resources.openRawResource(R.raw.msc).readAllBytes())
    val yearInfo = Json.decodeFromString<YearInfo>(rawInfo)
    return WeekInfo(yearInfo)
}
