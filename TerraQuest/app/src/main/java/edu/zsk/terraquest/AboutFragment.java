package edu.zsk.terraquest;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Typeface;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Teksty statystyk
        setBoldNumber(view, R.id.textStatsLanguages, "31", " języki");
        setBoldNumber(view, R.id.textStatsApps, "53", " strony internetowe i aplikacje");
        setBoldNumber(view, R.id.textStatsCountries, "190", " kraje");
        setBoldNumber(view, R.id.textStatsHotels, "5 mln", " hotele i podobne noclegi");

        // Wypełnianie wpisów na osi czasu
        setTimelineEntry(view, R.id.entry2020_1, "2020", "Narodziny pomysłu TerraQuest\n\n");
        setTimelineEntry(view, R.id.entry2021_1, "2021", "Założenie w Niemczech\n");
        setTimelineEntry(view, R.id.entry2021_2, "", "Pierwsza runda finansowania\n");
        setTimelineEntry(view, R.id.entry2021_3, "", "Narodziny porównywarki cen\n\n");
        setTimelineEntry(view, R.id.entry2022_1, "2022", "TerraQuest zostaje uruchomiony we Włoszech, Hiszpanii i Francji\n");
        setTimelineEntry(view, R.id.entry2023_1, "2023", "Nowy rynek – Azja\n");
        setTimelineEntry(view, R.id.entry2023_2, "", "Druga runda finansowania\n\n\n");
        setTimelineEntry(view, R.id.entry2024_1, "2024", "Przejęcie weekend.com\n");
        setTimelineEntry(view, R.id.entry2024_2, "", "Rozpoczęcie współpracy z Chelsea FC\n\n");
        setTimelineEntry(view, R.id.entry2025_1, "2025", "Uruchomienie pierwszej w swoim rodzaju kampanii reklamowej opartej na sztucznej inteligencji w ramach innowacyjnego odświeżenia marki\n");

        return view;
    }

    private void setBoldNumber(View parentView, int textViewId, String number, String text) {
        TextView textView = parentView.findViewById(textViewId);
        String fullText = number + text;
        SpannableString spannable = new SpannableString(fullText);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, number.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }
    private void setTimelineEntry(View root, int entryId, String year, String event) {
        View entry = root.findViewById(entryId);
        if (entry != null) {
            TextView yearText = entry.findViewById(R.id.yearText);
            TextView eventText = entry.findViewById(R.id.eventText);
            yearText.setText(year);
            eventText.setText(event);
        }
    }
}
