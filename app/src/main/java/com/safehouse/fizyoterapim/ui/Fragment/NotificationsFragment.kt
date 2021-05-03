package com.safehouse.fizyoterapim.ui.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.safehouse.fizyoterapim.Firebase.Model.Exercise
import com.safehouse.fizyoterapim.Firebase.Model.Form
import com.safehouse.fizyoterapim.Firebase.Model.Response
import com.safehouse.fizyoterapim.Firebase.Service.ChildService
import com.safehouse.fizyoterapim.R
import com.safehouse.fizyoterapim.ui.Adapter.ExerciseAdapter
import com.safehouse.fizyoterapim.ui.Adapter.FormAdapter
import java.util.ArrayList


class NotificationsFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val formRc : RecyclerView = root.findViewById(R.id.formRc)
        val textView : TextView = root.findViewById(R.id.noForm)
        val card : CardView = root.findViewById(R.id.materialCardViewForm)
        card.visibility = View.GONE
        formRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        var response : Response = Response()
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        db.collection(ChildService().FORMCOLLECTIONNAME)
                .whereEqualTo("userId",mAuth.currentUser.uid)
                .whereEqualTo("active",true)
                .addSnapshotListener(MetadataChanges.INCLUDE,
                    EventListener<QuerySnapshot?> { queryDocumentSnapshots, e ->
                        if (e == null) {

                            response.setObject(queryDocumentSnapshots!!.toObjects(Form::class.java))
                            Log.d("Form", "onCreateView: " + Gson().toJson(response.`object`))
                            val adapter = FormAdapter(
                                response.`object` as ArrayList<Form>?,
                                context
                            )
                            if((response.`object` as ArrayList<*>?)?.isEmpty() == true){
                                formRc.visibility = View.INVISIBLE
                                card.visibility = View.VISIBLE
                                textView.text = "Henüz kontrolünüz bulunmamaktadır :("
                            }else{
                                formRc.adapter = adapter
                            }
                        } else {
                            response.setErrorMessage(e.message)
                        }

                    })




        return root
    }
}