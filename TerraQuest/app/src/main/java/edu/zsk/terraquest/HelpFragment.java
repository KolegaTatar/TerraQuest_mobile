package edu.zsk.terraquest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends Fragment {

    private LinearLayout faqContainer;
    private LinearLayout paginationContainer;
    private EditText searchInput;

    private List<FAQItem> faqList = new ArrayList<>();
    private int currentPage = 1;
    private final int itemsPerPage = 5;
    private int totalPages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        faqContainer = view.findViewById(R.id.faqContainer);
        paginationContainer = view.findViewById(R.id.paginationContainer);
        searchInput = view.findViewById(R.id.searchInput);
        Button searchButton = view.findViewById(R.id.searchButton);

        // Przycisk szukania (niewymagana funkcjonalność)
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            // Możesz tu dodać filtrowanie po query
        });

        // Dodaj przykładowe FAQ
        generateSampleFaqs();

        // Ustaw stronicowanie
        totalPages = (int) Math.ceil((double) faqList.size() / itemsPerPage);
        renderFaqs();
        renderPagination();

        // Obsługa strzałek
        Button prevButton = view.findViewById(R.id.prevButton);
        Button nextButton = view.findViewById(R.id.nextButton);

        prevButton.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                renderFaqs();
                renderPagination();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentPage < totalPages) {
                currentPage++;
                renderFaqs();
                renderPagination();
            }
        });

        return view;
    }

    private void generateSampleFaqs() {
        for (int i = 1; i <= 20; i++) {
            faqList.add(new FAQItem(
                    "Tytuł pytania numer " + i,
                    "To jest treść odpowiedzi na pytanie numer " + i + ". Może być również bardzo długa i zawierać wiele linijek informacji dla użytkownika."
            ));
        }
    }

    private void renderFaqs() {
        faqContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, faqList.size());

        for (int i = start; i < end; i++) {
            FAQItem item = faqList.get(i);
            View faqView = inflater.inflate(R.layout.faq_item, faqContainer, false);

            TextView titleView = faqView.findViewById(R.id.faqTitle);
            TextView contentView = faqView.findViewById(R.id.faqContent);
            ImageView arrowView = faqView.findViewById(R.id.arrowIcon);

            titleView.setText(item.getTitle());
            contentView.setText(item.getContent());

            // Obsługa rozwijania/zamykania z animacją strzałki i tekstu
            titleView.setOnClickListener(v -> {
                boolean isVisible = contentView.getVisibility() == View.VISIBLE;
                if (isVisible) {
                    contentView.setVisibility(View.GONE);
                    arrowView.animate().rotation(0f).setDuration(200).start();
                } else {
                    contentView.setVisibility(View.VISIBLE);
                    AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
                    anim.setDuration(300);
                    contentView.startAnimation(anim);
                    arrowView.animate().rotation(180f).setDuration(200).start();
                }
            });

            faqContainer.addView(faqView);
        }
    }

    private void renderPagination() {
        int count = paginationContainer.getChildCount();
        if (count > 2) paginationContainer.removeViews(1, count - 2);

        for (int i = 1; i <= totalPages; i++) {
            Button pageButton = new Button(getContext());
            pageButton.setText(String.valueOf(i));

            // Zmniejszony margines i większy rozmiar
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) getResources().getDisplayMetrics().density * 40, // 40dp
                    (int) getResources().getDisplayMetrics().density * 40  // 40dp
            );
            params.setMargins(4, 0, 4, 0);
            pageButton.setLayoutParams(params);

            // Wygląd przycisku
            pageButton.setBackgroundColor(i == currentPage ? 0xFF000000 : 0xFFDDDDDD);
            pageButton.setTextColor(i == currentPage ? 0xFFFFFFFF : 0xFF000000);
            pageButton.setTextSize(14f);
            pageButton.setPadding(0, 0, 0, 0);

            final int page = i;
            pageButton.setOnClickListener(v -> {
                currentPage = page;
                renderFaqs();
                renderPagination();
            });

            paginationContainer.addView(pageButton, paginationContainer.getChildCount() - 1);
        }

        // Zmiana rozmiaru strzałek (w XML upewnij się, że przyciski prevButton i nextButton są widoczne i mają szerokość 48dp, wysokość 40dp)
    }


    // Model FAQ
    private static class FAQItem {
        private final String title;
        private final String content;

        public FAQItem(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }
}
