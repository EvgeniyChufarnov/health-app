package com.github.evgeniychufarnov.healthapp.data

import com.github.evgeniychufarnov.healthapp.domain.IRecordsRepository
import com.github.evgeniychufarnov.healthapp.domain.RecordCommonEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

private const val COLLECTION_NAME = "reports"

class RecordsRepository(
    private val database: FirebaseFirestore,
) : IRecordsRepository {

    private val _reports: MutableStateFlow<List<RecordCommonEntity.RecordEntity>> = MutableStateFlow(listOf())
    override val reports: Flow<List<RecordCommonEntity.RecordEntity>> = _reports

    init {
        database.collection(COLLECTION_NAME)
            .addSnapshotListener { value: QuerySnapshot?, _: FirebaseFirestoreException? ->
                val docs = value?.documents
                    ?.mapNotNull {
                        it.toObject(RecordCommonEntity.RecordEntity::class.java)
                    }
                    ?.sortedByDescending {
                        it.date
                    }

                docs?.let {
                    _reports.value = it
                }
            }
    }

    override fun addRecord(record: RecordCommonEntity.RecordEntity) {
        database.collection(COLLECTION_NAME).document(record.uuid).set(record)
    }
}