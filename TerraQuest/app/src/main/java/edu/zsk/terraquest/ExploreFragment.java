package edu.zsk.terraquest;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExploreFragment extends Fragment {

    private RecyclerView recyclerViewHotels;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private EditText editTextDate;
    private final Calendar calendar = Calendar.getInstance();

    private HotelApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerViewHotels = view.findViewById(R.id.recyclerViewHotels);
        recyclerViewHotels.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(hotelList);
        recyclerViewHotels.setAdapter(hotelAdapter);

        editTextDate = view.findViewById(R.id.editTextDate);
        editTextDate.setFocusable(false);
        editTextDate.setOnClickListener(v -> showDatePicker());

        // Inicjalizacja API
        apiService = new HotelApiService();

        // Pobierz hotele dla domyślnego miasta
        loadHotels("Tokio");

        return view;
    }

    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateEditText();
        };

        new DatePickerDialog(
                getContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateDateEditText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        editTextDate.setText(sdf.format(calendar.getTime()));
    }

    private void loadHotels(String cityName) {
        hotelList.clear();
        hotelAdapter.notifyDataSetChanged();

        apiService.getHotelsForCity(cityName, new HotelApiService.HotelApiCallback() {
            @Override
            public void onSuccess(String hotelsJsonString) {
                try {
                    JSONArray hotelsArray = new JSONArray(hotelsJsonString);

                    for (int i = 0; i < hotelsArray.length(); i++) {
                        JSONObject hotelJson = hotelsArray.getJSONObject(i);

                        String name = hotelJson.optString("PropertyName", "Brak nazwy");
                        String location = hotelJson.optString("PropertyAddress", "Nieznana lokalizacja");
                        String imageUrl = "https:" + hotelJson.optString("PropertyImageUrl", "");
                        int price = (int) hotelJson.optDouble("ReferencePrice", 0);
                        int oldPrice = price + 200;
                        int nights = 1; // Brak info w API – ustaw domyślnie 1

                        Hotel hotel = new Hotel(name, location, imageUrl, oldPrice, price, nights);
                        hotelList.add(hotel);
                    }

                    requireActivity().runOnUiThread(() -> hotelAdapter.notifyDataSetChanged());

                } catch (Exception e) {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Błąd przetwarzania danych", Toast.LENGTH_SHORT).show()
                    );
                }
            }


            @Override
            public void onError(String errorMessage) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Błąd API: " + errorMessage, Toast.LENGTH_LONG).show()
                );
            }
        });
    }
}
