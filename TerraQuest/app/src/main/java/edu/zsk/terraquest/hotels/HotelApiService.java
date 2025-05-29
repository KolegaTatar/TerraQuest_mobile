package edu.zsk.terraquest.hotels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotelApiService {

    private static final String TAG = "HotelApiService";

    private final OkHttpClient client = new OkHttpClient();

    public interface HotelApiCallback {
        void onSuccess(String hotelsJsonArrayString);
        void onError(String errorMessage);
    }

    public void getHotelsForCity(String cityName, HotelApiCallback callback) {
        new Thread(() -> {
            try {
                String urlCity = "https://api.travsrv.com/widgetapi.aspx?type=cities&name=" + cityName + "&count=1";

                Request requestCity = new Request.Builder()
                        .url(urlCity)
                        .build();

                Response responseCity = client.newCall(requestCity).execute();
                if (!responseCity.isSuccessful()) {
                    callback.onError("Błąd pobierania miasta: " + responseCity.message());
                    return;
                }

                String cityResponseStr = responseCity.body().string();
                JSONArray cityArray = new JSONArray(cityResponseStr);

                if (cityArray.length() == 0) {
                    callback.onError("Nie znaleziono miasta: " + cityName);
                    return;
                }

                JSONObject cityObj = cityArray.getJSONObject(0);
                int locationId = cityObj.optInt("LocationId", -1);
                if (locationId == -1) {
                    callback.onError("Nie znaleziono LocationId");
                    return;
                }


                String urlHotels = "https://api.travsrv.com/Content.aspx?type=findfeaturedhoteldeals&locationid=" + locationId;

                Request requestHotels = new Request.Builder()
                        .url(urlHotels)
                        .build();

                Response responseHotels = client.newCall(requestHotels).execute();
                if (!responseHotels.isSuccessful()) {
                    callback.onError("Błąd pobierania hoteli: " + responseHotels.message());
                    return;
                }

                String hotelsResponseStr = responseHotels.body().string();
                Log.d("API_RESPONSE", hotelsResponseStr);

                callback.onSuccess(hotelsResponseStr);

            } catch (IOException e) {
                Log.e(TAG, "IO error: ", e);
                callback.onError("Błąd sieci: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Inny błąd: ", e);
                callback.onError("Błąd parsowania: " + e.getMessage());
            }
        }).start();
    }
}
