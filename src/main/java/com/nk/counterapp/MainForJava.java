package com.nk.counterapp;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.otaliastudios.cameraview.CameraView;

public class MainForJava extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportActionBar().hide();

    }

    public void peopleCounter(View view){
        Intent intent = new Intent(MainForJava.this, TotalPeople.class);
        startActivity(intent);

    }
    public void peopleDetection(View view){
        Intent intent = new Intent(MainForJava.this, MainActivity.class);
        startActivity(intent);
    }
}
