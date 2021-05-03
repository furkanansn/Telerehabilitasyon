package com.safehouse.fizyoterapim.Mvp.Contract;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.safehouse.fizyoterapim.Firebase.Model.User;

public interface AuthContract {

    public interface View{
        void init();
        void showProgress();
        void hideProgress();
        void showError(String error);
        void navigate(FirebaseUser firebaseUser);
    }

    public interface Presenter{
        void auth(String email, String password, boolean isRegister, Context context, User user);
        void start();
    }

}
