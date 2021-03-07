package com.nk.counterapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public int currentCount;


    FirebaseHelper() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("counter");
    }

    public void updateCounter(int count) {
        mFirebaseDatabase.child("count").setValue(count);
    }

    public DatabaseReference getDatabaseRef(){
        return mFirebaseDatabase;
    }
    public int getCount() {
        final int[] count = new int[1];
        mFirebaseDatabase.child("count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                count[0] = snapshot.getValue(Integer.class);  //prints "Do you have data? You'll love Firebase."


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return count[0];
    }

}
