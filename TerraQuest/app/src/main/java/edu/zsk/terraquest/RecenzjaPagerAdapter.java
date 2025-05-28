package edu.zsk.terraquest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class RecenzjaPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> recenzje;

    public RecenzjaPagerAdapter(Context context, List<String> recenzje) {
        this.context = context;
        this.recenzje = recenzje;
    }

    @Override
    public int getCount() {
        return recenzje.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TextView textView = new TextView(context);
        textView.setText(recenzje.get(position));
        textView.setTextSize(16f);
        textView.setPadding(32, 32, 32, 32);
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

