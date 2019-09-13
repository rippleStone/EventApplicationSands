package com.example.eventapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Main3ActivityS extends AppCompatActivity {

    public static final String EVENT_NAME = "event_name";
    public static final String COUNT = "count";
    public static final String A1 = "a1";
    public static final String A2 = "a2";
    public static final String A3 = "a3";
    public static final String A4 = "a4";
    public static final String A5 = "a5";

    Button esbtn1, esbtn2, esbtn3, esbtn4, esbtn5;
    String UserID;
    Integer j;
    DatabaseReference dbRef;

    String a1, a2, a3, a4, a5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        esbtn1 = findViewById(R.id.btnShowEvent1);
        esbtn2 = findViewById(R.id.btnShowEvent2);
        esbtn3 = findViewById(R.id.btnShowEvent3);
        esbtn4 = findViewById(R.id.btnShowEvent4);
        esbtn5 = findViewById(R.id.btnShowEvent5);

        UserID = "User1";

        dbRef = FirebaseDatabase.getInstance().getReference().child("Event").child(UserID);
        ValueEventListener eventListener = new ValueEventListener() {

            ArrayList<String> arr = new ArrayList<>();
            int i=1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    arr.add(ds.getKey());
                }
                for(String a:arr){
                    if(i==1){
                        a1 = a;
                        esbtn1.setText(a);
                    }
                    else if(i==2){
                        a2 = a;
                        esbtn2.setText(a);
                    }
                    else if(i==3){
                        a3 = a;
                        esbtn3.setText(a);
                    }
                    else if(i==4){
                        a4 = a;
                        esbtn4.setText(a);
                    }
                    else if(i==5){
                        a5 = a;
                        esbtn5.setText(a);
                    }
                    i++;
                }
                j = i;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dbRef.addListenerForSingleValueEvent(eventListener);
    }

    public void onClick(View view){
        Intent intent = new Intent(Main3ActivityS.this, MainActivityS.class);
        intent.putExtra(COUNT,j);
        intent.putExtra(A1,a1);
        intent.putExtra(A2,a2);
        intent.putExtra(A3,a3);
        intent.putExtra(A4,a4);
        intent.putExtra(A5,a5);
        startActivity(intent);
    }

    public void btnClick1(View view){
        Intent intentBtn = new Intent(Main3ActivityS.this, Main2ActivityS.class);
        intentBtn.putExtra(EVENT_NAME,a1);
        startActivity(intentBtn);
    }
    public void btnClick2(View view){
        Intent intentBtn = new Intent(Main3ActivityS.this, Main2ActivityS.class);
        intentBtn.putExtra(EVENT_NAME,a2);
        startActivity(intentBtn);
    }
    public void btnClick3(View view){
        Intent intentBtn = new Intent(Main3ActivityS.this, Main2ActivityS.class);
        intentBtn.putExtra(EVENT_NAME,a3);
        startActivity(intentBtn);
    }
    public void btnClick4(View view){
        Intent intentBtn = new Intent(Main3ActivityS.this, Main2ActivityS.class);
        intentBtn.putExtra(EVENT_NAME,a4);
        startActivity(intentBtn);
    }
    public void btnClick5(View view){
        Intent intentBtn = new Intent(Main3ActivityS.this, Main2ActivityS.class);
        intentBtn.putExtra(EVENT_NAME,a5);
        startActivity(intentBtn);
    }
}
