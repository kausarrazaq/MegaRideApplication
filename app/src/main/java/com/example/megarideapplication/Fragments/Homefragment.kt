package com.example.megarideapplication.Fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.megarideapplication.Activities.HomeActivity
import com.example.megarideapplication.Activities.Map_Activity
import com.example.megarideapplication.Activities.NotificationActivity
import com.example.megarideapplication.Models.RiderequestcarownerModel
import com.example.megarideapplication.Models.RiderequestuserModel
import com.example.megarideapplication.R
import com.example.megarideapplication.WebServices.ParameterService
import com.example.megarideapplication.utilis.AppURL
import com.example.megarideapplication.utilis.ShareMemory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.irozon.sneaker.Sneaker
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class Homefragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    ParameterService.ResponseInterfaces, ParameterService.ResponseErrorInterface {
    private lateinit var lastLocation: Location
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var bottomSheet: View
    private lateinit var notificationIcon: ImageView
    private lateinit var dropView: View
    private lateinit var shareMyCar: Button
    private lateinit var requestRide: Button
    private lateinit var requestRideCalender: View
    private lateinit var requestRideTv: TextView
    private lateinit var shearRideCalender: View
    private lateinit var shearRideTv: TextView
    private lateinit var requestRideTimer: View
    private lateinit var requestRideTimerTv: TextView
    private lateinit var shearMyCarTimer: View
    private lateinit var shearMyCarTv: TextView
    private lateinit var postRequestBtn: Button
    private lateinit var shearMyCarBtn: Button
    private lateinit var showLocation: TextView
    private lateinit var requiredSeats: EditText
    private lateinit var shearMemory: ShareMemory
    private lateinit var carModel: EditText
    private lateinit var noPlate :EditText
    private lateinit var availableSeats : EditText
    private var isBool:Boolean=false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_homefragment, container, false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapp) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        setUpMap(rootView)
        initilisation(rootView)
        return rootView
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun initilisation(rootView: View) {
        bottomSheet = rootView.findViewById(R.id.bottomsheetbtn)
        notificationIcon = rootView.findViewById(R.id.notifyicon)
        dropView = rootView.findViewById(R.id.dropmeat)
        showLocation = rootView.findViewById(R.id.currentlocation)
        shearMemory = ShareMemory.mInstence
        shearMemory = ShareMemory.getmInstence()
        //Drop me at
        dropView.setOnClickListener {
            activity?.let {
                val intent = Intent(it, Map_Activity::class.java)
                it.startActivity(intent)
            }
        }

        //Notificationicon
        notificationIcon.setOnClickListener {
            activity?.let {
                val intent = Intent(it, NotificationActivity::class.java)
                it.startActivity(intent)
            }
        }

        //Bottomsheetforproceedbtn
        val bottomSheetDialog1 = BottomSheetDialog(requireContext())
        val bottomSheetView1 = this.layoutInflater.inflate(R.layout.bottomsheetforhome, null)
        with(bottomSheetDialog1)
        {
            setContentView(bottomSheetView1)
        }
        bottomSheet.setOnClickListener {
            showDialogNotificationAction(bottomSheetDialog1)
        }


        //BottomsheetforRequestridebtn
        shareMyCar = bottomSheetView1.findViewById(R.id.sharemycar)
        requestRide = bottomSheetView1.findViewById(R.id.requestride)
        val bottomSheetDialog2 = BottomSheetDialog(requireContext())
        val bottomSheetView2 = this.layoutInflater.inflate(R.layout.bottomsheetforrequestride, null)
        with(bottomSheetDialog2)
        {
            setContentView(bottomSheetView2)
        }
        requestRide.setOnClickListener {
            showDialogNotificationAction(bottomSheetDialog2)
        }

        //BottomsheetforSharemycarbtn
        val bottomSheetDialog3 = BottomSheetDialog(requireContext())
        val bottomSheetView3 = this.layoutInflater.inflate(R.layout.bottomsheetforsharemycar, null)
        with(bottomSheetDialog3)
        {
            setContentView(bottomSheetView3)
        }
        shareMyCar.setOnClickListener {
            showDialogNotificationAction(bottomSheetDialog3)
        }

        //CalenderforRequestride
        requestRideCalender = bottomSheetView2.findViewById(R.id.calenderview)
        requestRideTv = bottomSheetView2.findViewById(R.id.requestridetv)
        requestRideTimer = bottomSheetView2.findViewById(R.id.timerforrequestride)
        requestRideTimerTv = bottomSheetView2.findViewById(R.id.requestrideTv)
        postRequestBtn = bottomSheetView2.findViewById(R.id.postrequestbtn)
        requiredSeats = bottomSheetView2.findViewById(R.id.requiredseats)

        postRequestBtn.setOnClickListener {
            requestrideApi()
        }

        val myCalendar1: Calendar = Calendar.getInstance()
        val date1 =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar1.set(Calendar.YEAR, year)
                myCalendar1.set(Calendar.MONTH, monthOfYear)
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                requestRideTv.text = " $dayOfMonth / $monthOfYear / $year"
                requestRideTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        requestRideCalender.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DialogTheme,
                date1,
                myCalendar1[Calendar.YEAR],
                myCalendar1[Calendar.MONTH],
                myCalendar1[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
            requestRideTv.resources.getColor(R.color.black)
        }

        //TimerforRequestride
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    requestRideTimerTv.setText(String.format("%d : %d", hourOfDay, minute))
                }
            }, hour, minute, false)

        requestRideTimer.setOnClickListener({ v ->
            mTimePicker.show()

        })


        //Calenderforshare my car
        shearRideCalender = bottomSheetView3.findViewById(R.id.calenderforshearcar)
        shearRideTv = bottomSheetView3.findViewById(R.id.calenderforsheartv)
        shearMyCarTimer = bottomSheetView3.findViewById(R.id.timerforshearcar)
        shearMyCarTv = bottomSheetView3.findViewById(R.id.timerforshearmycartv)
        shearMyCarBtn = bottomSheetView3.findViewById(R.id.sharemycarbtn)
        carModel= bottomSheetView3.findViewById(R.id.carmodeltv)
        noPlate= bottomSheetView3.findViewById(R.id.noplatetv)
        availableSeats= bottomSheetView3.findViewById(R.id.availableseats)

        shearMyCarBtn.setOnClickListener {
            shareMyCarApi()
        }
        val myCalendar2: Calendar = Calendar.getInstance()
        val date2 =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar2.set(Calendar.YEAR, year)
                myCalendar2.set(Calendar.MONTH, monthOfYear)
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                shearRideTv.text = " $dayOfMonth / $monthOfYear / $year"
                shearRideTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            }
        shearRideCalender.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DialogTheme,
                date2,
                myCalendar2[Calendar.YEAR],
                myCalendar2[Calendar.MONTH],
                myCalendar2[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.show()
            shearRideTv.resources.getColor(R.color.black)
        }

        //Timerforshearmycar
        val mTimePicker1: TimePickerDialog
        val mcurrentTime1 = Calendar.getInstance()
        val hour1 = mcurrentTime1.get(Calendar.HOUR_OF_DAY)
        val minute1 = mcurrentTime1.get(Calendar.MINUTE)

        mTimePicker1 =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    shearMyCarTv.setText(String.format("%d : %d", hourOfDay, minute))
                }
            }, hour1, minute1, false)

        shearMyCarTimer.setOnClickListener({ v ->
            mTimePicker1.show()

        })

    }

    private fun showDialogNotificationAction(bottomSheetDialog: BottomSheetDialog) {
        bottomSheetDialog.show()
        val bottomSheetDialogFrameLayout =
            bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetDialogFrameLayout?.background = null
    }

    //    private fun bitmapDescriptorFromVector(context: Context1?, vectorResId: Int): BitmapDescriptor {
