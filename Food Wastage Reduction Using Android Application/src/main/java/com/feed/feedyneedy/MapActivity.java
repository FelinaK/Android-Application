package com.feed.feedyneedy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        Fragment fragment = new Map_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

    }
}
