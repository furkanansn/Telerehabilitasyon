package com.safehouse.fizyoterapim.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.safehouse.fizyoterapim.Firebase.Service.AuthService
import com.safehouse.fizyoterapim.R

class SplashActivity : AppCompatActivity() {

    lateinit var auth : AuthService
    var userSignIn : Boolean = false
    lateinit var myIntent : Intent

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = AuthService()

            logic()

    }

    fun logic(){

        if(checkUser()){
            navigateInsideApp()
        }
        else{
            navigateLogin()
        }

    }

    fun checkUser() : Boolean{
        return auth.checkUserSignedIn()
    }

    fun navigateLogin(){

        myIntent = Intent(this,LoginActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(myIntent)

    }

    fun navigateInsideApp(){
        myIntent=  Intent(this,MainActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(myIntent)
    }
}