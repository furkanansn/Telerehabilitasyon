package com.safehouse.fizyoterapim.ui.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import com.safehouse.fizyoterapim.Firebase.Model.User
import com.safehouse.fizyoterapim.Firebase.Service.ChildService
import com.safehouse.fizyoterapim.Mvp.Presenter.AuthPresenter
import com.safehouse.fizyoterapim.R
import com.safehouse.fizyoterapim.Mvp.Contract.AuthContract
import com.safehouse.fizyoterapim.util.Extensions
import com.safehouse.fizyoterapim.util.OnSwipeTouchListener
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.toolbar
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), AuthContract.View {

    lateinit var authPresenter: AuthPresenter
    lateinit var myIntent: Intent
    var onam : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        authPresenter = AuthPresenter(this)
        authPresenter.start()
    }

    override fun init(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        prepareOnam()
        initializeInputs()
        date()
        register.setOnClickListener{

            inputs.forEach {
                val til = it.parent.parent as TextInputLayout
                til.isErrorEnabled = false

            }

            if(isFromValid){
                var user : User = User()
                user.name = name.text.toString()
                user.birthDay = birth.text.toString()
                user.phone = phone.text.toString()
                user.email = email.text.toString()
                user.tc = tc.text.toString()
                user.issues = sikayet.text.toString()
                authPresenter.auth(email.text.toString(),password.text.toString(),true,this,user)

            }

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
        val childService: ChildService = ChildService()
        progressBar.visibility = View.VISIBLE
        Handler().postDelayed(
                {
                   val name = name.text.toString()
                    val birtDay = birth.text.toString()
                    val phone = phone.text.toString()
                    val email = email.text.toString()
                    val tc = tc.text.toString()
                    val sikayet = sikayet.text.toString()

                        val user : User = User(firebaseUser?.uid,name,birtDay,email,phone,tc,sikayet)
                        childService.add(childService.USERCOLLECTIONNAME,user)

                        progressBar.visibility = View.GONE
                        myIntent=  Intent(this, MainActivity::class.java)
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(myIntent)

                },
                2000 // value in milliseconds,
        )
    }

    fun prepareOnam(){

        close.setOnClickListener{
            onamView.visibility = View.GONE
        }

        onamView.setOnTouchListener(object : OnSwipeTouchListener(this@RegisterActivity) {
            override fun onSwipeDown() {
                onamView.visibility = View.GONE
                super.onSwipeDown()
            }
        })

        onamFormOpenText.setOnClickListener {
            onamView.visibility = View.VISIBLE
        }

        onamCheckBox.setOnCheckedChangeListener{ buttonView, isChecked->
            if(isChecked){
                onam = true
            }
            else{
                onam = false
            }
            Log.d("TAG", "prepareOnam: " + onam.toString())
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun date() : String{
        var dateOfUser : String = ""
        birth.setOnClickListener{
            Log.d("dateClicked", "date: " + it.toString())

            val datePicker  = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Doğum tarihinizi seçin")
                    .build()
            datePicker.show(supportFragmentManager, datePicker.toString())

            datePicker.addOnPositiveButtonClickListener {
                val dateAsLong = Date(it)
                val dateAsString = SimpleDateFormat("dd-MM-yyyy").format(dateAsLong)

                birth.text = Editable.Factory.getInstance().newEditable(dateAsString)

            }
            datePicker.addOnNegativeButtonClickListener{
                datePicker.exitTransition
            }
            datePicker.addOnCancelListener {
                datePicker.exitTransition
            }



        }
            return dateOfUser
    }


    private val isFromValid: Boolean
    get() {
        inputs.forEach {
            if (it.text!!.isBlank()) {
                val tilParent = it.parent.parent as TextInputLayout
                tilParent.isErrorEnabled = true
                tilParent.error = "Bu alan boş bırakılamaz"
            }
            if(it.hint == "TC Kimlik Numarası"){

                if(it.text!!.length != 11){
                    val tilParent = it.parent.parent as TextInputLayout
                    tilParent.isErrorEnabled = true
                    tilParent.error = "Tc kimlik numarası eksik ya da hatalı"
                }

            }
            if(it.hint == "Telefon Numarası"){

                if(it.text!!.length < 10){
                    val tilParent = it.parent.parent as TextInputLayout
                    tilParent.isErrorEnabled = true
                    tilParent.error = "Telefon numarası eksik ya da hatalı"
                }

            }


            if(password.text!!.isNotEmpty()){
                if(!password.text.toString().equals(passwordAgain.text.toString())){
                    textInputLayout8.isErrorEnabled = true
                    textInputLayout8.error = "Parolalarınız eşleşmiyor"
                }
            }
            inputs.forEach {
                if((it.parent.parent as TextInputLayout).isErrorEnabled){
                    val til = it.parent.parent as TextInputLayout
                    scroolView.smoothScrollTo(0, til.top)
                    return false
                }
            }
        }

        if(onam == false){
            Extensions().showToast(
                "Devam edebilmek için Onam Formunu onaylamanız gerekmektedir",
                this
            )
            return false
        }

        return true
    }

    fun initializeInputs(){

        progressBar.visibility = View.GONE

        inputs.forEach {

            it.onFocusChangeListener = focusChangeListener
        }
    }
    private val focusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

        if (hasFocus) return@OnFocusChangeListener
        val textToValidate = (v as TextInputEditText).text
        val tilParent = v.parent.parent as TextInputLayout
        tilParent.isErrorEnabled = false
        tilParent.error = ""
        if (textToValidate!!.isBlank()) {
            tilParent.isErrorEnabled = true
            tilParent.error = "Bu alan boş bırakılamaz"
        }
        when (v) {
            email -> {
                if (textToValidate!!.isNotEmpty()) {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(textToValidate).matches()) {
                        tilParent.isErrorEnabled = true
                        tilParent.error = "Lütfen geçerli bir email adresi giriniz"
                    }
                }
            }
        }
    }


    private val inputs by lazy{
        listOf(
            name,
            birth,
            email,
            phone,
            tc,
            sikayet,
            password,
            passwordAgain

        )
    }
}


