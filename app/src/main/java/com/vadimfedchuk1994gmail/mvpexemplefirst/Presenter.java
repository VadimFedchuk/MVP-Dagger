package com.vadimfedchuk1994gmail.mvpexemplefirst;

import android.content.ContentValues;
import android.text.TextUtils;

import com.vadimfedchuk1994gmail.mvpexemplefirst.common.User;
import com.vadimfedchuk1994gmail.mvpexemplefirst.common.UserTable;
import com.vadimfedchuk1994gmail.mvpexemplefirst.database.DbHelper;

import java.util.List;

public class Presenter {

    private UsersContractView view;
    private final Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public void attachView(UsersContractView activity) {
        this.view = activity;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        loadUsers();
    }

    public void loadUsers() {
        model.loadUser(new Model.LoadUserCallback() {
            @Override
            public void onLoad(List<User> users) {
                view.showUsers(users);
            }
        });
    }

    public void add() {
        User userData = view.getUserData();
        if (TextUtils.isEmpty(userData.getName()) || TextUtils.isEmpty(userData.getEmail())) {
            view.showToast(R.string.empty_values);
            return;
        }

        ContentValues contentValues = new ContentValues(2);
        contentValues.put(UserTable.COLUMN.NAME, userData.getName());
        contentValues.put(UserTable.COLUMN.EMAIL, userData.getEmail());
        view.showProgress();

        model.addUser(contentValues, new Model.CompleteCallback() {
            @Override
            public void onComplete() {
                view.hideProgress();
                loadUsers();
            }
        });
    }

    public void clear() {
        view.showProgress();
        model.clear(new Model.CompleteCallback() {
            @Override
            public void onComplete() {
                view.hideProgress();
                loadUsers();
            }
        });
    }
}
