package edu.zsk.terraquest;

import android.widget.Toast;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        View profileUpdate = view.findViewById(R.id.profile_update);
        View timeSettings = view.findViewById(R.id.time_settings);
        View newsActive = view.findViewById(R.id.news_active);
        View logoutButton = view.findViewById(R.id.logout_button);

        profileUpdate.setOnClickListener(v ->
                Toast.makeText(getContext(), "Aktualizacja profilu", Toast.LENGTH_SHORT).show());

        timeSettings.setOnClickListener(v ->
                Toast.makeText(getContext(), "Czas i godzina", Toast.LENGTH_SHORT).show());

        newsActive.setOnClickListener(v ->
                Toast.makeText(getContext(), "Historia rezerwacji", Toast.LENGTH_SHORT).show());

        logoutButton.setOnClickListener(v ->
                Toast.makeText(getContext(), "Wylogowano", Toast.LENGTH_SHORT).show());


        TextView nameTextView = view.findViewById(R.id.user_name);
        TextView emailTextView = view.findViewById(R.id.user_email);

        String firstName = "Jacek";
        String lastName = "Prokop";
        String email = "filiptogowno@gmail.com";

        nameTextView.setText(firstName + " " + lastName);
        emailTextView.setText(email);

        LinearLayout bookingContainer = view.findViewById(R.id.booking_container);
        addBookingEntry(inflater, bookingContainer,
                "Bergson Lubi w Dupe",
                "ul. Wiktora Tatrynowicza 69",
                "100.20 PLN",
                "505.43 PLN",
                "Gówno Gówno Gówno Gówno Gówno Gówno Gówno Gówno Gówno Gówno ");

        addBookingEntry(inflater, bookingContainer,
                "Niech to sie w koncu skonczy",
                "ul. Malpiszona Palacza 1",
                "100.20 PLN",
                "505.43 PLN",
                "Gówno Gówno Gówno Gówno Gówno Gówno Gówno Gówno Gówno Gówno ");

        return view;


    }

    private void addBookingEntry(LayoutInflater inflater, LinearLayout container,
                                 String hotelName, String address, String priceOld,
                                 String priceNew, String details) {

        View bookingView = inflater.inflate(R.layout.booking_item, container, false);

        TextView title = bookingView.findViewById(R.id.booking_title);
        TextView subtitle = bookingView.findViewById(R.id.booking_subtitle);
        TextView oldPrice = bookingView.findViewById(R.id.booking_old_price);
        TextView newPrice = bookingView.findViewById(R.id.booking_new_price);
        TextView detailsText = bookingView.findViewById(R.id.booking_details);
        View arrowIcon = bookingView.findViewById(R.id.arrow_icon);

        title.setText(hotelName);
        subtitle.setText(address);
        oldPrice.setText(priceOld);
        newPrice.setText(priceNew);
        detailsText.setText(details);

        bookingView.setOnClickListener(v -> {
            boolean isVisible = detailsText.getVisibility() == View.VISIBLE;

            if (isVisible) {
                detailsText.animate()
                        .alpha(0f)
                        .translationY(-10f)
                        .setDuration(200)
                        .withEndAction(() -> detailsText.setVisibility(View.GONE))
                        .start();

                arrowIcon.animate()
                        .rotation(0f)
                        .setDuration(200)
                        .start();
            } else {
                detailsText.setAlpha(0f);
                detailsText.setTranslationY(-10f);
                detailsText.setVisibility(View.VISIBLE);
                detailsText.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(200)
                        .start();

                arrowIcon.animate()
                        .rotation(180f)
                        .setDuration(200)
                        .start();
            }
        });

        container.addView(bookingView);
    }

}
