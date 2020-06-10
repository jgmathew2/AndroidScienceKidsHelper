package com.wet.beastbots.sciencekidshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button astronomyButton = (Button) this.findViewById(R.id.astronomy_button);
        astronomyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainAstronomyActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        Button biologyButton = (Button) this.findViewById(R.id.biology_start_button);
        biologyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ElementaryBiologyActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }



}
