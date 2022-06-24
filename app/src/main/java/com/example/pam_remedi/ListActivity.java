package com.example.pam_remedi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pam_remedi.adapter.UserAdapter;
import com.example.pam_remedi.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    FloatingActionButton fab;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<User> list = new ArrayList<>();
    private UserAdapter userAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.btn_add);

        progressDialog = new ProgressDialog(ListActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        userAdapter = new UserAdapter(getApplicationContext(), list);
        userAdapter.setDialog(new UserAdapter.Dialog() {
            @Override
            public void onClick(final int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNama());
                                intent.putExtra("subuh", list.get(pos).getSubuh());
                                intent.putExtra("dzuhur", list.get(pos).getDzuhur());
                                intent.putExtra("ashar", list.get(pos).getAshar());
                                intent.putExtra("maghrib", list.get(pos).getMaghrib());
                                intent.putExtra("isya", list.get(pos).getIsya());

                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(userAdapter);

        fab.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), EditorActivity.class));
        });
    }


    @Override
    protected void onStart(){
        super.onStart();
        getData();
    }


    private void getData() {
        progressDialog.show();

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        User user = new User(document.getString("nama"), document.getString("subuh"), document.getString("dzuhur"), document.getString("ashar"), document.getString("maghrib"), document.getString("isya") );
                        user.setId(document.getId());
                        list.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    private void deleteData(String id){
        progressDialog.show();
        db.collection("users").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Data gagal di hapus!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                getData();
            }
        });
    }
}