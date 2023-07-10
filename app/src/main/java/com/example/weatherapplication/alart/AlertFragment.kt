package com.example.weatherapplication.alart

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.weatherapplication.R
import com.example.weatherapplication.SharedViewModel
import com.example.weatherapplication.databinding.FragmentAlertBinding
import com.example.weatherapplication.databinding.FragmentDialogForAlertBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AlertFragment : Fragment() {
    lateinit var  menuLayout: LinearLayout

    private var isMenuOpen = false
    private lateinit var builder: AlertDialog.Builder
    private var startD: Long = 0L
    private var endD: Long = 0L
    private var startT: Long = 0L
    private var endT: Long = 0L
    var delay: Long = 0L
    lateinit var viewModel: SharedViewModel
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private lateinit var dialog: AlertDialog
    private lateinit var bindingDialog: FragmentDialogForAlertBinding
    lateinit var binding: FragmentAlertBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dialogs = DialogFragmentForAlert()
        dialogs.show(requireFragmentManager(), "dialog")
        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bindingDialog.root)
        dialog = builder.create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

        binding.floatingActionButton.setOnClickListener {

            dialog.show()


        }

        bindingDialog.startimageview.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    startD = calendar.timeInMillis
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            calendar.set(Calendar.MONTH, month)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendar.set(Calendar.MINUTE, minute)
                            startT = calendar.timeInMillis
                            println(startT)
                            val currentTime = System.currentTimeMillis()
                            val notificationTime = calendar.timeInMillis
                            delay = notificationTime - currentTime
                            println("currrrent $currentTime")
                            println("nofffffffffffff $notificationTime")

                            val formattedDateTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                                    calendar.time
                                )
                            bindingDialog.startTimeTextvie.text = "Start time is $formattedDateTime"
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )

                    timePickerDialog.show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()

        }

        bindingDialog.endtimecalender.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    startD = calendar.timeInMillis
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            calendar.set(Calendar.MONTH, month)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendar.set(Calendar.MINUTE, minute)
                            startT = calendar.timeInMillis
                            println(startT)

                            val formattedDateTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                                    calendar.time
                                )
                            bindingDialog.endTimeTextvie.text = "end time is $formattedDateTime"
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    timePickerDialog.show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()

        }

        viewModel.longitudegps.observe(viewLifecycleOwner) { longitude ->
            this.longitude = longitude
        }

        viewModel.latitudegps.observe(viewLifecycleOwner) { latitude ->
            this.latitude = latitude
        }

        bindingDialog.setAlarmButto.setOnClickListener {
//            if (longitude == 0.0 || latitude == 0.0) {
//                Toast.makeText(
//                    requireContext(),
//                    "GPS coordinates not available",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }

            // Create and enqueue the work request
            val data = Data.Builder()
                .putDouble("lat", latitude)
                .putDouble("long", longitude)
                .putBoolean("sound", true)
                .build()
            val myContext = requireContext()
            val workRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()

            WorkManager.getInstance(myContext).enqueue(workRequest)

            if (::dialog.isInitialized) {
                dialog.dismiss()

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAlertBinding.inflate(inflater, container, false)
        bindingDialog = FragmentDialogForAlertBinding.inflate(inflater, null, false)

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        return binding.root
    }


 
}