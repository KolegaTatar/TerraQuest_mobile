package edu.zsk.terraquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    public interface OnHotelClickListener {
        void onHotelClick(Hotel hotel);
    }

    private List<Hotel> hotelList;
    private OnHotelClickListener listener;


    @LayoutRes
    private int itemLayoutResId = R.layout.hotel_item;


    public HotelAdapter(List<Hotel> hotelList, OnHotelClickListener listener) {
        this.hotelList = hotelList;
        this.listener = listener;
    }


    public HotelAdapter(List<Hotel> hotelList, OnHotelClickListener listener, @LayoutRes int itemLayoutResId) {
        this.hotelList = hotelList;
        this.listener = listener;
        this.itemLayoutResId = itemLayoutResId;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutResId, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.bind(hotel, listener);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
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

        public void bind(final Hotel hotel, final OnHotelClickListener listener) {
            hotelName.setText(hotel.getName());
            hotelLocation.setText(hotel.getLocation());
            nightsCount.setText(hotel.getNights() + " noce");
            originalPrice.setText(hotel.getOriginalPrice() + "zł");
            discountedPrice.setText(hotel.getDiscountedPrice() + "zł");

            Glide.with(itemView.getContext())
                    .load(hotel.getImageUrl())
                    .into(hotelImage);

            itemView.setOnClickListener(v -> listener.onHotelClick(hotel));
        }
    }
}
