package com.vadimfedchuk1994gmail.mvpexemplefirst;

import android.service.autofill.UserData;

import com.vadimfedchuk1994gmail.mvpexemplefirst.common.User;

import java.util.List;

public interface UsersContractView {

    User getUserData();
    void showUsers(List<User> users);
    void showToast(int resId);
    void showProgress();
    void hideProgress();
}