//        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
//        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
//        val bitmap =
//            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        vectorDrawable.draw(canvas)
//        return BitmapDescriptorFactory.fromBitmap(bitmap)
//    }
//    private fun googlemap(rootView:View){
//        val mapFragment =
//            childFragmentManager.findFragmentById(R.id.mapp) as SupportMapFragment?  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
//        mapFragment!!.getMapAsync { mMap ->
//            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//
//            mMap.clear() //clear old markers
//
//            val googlePlex = CameraPosition.builder()
//                .target(LatLng(30.3753, 69.3451))
//                .zoom(10f)
//                .bearing(0f)
//                .tilt(45f)
//                .build()
//
//            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null)
//
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(LatLng(30.3753, 69.3451))
//                    .title("")
//                    .icon(bitmapDescriptorFromVector(activity, R.drawable.meicon))
//            )
//        }
//    }
//    private fun googlemap2(rootView:View){
//    val mapFragment =
//            childFragmentManager.findFragmentById(R.id.mapp) as SupportMapFragment?
//    mapFragment!!.getMapAsync(OnMapReadyCallback { requireContext() })//clear old markers
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//    }
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Map_Activity.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)
        // 2
        map.addMarker(markerOptions)
        markerOptions.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.meicon)
            )
        )

    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
    }


    override fun onMarkerClick(p0: Marker): Boolean = false

    private fun requestrideApi() {
            val jsonObject = JSONObject()
            val date = requestRideTv.text.toString()
            val time = requestRideTimerTv.text.toString()
            val requiredseats = requiredSeats.text.toString()

            jsonObject.put("date", date)
            jsonObject.put("time", time)
            jsonObject.put("required_seats",requiredseats)


            val parameterService = ParameterService(requireContext(), this, this)
            parameterService.getData(jsonObject, AppURL.RIDEREQUESTUSER_URL)
    }
    private fun shareMyCarApi() {
        val jsonObject = JSONObject()
        val date = shearRideTv.text.toString()
        val time = shearMyCarTv.text.toString()
        val carmodel = carModel.text.toString()
        val noplate = noPlate.text.toString()
        val availableseats = availableSeats.text.toString()


        jsonObject.put("date", date)
        jsonObject.put("time", time)
        jsonObject.put("car_model",carmodel)
        jsonObject.put("no_plate",noplate)
        jsonObject.put("available_seats",availableseats)
        jsonObject.put("user_type","car owner")


        val parameterService = ParameterService(requireContext(), this, this)
        parameterService.getData(jsonObject, AppURL.RIDEREQUESTCAROWNER_URL)
        isBool=true
    }


    override fun getResponses(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            val message = `object`.getString("message")
            if (status == true) {
                if (isBool){
                    val gsonBuilder = GsonBuilder()
                    val gson: Gson = gsonBuilder.create()
                    val userData = gson.fromJson(`object`.toString(), RiderequestcarownerModel::class.java)
                    shearMemory.userId = userData.data.id
                    activity?.let {
                        val intent = Intent(it, HomeActivity::class.java)
                        startActivity(intent)
                        it.finish()
                    }
                    isBool=false
                }else{
                    val gsonBuilder = GsonBuilder()
                    val gson: Gson = gsonBuilder.create()
                    val userData = gson.fromJson(`object`.toString(), RiderequestuserModel::class.java)
                    shearMemory.userId = userData.data.id
                    activity?.let {
                        val intent = Intent(it, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            else {
                Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setMessage("Please fill all fields")
                    .setDuration(2000)
                    .autoHide(true)
                    .sneakError()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }




    override fun getError(o: Any?) {

    }


//
//    override fun getResponses(o: Any?) {
//        val `object` = o as JSONObject
//        try {
//            val status = `object`.getBoolean("status")
//            if (status) {
//                val gsonBuilder = GsonBuilder()
//                val gson: Gson = gsonBuilder.create()
//
//                }
//            }
//            else {
//                Sneaker.with(this) // Activity, Fragment or ViewGroup
//                    .setMessage("Please fill all fields")
//                    .setDuration(2000)
//                    .autoHide(true)
//                    .sneakError()
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//    }
}









