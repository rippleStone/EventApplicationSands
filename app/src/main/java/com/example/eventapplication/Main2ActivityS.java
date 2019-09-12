package com.example.eventapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.eventapplication.Main3ActivityS.EVENT_NAME;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Main2ActivityS extends AppCompatActivity {

    EditText txtDate, txtTime, txtPlace, txtnumOfGuests, txtBudget, txtTheme ;
    CheckBox chkPhoto, chkEnter, chkFood;
    Button updateBtn, deleteBtn;
    String UserID;
    String name;

    DatabaseReference dbRef;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intentBtn = getIntent();
        name = intentBtn.getStringExtra(Main3ActivityS.EVENT_NAME);

        txtDate = findViewById(R.id.dateUpdate);
        txtTime = findViewById(R.id.timeUpdate);
        txtPlace = findViewById(R.id.placeUpdate);
        txtnumOfGuests = findViewById(R.id.numOfGuestsUpdate);
        txtBudget = findViewById(R.id.budgetUpdate);
        txtTheme = findViewById(R.id.themeUpdate);


        chkPhoto = findViewById(R.id.photoUpdate);
        chkEnter = findViewById(R.id.enterUpdate);
        chkFood = findViewById(R.id.foodUpdate);

        updateBtn = findViewById(R.id.eventBtnUpdate);
        deleteBtn = findViewById(R.id.eventBtnDelete);

        UserID = "User1";

        event = new Event();

        dbRef = FirebaseDatabase.getInstance().getReference().child("Event").child(UserID).child(name);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtDate.setText(dataSnapshot.child("date").getValue().toString());
                    txtTime.setText(dataSnapshot.child("time").getValue().toString());
                    txtPlace.setText(dataSnapshot.child("place").getValue().toString());
                    txtnumOfGuests.setText(dataSnapshot.child("numOfGuests").getValue().toString());
                    txtBudget.setText(dataSnapshot.child("budget").getValue().toString());
                    txtTheme.setText(dataSnapshot.child("theme").getValue().toString());

                    chkPhoto.setChecked((Boolean)dataSnapshot.child("photo").getValue());
                    chkFood.setChecked((Boolean)dataSnapshot.child("food").getValue());
                    chkEnter.setChecked((Boolean)dataSnapshot.child("enter").getValue());

                }
                else{
                    Toast.makeText(getApplicationContext(),"No source",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Event").child(UserID);
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(name)){
                            try {
                                if (TextUtils.isEmpty(txtDate.getText().toString()) ||
                                        TextUtils.isEmpty(txtTime.getText().toString()) || TextUtils.isEmpty(txtPlace.getText().toString()) ||
                                        TextUtils.isEmpty(txtnumOfGuests.getText().toString()) || TextUtils.isEmpty(txtBudget.getText().toString()) ||
                                        TextUtils.isEmpty(txtTheme.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), "One or more empty fields", Toast.LENGTH_LONG).show();
                                } else if (!((txtDate.getText().toString()).matches("\\d{2}-\\d{2}-\\d{4}") &&
                                        (txtTime.getText().toString()).matches("\\d{2}:\\d{2}"))) {
                                    Toast.makeText(getApplicationContext(), "Please enter valid date and time according to pattern", Toast.LENGTH_LONG).show();
                                } else {
                                    event.setDate(txtDate.getText().toString().trim());
                                    event.setTime(txtTime.getText().toString().trim());
                                    event.setPlace(txtPlace.getText().toString().trim());
                                    event.setNumOfGuests(Integer.parseInt(txtnumOfGuests.getText().toString()));
                                    event.setBudget(txtBudget.getText().toString().trim());
                                    event.setTheme(txtTheme.getText().toString().trim());

                                    event.setPhoto(chkPhoto.isChecked());
                                    event.setFood(chkFood.isChecked());
                                    event.setEnter(chkEnter.isChecked());

                                    dbRef.setValue(event);

                                    Toast.makeText(getApplicationContext(),"Update Successful!!!", Toast.LENGTH_LONG).show();
                                    clearControls();
                                }

                            }
                            catch(Exception e){
                                    Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Event").child(UserID);
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(name)){
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Deletion Successful!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void clearControls(){
        txtDate.setText("");
        txtTime.setText("");
        txtPlace.setText("");
        txtnumOfGuests.setText("");
        txtBudget.setText("");
        txtTheme.setText("");
        chkPhoto.setChecked(FALSE);
        chkEnter.setChecked(FALSE);
        chkFood.setChecked(FALSE);
    }
}
