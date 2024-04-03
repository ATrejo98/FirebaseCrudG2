package com.uth.firebasecrudg2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.uth.firebasecrudg2.model.Persona;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<Persona> dataList;

    public MyAdapter(Context context, List<Persona> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtNombres.setText(dataList.get(position).getNombre() + " " + dataList.get(position).getApellidos());
        holder.txtEdad.setText(dataList.get(position).getEdad() + " Años ");

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Nombres", dataList.get(holder.getAdapterPosition()).getNombre());
                intent.putExtra("Apellidos", dataList.get(holder.getAdapterPosition()).getApellidos());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Edad", dataList.get(holder.getAdapterPosition()).getEdad());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<Persona> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView txtNombres, txtApellidos, txtEdad;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
        txtNombres = itemView.findViewById(R.id.txtNombres);
        txtApellidos = itemView.findViewById(R.id.txtApellidos);
        txtEdad = itemView.findViewById(R.id.txtEdad);
    }
}