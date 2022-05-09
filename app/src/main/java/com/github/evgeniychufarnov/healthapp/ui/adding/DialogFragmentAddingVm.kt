package com.github.evgeniychufarnov.healthapp.ui.adding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.evgeniychufarnov.healthapp.domain.IRecordsRepository
import com.github.evgeniychufarnov.healthapp.domain.RecordCommonEntity
import java.util.*

class DialogFragmentAddingVm(
    private val recordRepository: IRecordsRepository
) : ViewModel() {

    private var pressureLow: Int? = null
    private var pressureHigh: Int? = null
    private var pulseRate: Int? = null

    private val _errorEvent: MutableLiveData<Boolean?> = MutableLiveData()
    val errorEvent: LiveData<Boolean?> = _errorEvent

    private val _closeEvent: MutableLiveData<Boolean?> = MutableLiveData()
    val closeEvent: LiveData<Boolean?> = _closeEvent

    fun onPressureLowChanged(new: String) {
        pressureLow = new.toIntOrNull()
    }

    fun onPressureHighChanged(new: String) {
        pressureHigh = new.toIntOrNull()
    }

    fun onHeartRateChanged(new: String) {
        pulseRate = new.toIntOrNull()
    }

    fun onAddClicked() {
        if (isInputValid()) {
            recordRepository.addRecord(createRecordEntity())
            _closeEvent.value = true
        } else {
            _errorEvent.value = true
        }
    }

    private fun createRecordEntity(): RecordCommonEntity.RecordEntity {
        return RecordCommonEntity.RecordEntity(
            UUID.randomUUID().toString(),
            System.currentTimeMillis(),
            pressureLow ?: 0,
            pressureHigh ?: 0,
            pulseRate ?: 0,
        )
    }

    private fun isInputValid(): Boolean {
        return pressureLow != null && pressureHigh != null && pulseRate != null
    }

    fun onErrorEventFinished() {
        _errorEvent.value = null
    }

    fun onCloseEventFinished() {
        _closeEvent.value = null
    }
}