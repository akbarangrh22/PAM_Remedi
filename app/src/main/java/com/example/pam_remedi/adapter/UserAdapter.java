package com.example.pam_remedi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pam_remedi.R;
import com.example.pam_remedi.model.User;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context context;
    private List<User> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (@NotNull MyViewHolder holder, int position) {
        holder.nama.setText(list.get(position).getNama());
        holder.subuh.setText(list.get(position).getSubuh());
        holder.dzuhur.setText(list.get(position).getDzuhur());
        holder.ashar.setText(list.get(position).getAshar());
        holder.maghrib.setText(list.get(position).getMaghrib());
        holder.isya.setText(list.get(position).getIsya());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama, subuh, dzuhur, ashar, maghrib, isya;

        public MyViewHolder(@NotNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            subuh = itemView.findViewById(R.id.subuh);
            dzuhur = itemView.findViewById(R.id.dzuhur);
            ashar = itemView.findViewById(R.id.ashar);
            maghrib = itemView.findViewById(R.id.maghrib);
            isya = itemView.findViewById(R.id.isya);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}