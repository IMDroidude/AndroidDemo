package com.freenow.android_demo.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freenow.android_demo.App;
import com.freenow.android_demo.dependencies.component.AppComponent;
import com.freenow.android_demo.utils.storage.SharedPrefStorage;

import javax.inject.Inject;

public class AuthenticatedActivity extends AppCompatActivity {

    @Inject
    SharedPrefStorage mSharedPrefStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = App.getApplicationContext(this).getAppComponent();
        appComponent.inject(this);
    }

    protected boolean isAuthenticated() {
        return mSharedPrefStorage.loadUser() != null;
    }

}
