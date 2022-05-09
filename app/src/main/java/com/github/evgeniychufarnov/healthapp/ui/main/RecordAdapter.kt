package com.github.evgeniychufarnov.healthapp.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.evgeniychufarnov.healthapp.R
import com.github.evgeniychufarnov.healthapp.databinding.ItemDateBinding
import com.github.evgeniychufarnov.healthapp.databinding.ItemMedicalRecordBinding
import com.github.evgeniychufarnov.healthapp.domain.RecordCommonEntity
import java.text.SimpleDateFormat
import java.util.*

private const val RECORD_TYPE = 0
private const val DATE_TYPE = 1

private const val NORMAL_PRESSURE_BORDER = 129

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.CommonViewHolder>() {
    private var data: List<RecordCommonEntity> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<RecordCommonEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is RecordCommonEntity.RecordEntity -> RECORD_TYPE
            is RecordCommonEntity.DateEntity -> DATE_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return when (viewType) {
            RECORD_TYPE -> ReportViewHolder(parent)
            DATE_TYPE -> DateViewHolder(parent)
            else -> throw RuntimeException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    abstract class CommonViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        abstract fun bind(entity: RecordCommonEntity)
    }

    class ReportViewHolder(
        viewGroup: ViewGroup
    ) : CommonViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_medical_record, viewGroup, false)
    ) {
        private val binding = ItemMedicalRecordBinding.bind(itemView)

        override fun bind(entity: RecordCommonEntity) {
            val recordEntity = entity as RecordCommonEntity.RecordEntity

            binding.tvPressureLow.text = recordEntity.pressureLow.toString()
            binding.tvPressureHigh.text = recordEntity.pressureHigh.toString()
            binding.tvPulseRate.text = recordEntity.pulseRate.toString()
            binding.tvTime.text =
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(recordEntity.date))

            binding.root.background = ResourcesCompat.getDrawable(
                itemView.resources,
                if (recordEntity.pressureHigh < NORMAL_PRESSURE_BORDER) {
                    R.drawable.bg_gradient_normal
                } else {
                    R.drawable.bg_gradient_bad
                },
                null
            )
        }
    }

    class DateViewHolder(
        viewGroup: ViewGroup
    ) : CommonViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_date, viewGroup, false)
    ) {
        private val binding = ItemDateBinding.bind(itemView)

        override fun bind(entity: RecordCommonEntity) {
            val dateEntity = entity as RecordCommonEntity.DateEntity

            binding.tvDate.text =
                SimpleDateFormat("dd MMMM", Locale.getDefault()).format(Date(dateEntity.date))
        }
    }
}
