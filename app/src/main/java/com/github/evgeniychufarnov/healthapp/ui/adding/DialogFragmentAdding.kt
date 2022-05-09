package com.github.evgeniychufarnov.healthapp.ui.adding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.github.evgeniychufarnov.healthapp.R
import com.github.evgeniychufarnov.healthapp.databinding.DialogAddingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogFragmentAdding : DialogFragment() {

    private var _binding: DialogAddingBinding? = null
    private val binding get() = _binding!!

    private val vm: DialogFragmentAddingVm by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        vm.errorEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                showError()
                vm.onErrorEventFinished()
            }
        }

        vm.closeEvent.observe(viewLifecycleOwner) {
            if (it != null) {
                dismiss()
                vm.onCloseEventFinished()
            }
        }
    }

    private fun initListeners() {
        binding.etPressureLow.doOnTextChanged { text, _, _, _ ->
            vm.onPressureLowChanged(text.toString())
        }

        binding.etPressureHigh.doOnTextChanged { text, _, _, _ ->
            vm.onPressureHighChanged(text.toString())
        }

        binding.etHeartRate.doOnTextChanged { text, _, _, _ ->
            vm.onHeartRateChanged(text.toString())
        }

        binding.btnAdd.setOnClickListener {
            vm.onAddClicked()
        }
    }

    private fun showError() {
        Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}