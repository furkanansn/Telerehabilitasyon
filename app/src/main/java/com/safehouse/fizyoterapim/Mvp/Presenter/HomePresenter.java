package com.safehouse.fizyoterapim.Mvp.Presenter;

import com.safehouse.fizyoterapim.Mvp.Contract.HomeContract;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.init();
    }

    @Override
    public void fetchExercices() {

    }
}
