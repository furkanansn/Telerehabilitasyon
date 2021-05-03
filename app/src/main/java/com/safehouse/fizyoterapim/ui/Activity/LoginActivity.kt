package com.safehouse.fizyoterapim.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseUser
import com.safehouse.fizyoterapim.Firebase.Model.Response
import com.safehouse.fizyoterapim.Firebase.Service.AuthService
import com.safehouse.fizyoterapim.Mvp.Presenter.AuthPresenter
import com.safehouse.fizyoterapim.R
import com.safehouse.fizyoterapim.Mvp.Contract.AuthContract
import com.safehouse.fizyoterapim.util.Extensions
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthContract.View {
    lateinit var auth : AuthService
    lateinit var response: Response
    lateinit var authPresenter: AuthPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authPresenter = AuthPresenter(this)
        authPresenter.start()
    }

    override fun init(){
        progressBar.visibility = View.GONE

        login.setOnClickListener{
            authPresenter.signIn(loginId.text.toString().trim(),password.text.toString(),null)
        }

        register.setOnClickListener {
            val myIntent = Intent(this,RegisterActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showError(error: String?) {
        if (error != null) {
            Extensions().showToast(error,this)
        }
    }

    override fun navigate(firebaseUser: FirebaseUser?) {
        val myIntent = Intent(this,MainActivity::class.java)
        startActivity(myIntent)
    }





}