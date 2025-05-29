package edu.zsk.terraquest.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import edu.zsk.terraquest.R;
import edu.zsk.terraquest.database.UserDatabaseHelper;

public class UserFragment extends Fragment {

    private SQLiteDatabase database;
    private TextView emailView, firstNameInput, textNewsletterStatus;
    private String email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        TextView currentTimeText = view.findViewById(R.id.current_time);

        Handler handler = new Handler();
        Runnable updateClock = new Runnable() {
            @Override
            public void run() {
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                currentTimeText.setText("Aktualna godzina: " + currentTime);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(updateClock);

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", 0);
        email = prefs.getString("email", null);

        firstNameInput = view.findViewById(R.id.user_name);
        emailView = view.findViewById(R.id.user_email);
        textNewsletterStatus = view.findViewById(R.id.textNewsletterStatus);

        emailView.setText(email);

        loadUserData();

        LinearLayout saveBtn = view.findViewById(R.id.profile_update);
        LinearLayout logoutBtn = view.findViewById(R.id.logout_button);

        saveBtn.setOnClickListener(v -> {
            Context context = requireContext();

            // Tworzymy layout z dwoma EditTextami
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 40, 50, 10);

            EditText firstNameEdit = new EditText(context);
            firstNameEdit.setHint("Imię");

            EditText lastNameEdit = new EditText(context);
            lastNameEdit.setHint("Nazwisko");

            layout.addView(firstNameEdit);
            layout.addView(lastNameEdit);

            new android.app.AlertDialog.Builder(context)
                    .setTitle("Aktualizuj profil")
                    .setView(layout)
                    .setPositiveButton("Zapisz", (dialog, which) -> {
                        String fname = firstNameEdit.getText().toString().trim();
                        String lname = lastNameEdit.getText().toString().trim();

                        ContentValues values = new ContentValues();
                        values.put("first_name", fname);
                        values.put("last_name", lname);

                        int updated = database.update("users", values, "email = ?", new String[]{email});
                        if (updated > 0) {
                            Toast.makeText(context, "Zaktualizowano profil!", Toast.LENGTH_SHORT).show();
                            loadUserData(); //
                        } else {
                            Toast.makeText(context, "Błąd podczas aktualizacji.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Anuluj", null)
                    .show();
        });

        LinearLayout toNewsletterLayout = view.findViewById(R.id.toNewsletter);
        toNewsletterLayout.setOnClickListener(w -> {
            ExploreFragment exploreFragment = new ExploreFragment();

            Bundle args = new Bundle();
            args.putBoolean("scrollToNewsletter", true);
            exploreFragment.setArguments(args);

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, exploreFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        logoutBtn.setOnClickListener(v -> {
            prefs.edit().clear().apply();

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new LoginFragment());
            transaction.commit();

            Toast.makeText(getContext(), "Wylogowano", Toast.LENGTH_SHORT).show();
        });

        loadBookings(view);

        return view;
    }

    private void updateNewsletterStatusUI(int status) {
        if (status == 1) {
            textNewsletterStatus.setText("Newsletter aktywny");
            textNewsletterStatus.setTextColor(Color.parseColor("#4CAF50")); // zielony
        } else {
            textNewsletterStatus.setText("Newsletter nieaktywny");
            textNewsletterStatus.setTextColor(Color.RED);
        }
    }

    private void loadUserData() {
        Cursor cursor = database.rawQuery("SELECT first_name, last_name, newsletter FROM users WHERE email = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(0);
            String lastName = cursor.getString(1);
            int newsletterStatus = cursor.getInt(2);

            if(firstName.isEmpty() || lastName.isEmpty()){
                firstNameInput.setText("Zaktualizuj profil");
            }
            else{
                firstNameInput.setText(firstName + " " + lastName);
            }


            updateNewsletterStatusUI(newsletterStatus);
        } else {
            Toast.makeText(getContext(), "Nie znaleziono użytkownika!", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    private void loadBookings(View root) {
        LinearLayout bookingContainer = root.findViewById(R.id.booking_container);
        bookingContainer.removeAllViews();

        Cursor cursor = database.rawQuery(
                "SELECT hotel_name, hotel_location, prize, new_prize, check_in, check_out FROM reservation WHERE user_id = ?",
                new String[]{String.valueOf(getUserIdFromEmail(email))}
        );
        LayoutInflater inflater = LayoutInflater.from(getContext());

        while (cursor.moveToNext()) {
            View bookingView = inflater.inflate(R.layout.booking_item, bookingContainer, false);

            TextView title = bookingView.findViewById(R.id.booking_title);
            TextView subtitle = bookingView.findViewById(R.id.booking_subtitle);
            TextView oldPrice = bookingView.findViewById(R.id.booking_old_price);
            TextView newPrice = bookingView.findViewById(R.id.booking_new_price);
            TextView details = bookingView.findViewById(R.id.booking_details);
            ImageView arrow = bookingView.findViewById(R.id.arrow_icon);

            String hotelName = cursor.getString(0);
            String oldP = cursor.getString(1);
            String newP = cursor.getString(2);
            String location = cursor.getString(3);
            String checkIn = formatDate(cursor.getString(4));
            String checkOut = formatDate(cursor.getString(5));

            title.setText(cursor.getString(0)); // hotel_name
            subtitle.setText(cursor.getString(1)); // hotel_location
            oldPrice.setText(cursor.getString(2) + " zł"); // prize
            newPrice.setText(cursor.getString(3) + " zł"); // new_prize
            details.setText("Od: " + formatDate(cursor.getString(4)) + "\nDo: " + formatDate(cursor.getString(5))); // check_in/out

            bookingView.setOnClickListener(v -> {
                boolean visible = details.getVisibility() == View.VISIBLE;
                details.setVisibility(visible ? View.GONE : View.VISIBLE);
                arrow.setRotation(visible ? 0f : 180f);
            });

            bookingContainer.addView(bookingView);
        }

        cursor.close();
    }

    private int getUserIdFromEmail(String email) {
        Cursor cursor = database.rawQuery("SELECT id FROM users WHERE email = ?", new String[]{email});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }

    // pomocnicza metoda do formatowania daty
    private String formatDate(String dbDate) {
        try {
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date date = dbFormat.parse(dbDate);
            return displayFormat.format(date);
        } catch (Exception e) {
            return dbDate;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) database.close();
    }
}
