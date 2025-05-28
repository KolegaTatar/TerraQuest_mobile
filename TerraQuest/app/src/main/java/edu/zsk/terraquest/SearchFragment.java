package edu.zsk.terraquest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        TextView textViewInfo = view.findViewById(R.id.textViewInfo);

        Bundle args = getArguments();
        if (args != null) {
            String destination = args.getString("destination");
            String date = args.getString("date");
            String people = args.getString("people");

            String info = "Szukasz: " + destination + "\nData: " + date + "\nLiczba osób: " + people;
            textViewInfo.setText(info);
        }

        return view;
    }
}
