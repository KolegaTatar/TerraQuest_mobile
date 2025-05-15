package edu.zsk.terraquest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import android.widget.TextView;

public class User_menuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.btn_weather).setOnClickListener(v -> navigateTo(new WeatherFragment()));
        view.findViewById(R.id.btn_privacy).setOnClickListener(v -> navigateTo(new PPFragment()));
        view.findViewById(R.id.btn_about).setOnClickListener(v -> navigateTo(new AboutFragment()));

        view.findViewById(R.id.btn_website).setOnClickListener(v -> openUrl("https://github.com/BergFilip/TerraQuest_web.git"));
        view.findViewById(R.id.btn_mobile).setOnClickListener(v -> openUrl("https://github.com/KolegaTatar/TerraQuest_mobile.git"));
        view.findViewById(R.id.btn_desktop).setOnClickListener(v -> openUrl("https://github.com/KolegaTatar/SkyVision_desktop.git"));
        view.findViewById(R.id.btn_project).setOnClickListener(v -> openUrl("https://www.figma.com/proto/VAEeMmg1rGRkZhTuEwuFnK/Platforma-do-Planowania-Podr%C3%B3%C5%BCy-i-Rezerwacji---TerraQuest?node-id=1095-7789&p=f&t=Ks5QFEs4WzJU5fcy-0&scaling=scale-down&content-scaling=fixed&page-id=87%3A1675&starting-point-node-id=1095%3A7789"));
    }

    private void navigateTo(Fragment fragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
