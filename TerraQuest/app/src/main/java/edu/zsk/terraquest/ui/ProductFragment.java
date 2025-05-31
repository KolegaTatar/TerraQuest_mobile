package edu.zsk.terraquest.ui;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.zsk.terraquest.R;
import edu.zsk.terraquest.reviews.Review;
import edu.zsk.terraquest.reviews.ReviewPagerAdapter;
import edu.zsk.terraquest.database.DatabaseHelper;

public class ProductFragment extends Fragment {


    private ViewPager2 reviewsViewPager;
    private ReviewPagerAdapter reviewPagerAdapter;
    private List<Review> reviewList;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private String opisRozwijany = "Piękna willa z widokiem na góry, duży ogród, jacuzzi.";
    private String opisInfo = "Nowoczesna willa z 4 sypialniami, salonem i dużą kuchnią.";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        ImageView imageView = view.findViewById(R.id.hotelImage);
        TextView nameText = view.findViewById(R.id.hotelName);
        TextView locationText = view.findViewById(R.id.hotelLocation);
        TextView priceText = view.findViewById(R.id.discountedPrice);
        TextView oldPriceText = view.findViewById(R.id.originalPrice);
        TextView nightsText = view.findViewById(R.id.nightsCount);
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", 0);
        String email = prefs.getString("email", null);


        TextView textOpis = view.findViewById(R.id.textOpis);
        TextView textInfoOpis = view.findViewById(R.id.textInfoOpis);
        textOpis.setText(opisRozwijany);
        textInfoOpis.setText(opisInfo);


        TextView labelOpis = view.findViewById(R.id.labelOpis);
        TextView labelArrow = view.findViewById(R.id.labelArrow);
        textOpis.setVisibility(View.GONE);

        View.OnClickListener toggleOpis = v -> {
            if (textOpis.getVisibility() == View.GONE) {
                textOpis.setVisibility(View.VISIBLE);
                labelArrow.setText("▲");
            } else {
                textOpis.setVisibility(View.GONE);
                labelArrow.setText("▼");
            }
        };
        labelOpis.setOnClickListener(toggleOpis);
        labelArrow.setOnClickListener(toggleOpis);


        Bundle args = getArguments();
        if (args != null) {
            nameText.setText(args.getString("name"));
            locationText.setText(args.getString("location"));
            priceText.setText(args.getInt("price") + " zł");
            oldPriceText.setText(args.getInt("oldPrice") + " zł");
            nightsText.setText("na " + args.getInt("nights") + " noc");

            Glide.with(this)
                    .load(args.getString("imageUrl"))
                    .into(imageView);
        }

        reviewsViewPager = view.findViewById(R.id.reviewsViewPager);
        reviewList = new ArrayList<>();
        reviewPagerAdapter = new ReviewPagerAdapter(reviewList);
        reviewsViewPager.setAdapter(reviewPagerAdapter);


        dbHelper = new DatabaseHelper(getContext());
        database = dbHelper.getReadableDatabase();

        loadReviewsFromDb();

        Button reserveButton = view.findViewById(R.id.btnReserve);

        reserveButton.setOnClickListener(v -> {
            if (email == null) {
                Toast.makeText(getContext(), "Zaloguj się, by zarezerwować hotel", Toast.LENGTH_SHORT).show();
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();

            String checkIn = sdf.format(calendar.getTime());

            calendar.add(Calendar.DAY_OF_MONTH, 3);
            String checkOut = sdf.format(calendar.getTime());

            int userId = getUserIdByEmail(email);
            if (userId == -1) {
                Toast.makeText(getContext(), "Zaloguj się, by zarezerwować hotel", Toast.LENGTH_SHORT).show();
                return;
            }
            String hotelName = nameText.getText().toString();
            int guests = 2;
            String location = locationText.getText().toString();
            int prize = args.getInt("oldPrice");
            int newPrize = args.getInt("price");

            SQLiteDatabase writableDb = dbHelper.getWritableDatabase();

            Cursor checkCursor = writableDb.rawQuery(
                    "SELECT COUNT(*) FROM reservation WHERE user_id = ? AND hotel_name = ? AND check_in = ? AND check_out = ?",
                    new String[]{String.valueOf(userId), hotelName, checkIn, checkOut}
            );

            if (checkCursor.moveToFirst() && checkCursor.getInt(0) > 0) {
                Toast.makeText(getContext(), "Masz już taką rezerwację!", Toast.LENGTH_SHORT).show();
                checkCursor.close();
                return;
            }
            checkCursor.close();

            writableDb.execSQL(
                    "INSERT INTO reservation (user_id, hotel_name, prize, new_prize, hotel_location, check_in, check_out, guests) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{userId, hotelName, prize, newPrize, location, checkIn, checkOut, guests}
            );

            Toast.makeText(getContext(), "Rezerwacja dodana!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
    private int getUserIdByEmail(String email) {
        Cursor cursor = database.rawQuery("SELECT id FROM users WHERE email = ?", new String[]{email});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }
    private void loadReviewsFromDb() {
        reviewList.clear();

        Cursor cursor = database.rawQuery("SELECT * FROM reviews_terraQuest ORDER BY date DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int ratingInt = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));
                String rating = "★★★★★".substring(0, ratingInt);

                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String reviewer = cursor.getString(cursor.getColumnIndexOrThrow("reviewer"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                Review review = new Review(rating, title, description, reviewer + ", " + date);
                reviewList.add(review);
            } while (cursor.moveToNext());
        }
        cursor.close();

        requireActivity().runOnUiThread(() -> reviewPagerAdapter.notifyDataSetChanged());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }


}