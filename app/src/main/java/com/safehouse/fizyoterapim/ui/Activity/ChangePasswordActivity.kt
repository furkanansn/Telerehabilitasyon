package com.safehouse.fizyoterapim.ui.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.safehouse.fizyoterapim.LocalData
import com.safehouse.fizyoterapim.R
import com.safehouse.fizyoterapim.util.Extensions
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        changePasswordButtonActivity.setOnClickListener {
            if(oldPassword.text.isNullOrEmpty()){
                textInputLayout9.isErrorEnabled = true
                textInputLayout9.error = "Bu alanı doldurmak zorunludur"
                return@setOnClickListener
            }
            if(password.text.isNullOrEmpty()){
                textInputLayout10.isErrorEnabled = true
                textInputLayout10.error = "Bu alanı doldurmak zorunludur"
                return@setOnClickListener
            }
            if(passwordAgain.text.isNullOrEmpty()){
                textInputLayout8.isErrorEnabled = true
                textInputLayout8.error = "Bu alanı doldurmak zorunludur"
                return@setOnClickListener
            }
            if(!passwordAgain.text.toString().trim().equals(password.text.toString().trim())){
                textInputLayout8.isErrorEnabled = true
                textInputLayout8.error = "Parolalarınız eşleşmiyor"
                return@setOnClickListener
            }
            if(password.text!!.length < 6){
                textInputLayout8.isErrorEnabled = true
                textInputLayout8.error = "Parolanız en az 6 karakter uzunluğunda olmalıdır"
                return@setOnClickListener
            }
            textInputLayout9.isErrorEnabled = false
            textInputLayout8.isErrorEnabled = false
            textInputLayout10.isErrorEnabled = false


            val user = FirebaseAuth.getInstance()
            val currentUser1 = user.currentUser
            user.signInWithEmailAndPassword(currentUser1.email, oldPassword.text.toString().trim())
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        val currentUser = user.currentUser
                        currentUser!!.updatePassword(passwordAgain.text.toString().trim()).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "Parolanız başarıyla değişti: " + "ok")
                                Extensions().showToast("Parolanız başarıyla değişti", this)
                                onBackPressed()
                            } else {
                                Log.d("TAG", "Parolanız çok zayıf lütfen güçlü bir parola giriniz: " + (task.exception?.message))
                                Extensions().showToast(
                                    "Parolanız çok zayıf lütfen güçlü bir parola giriniz",
                                    this
                                )
                            }
                        }

                    } else {
                        Log.d("TAG", "Beklenmedik bir sorunla karşılaşıldı çıkış yapıp tekrar deneyiniz: " + (task.exception?.message))
                        Extensions().showToast(

                            "Beklenmedik bir sorunla karşılaşıldı çıkış yapıp tekrar deneyiniz",
                            this
                        )
                        textInputLayout9.isErrorEnabled = true
                        textInputLayout9.error = "Parolanızı hatalı girdiniz"

                    }
                })



        }
    }
}