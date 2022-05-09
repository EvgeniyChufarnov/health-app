package com.github.evgeniychufarnov.healthapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.evgeniychufarnov.healthapp.databinding.ActivityMainBinding
import com.github.evgeniychufarnov.healthapp.ui.adding.DialogFragmentAdding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter: RecordAdapter by lazy { RecordAdapter() }
    private val vm:MainActivityVm by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvReports.adapter = adapter

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        vm.reports.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            openAddingReport()
        }
    }

    private fun openAddingReport() {
        DialogFragmentAdding().show(supportFragmentManager, null)
    }
}