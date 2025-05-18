package edu.zsk.terraquest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView registerLink = view.findViewById(R.id.registerLink);
        registerLink.setOnClickListener(v -> {
            Fragment registerFragment = new RegisterFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, registerFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        initDb();

        EditText emailInput = view.findViewById(R.id.emailInput);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        Button loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String hashedPassword = hashPassword(password);

            if (checkCredentials(email, hashedPassword)) {
                Toast.makeText(getContext(), "Zalogowano!", Toast.LENGTH_SHORT).show();
                Fragment userFragment = new UserFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, userFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                Toast.makeText(getContext(), "Niepoprawne dane logowania!", Toast.LENGTH_SHORT).show();
                passwordInput.setText("");
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
        database = dbHelper.getReadableDatabase();
    }

    private boolean checkCredentials(String email, String hashedPassword) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM users WHERE email = ? AND password = ?",
                new String[]{email, hashedPassword}
        );
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Błąd hashowania hasła!", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) database.close();
    }
}
