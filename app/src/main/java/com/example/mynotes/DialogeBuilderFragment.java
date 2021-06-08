package com.example.mynotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogeBuilderFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText editText = (EditText) requireActivity().getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout, null);

        //подпихнем в наш диалег-фрагмент аллерт-диалог
//в методичке другой пример, видимо, лучше
        return new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.title)
                .setView(editText) //можем передать либо реальную вью, которую сами создадим, либо получить ее из ресурсов, либо сами заинфлейтить
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) requireActivity()).onDialogeResult(editText.getText().toString());
                        dismiss();
                    }
                })
                .create(); //нам не нужно отобразить диалог, только передать, поэтому не шооу
    }
}
