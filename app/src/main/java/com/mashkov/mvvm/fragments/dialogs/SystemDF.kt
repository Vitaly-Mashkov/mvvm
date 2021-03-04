package com.mashkov.mvvm.fragments.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.mashkov.mvvm.databinding.DialogSystemBinding

class SystemDF : DialogFragment() {

    val vm: DiiaSystemDFVM by activityViewModels()
    val args: SystemDFArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setCancelable(args.dialog.cancelable)
        val binding = DialogSystemBinding.inflate(inflater, container, false)
        binding.apply {
            dialog = args.dialog
            positiveButton.setOnClickListener {
                vm.positive()
                dismiss()
            }

            negativeButton.setOnClickListener {
                vm.negative()
                dismiss()
            }
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}

