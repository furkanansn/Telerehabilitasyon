package com.safehouse.fizyoterapim.ui.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.safehouse.fizyoterapim.Firebase.Model.Response
import com.safehouse.fizyoterapim.Firebase.Model.User
import com.safehouse.fizyoterapim.Firebase.Service.ChildService
import com.safehouse.fizyoterapim.R


class ProfileFragment : Fragment() {
    var user : User = User()
    val mAuth = FirebaseAuth.getInstance()
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val nameHeader : TextView = root.findViewById(R.id.nameHeader)
        val name : TextView = root.findViewById(R.id.name)
        val birth : TextView = root.findViewById(R.id.birth)
        val tc : TextView = root.findViewById(R.id.tc)
        val issues : TextView = root.findViewById(R.id.issues)
        val email : TextView = root.findViewById(R.id.email)
        val phone : TextView = root.findViewById(R.id.phone)
        val changePassword : Button = root.findViewById(R.id.changePasswordButton)
        val logOutButton : Button = root.findViewById(R.id.logOutButton)
        var response : Response = Response()
        val db = FirebaseFirestore.getInstance()

        db.collection(ChildService().USERCOLLECTIONNAME)
            .whereEqualTo("id", mAuth.currentUser.uid)
            .addSnapshotListener(
                    MetadataChanges.INCLUDE,
                    EventListener<QuerySnapshot?> { queryDocumentSnapshots, e ->

                        if (e == null) {

                            if (queryDocumentSnapshots != null) {

                                user = queryDocumentSnapshots.toObjects(User::class.java)[0]
                                Log.d("TAG", "queryDocumentSnapshots: " + Gson().toJson(user))
                                nameHeader.text = "Kişisel Bilgiler"
                                name.text = user.name
                                birth.text = user.birthDay
                                tc.text = user.tc
                                issues.text = user.issues
                                email.text = user.email
                                phone.text = user.phone


                            }

                        } else {
                            Log.d("TAG", "error: " + Gson().toJson(e.message))
                            response.setErrorMessage(e.message)
                        }

                    })

        changePassword.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(context, ChangePasswordActivity::class.java)
            it.context.startActivity(intent)
        }
        logOutButton.setOnClickListener {
            mAuth.signOut()
            val bundle = Bundle()
            val intent = Intent(context, SplashActivity::class.java)
            requireContext().startActivity(intent)
        }


        return root
    }




}