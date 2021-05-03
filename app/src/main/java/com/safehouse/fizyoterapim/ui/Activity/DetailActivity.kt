package com.safehouse.fizyoterapim.ui.Activity

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.safehouse.fizyoterapim.Firebase.Model.Exercise
import com.safehouse.fizyoterapim.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    lateinit var exercise : Exercise

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        exercise = intent?.getSerializableExtra("exercise") as Exercise

        exercise_description.text = exercise.description
        exercise_name.text = exercise.name
        Picasso.get().load(exercise.image1).fit().into(exercise_image1)
        Picasso.get().load(exercise.image2).fit().into(exercise_image2)
        Picasso.get().load(exercise.image3).fit().into(exercise_image3)
        exercise_video.setSource((exercise.video))
        toolbar.title = exercise.name
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


    }


}