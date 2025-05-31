package edu.zsk.terraquest.ui;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.zsk.terraquest.R;

public class ContactFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View layout = view.findViewById(R.id.frameLayout);
        View trapezoidImage = view.findViewById(R.id.trapezoidImage);

        layout.post(() -> {
            int height = layout.getHeight();
            LayoutParams params = trapezoidImage.getLayoutParams();
            params.height = height;
            trapezoidImage.setLayoutParams(params);
        });

        EditText etImie = view.findViewById(R.id.etImie);
        EditText etNazwisko = view.findViewById(R.id.etNazwisko);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etWiadomosc = view.findViewById(R.id.etWiadomosc);
        Button sendButton = view.findViewById(R.id.btnSend);

        sendButton.setOnClickListener(v -> {
            String imie = etImie.getText().toString().trim();
            String nazwisko = etNazwisko.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String wiadomosc = etWiadomosc.getText().toString().trim();

            if (imie.isEmpty() || nazwisko.isEmpty() || email.isEmpty() || wiadomosc.isEmpty()) {
                Toast.makeText(getContext(), "Wszystkie pola są wymagane", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Niepoprawny adres e-mail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Wysłano formularz", Toast.LENGTH_SHORT).show();

                etImie.setText("");
                etNazwisko.setText("");
                etEmail.setText("");
                etWiadomosc.setText("");
            }
        });
    }
}
