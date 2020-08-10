package com.example.skripsi1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Termometer extends AppCompatActivity {

    SwitchCompat switchCompat;
    String suhuVal;
    int btSuhu;
    TextView text, suhu;
    FirebaseDatabase database;
    DatabaseReference dbSuhuVal, dbSuhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termometer);

        database = FirebaseDatabase.getInstance();
        switchCompat = findViewById(R.id.swTemp);
        text = findViewById(R.id.textView);
        text.setVisibility(View.INVISIBLE);
//        identifier Suhu TextView
        suhu = findViewById(R.id.tvSuhuValue);
        suhu.setVisibility(View.INVISIBLE);

//        identifier data reference
        dbSuhuVal = FirebaseDatabase.getInstance().getReference("suhu");
        dbSuhuVal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    suhuVal = dataSnapshot.getValue(String.class);
                    suhu.setText(suhuVal + "°C");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dbSuhu = FirebaseDatabase.getInstance().getReference("termometer");
        dbSuhu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btSuhu = dataSnapshot.getValue(Integer.class);
                    if(btSuhu != 0){
                        switchCompat.setChecked(true);
                        text.setVisibility(View.VISIBLE);
                        suhu.setVisibility(View.VISIBLE);
                    } else {
                        switchCompat.setChecked(false);
                        text.setVisibility(View.INVISIBLE);
                        suhu.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("termometer").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (switchCompat.isChecked()){
                            Toast.makeText(Termometer.this, "Termometer Menyala", Toast.LENGTH_SHORT).show();

                        }else{
                            database.getReference("termometer").setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Termometer.this, "Termometer Mati", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }

        });
    }

}