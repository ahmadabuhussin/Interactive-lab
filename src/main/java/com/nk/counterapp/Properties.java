package com.nk.counterapp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Properties {
    FirebaseHelper firebaseHelper;
    String trackingId = "";
    int firstPostion, lastPosition, height;
    Context context;

    Runnable myRunnable;
    Handler handler;
    boolean isAlreadyDone = false;

    Properties() {
        handler = new Handler();
        firebaseHelper = new FirebaseHelper();
    }

    public void timer() {
        handler.removeCallbacks(myRunnable);
        myRunnable = new Runnable() {
            public void run() {
                // do something
                if (firstPostion == lastPosition) {
                    return;
                }
                if (!isAlreadyDone) {
                    int diff = Math.abs(firstPostion - lastPosition);
                    if (diff > 50) {
                        if (lastPosition > firstPostion) {
                            Toast toast = Toast.makeText(context,
                                    "Count Decrease",
                                    Toast.LENGTH_LONG);

                            toast.show();
                            Log.d("hello", trackingId + " run: Left to Right " + "first " + firstPostion + "Last " + lastPosition + " height " + height);
                            decreaseCount();
                        } else {
                            Log.d("hello", trackingId + " run: Right to Left " + "first " + firstPostion + "Last " + lastPosition + " height " + height);
                            increaseCount();
                            Toast toast = Toast.makeText(context,
                                    "Count Increase",
                                    Toast.LENGTH_LONG);

                            toast.show();
                        }
                        isAlreadyDone = true;
                    }

                }
            }
        };
        handler.postDelayed(myRunnable, 3000);

    }

    public void increaseCount() {
        firebaseHelper.getDatabaseRef().child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int currentCount = snapshot.getValue(Integer.class);  //prints "Do you have data? You'll love Firebase."
                currentCount = currentCount + 1;
                firebaseHelper.updateCounter(currentCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void decreaseCount() {

        firebaseHelper.getDatabaseRef().child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int currentCount = snapshot.getValue(Integer.class);  //prints "Do you have data? You'll love Firebase."
                if (currentCount == 0) {
                    return;
                }
                currentCount = currentCount - 1;
                firebaseHelper.updateCounter(currentCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

