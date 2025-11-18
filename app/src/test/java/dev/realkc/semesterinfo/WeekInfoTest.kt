package dev.realkc.semesterinfo

import dev.realkc.semesterinfo.model.WeekBlock
import dev.realkc.semesterinfo.model.YearInfo
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WeekInfoTest {
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
    fun `correctly handles holidays`() {
        assertTrue(WeekInfo(yearInfo, LocalDate(2025, 10, 17)).isHoliday)
        assertFalse(WeekInfo(yearInfo, LocalDate(2025, 10, 17)).isExams)
        assertFalse(WeekInfo(yearInfo, LocalDate(2025, 10, 17)).isReexams)
        assertFalse(WeekInfo(yearInfo, LocalDate(2025, 11, 17)).isHoliday)
    }

    @Test
    fun `correctly handles exams`() {
        assertTrue(WeekInfo(yearInfo, LocalDate(2025, 12, 17)).isExams)
        assertFalse(WeekInfo(yearInfo, LocalDate(2025, 12, 17)).isHoliday)
        assertFalse(WeekInfo(yearInfo, LocalDate(2025, 12, 17)).isReexams)
        assertFalse(WeekInfo(yearInfo, LocalDate(2025, 11, 17)).isExams)
    }

    @Test
    fun `correctly handles reexams`() {
        assertTrue(WeekInfo(yearInfo, LocalDate(2026, 1, 17)).isReexams)
        assertFalse(WeekInfo(yearInfo, LocalDate(2026, 1, 17)).isHoliday)
        assertFalse(WeekInfo(yearInfo, LocalDate(2026, 1, 17)).isExams)
        assertFalse(WeekInfo(yearInfo, LocalDate(2025, 11, 17)).isReexams)
    }

    @Test
    fun `holidays, exams and reexams are not counted as semester weeks in week counting`() {
        assertEquals(1, WeekInfo(yearInfo, LocalDate(2025, 11, 1)).currentWeek)
        assertEquals(4, WeekInfo(yearInfo, LocalDate(2026, 2, 1)).currentWeek)
    }
}
