package com.safehouse.fizyoterapim.util

import android.content.Context
import android.widget.Toast

class Extensions {

    fun showToast(message : String, context: Context){

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }



}