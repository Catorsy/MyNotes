package com.example.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FrasesFragment extends Fragment {
    private int random = (int) (Math.random() * 3);
    private static String oneFrase = "Совершенномудрый исходит не только из того, что сам видит, и потому может видеть ясно; " +
            "он не считает правым только себя, поэтому может обладать истиной; " +
            "он не прославляет себя, поэтому имеет заслуженную славу; " +
            "он не возвышает себя, поэтому он старше среди других.";
    private static String twoFrase = "Когда совершенномудрый желает возвыситься над народом, он доллжен ставить себя нижже других." +
            "Когда он желает быть впереди людей, он должен ставить себя позади других.";
    private static String threeFrase = "Совершенномудрый отказывается от самолюбияи предпочитает невозвышение.";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frases, container, false);
        TextView textView = view.findViewById(R.id.frases_app);
        switch (random){
            case 0:
                textView.setText(oneFrase);
            case 1:
                textView.setText(twoFrase);
            case 2:
                textView.setText(threeFrase);
        }
        return view;
    }
}