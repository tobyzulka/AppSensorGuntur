package com.example.skripsi1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PompaActivity extends AppCompatActivity {

    SwitchCompat switchCompat;
    FirebaseDatabase database;
    int btPompa;
    DatabaseReference dbPompa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pompa);

        database = FirebaseDatabase.getInstance();
        switchCompat = findViewById(R.id.swPompa);

        dbPompa = FirebaseDatabase.getInstance().getReference("pompa");
        dbPompa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btPompa = dataSnapshot.getValue(Integer.class);
                    if(btPompa != 0){
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
                database.getReference("pompa").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (switchCompat.isChecked()){
                            Toast.makeText(PompaActivity.this, "Pompa Menyala", Toast.LENGTH_SHORT).show();

                        }else{
                            database.getReference("pompa").setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(PompaActivity.this, "Pompa Mati", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });
            }

        });
    }
}
