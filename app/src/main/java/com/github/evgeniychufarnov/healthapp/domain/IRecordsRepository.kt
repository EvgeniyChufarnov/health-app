package com.github.evgeniychufarnov.healthapp.domain

import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    val reports: Flow<List<RecordCommonEntity.RecordEntity>>

    fun addRecord(record: RecordCommonEntity.RecordEntity)
}