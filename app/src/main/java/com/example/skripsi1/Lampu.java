package com.example.skripsi1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Lampu extends AppCompatActivity {

    SwitchCompat switchCompat;
    ImageView imageView;
    FirebaseDatabase database;
    int btLampu;
    DatabaseReference dbLampu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lampu);

        database = FirebaseDatabase.getInstance();
        switchCompat = findViewById(R.id.swLampu);
        imageView = findViewById(R.id.imageView);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.lampuoff));

        dbLampu = FirebaseDatabase.getInstance().getReference("lampu");
        dbLampu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btLampu = dataSnapshot.getValue(Integer.class);
                    if(btLampu != 0){
                        switchCompat.setChecked(true);
                    } else {
                        switchCompat.setChecked(false);
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
                database.getReference("lampu").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (switchCompat.isChecked()){
                            Toast.makeText(Lampu.this, "Lampu Menyala", Toast.LENGTH_SHORT).show();
                            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lampuon));
                        }else{
                            database.getReference("lampu").setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Lampu.this, "Lampu Mati", Toast.LENGTH_SHORT).show();
                                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.lampuoff));
                                }
                            });
                        }
                    }
                });
            }

        });
    }
}
