package com.wet.beastbots.sciencekidshelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class ElementaryAstronomyResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementary_astronomy_resources);
        final TextView resourcesTextView1 = (TextView) this.findViewById(R.id.elementary_resources_text1);
        resourcesTextView1.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
