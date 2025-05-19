package edu.zsk.terraquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotelList;

    public HotelAdapter(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.hotelName.setText(hotel.getName());
        holder.hotelLocation.setText(hotel.getLocation());
        holder.nightsCount.setText(hotel.getNights() + " noce");
        holder.originalPrice.setText(hotel.getOriginalPrice() + "zł");
        holder.discountedPrice.setText(hotel.getDiscountedPrice() + "zł");

        // Załaduj obrazek hotelu z Glide
        Glide.with(holder.itemView.getContext())
                .load(hotel.getImageUrl())  // URL obrazka, musi być pełny (np. https://...)
                .into(holder.hotelImage);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView hotelImage;
        TextView hotelName, hotelLocation, nightsCount, originalPrice, discountedPrice;

        public HotelViewHolder(View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            hotelName = itemView.findViewById(R.id.hotelName);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            nightsCount = itemView.findViewById(R.id.nightsCount);
            originalPrice = itemView.findViewById(R.id.originalPrice);
            discountedPrice = itemView.findViewById(R.id.discountedPrice);
        }
    }
}
