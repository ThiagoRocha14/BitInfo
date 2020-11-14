package com.example.testefinal.ui.noticias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.testefinal.R;

import java.util.ArrayList;

public class NoticiaAdapter extends ArrayAdapter<Noticia> {

    private ArrayList<Noticia> lista;
    private Context context;

    public NoticiaAdapter(Context c, ArrayList<Noticia> objects){
        super(c,0,objects);
        this.context = c;
        this.lista = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;
        if(lista != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.act_lista,parent,false);

            TextView titulo = view.findViewById(R.id.txtTitulo);

            Noticia t = lista.get(position);
            titulo.setText(t.getTitulo());
        }
        return view;
    }
}
