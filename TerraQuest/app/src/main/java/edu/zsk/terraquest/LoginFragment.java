package edu.zsk.terraquest;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);



        TextView registerLink = view.findViewById(R.id.registerLink);
        registerLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment registerFragment = new RegisterFragment(); // Załaduj fragment_reg
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, registerFragment); // Upewnij się, że masz `fragment_container` w layout
                transaction.addToBackStack(null); // opcjonalne: pozwala wrócić wstecz
                transaction.commit();
            }
        });

        return view;
    }
}
