package com.github.evgeniychufarnov.healthapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.evgeniychufarnov.healthapp.domain.IRecordsRepository
import com.github.evgeniychufarnov.healthapp.domain.RecordCommonEntity
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class MainActivityVm(
    recordRepository: IRecordsRepository
) : ViewModel() {

    val reports: LiveData<List<RecordCommonEntity>> = recordRepository.reports
        .map { it.addDateEntities() }
        .asLiveData()

}

private fun List<RecordCommonEntity.RecordEntity>.addDateEntities(): List<RecordCommonEntity> {
    val entities: MutableList<RecordCommonEntity> = mutableListOf()
    var lastDate: Date? = null

    forEach { entity ->
        if (lastDate == null) {
            entities.add(RecordCommonEntity.DateEntity(entity.date))
        } else {
            lastDate?.let {
                if (!isSameDay(it, Date(entity.date))) {
                    entities.add(RecordCommonEntity.DateEntity(entity.date))
                }
            }
        }

        entities.add(entity)
        lastDate = Date(entity.date)
    }

    return entities
}

private fun isSameDay(date1: Date, date2: Date): Boolean {
    val fmt = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    return fmt.format(date1).equals(fmt.format(date2))
}