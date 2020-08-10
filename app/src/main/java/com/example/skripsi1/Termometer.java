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
import com.google.firebase.database.FirebaseDatabase;

public class Termometer extends AppCompatActivity {

    SwitchCompat switchCompat;

    TextView text;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termometer);

        database = FirebaseDatabase.getInstance();
        switchCompat = findViewById(R.id.switchButton);
        text = findViewById(R.id.textView);

        text.setVisibility(View.INVISIBLE);

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("termometer").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (switchCompat.isChecked()){
                            Toast.makeText(Termometer.this, "Termometer Menyala", Toast.LENGTH_SHORT).show();
                            text.setVisibility(View.VISIBLE);
                        }else{
                            database.getReference("termometer").setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Termometer.this, "Termometer Mati", Toast.LENGTH_SHORT).show();
                                    text.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }
                });
            }

        });
    }
}
