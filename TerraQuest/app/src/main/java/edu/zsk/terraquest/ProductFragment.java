package edu.zsk.terraquest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class ProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        ImageView imageView = view.findViewById(R.id.hotelImage);
        TextView nameText = view.findViewById(R.id.hotelName);
        TextView locationText = view.findViewById(R.id.hotelLocation);
        TextView priceText = view.findViewById(R.id.discountedPrice);
        TextView oldPriceText = view.findViewById(R.id.originalPrice);
        TextView nightsText = view.findViewById(R.id.nightsCount);

        Bundle args = getArguments();
        if (args != null) {
            nameText.setText(args.getString("name"));
            locationText.setText(args.getString("location"));
            priceText.setText(args.getInt("price") + " zł");
            oldPriceText.setText(args.getInt("oldPrice") + " zł");
            nightsText.setText("na " + args.getInt("nights") + " nocy");

            Glide.with(this)
                    .load(args.getString("imageUrl"))
                    .into(imageView);
        }

        return view;
    }
}