package dev.realkc.semesterinfo.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class WeekBlock(
) {
    abstract val start: LocalDate
    abstract val end: LocalDate

    @Serializable
    @SerialName("classes")
    class Classes(
        override val start: LocalDate,
        override val end: LocalDate
    ) : WeekBlock()

    @Serializable
    @SerialName("holiday")
    class Holiday(
        override val start: LocalDate,
        override val end: LocalDate
    ) : WeekBlock()

    val dateRange: LocalDateRange
        get() = LocalDateRange(start, end)
}