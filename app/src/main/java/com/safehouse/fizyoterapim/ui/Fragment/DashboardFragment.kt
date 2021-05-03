package com.safehouse.fizyoterapim.ui.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.safehouse.fizyoterapim.Firebase.Model.Meet
import com.safehouse.fizyoterapim.Firebase.Model.Response
import com.safehouse.fizyoterapim.Firebase.Service.ChildService
import com.safehouse.fizyoterapim.R
import sun.bob.mcalendarview.MCalendarView
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.vo.DateData
import java.text.SimpleDateFormat
import java.util.*


class DashboardFragment : Fragment() {
    lateinit var meet : MutableList<Meet>


    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        var mCalendarView : MCalendarView = root.findViewById(R.id.calenderView)
        mCalendarView.setMarkedStyle(MarkStyle.BACKGROUND)


        var response : Response = Response()
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        db.collection(ChildService().RANDEVUCOLLECTIONNAME)
            .whereEqualTo("userId", mAuth.currentUser.uid)
            .addSnapshotListener(
                MetadataChanges.INCLUDE,
                EventListener<QuerySnapshot?> { queryDocumentSnapshots, e ->

                    if (e == null) {

                        meet = queryDocumentSnapshots!!.toObjects(Meet::class.java)

                        meet.forEach {
                            mCalendarView.markedDates.all.clear()
                            var date: Date = SimpleDateFormat("dd-MM-yyyy").parse(it.date)
                            var calendar: Calendar = Calendar.getInstance()
                            calendar.time = date
                            var dateData: DateData = DateData(
                                calendar.get(Calendar.YEAR), calendar.get(
                                    Calendar.MONTH
                                ) + 1, calendar.get(Calendar.DAY_OF_MONTH)
                            )

                            Log.d("DATE", "onCreateView: " + Gson().toJson(dateData))
                            mCalendarView.markDate(
                                dateData.year,
                                dateData.month,
                                dateData.day
                            )
                        }
                    } else {
                        response.setErrorMessage(e.message)
                    }

                })

        mCalendarView.setOnDateClickListener(object : OnDateClickListener() {
            override fun onDateClick(view: View, otherDate: DateData) {
                meet.forEach {
                    var date: Date = SimpleDateFormat("dd-MM-yyyy").parse(it.date)
                    var calendar: Calendar = Calendar.getInstance()
                    calendar.time = date
                    var dateData: DateData = DateData(
                        calendar.get(Calendar.YEAR), calendar.get(
                            Calendar.MONTH
                        ) + 1, calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    if (dateData == otherDate) {
                        val builder = AlertDialog.Builder(context)
                        // Set the alert dialog title
                        builder.setTitle("Randevu Detayı")
                        // Display a message on alert dialog
                        builder.setMessage(it.description)
                        // Set a positive button and its click listener on alert dialog
                        builder.setPositiveButton("Tamam") { dialog, which ->
                            dialog.dismiss()

                        }
                        // Finally, make the alert dialog using builder
                        val dialog: AlertDialog = builder.create()
                        // Display the alert dialog on app interface
                        dialog.show()

                    }
                }
            }
        })







        return root
    }

}