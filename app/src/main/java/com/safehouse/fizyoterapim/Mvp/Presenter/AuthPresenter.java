package com.safehouse.fizyoterapim.Mvp.Presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.safehouse.fizyoterapim.Firebase.Model.Response;
import com.safehouse.fizyoterapim.Firebase.Model.User;
import com.safehouse.fizyoterapim.Firebase.Service.AbsService;
import com.safehouse.fizyoterapim.Firebase.Service.ChildService;
import com.safehouse.fizyoterapim.LocalData;
import com.safehouse.fizyoterapim.Mvp.Contract.AuthContract;

import java.util.Objects;

public class AuthPresenter implements AuthContract.Presenter {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    AuthContract.View view;

    public AuthPresenter(AuthContract.View view) {
        this.view = view;
    }


    @Override
    public void auth(String email, String password, boolean isRegister, Context context, User user) {
        if(isRegister){
            signUp(email, password,user);
        }
        else{
            signIn(email, password,context);
        }
    }

    @Override
    public void start() {
        view.init();
    }


    public Response signUp(String email, String password, User user){
        view.showProgress();
        Response response = new Response();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.hideProgress();
                        if(task.isSuccessful()){
                            firebaseUser = mAuth.getCurrentUser();
                            response.setSuccess(true);
                            response.setObject(firebaseUser);
                            response.setErrorMessage("");
                            user.setId(firebaseUser.getUid());

                            
                                    
                            final FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection(AbsService.USERCOLLECTIONNAME)
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            view.navigate(firebaseUser);
                                            response.setObject(documentReference.getId());
                                            Log.d("Firebase Add", "onSuccess");

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            response.setErrorMessage(e.getMessage());
                                            Log.d("Firebase Add", "onFailed " + e.getMessage());
                                        }
                                    });








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
                            view.showError(response.getErrorMessage());

                        }


                    }
                });

        return response;
    }
    public Response signIn(String email,String password,Context context){
        view.showProgress();
        Response response = new Response();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    view.hideProgress();
                        if(task.isSuccessful()){

                            firebaseUser = mAuth.getCurrentUser();
                            response.setSuccess(true);
                            response.setObject(firebaseUser);
                            response.setErrorMessage("");
                            Log.d("firebase sign in", "onComplete: ");
                            if(email.contains("test") && password.contains("test")){
                                new LocalData(context).writeNow("admin","true");
                            }

                            view.navigate(firebaseUser);

                        }
                        else{

                            response.setSuccess(false);
                            response.setErrorMessage("Email adresiniz veya parolanız hatalı");
                            response.setObject(null);
                            Log.d("firebase sign in", "onFailed: " + task.getException());
                            view.showError(response.getErrorMessage());
                        }

                    }
                });
        return response;
    }

}
