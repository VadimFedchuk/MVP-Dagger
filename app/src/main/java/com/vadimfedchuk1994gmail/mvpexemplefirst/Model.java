package com.vadimfedchuk1994gmail.mvpexemplefirst;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import com.vadimfedchuk1994gmail.mvpexemplefirst.common.User;
import com.vadimfedchuk1994gmail.mvpexemplefirst.common.UserTable;
import com.vadimfedchuk1994gmail.mvpexemplefirst.database.DbHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Model {

    private final DbHelper mDbHelper;

    public Model(DbHelper dbHelper) {
        this.mDbHelper = dbHelper;
    }

    public void loadUser(LoadUserCallback callback) {
        LoadUsersTask loadUsersTask = new LoadUsersTask(callback);
        loadUsersTask.execute();
    }

    public void addUser(ContentValues cv,CompleteCallback callback) {
        AddUserTask addUserTask = new AddUserTask(callback);
        addUserTask.execute(cv);
    }

    public void clear(CompleteCallback callback) {
        ClearUsersTask clearUserTask = new ClearUsersTask(callback);
        clearUserTask.execute();
    }

    interface LoadUserCallback {
        void onLoad(List<User>users);
    }

    interface CompleteCallback {
        void onComplete();
    }

    class LoadUsersTask extends AsyncTask<Void, Void, List<User>> {

        private final LoadUserCallback mCallback;

        LoadUsersTask(LoadUserCallback callback) {mCallback = callback;}

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> users = new LinkedList<>();
            Cursor cursor = mDbHelper.getReadableDatabase().query(UserTable.TABLE, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getLong(cursor.getColumnIndex(UserTable.COLUMN.ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.EMAIL)));
                users.add(user);
            }
            cursor.close();
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            if(mCallback != null) {
                mCallback.onLoad(users);
            }
        }
    }

    class AddUserTask extends AsyncTask<ContentValues, Void, Void> {

        private final CompleteCallback mCallback;

        AddUserTask (CompleteCallback callback) { mCallback = callback;}

        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            ContentValues cv = contentValues[0];
            mDbHelper.getWritableDatabase().insert(UserTable.TABLE, null, cv);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mCallback != null) {
                mCallback.onComplete();
            }
        }
    }

    class ClearUsersTask extends AsyncTask<Void, Void, Void> {

        private final CompleteCallback callback;

        ClearUsersTask(CompleteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mDbHelper.getWritableDatabase().delete(UserTable.TABLE, null, null);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }
}
