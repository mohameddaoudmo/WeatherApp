package com.example.weatherapplication.alart

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.designpattern.allproduct.viewModel.AllproductviewFactory
import com.example.designpattern.allproduct.viewModel.ForcastViewModel
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.model.Repostiory
import com.example.designpattern.network.ApiClient
import com.example.weatherapplication.MapssActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.SharedViewModel
import com.example.weatherapplication.databinding.FragmentAlertBinding
import com.example.weatherapplication.databinding.FragmentDialogForAlertBinding
import com.example.weatherapplication.databinding.MenuLayoutBinding
import com.example.weatherapplication.favarouite.FavRecycleAdapter
import com.example.weatherapplication.model.Alert
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit


class AlertFragment : Fragment() {
    lateinit var  menuLayout: LinearLayout
    private lateinit var spinner: Spinner
    private var alarm :Boolean = false
    private var statusOfSound :String=""
    private lateinit var myLayoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: AlartAdapter
    lateinit var forcastViewModel: ForcastViewModel
    lateinit var forcastViewModelFactory: AllproductviewFactory
    var formattedendDateTime:String =""
    var formattedStartDateTime:String=""
lateinit var workManager: WorkManager

    private var isMenuOpen = false
    private lateinit var builder: AlertDialog.Builder
    private var startD: Long = 0L
    private var endD: Long = 0L
    private var startT: Long = 0L
    private var endT: Long = 0L
    var delayForStart: Long = 0L
    var delayForEnd :Long =0L
    lateinit var viewModel: SharedViewModel
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private var land:String? =""
    private lateinit var dialog: AlertDialog
    private lateinit var bindingDialog: FragmentDialogForAlertBinding
    lateinit var binding: FragmentAlertBinding
    lateinit var layoutmenubinding: MenuLayoutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workManager=WorkManager.getInstance(requireContext())
        forcastViewModelFactory =
            AllproductviewFactory(Repostiory(ApiClient, ConLocalSource(requireContext())))
        forcastViewModel = ViewModelProvider(
            this,
            forcastViewModelFactory
        ).get(ForcastViewModel::class.java)
        myLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
recyclerAdapter = AlartAdapter(requireContext()){
    forcastViewModel.deleteFromFav(it)
    workManager.cancelAllWorkByTag(it.startTime)


}
        binding.recyclerView.apply {   adapter = recyclerAdapter
            layoutManager = myLayoutManager }
        var dialogs = DialogFragmentForAlert()
        dialogs.show(requireFragmentManager(), "dialog")
        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bindingDialog.root)
        dialog = builder.create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
layoutmenubinding.fbtime.setOnClickListener { dialog.show() }
        binding.fabMain.setOnClickListener {
            toggle()


        }
                spinner= bindingDialog.soundSpinne
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sound_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if(selectedItem=="Alarm sound"){
                    alarm =true
statusOfSound ="Alarm sound"               }
                else{
                    alarm =false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
bindingDialog.button2.setOnClickListener { dialog.dismiss() }
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
                            val notificationStartTime = calendar.timeInMillis
                            delayForStart = notificationStartTime - currentTime
                            println("currrrent $currentTime")
                            println("nofffffffffffff $notificationStartTime")

                             formattedStartDateTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                                    calendar.time
                                )
                            bindingDialog.startTimeTextvie.text = "Start time is $formattedStartDateTime"
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
                            val currentTime = System.currentTimeMillis()

                            val notificationendTime = calendar.timeInMillis
                            delayForEnd = notificationendTime - currentTime
                            println(endT)

                            formattedendDateTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                                    calendar.time
                                )
                            bindingDialog.endTimeTextvie.text = "end time is $formattedendDateTime"
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
//            this.longitude = longitude
        }

        viewModel.latitudegps.observe(viewLifecycleOwner) { latitude ->
//            this.latitude = latitude
        }
        forcastViewModel.getsavedAlert()
        lifecycleScope.launch {
            forcastViewModel.savedalartStateFlow.collect { list ->

                recyclerAdapter.submitList(list)

            }

        }


        bindingDialog.setAlarmButto.setOnClickListener {
            if (longitude == 0.0 || latitude == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "GPS coordinates not available , You Should set location frist",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val data = Data.Builder()
                .putDouble("lat", latitude)
                .putDouble("long", longitude)
                .putString("land",land)
                .putBoolean("sound", alarm)
                .putLong("startTime",startT)
                .putLong("endTime",endT)

                .build()
            val myContext = requireContext()
            val workRequest = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.HOURS)
                .setInitialDelay(delayForStart, TimeUnit.MILLISECONDS)
                .keepResultsForAtLeast(delayForStart-delayForEnd,TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag(formattedStartDateTime?:"")
                .build()


            workManager.enqueue(workRequest)
forcastViewModel.addToAlert(Alert(formattedStartDateTime,formattedendDateTime,land?:"",statusOfSound))
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
        layoutmenubinding = MenuLayoutBinding.inflate(inflater, null, false)


        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        return binding.root
    }

    private fun toggle() {
        if (isMenuOpen) {
            removeMenu()
        } else {
            showMenu()
        }
        isMenuOpen = !isMenuOpen
    }
    private fun showMenu() {
        val inflater = LayoutInflater.from(requireContext())
        menuLayout = inflater.inflate(R.layout.menu_layout, null) as LinearLayout
        val layoutParams = CoordinatorLayout.LayoutParams(
            CoordinatorLayout.LayoutParams.WRAP_CONTENT,
            CoordinatorLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.TOP or Gravity.END
        layoutParams.setMargins(16, 16, 16, 16)
        layoutParams.anchorGravity = Gravity.TOP or Gravity.END
        layoutParams.anchorId = R.id.fabMain
        menuLayout.layoutParams = layoutParams
        menuLayout.findViewById<View>(R.id.fbtime).setOnClickListener { dialog.show()
    print("AAAAAAAAAa")
    }
        menuLayout.findViewById<View>(R.id.fbaddlocation).setOnClickListener {

            val intent = Intent(requireContext(), MapssActivity::class.java)
            startActivityForResult(intent, 1)
        }
        val coordinatorLayout: CoordinatorLayout =binding.coordinatorLayout
        coordinatorLayout.addView(menuLayout)
    }

    private fun removeMenu() {
        if (menuLayout != null && menuLayout.parent != null) {
            val coordinatorLayout: CoordinatorLayout =
                binding.coordinatorLayout
            coordinatorLayout.removeView(menuLayout)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val latitude = data?.getDoubleExtra("latitude", 0.0) ?: 0.0
            val longitude = data?.getDoubleExtra("longitude", 0.0) ?: 0.0
            val land = data?.getStringExtra("land")

            this.longitude = longitude
            this.latitude =latitude
            this.land =land
            println("long $longitude")
            println("loc $latitude")






        }
    }

}