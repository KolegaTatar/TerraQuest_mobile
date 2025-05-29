package edu.zsk.terraquest.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.zsk.terraquest.hotels.Hotel;
import edu.zsk.terraquest.hotels.HotelAdapter;
import edu.zsk.terraquest.hotels.HotelApiService;
import edu.zsk.terraquest.R;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerViewHotels;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;

    private EditText editTextDate;
    private EditText inputDestination;
    private EditText textPeople;
    private Button buttonSearch;


    private final Calendar calendar = Calendar.getInstance();
    private HotelApiService apiService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView cityImage1 = view.findViewById(R.id.cityImage1);
        Glide.with(this)
                .load(R.drawable.warsaw)
                .centerCrop()
                .into(cityImage1);

        ImageView cityImage2 = view.findViewById(R.id.cityImage2);
        Glide.with(this)
                .load(R.drawable.gdansk)
                .centerCrop()
                .into(cityImage2);

        ImageView cityImage3 = view.findViewById(R.id.cityImage3);
        Glide.with(this)
                .load(R.drawable.krakow)
                .centerCrop()
                .into(cityImage3);

        ImageView cityImage4 = view.findViewById(R.id.cityImage4);
        Glide.with(this)
                .load(R.drawable.poznan)
                .centerCrop()
                .into(cityImage4);

        ImageView cityImage5 = view.findViewById(R.id.cityImage5);
        Glide.with(this)
                .load(R.drawable.karpacz)
                .centerCrop()
                .into(cityImage5);

        ImageView inspirationImage = view.findViewById(R.id.image_inspiration);
        Glide.with(this)
                .load(R.drawable.austria)
                .centerCrop()
                .into(inspirationImage);
        inspirationImage.setOnClickListener(v -> {
            ExploreFragment newFragment = new ExploreFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        });

        ImageView inspirationImage2 = view.findViewById(R.id.image_inspiration2);
        Glide.with(this)
                .load(R.drawable.spain)
                .centerCrop()
                .into(inspirationImage2);
        inspirationImage2.setOnClickListener(v -> {
            ExploreFragment newFragment = new ExploreFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        });

        ImageView sale = view.findViewById(R.id.sale);
        Glide.with(this)
                .load(R.drawable.baner_weather)
                .centerCrop()
                .into(sale);

        Button learn_more = view.findViewById((R.id.learn_more));
        learn_more.setOnClickListener(v -> {
            LoginFragment newFragment = new LoginFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        });

        Button learn_more2 = view.findViewById((R.id.learn_more2));
        learn_more2.setOnClickListener(v -> {
            ExploreFragment newFragment = new ExploreFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .commit();
        });

        recyclerViewHotels = view.findViewById(R.id.recyclerViewHotels);
        recyclerViewHotels.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(hotelList, hotel -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", hotel.getName());
            bundle.putString("location", hotel.getLocation());
            bundle.putString("imageUrl", hotel.getImageUrl());
            bundle.putInt("price", hotel.getDiscountedPrice());
            bundle.putInt("oldPrice", hotel.getOriginalPrice());
            bundle.putInt("nights", hotel.getNights());

            ProductFragment productFragment = new ProductFragment();
            productFragment.setArguments(bundle);

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, productFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        recyclerViewHotels.setAdapter(hotelAdapter);

        editTextDate = view.findViewById(R.id.editTextDate);
        editTextDate.setFocusable(false);
        editTextDate.setOnClickListener(v -> showDatePicker());

        inputDestination = view.findViewById(R.id.input_destination);
        textPeople = view.findViewById(R.id.text_people);
        buttonSearch = view.findViewById(R.id.button_search);

        buttonSearch.setOnClickListener(v -> {
            String destination = inputDestination.getText().toString().trim();
            String date = editTextDate.getText().toString().trim();
            String people = textPeople.getText().toString().trim();

            if (destination.isEmpty() || date.isEmpty() || people.isEmpty()) {
                Toast.makeText(getContext(), "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("destination", destination);
            bundle.putString("date", date);
            bundle.putString("people", people);

            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setArguments(bundle);

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, searchFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        apiService = new HotelApiService();
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

                    hotelList.clear();

                    for (int i = 0; i < hotelsArray.length(); i++) {
                        JSONObject hotelJson = hotelsArray.getJSONObject(i);

                        String name = hotelJson.optString("PropertyName", "Brak nazwy");
                        String location = hotelJson.optString("PropertyAddress", "Nieznana lokalizacja");
                        String imageUrl = "https:" + hotelJson.optString("PropertyImageUrl", "");
                        double rawPrice = hotelJson.optDouble("ReferencePrice", 0);
                        double maxdiscountedPrice = hotelJson.optDouble("MaxDiscountPercent", 0);
                        String currency = hotelJson.optString("Currency", "USD");

                        int oldPrice = convertToPLN(rawPrice, currency);
                        int price = (int) ((oldPrice * (100 - maxdiscountedPrice)) / 100);
                        int nights = 1;

                        Hotel hotel = new Hotel(name, location, imageUrl, oldPrice, price, nights);
                        hotelList.add(hotel);
                    }

                    if (isAdded() && getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            hotelAdapter.notifyDataSetChanged();
                        });
                    }

                } catch (Exception e) {
                    if (isAdded() && getActivity() != null) {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Błąd przetwarzania danych", Toast.LENGTH_SHORT).show()
                        );
                    }
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


    private int convertToPLN(double price, String currency) {
        double exchangeRate;
        switch (currency) {
            case "USD":
                exchangeRate = 4.3;
                break;
            case "EUR":
                exchangeRate = 4.5;
                break;
            default:
                exchangeRate = 1.0;
        }
        return (int) (price * exchangeRate);
    }
}
