package edu.zsk.terraquest;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExploreFragment extends Fragment {

    private EditText editTextEmail;
    private Button buttonSubscribe;
    private UserDatabaseHelper dbHelper;

    private RecyclerView recyclerViewHotels;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;

    private ViewPager2 reviewsViewPager;
    private ReviewPagerAdapter reviewPagerAdapter;
    private List<Review> reviewList;

    private EditText editTextDate;
    private EditText inputDestination;
    private EditText textPeople;
    private Button buttonSearch;

    private final Calendar calendar = Calendar.getInstance();

    private HotelApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);


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

        reviewsViewPager = view.findViewById(R.id.reviewsViewPager);
        reviewList = new ArrayList<>();
        reviewPagerAdapter = new ReviewPagerAdapter(reviewList);
        reviewsViewPager.setAdapter(reviewPagerAdapter);

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

            if(destination.isEmpty() || date.isEmpty() || people.isEmpty()) {
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
        loadReviews();

        EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        Button buttonSubscribe = view.findViewById(R.id.buttonSubscribe);

        buttonSubscribe.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();

            if (!email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Zapisz do newslettera
                saveNewsletterStatusToDatabase(1); // ustawienie newsletter = 1
                Toast.makeText(getContext(), "Zapisano do newslettera!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Podaj poprawny email!", Toast.LENGTH_SHORT).show();
            }
        });

        Button newsletterSection = view.findViewById(R.id.buttonSubscribe);

        Bundle args = getArguments();
        if (args != null && args.getBoolean("scrollToNewsletter", false)) {
            new Handler().postDelayed(() -> {
                newsletterSection.getParent().requestChildFocus(newsletterSection, newsletterSection);
            }, 1500); // odroczone, by ScrollView się załadował
        }

        return view;
    }

    private void saveNewsletterStatusToDatabase(int status) {
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("newsletter", status);

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", 0);
        String email = prefs.getString("email", null);

        if (email != null) {
            int rows = db.update("users", values, "email = ?", new String[]{email});
            if (rows == 0) {
                Toast.makeText(getContext(), "Nie udało się zapisać ustawień newslettera.", Toast.LENGTH_SHORT).show();
            }
        }

        db.close();
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
                        int nights = 1;

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

    private void loadReviews() {
        reviewList.clear();
        reviewList.add(new Review("★★★★★", "Świetny hotel!", "Polecam wszystkim.", "Jan Kowalski, 2025-05-20"));
        reviewList.add(new Review("★★★★★", "Świetny hotel!", "Polecam wszystkim.", "Jan Kowalski, 2025-05-20"));
        reviewList.add(new Review("★★★★★", "Świetny hotel!", "Polecam wszystkim.", "Jan Kowalski, 2025-05-20"));
        reviewList.add(new Review("★★★★★", "Świetny hotel!", "Polecam wszystkim.", "Jan Kowalski, 2025-05-20"));

        requireActivity().runOnUiThread(() -> reviewPagerAdapter.notifyDataSetChanged());
    }
}
