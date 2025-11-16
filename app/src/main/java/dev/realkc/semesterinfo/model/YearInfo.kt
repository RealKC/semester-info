package dev.realkc.semesterinfo.model

import kotlinx.datetime.LocalDateRange
import kotlinx.serialization.Serializable

@Serializable
data class YearInfo(
    val semesterOne: List<WeekBlock>,
    val semesterTwo: List<WeekBlock>,
) {
    val semesterOneDateRange: LocalDateRange
        get() = semesterOne.first().start.rangeTo(semesterOne.last().end)

    val semesterTwoDateRange: LocalDateRange
        get() = semesterTwo.first().start.rangeTo(semesterTwo.last().end)
}
