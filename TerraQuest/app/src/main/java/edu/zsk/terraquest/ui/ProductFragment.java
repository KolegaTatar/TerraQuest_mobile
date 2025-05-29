package edu.zsk.terraquest.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

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
        return view;
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