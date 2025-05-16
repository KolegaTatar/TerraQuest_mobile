package edu.zsk.terraquest;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private List<FAQItem> displayedFaqList = new ArrayList<>();
    private int currentPage = 1;
    private final int itemsPerPage = 6;
    private int totalPages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        faqContainer = view.findViewById(R.id.faqContainer);
        paginationContainer = view.findViewById(R.id.paginationContainer);
        searchInput = view.findViewById(R.id.searchInput);

        // Dane startowe
        generateSampleFaqs();
        displayedFaqList = new ArrayList<>(faqList);

        // Live search
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim().toLowerCase();

                if (query.isEmpty()) {
                    displayedFaqList = new ArrayList<>(faqList);
                    paginationContainer.setVisibility(View.VISIBLE);
                } else {
                    displayedFaqList = new ArrayList<>();
                    for (FAQItem item : faqList) {
                        if (item.getTitle().toLowerCase().contains(query)) {
                            displayedFaqList.add(item);
                        }
                    }
                    paginationContainer.setVisibility(View.GONE);
                }

                currentPage = 1;
                renderFaqs();
                if (query.isEmpty()) renderPagination();
            }
        });

        // Paginacja
        renderFaqs();
        renderPagination();

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

        totalPages = (int) Math.ceil((double) displayedFaqList.size() / itemsPerPage);
        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, displayedFaqList.size());

        for (int i = start; i < end; i++) {
            FAQItem item = displayedFaqList.get(i);
            View faqView = inflater.inflate(R.layout.faq_item, faqContainer, false);

            TextView titleView = faqView.findViewById(R.id.faqTitle);
            TextView contentView = faqView.findViewById(R.id.faqContent);
            ImageView arrowView = faqView.findViewById(R.id.arrowIcon);
            LinearLayout faqItemLayout = faqView.findViewById(R.id.faqItemLayout);

            titleView.setText(item.getTitle());
            contentView.setText(item.getContent());

            // Animacja pojawiania się całego bloku
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(300);
            faqView.startAnimation(anim);

            // Klikanie w tytuł pytania – rozwijanie odpowiedzi
            faqItemLayout.setOnClickListener(v -> {
                boolean isVisible = contentView.getVisibility() == View.VISIBLE;
                if (isVisible) {
                    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                    fadeOut.setDuration(200);
                    contentView.startAnimation(fadeOut);
                    contentView.setVisibility(View.GONE);
                    arrowView.animate().rotation(0f).setDuration(200).start();
                } else {
                    contentView.setVisibility(View.VISIBLE);
                    AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                    fadeIn.setDuration(300);
                    contentView.startAnimation(fadeIn);
                    arrowView.animate().rotation(180f).setDuration(200).start();
                }
            });

            faqContainer.addView(faqView);
        }
    }


    private void renderPagination() {
        int count = paginationContainer.getChildCount();
        if (count > 2) paginationContainer.removeViews(1, count - 2);

        totalPages = (int) Math.ceil((double) displayedFaqList.size() / itemsPerPage);

        for (int i = 1; i <= totalPages; i++) {
            Button pageButton = new Button(getContext());
            pageButton.setText(String.valueOf(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) getResources().getDisplayMetrics().density * 50,
                    (int) getResources().getDisplayMetrics().density * 50
            );
            params.setMargins(20, 0, 20, 0);
            pageButton.setLayoutParams(params);

            pageButton.setBackgroundColor(i == currentPage ? 0xFF000000 : 0xFFDDDDDD);
            pageButton.setTextColor(i == currentPage ? 0xFFFFFFFF : 0xFF000000);
            pageButton.setTextSize(20f);
            pageButton.setPadding(0, 0, 0, 0);

            final int page = i;
            pageButton.setOnClickListener(v -> {
                currentPage = page;
                renderFaqs();
                renderPagination();
            });

            paginationContainer.addView(pageButton, paginationContainer.getChildCount() - 1);
        }
    }

    private static class FAQItem {
        private final String title;
        private final String content;

        public FAQItem(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() { return title; }
        public String getContent() { return content; }
    }
}
