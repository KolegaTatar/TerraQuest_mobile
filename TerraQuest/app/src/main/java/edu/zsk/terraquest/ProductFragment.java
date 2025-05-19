package edu.zsk.terraquest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    // Przykladowe dane symulujące dane z API
    private String miastoKraj = "Kraków (Polska)";
    private String nazwaObiektu = "Willa Panorama";
    private String opisRozwijany = "Piękna willa z widokiem na góry, duży ogród, jacuzzi.";
    private String najlepszeUdogodnienia = "Wi-Fi, sauna, darmowy parking, telewizor, klimatyzacja";
    private String nazwaInfo = "Willa Panorama";
    private String opisInfo = "Nowoczesna willa z 4 sypialniami, salonem i dużą kuchnią.";

    private List<RecenzjaModel> listaRecenzji = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Inicjalizacja widoków
        TextView textMiasto = view.findViewById(R.id.textMiastoKraj);
        TextView textNazwa = view.findViewById(R.id.textNazwaObiektu);
        TextView textOpis = view.findViewById(R.id.textOpis);
        TextView textUdogodnienia = view.findViewById(R.id.textUdogodnienia);
        TextView textInfoTitle = view.findViewById(R.id.textInfoTitle);
        TextView textInfoOpis = view.findViewById(R.id.textInfoOpis);

        // Ustaw dane z "API"
        textMiasto.setText(miastoKraj);
        textNazwa.setText(nazwaObiektu);
        textOpis.setText(opisRozwijany);
        textUdogodnienia.setText(najlepszeUdogodnienia);
        textInfoTitle.setText(nazwaInfo);
        textInfoOpis.setText(opisInfo);

        // Obsługa rozwoju opisu
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

        // ----- Recenzje -----
        ViewPager viewPager = view.findViewById(R.id.viewPagerRecenzje);
        LinearLayout dotsLayout = view.findViewById(R.id.pagerDots);

        // Przykładowe recenzje
        listaRecenzji.add(new RecenzjaModel("Wakacje życia!!!", "Najlepsze wakacje ever!", 5, "Reviewer name", "2024-08-21", R.drawable.terra_quest_logo));
        listaRecenzji.add(new RecenzjaModel("Fantastyczny widok", "Góry zapierały dech w piersiach", 4, "Anna Nowak", "2024-09-05", R.drawable.terra_quest_logo));
        listaRecenzji.add(new RecenzjaModel("Bardzo czysto", "Obiekt zadbany i czysty", 5, "Tomasz K.", "2024-07-15", R.drawable.terra_quest_logo));

        RecenzjaPagerAdapter adapter = new RecenzjaPagerAdapter(requireContext(), listaRecenzji);
        viewPager.setAdapter(adapter);

        // Kropki
        TextView[] dots = new TextView[listaRecenzji.size()];
        for (int i = 0; i < listaRecenzji.size(); i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText("•");
            dots[i].setTextSize(30);
            dots[i].setTextColor(Color.GRAY);
            dotsLayout.addView(dots[i]);
        }
        dots[0].setTextColor(Color.BLACK);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageSelected(int position) {
                for (TextView dot : dots) {
                    dot.setTextColor(Color.GRAY);
                }
                dots[position].setTextColor(Color.BLACK);
            }
        });

        return view;
    }

    public static class RecenzjaModel {
        String tytul, tresc, autor, data;
        int gwiazdki;
        int avatarResId;

        public RecenzjaModel(String tytul, String tresc, int gwiazdki, String autor, String data, int avatarResId) {
            this.tytul = tytul;
            this.tresc = tresc;
            this.gwiazdki = gwiazdki;
            this.autor = autor;
            this.data = data;
            this.avatarResId = avatarResId;
        }
    }

    public static class RecenzjaPagerAdapter extends PagerAdapter {
        private Context context;
        private List<RecenzjaModel> recenzje;

        public RecenzjaPagerAdapter(Context context, List<RecenzjaModel> recenzje) {
            this.context = context;
            this.recenzje = recenzje;
        }

        @Override public int getCount() { return recenzje.size(); }
        @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) { return view == object; }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recenzja_item, container, false);

            RecenzjaModel r = recenzje.get(position);
            ((TextView) view.findViewById(R.id.tytul)).setText(r.tytul);
            ((TextView) view.findViewById(R.id.tresc)).setText(r.tresc);
            ((TextView) view.findViewById(R.id.autor)).setText(r.autor);
            ((TextView) view.findViewById(R.id.data)).setText(r.data);
            ((ImageView) view.findViewById(R.id.avatar)).setImageResource(r.avatarResId);

            LinearLayout gwiazdkiLayout = view.findViewById(R.id.gwiazdkiLayout);
            gwiazdkiLayout.removeAllViews();
            for (int i = 0; i < 5; i++) {
                ImageView gwiazdka = new ImageView(context);
                gwiazdka.setImageResource(i < r.gwiazdki ? R.drawable.star_unfilled : R.drawable.star_filled);
                gwiazdka.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
                gwiazdkiLayout.addView(gwiazdka);
            }

            container.addView(view);
            return view;
        }

        @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
