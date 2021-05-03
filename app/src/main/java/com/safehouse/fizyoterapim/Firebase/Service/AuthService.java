package com.safehouse.fizyoterapim.Firebase.Service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.safehouse.fizyoterapim.Firebase.Model.Response;

import java.util.Objects;
import java.util.concurrent.Executor;

public class AuthService {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;


    public boolean checkUserSignedIn(){

        firebaseUser = mAuth.getCurrentUser();

        return firebaseUser != null;

    }



    public Response signUp(String email, String password){
         Response response = new Response();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            firebaseUser = mAuth.getCurrentUser();
                            response.setSuccess(true);
                            response.setObject(firebaseUser);
                            response.setErrorMessage("");
                            Log.d("firebase sign up", "onComplete: ");
                        }
                        else{
                            response.setSuccess(false);
                            response.setObject(null);
                            response.setErrorMessage("");



                            try {
                                throw Objects.requireNonNull(task.getException());

                            } catch(FirebaseAuthWeakPasswordException e) {
                                response.setErrorMessage("Parolanız çok zayıf lütfen güçlü bir parola giriniz");

                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                response.setErrorMessage("Lütfen doğru bir Email girdiğinizden emin olun");

                            } catch(FirebaseAuthUserCollisionException e) {
                                response.setErrorMessage("Böyle bir kullanıcı zaten bulunmaktadır");

                            } catch(Exception e) {
                                response.setErrorMessage("Beklenmedik bir sorun oluştu lütfen tekrar deneyiniz");
                            }
                            Log.d("firebase sign up", "onFailed: " + task.getException());
                            Log.d("", new Gson().toJson(response));

                        }


                    }
                });

        return response;
    }

    public Response signIn(String email,String password){
        Response response = new Response();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            firebaseUser = mAuth.getCurrentUser();
                            response.setSuccess(true);
                            response.setObject(firebaseUser);
                            response.setErrorMessage("");
                            Log.d("firebase sign in", "onComplete: ");

                        }
                        else{
                            response.setSuccess(false);
                            response.setErrorMessage("Email adresiniz veya parolanız hatalı");
                            response.setObject(null);
                            Log.d("firebase sign in", "onFailed: " + task.getException());
                        }

                    }
                });
        return response;
    }

}
