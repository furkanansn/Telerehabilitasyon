package com.safehouse.fizyoterapim.ui.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.safehouse.fizyoterapim.Firebase.Model.VideoChat
import com.safehouse.fizyoterapim.R

class VideoChatFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.video_chat_fragment, container, false)

        val joinVideo : TextView = root.findViewById(R.id.joinVideo)
        joinVideo.text = "Henüz görüntülü konferansınız yok :("
        joinVideo.isClickable = false
        var videoChat : MutableList<VideoChat>
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        db.collection("video_chat")
            .whereEqualTo("userId", mAuth.currentUser.uid)
            .whereEqualTo("openLink", true)
            .addSnapshotListener(
                    MetadataChanges.INCLUDE,
                    EventListener<QuerySnapshot?> { queryDocumentSnapshots, e ->
                        if (e == null) {

                            videoChat = queryDocumentSnapshots!!.toObjects(VideoChat::class.java)
                            if (!videoChat.isEmpty()) {
                                joinVideo.text = "Görüntülü konferansa bağlanmak için tıkla"
                                joinVideo.isClickable = true
                                joinVideo.setOnClickListener {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoChat[0].link))
                                    startActivity(intent)
                                }
                            }


                        }

                    })

        val url = "https://api.whatsapp.com/send?phone=$+905449561307"
        val joinWhatsapp : TextView = root.findViewById(R.id.joinWhatsapp)

        joinWhatsapp.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


        return root
    }


}