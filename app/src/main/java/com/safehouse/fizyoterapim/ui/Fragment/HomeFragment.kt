package com.safehouse.fizyoterapim.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.safehouse.fizyoterapim.Firebase.Model.Exercise
import com.safehouse.fizyoterapim.Firebase.Model.Response
import com.safehouse.fizyoterapim.Firebase.Service.ChildService
import com.safehouse.fizyoterapim.R
import com.safehouse.fizyoterapim.ui.Adapter.ExerciseAdapter
import java.util.*


class HomeFragment : Fragment() {
    lateinit var myIntent: Intent


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

     val root = inflater.inflate(R.layout.fragment_home, container, false)

        initialRc(root)
        return root
    }




    fun initialRc(root: View) {
        val exerciseRc : RecyclerView = root.findViewById(R.id.exerciseRc)
        val progressBarHome : ProgressBar = root.findViewById(R.id.progressBarHome)
        val textView : TextView = root.findViewById(R.id.noExercise)
        val card : CardView = root.findViewById(R.id.materialCardViewExercise)
        card.visibility = View.GONE
        exerciseRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var response :Response = Response()
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        db.collection(ChildService().EGZERSİZCOLLECTIONNAME)
            .whereEqualTo("userId",mAuth.currentUser.uid)
            .addSnapshotListener(MetadataChanges.INCLUDE,
                EventListener<QuerySnapshot?> { queryDocumentSnapshots, e ->
                    progressBarHome.visibility = View.GONE
                    if (e == null) {

                        response.setObject(queryDocumentSnapshots!!.toObjects(Exercise::class.java))
                        val adapter = ExerciseAdapter(
                            response.`object` as ArrayList<Exercise>?,
                            context
                        )
                        if((response.`object` as ArrayList<*>?)?.isEmpty() == true){
                            exerciseRc.visibility = View.INVISIBLE
                            card.visibility = View.VISIBLE
                            textView.text = "Henüz egzersiziniz bulunmamaktadır :("
                        }else{
                            exerciseRc.adapter = adapter
                        }

                    } else {
                        response.setErrorMessage(e.message)
                    }

                })
    }


}