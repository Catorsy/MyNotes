package com.example.mynotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
//лучше всего сохранять все настройки сразу, а не 5 раз по 1 строчке, это нагрузка на систему
//сохранять хорошо в преференс - преференс жив, пока установлено приложение. А вот поток заводить - ресурсоемко. В мелочах не надо их заводить.

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settingas, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initSwitchBackStack(view);
        initRadioAdd(view);
        initRadioReplace(view);
        initSwitchBackAsRemove(view);
        initSwitchDeleteBeforeAdd(view);
    }

    private void initSwitchDeleteBeforeAdd(View view) {
        SwitchCompat switchDeleteBeforeAdd = view.findViewById(R.id.switchDeleteBeforeAdd);
        switchDeleteBeforeAdd.setChecked(Settings.isDeleteBeforeAdd);
        switchDeleteBeforeAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean
                    isChecked) {
                Settings.isDeleteBeforeAdd = isChecked;
                writeSettings();
            }
        });
    }

    private void initSwitchBackAsRemove(View view) {
        SwitchCompat switchBackAsRemove = view.findViewById(R.id.switchBackAsRemove);
        switchBackAsRemove.setChecked(Settings.isBackAsRemove);
        switchBackAsRemove.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isBackAsRemove = isChecked; //сохраняем настройку
            writeSettings(); //а тут сохраним в преференсе
        });
    }

    private void initRadioReplace(View view) {
        RadioButton radioButtonReplace = view.findViewById(R.id.radioButtonReplace);
        radioButtonReplace.setChecked(!Settings.isAddFragment);
        radioButtonReplace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isAddFragment = !isChecked;
                writeSettings();
            }
        });
    }

    private void initRadioAdd(View view) {
        RadioButton radioButtonReplace = view.findViewById(R.id.radioButtonAdd);
        radioButtonReplace.setChecked(Settings.isAddFragment);
        radioButtonReplace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isAddFragment = isChecked;
                writeSettings();
            }
        });
    }

    private void initSwitchBackStack(View view) {
        //переключатель
        SwitchCompat switchCompat = view.findViewById(R.id.switchBackStack); //нашли
        switchCompat.setChecked(Settings.isBackStack); //изменяем проверенное состояние через этот метод
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Settings.isBackStack = isChecked;
            writeSettings();
        }); //это слушатель для свитча. Сюда приходит кнопка и состояние, вкл или выкл

    }

    private void writeSettings() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE); //надо контекст, чтобы создать, передать имя файла, где будет храниться преференс, и мод (как мы будем пользоваться ресурсами).
        SharedPreferences.Editor editor = sharedPreferences.edit(); //это его паттерн билдер, позволяет транзакциями воспользоваться преференсом
        //эдитом мы открыли файл
        editor.putBoolean(Settings.IS_BACK_STACK_USED, Settings.isBackStack);
        editor.putBoolean(Settings.IS_ADD_FRAGMENT_USED, Settings.isAddFragment);
        editor.putBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, Settings.isBackAsRemove);
        editor.putBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, Settings.isDeleteBeforeAdd);
        //можно делать цепочки! т.ке. всё это цепочкой через точку.
        //editor.putBoolean(Settings.IS_BACK_STACK_USED, Settings.isBackStack).putBoolean(Settings.IS_ADD_FRAGMENT_USED, Settings.isAddFragment); //и т д
        // или вообще sharedPreferences.edit().put boolean...
        editor.apply(); //закрыли транзакцию
    }
}
