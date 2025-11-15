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

@OptIn(ExperimentalTime::class)
class WeekInfo(yearInfo: YearInfo) {
    var isHoliday: Boolean = false
        private set

    var currentWeek: Long = 0
        private set

    init {
        val currentDay = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        var weekCount = 1L

        for (weekBlock in yearInfo.semesterOne) {
            if (weekBlock.dateRange.contains(currentDay)) {
                when (weekBlock) {
                    is WeekBlock.Classes -> {
                        currentWeek = weekCount + weeksBetween(weekBlock.start, currentDay)
                    }
                    is WeekBlock.Holiday -> {
                        isHoliday = true
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

    private fun weeksBetween(a: LocalDate, b: LocalDate): Long {
        val aj = a.toJavaLocalDate()
        val bj = b.toJavaLocalDate()

        return aj.until(bj.plusDays(1), ChronoUnit.WEEKS)
    }

    val description: String
        get() = if (isHoliday) "Holiday week" else "Week ${currentWeek}"
}

fun weekInfo(resources: Resources, @RawRes id: Int): WeekInfo {
    val rawInfo = String(resources.openRawResource(R.raw.msc).readAllBytes())
    val yearInfo = Json.decodeFromString<YearInfo>(rawInfo)
    return WeekInfo(yearInfo)
}