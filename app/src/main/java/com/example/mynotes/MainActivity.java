package com.example.mynotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
            })
            .show(); //show сам вызовет сначала create
        });
    }
}