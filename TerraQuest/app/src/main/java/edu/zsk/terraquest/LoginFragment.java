package edu.zsk.terraquest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginFragment extends Fragment {

    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());
        database = dbHelper.getReadableDatabase();

        EditText emailInput = view.findViewById(R.id.emailInput);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        Button loginButton = view.findViewById(R.id.loginButton);
        TextView registerLink = view.findViewById(R.id.registerLink);

        registerLink.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new RegisterFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String hashedPassword = hashPassword(password);

            if (checkCredentials(email, hashedPassword)) {
                requireActivity().getSharedPreferences("user_prefs", 0)
                        .edit()
                        .putBoolean("is_logged_in", true)
                        .putString("email", email)
                        .apply();

                Toast.makeText(getContext(), "Zalogowano!", Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new UserFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                Toast.makeText(getContext(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show();
                passwordInput.setText("");
            }
        });

        return view;
    }

    private boolean checkCredentials(String email, String hashedPassword) {
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, hashedPassword});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing error", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) database.close();
    }
}
