package com.nk.counterapp;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TotalPeople extends AppCompatActivity {
    TextView totlaPeopleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_people);
        totlaPeopleTV = (TextView) findViewById(R.id.totalPeople);
        getSupportActionBar().hide();


        FirebaseHelper firebaseHelper = new FirebaseHelper();

        firebaseHelper.getDatabaseRef().child("count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.getValue().equals("")) {
                    int currentCount = snapshot.getValue(Integer.class);  //prints "Do you have data? You'll love Firebase."
                    totlaPeopleTV.setText(currentCount + "");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}
