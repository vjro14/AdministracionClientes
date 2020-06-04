package com.example.administracionclientes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administracionclientes.Clases.Client;
import com.example.administracionclientes.R;

import java.util.List;

public class clientsAdapter extends RecyclerView.Adapter<clientsAdapter.clientsViewHolder> {

    private Context myContext;
    private List<Client> myData;

    public clientsAdapter(Context myContext, List<Client> myData) {
        this.myContext = myContext;
        this.myData = myData;
    }

    @NonNull
    @Override
    public clientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(myContext);
        view = inflater.inflate(R.layout.item_client, parent, false);

        return new clientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull clientsViewHolder holder, int position) {

        holder.nombres.setText(myData.get(position).getNombres());
        holder.apellidos.setText(myData.get(position).getApellidos());
        holder.id.setText(myData.get(position).getId() + "");
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    static class clientsViewHolder extends RecyclerView.ViewHolder {

        TextView nombres, apellidos, id;


        clientsViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.tvNombres);
            apellidos = itemView.findViewById(R.id.tvApellidos);
            id = itemView.findViewById(R.id.tv_id_client);
        }
    }
}
