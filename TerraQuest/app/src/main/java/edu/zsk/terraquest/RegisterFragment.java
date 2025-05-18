package edu.zsk.terraquest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterFragment extends Fragment {

    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg, container, false);

        TextView loginLink = view.findViewById(R.id.loginLink);
        loginLink.setOnClickListener(v -> {
            Fragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, loginFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        initDb();

        EditText emailInput = view.findViewById(R.id.emailInput);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        Button registerButton = view.findViewById(R.id.registerButton);
        CheckBox autoLoginCheckBox = view.findViewById(R.id.autoLoginCheckBox); // dodaj do XML checkbox z takim ID

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

            String hashedPassword = hashPassword(password);

            if (isEmailTaken(email)) {
                Toast.makeText(getContext(), "Ten e-mail jest już zajęty!", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();
            values.put("email", email);
            values.put("password", hashedPassword);

            long result = database.insert("users", null, values);
            if (result == -1) {
                Toast.makeText(getContext(), "Błąd rejestracji!", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(getContext(), "Zarejestrowano pomyślnie!", Toast.LENGTH_SHORT).show();

            if (autoLoginCheckBox.isChecked()) {
                goToUserFragment();
            } else {
                Fragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void initDb() {
        SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(getContext(), "users.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, password TEXT)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS users");
                onCreate(db);
            }
        };
        database = dbHelper.getWritableDatabase();
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
            byte[] result = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Błąd hashowania hasła", e);
        }
    }

    private void goToUserFragment() {
        Fragment userFragment = new UserFragment(); // przygotuj ten fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, userFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) database.close();
    }
}
