package com.example.pam_remedi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText ednama;
    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnmasuk);
        ednama = findViewById(R.id.Nama);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = ednama.getText().toString();

                if (ednama.getText().toString().length() == 0) {
                    ednama.setError("nama diperlukan");
                }

                Intent i = new Intent(MainActivity.this, ListActivity.class);
                startActivity(i);
            }
        });
    }
}