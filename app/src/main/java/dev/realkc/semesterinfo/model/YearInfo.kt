package dev.realkc.semesterinfo.model

import kotlinx.serialization.Serializable

@Serializable
data class YearInfo(
    val semesterOne: List<WeekBlock>
) {

}
