package com.freenow.android_demo.utils.network;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.freenow.android_demo.models.Driver;
import com.freenow.android_demo.models.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.freenow.android_demo.misc.Constants.LOG_TAG;
import static com.freenow.android_demo.misc.Constants.SOCKET_TIMEOUT;

public class HttpClient {

    private static final String RANDOM_USER_URL = "https://randomuser.me/api/";
    private final OkHttpClient mClient;
    private final JsonParser mJsonParser;

    public HttpClient() {
        mClient = new OkHttpClient.Builder().readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS).build();
        mJsonParser = new JsonParser();
    }

    public void fetchDrivers(final DriverCallback driverCallback) {
        int amount = 256;
        String seed = "23f8827e04239990";
        String url = RANDOM_USER_URL + "?results=" + amount + "&seed=" + seed;
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    ArrayList<Driver> drivers = getDrivers(responseBody.string());
                    Log.i(LOG_TAG, "Fetched successfully " + drivers.size() + " drivers.");
                    driverCallback.setDrivers(drivers);
                    driverCallback.run();
                }
            }
        });
    }

    public void fetchUser(String seed, final UserCallback userCallback) {
        String url = RANDOM_USER_URL + "?seed=" + seed;
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    userCallback.setUser(getUser(responseBody.string()));
                    userCallback.run();
                }
            }
        });
    }

    private ArrayList<Driver> getDrivers(String jsonResponse) {
        JsonObject jsonObject = mJsonParser.parse(jsonResponse).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");
        ArrayList<Driver> drivers = new ArrayList<>();
        for (JsonElement jsonElement :results) {
            JsonObject jsonUser = jsonElement.getAsJsonObject();
            JsonObject name = jsonUser.getAsJsonObject("name");
            String firstName = name.get("first").toString();
            String FIRSTNAME= firstName.replace("\"","");
            FIRSTNAME = FIRSTNAME.substring(0, 1).toUpperCase() + FIRSTNAME.substring(1);
            String lastName = name.get("last").toString();
            String LASTNAME= lastName.replace("\"","");
            LASTNAME = LASTNAME.substring(0, 1).toUpperCase() + LASTNAME.substring(1);
            String fullName = FIRSTNAME + " " + LASTNAME;
            String phone = jsonUser.get("cell").toString();
            String PHONE= phone.replace("\"","");
            JsonObject picture = jsonUser.getAsJsonObject("picture");
            String avatar = picture.get("large").toString();
            String AVATAR= avatar.replace("\"","");
            JsonObject location = jsonUser.getAsJsonObject("location");
            String street = location.get("street").toString();
            String STREET= street.replace("\"","");
            JsonObject registered = jsonUser.getAsJsonObject("registered");
            String date = registered.get("date").toString();
            String DATE= date.replace("\"","");
            Date registeredDate;
            try {
                registeredDate = new SimpleDateFormat("yyyy-MM-dd").parse(DATE);
            } catch (ParseException e) {
                e.printStackTrace();
                registeredDate = new Date(0);
            }
            drivers.add(new Driver(fullName, PHONE, AVATAR, STREET, registeredDate));
        }
        return drivers;
    }

    private User getUser(String jsonResponse) {
        JsonObject jsonObject = mJsonParser.parse(jsonResponse).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("results");
        JsonElement jsonElement = results.get(0);
        JsonObject jsonUser = jsonElement.getAsJsonObject();
        JsonObject login = jsonUser.getAsJsonObject("login");
        String username = login.get("username").toString();
        String USERNAME= username.replace("\"","");
        String salt = login.get("salt").toString();
        String SALT=salt.replace("\"","");
        String sha256 = login.get("sha256").toString();
        String SHA256=sha256.replace("\"","");
        return new User(USERNAME, SALT, SHA256);
    }

    public abstract static class DriverCallback implements Runnable {

        protected ArrayList<Driver> mDrivers;

        void setDrivers(ArrayList<Driver> drivers) {
            mDrivers = drivers;
        }

    }

    public abstract static class UserCallback implements Runnable {

        protected User mUser;

        void setUser(User user) {
            mUser = user;
        }

    }

}
