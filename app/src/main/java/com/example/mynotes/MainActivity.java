package com.example.mynotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int chosen = -1; //значит, что никакой не выбран

    private boolean [] checkedItems = {
            //если мы хотим задать выбор заранее, пишем тут
            false,
            false,
            false
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.alertDialog).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //возвращает this, поэтому можно цепочку
            builder.setTitle(R.string.title).setMessage(R.string.message).setIcon(R.mipmap.ic_launcher)
            .setCancelable(false) //говорит о том, можно ли отменить наш диалог, тыкнув на серую зону
            .setPositiveButton(R.string.ok, (dialog, which) -> { //положительная кнопка находится справа
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); //отменили после нажатия на кнопку. Тут необязательно писать. При позитивной кнопке
                // само сработает, а это закрытие для кастомной кнопки.
            })       //просто смотрим, ок или отмена засчиталась
                    .setOnCancelListener(dialog -> {
                        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                    })
                    .setOnDismissListener(dialog -> {
                        Toast.makeText(this, "Dismiss", Toast.LENGTH_SHORT).show();
                    })
            .show(); //show сам вызовет сначала create
        });

        findViewById(R.id.alertDialog3).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.title).setMessage(R.string.message3)
                    .setCancelable(true)
                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                        Toast.makeText(this, "Правильно", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {
                        Toast.makeText(this, "Не правильно", Toast.LENGTH_SHORT).show();
                    })
                    .setNeutralButton(R.string.dk, (dialog, which) -> {
                        Toast.makeText(this, "Не знаю", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        findViewById(R.id.alertDialogList).setOnClickListener(v -> {
            String [] items = {
                 "Первый",
                 "Второй",
                 "Третий"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.title)
                    .setItems(items, (dialog, which) -> { //which - это элемент, в который был клик. setItems
                        //позволяет выбрать пункт из списка
                        Toast.makeText(this, items[which], Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        findViewById(R.id.alertDialogListSingleChoose).setOnClickListener(v -> {
            String [] items = {
                    "Первый",
                    "Второй",
                    "Третий"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //тут будет радиобаттон
            builder.setTitle(R.string.title)
                    .setSingleChoiceItems(items, chosen, (dialog, which) -> {
                        chosen = which;
                    })
                    .setNegativeButton("Отмена", (dialog, which) -> {
                        Toast.makeText(this, "Отменили", Toast.LENGTH_SHORT).show();
                    })
                    .setPositiveButton("OK", (dialog, which) -> {
                        if (chosen == -1) {
                            Toast.makeText(this, "Не выбран элемент", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(this, items[chosen], Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        findViewById(R.id.alertDialogListMultyChoose).setOnClickListener(v -> {
            String[] items = {
                    "Первый",
                    "Второй",
                    "Третий"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //тут будет радиобаттон
            builder.setTitle(R.string.title)
                    .setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> { //надо передать уже не номер, а массив выбранных
                        //возвращает, какой элемент выбран и какое у него состояние
                        checkedItems[which] = isChecked;
                       // dialog.cancel(); отмена на выбор элемента
                    })
                    .setNegativeButton("Отмена", (dialog, which) -> {
                        Toast.makeText(this, "Отменили", Toast.LENGTH_SHORT).show();
                    })
                    .setPositiveButton("OK", (dialog, which) -> {
                        StringBuilder result = new StringBuilder();
                        //String result = "";
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) { //значит, элемент выбран
                                result.append(items[i]).append(";");
                                //result += items[which] + ";" ;
                            }
                        }
                        Toast.makeText(this, "Выбраны элементы: " + result, Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });
    }
}