package com.example.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyButtomSheetDialogFragment extends BottomSheetDialogFragment {
    private OnDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_bottom_sheet_dialog, null);

        view.findViewById(R.id.matBatOk).setOnClickListener(v -> {
            if (listener != null){
                listener.onDialogOk();
            }
        });

        view.findViewById(R.id.matBatYes).setOnClickListener(v -> {
            if (listener != null){
                listener.onDialogYes();
            }
        });

        return view;
    }

    public MyButtomSheetDialogFragment setListener(OnDialogListener listener) {
        this.listener = listener;
        return this;
    }

    interface OnDialogListener {
        void onDialogOk();
        void onDialogYes();
    }
}
