package com.example.pam_remedi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EditorActivity extends AppCompatActivity {

    private EditText editName, editSubuh, editDzuhur, editAshar, editMaghrib, editIsya;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editName = findViewById(R.id.nama);
        editSubuh = findViewById(R.id.subuh);
        editDzuhur = findViewById(R.id.dzuhur);
        editAshar = findViewById(R.id.ashar);
        editMaghrib = findViewById(R.id.maghrib);
        editIsya = findViewById(R.id.isya);
        btnSave = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(EditorActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        btnSave.setOnClickListener(view -> {
            if (editName.getText().length()>0 && editSubuh.getText().length()>0 && editDzuhur.getText().length()>0 && editAshar.getText().length()>0 && editMaghrib.getText().length()>0 && editIsya.getText().length()>00){
                saveData(editName.getText().toString(), editSubuh.getText().toString(), editDzuhur.getText().toString(), editAshar.getText().toString(), editMaghrib.getText().toString(), editIsya.getText().toString());
            }else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent!= null){
            id = intent.getStringExtra("id");
            editName.setText(intent.getStringExtra("name"));
            editSubuh.setText(intent.getStringExtra("subuh"));
            editDzuhur.setText(intent.getStringExtra("dzuhur"));
            editAshar.setText(intent.getStringExtra("ashar"));
            editMaghrib.setText(intent.getStringExtra("maghrib"));
            editIsya.setText(intent.getStringExtra("isya"));
        }
    }

    private void saveData(String name, String subuh, String dzuhur, String ashar, String maghrib, String isya){
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("subuh", subuh);
        user.put("dzuhur", dzuhur);
        user.put("ashar", ashar);
        user.put("maghrib", maghrib);
        user.put("isya", isya);

        progressDialog.show();
        if (id!=null){
            db.collection("users").document(id).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(),"Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}