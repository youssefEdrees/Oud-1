package com.example.oud;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.oud.api.OudApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huxq17.download.Pump;
import com.huxq17.download.core.DownloadInfo;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class OudUtils {

    private static final String TAG = OudUtils.class.getSimpleName();

    public static OudApi instantiateRetrofitOudApi(String baseUrl) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(OudUtils.getGson()))
                .build();


        return retrofit.create(OudApi.class);

    }


    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {

            long t1 = System.nanoTime();
            Request request = chain.request();

            if ((Constants.SERVER_CONNECTION_AWARE_LOG_SETTINGS & Constants.SENDING) == Constants.SENDING) {
                Log.i(TAG, String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
            }

            long t2 = System.nanoTime();
            Response response = chain.proceed(request);

            if ((Constants.SERVER_CONNECTION_AWARE_LOG_SETTINGS & Constants.RECEIVING) == Constants.RECEIVING) {
                Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            }


            final String responseString = new String(response.body().bytes());
            if ((Constants.SERVER_CONNECTION_AWARE_LOG_SETTINGS & Constants.JSON_RESPONSE) == Constants.JSON_RESPONSE) {
                Log.i(TAG, "Response for " + response.request().url()+ ": " + responseString);
            }

            return  response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseString))
                    .build();
        }
    }

    public static <T> String commaSeparatedListQueryParameter(ArrayList<T> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : list) {
            stringBuilder.append(item.toString());
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static <T> String commaSeparatedListQueryParameter(T[] list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : list) {
            stringBuilder.append(item.toString());
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static void saveUserData(View v, String token, String userId) {

        SharedPreferences prefs = v.getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        token = "Bearer " + token;

        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.SHARED_PREFERENCES_TOKEN_NAME, token);
        prefsEditor.putString(Constants.SHARED_PREFERENCES_USER_ID_NAME, userId);

        prefsEditor.apply();    //token saved in shared preferences

    }

    public static void saveUserData(Context context, String token, String userId) {

        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        token = "Bearer " + token;

        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.SHARED_PREFERENCES_TOKEN_NAME, token);
        prefsEditor.putString(Constants.SHARED_PREFERENCES_USER_ID_NAME, userId);
        prefsEditor.apply();    //token saved in shared preferences

    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.SHARED_PREFERENCES_TOKEN_NAME, "");

    }

    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.SHARED_PREFERENCES_USER_ID_NAME, "");

    }

    public static Gson getGson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create();

        return gson;
    }

    public static String convertImageToFullUrl(String imageUrl) {

        if(Constants.MOCK)
            return imageUrl;

        imageUrl = (Constants.IMAGES_BASE_URL + imageUrl);

        for (int i = 0; i < imageUrl.length(); i++) {
            if (imageUrl.charAt(i) == (char) 92) {
                Log.e(TAG, imageUrl.charAt(i) + " at position: " + i);
                StringBuilder tempString = new StringBuilder(imageUrl);
                tempString.setCharAt(i, '/');
                imageUrl = tempString.toString();
            }
        }

        return imageUrl;
    }

    public static boolean isDownloaded(String trackId) {
        DownloadInfo downloadInfo = Pump.getDownloadInfoById(trackId);
        boolean downloaded = false;
        if (downloadInfo != null) {
            if (downloadInfo.getStatus() == DownloadInfo.Status.FINISHED)
                downloaded = true;

        }

        return downloaded;
    }
}