package com.example.administrator.chadaodiancompany.viewImpl;

public interface IView {
    void showLoading();

    void hideLoading();

    void showError(Throwable throwable);
}
