package com.example.weatherapplication.alart

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentDialogForAlertBinding


class DialogFragmentForAlert : DialogFragment() {

    private lateinit var binding : FragmentDialogForAlertBinding
    private lateinit var spinner: Spinner
    private var startD: Long = 0L
    private var endD: Long = 0L
    private var startT: Long = 0L
    private var endT: Long = 0L
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDialogForAlertBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder: AlertDialog.Builder=  AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.fragment_dialog_for_alert, null)
        builder.setView(view)


        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dismiss()
//        spinner= binding.soundSpinne
//        val adapter = ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.sound_options,
//            android.R.layout.simple_spinner_item
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = adapter
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedItem = parent?.getItemAtPosition(position).toString()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }
//        binding.setAlarmButto.setOnClickListener {
//        }
    }


}