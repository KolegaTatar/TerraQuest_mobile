package edu.zsk.terraquest.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

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

        View layout = view.findViewById(R.id.frameLayout); // Twój LinearLayout
        View trapezoidImage = view.findViewById(R.id.trapezoidImage); // TrapezoidImageView

        layout.post(() -> {
            int height = layout.getHeight();
            LayoutParams params = trapezoidImage.getLayoutParams();
            params.height = height;
            trapezoidImage.setLayoutParams(params);
        });
    }
}
