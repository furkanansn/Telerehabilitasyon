package com.safehouse.fizyoterapim.Mvp.Contract;

import com.safehouse.fizyoterapim.Firebase.Model.Response;

public interface HomeContract {

    public interface View{
        void init();
        void showProgress();
        void hideProgress();
        Response doneJob();
    }

    public  interface  Presenter{
        void start();
        void fetchExercices();
    }
}
