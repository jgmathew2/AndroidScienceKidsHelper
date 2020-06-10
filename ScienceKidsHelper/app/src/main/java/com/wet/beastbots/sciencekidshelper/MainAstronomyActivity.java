package com.wet.beastbots.sciencekidshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainAstronomyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_astronomy);
        Button astronomyButton = (Button) this.findViewById(R.id.astronomy_elementary_button);
        astronomyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAstronomyActivity.this, ElementaryAstronomyActivity.class);
                MainAstronomyActivity.this.startActivity(intent);
            }
        });


    }
}