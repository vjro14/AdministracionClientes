package com.example.administracionclientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Calendar;

public class SplashScreen extends AppCompatActivity {

    private Boolean botonAtrasPresionado = false;
    private static final int DURACION_SPLASH = 3000;
    private int currentYear;
    TextView tvYear;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screeen);

        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        tvYear = findViewById(R.id.app_year);
        tvYear.setText(Integer.toString(currentYear));

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if (!botonAtrasPresionado) {
                    Intent intento = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intento);
                }
            }
        }, DURACION_SPLASH);
    }

    @Override
    public void onBackPressed() {
        botonAtrasPresionado = true;
        super.onBackPressed();
    }
}
