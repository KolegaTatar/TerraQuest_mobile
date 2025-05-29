package edu.zsk.terraquest.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.zsk.terraquest.R;
import edu.zsk.terraquest.database.UserDatabaseHelper;

public class RegisterFragment extends Fragment {

    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg, container, false);

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();

        EditText emailInput = view.findViewById(R.id.emailInput);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        CheckBox autoLoginCheckBox = view.findViewById(R.id.autoLoginCheckBox);
        Button registerButton = view.findViewById(R.id.registerButton);
        TextView loginLink = view.findViewById(R.id.loginLink);

        loginLink.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new LoginFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getContext(), "Niepoprawny adres e-mail!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getContext(), "Hasło musi mieć co najmniej 6 znaków!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEmailTaken(email)) {
                Toast.makeText(getContext(), "Ten e-mail jest już zajęty!", Toast.LENGTH_SHORT).show();
                return;
            }

            String hashedPassword = hashPassword(password);

            ContentValues values = new ContentValues();
            values.put("email", email);
            values.put("password", hashedPassword);
            values.put("first_name", "");
            values.put("last_name", "");

            long result = database.insert("users", null, values);

            if (result != -1) {
                Toast.makeText(getContext(), "Zarejestrowano pomyślnie!", Toast.LENGTH_SHORT).show();

                if (autoLoginCheckBox.isChecked()) {
                    requireActivity().getSharedPreferences("user_prefs", 0)
                            .edit()
                            .putBoolean("is_logged_in", true)
                            .putString("email", email)
                            .apply();

                    goToUserFragment();
                } else {
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new LoginFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            } else {
                Toast.makeText(getContext(), "Błąd rejestracji!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private boolean isEmailTaken(String email) {
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing error", e);
        }
    }

    private void goToUserFragment() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new UserFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) database.close();
    }
}
