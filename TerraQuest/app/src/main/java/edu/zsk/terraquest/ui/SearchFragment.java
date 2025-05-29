package edu.zsk.terraquest.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import edu.zsk.terraquest.hotels.Hotel;
import edu.zsk.terraquest.hotels.HotelAdapter;
import edu.zsk.terraquest.hotels.HotelApiService;
import edu.zsk.terraquest.R;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerViewHotels;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private List<Hotel> fullHotelList = new ArrayList<>();

    private EditText editTextDate;
    private EditText inputDestination;
    private EditText textPeople;
    private Button buttonSearch;
    private SeekBar seekBarPrice;
    private TextView textViewPriceValue;
    private LinearLayout layoutFilters;
    private Button buttonToggleFilters;

    private final Calendar calendar = Calendar.getInstance();
    private HotelApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerViewHotels = view.findViewById(R.id.recyclerViewHotels);
        recyclerViewHotels.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
        }, R.layout.hotel_item2);
        recyclerViewHotels.setAdapter(hotelAdapter);

        editTextDate = view.findViewById(R.id.editTextDate);
        editTextDate.setFocusable(false);
        editTextDate.setOnClickListener(v -> showDatePicker());

        inputDestination = view.findViewById(R.id.input_destination);
        textPeople = view.findViewById(R.id.text_people);
        buttonSearch = view.findViewById(R.id.button_search);
        seekBarPrice = view.findViewById(R.id.seekBarPrice);
        textViewPriceValue = view.findViewById(R.id.textViewPriceValue);
        layoutFilters = view.findViewById(R.id.layout_filters);
        layoutFilters.setVisibility(View.GONE);
        buttonToggleFilters = view.findViewById(R.id.button_toggle_filters);

        buttonToggleFilters.setOnClickListener(v -> {
            if (layoutFilters.getVisibility() == View.VISIBLE) {
                layoutFilters.setVisibility(View.GONE);
                buttonToggleFilters.setText("Pokaż filtry");
            } else {
                layoutFilters.setVisibility(View.VISIBLE);
                buttonToggleFilters.setText("Ukryj filtry");
            }
        });

        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewPriceValue.setText(progress + " zł");
                applyFilters();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        view.findViewById(R.id.button_sort_price_asc).setOnClickListener(v -> {
            hotelList.sort(Comparator.comparingInt(Hotel::getDiscountedPrice));
            hotelAdapter.notifyDataSetChanged();
        });

        view.findViewById(R.id.button_sort_price_desc).setOnClickListener(v -> {
            hotelList.sort((h1, h2) -> Integer.compare(h2.getDiscountedPrice(), h1.getDiscountedPrice()));
            hotelAdapter.notifyDataSetChanged();
        });

        view.findViewById(R.id.button_sort_newest).setOnClickListener(v -> {
            Collections.shuffle(hotelList);
            hotelAdapter.notifyDataSetChanged();
        });


        inputDestination.addTextChangedListener(new TextWatcher() {
            private long lastInputTime = 0;

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastInputTime = System.currentTimeMillis();
                inputDestination.postDelayed(() -> {
                    if (System.currentTimeMillis() - lastInputTime >= 500) {
                        String city = s.toString().trim();
                        if (city.length() >= 3) {
                            loadHotels(city);
                        }
                    }
                }, 600);
            }
            @Override public void afterTextChanged(Editable s) {}
        });


        buttonSearch.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = requireActivity().getCurrentFocus();
            if (imm != null && focusedView != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            }

            String destination = inputDestination.getText().toString().trim();
            String date = editTextDate.getText().toString().trim();
            String people = textPeople.getText().toString().trim();

            if (destination.isEmpty() || date.isEmpty() || people.isEmpty()) {
                Toast.makeText(getContext(), "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                return;
            }

            loadHotels(destination);
        });

        apiService = new HotelApiService();
        Bundle args = getArguments();
        if (args != null) loadHotels(args.getString("destination"));

        return view;
    }

    private void applyFilters() {
        int maxPrice = seekBarPrice.getProgress();
        List<Hotel> filtered = new ArrayList<>();
        for (Hotel hotel : fullHotelList) {
            if (hotel.getDiscountedPrice() <= maxPrice) {
                filtered.add(hotel);
            }
        }
        hotelList.clear();
        hotelList.addAll(filtered);
        hotelAdapter.notifyDataSetChanged();
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
                    fullHotelList.clear();

                    for (int i = 0; i < hotelsArray.length(); i++) {
                        JSONObject hotelJson = hotelsArray.getJSONObject(i);

                        String name = hotelJson.optString("PropertyName", "Brak nazwy");
                        String location = hotelJson.optString("PropertyAddress", "Nieznana lokalizacja");
                        String imageUrl = "https:" + hotelJson.optString("PropertyImageUrl", "");
                        double rawPrice = hotelJson.optDouble("ReferencePrice", 0);
                        double maxDiscount = hotelJson.optDouble("MaxDiscountPercent", 0);
                        String currency = hotelJson.optString("Currency", "USD");

                        int oldPrice = convertToPLN(rawPrice, currency);
                        int price = (int) ((oldPrice * (100 - maxDiscount)) / 100);
                        int nights = 1;

                        Hotel hotel = new Hotel(name, location, imageUrl, oldPrice, price, nights);
                        hotelList.add(hotel);
                    }

                    fullHotelList.addAll(hotelList);
                    if (isAdded() && getActivity() != null) {
                        getActivity().runOnUiThread(() -> hotelAdapter.notifyDataSetChanged());
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
