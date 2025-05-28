package edu.zsk.terraquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewPagerAdapter extends RecyclerView.Adapter<ReviewPagerAdapter.ReviewViewHolder> {

    private List<Review> reviewList;

    public ReviewPagerAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.stars.setText(review.getStars());
        holder.title.setText(review.getTitle());
        holder.content.setText(review.getContent());
        holder.authorDate.setText(review.getAuthorDate());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView stars, title, content, authorDate;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            stars = itemView.findViewById(R.id.reviewStars);
            title = itemView.findViewById(R.id.reviewTitle);
            content = itemView.findViewById(R.id.reviewContent);
            authorDate = itemView.findViewById(R.id.reviewAuthorDate);
        }
    }
}
