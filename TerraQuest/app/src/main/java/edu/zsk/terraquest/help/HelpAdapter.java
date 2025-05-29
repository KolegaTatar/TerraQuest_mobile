package edu.zsk.terraquest.help;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.zsk.terraquest.R;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    private List<HelpItem> helpList;

    public HelpAdapter(List<HelpItem> helpList) {
        this.helpList = helpList;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_item, parent, false);
        return new HelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        HelpItem item = helpList.get(position);
        holder.titleText.setText(item.getTitle());
        holder.contentText.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public static class HelpViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, contentText;

        public HelpViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.faqTitle);
            contentText = itemView.findViewById(R.id.faqContent);
        }
    }
}
