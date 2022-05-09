package com.github.evgeniychufarnov.healthapp.domain

sealed class RecordCommonEntity {

    data class RecordEntity(
        val uuid: String = "",
        val date: Long = 0,
        val pressureLow: Int = 0,
        val pressureHigh: Int = 0,
        val pulseRate: Int = 0,
    ) : RecordCommonEntity()

    data class DateEntity(
        val date: Long
    ) : RecordCommonEntity()
}