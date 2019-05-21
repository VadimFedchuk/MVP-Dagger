package com.vadimfedchuk1994gmail.mvpexemplefirst;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.vadimfedchuk1994gmail.mvpexemplefirst.common.User;
import com.vadimfedchuk1994gmail.mvpexemplefirst.common.UserAdapter;
import com.vadimfedchuk1994gmail.mvpexemplefirst.database.DbHelper;

import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements UsersContractView{

    private UserAdapter mUserAdapter;
    private EditText editTextName;
    private EditText editTextEmail;
    private ProgressDialog progressDialog;

    private Presenter presenter;
    @Inject DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mDbHelper = App.getComponent().getDbHelper();

        App.getComponent().injectsMainActivity(this);
        init();
    }

    private void init() {

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);

        findViewById(R.id.add).setOnClickListener( v -> presenter.add());

        findViewById(R.id.clear).setOnClickListener( v -> presenter.clear());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mUserAdapter = new UserAdapter();

        RecyclerView userList = findViewById(R.id.list);
        userList.setLayoutManager(layoutManager);
        userList.setAdapter(mUserAdapter);


        Model model = new Model(mDbHelper);
        presenter = new Presenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();
        Log.i("TEST", presenter.hashCode() + " presenter.hashCode");
    }

    public User getUserData() {
        User userData = new User();
        userData.setName(editTextName.getText().toString());
        userData.setEmail(editTextEmail.getText().toString());
        return userData;
    }

    public void showUsers(List<User> users) {
        mUserAdapter.setData(users);
    }

    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait));
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
