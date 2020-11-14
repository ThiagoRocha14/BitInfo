package com.example.testefinal.ui.noticias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.testefinal.MainActivity;
import com.example.testefinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NoticiasFragment extends Fragment {

    private NoticiasViewModel noticiasViewModel;
    ArrayAdapter<Noticia> adapter;
    ListView listanoticias;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference noticiasbd = database.getReference("noticias");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        noticiasViewModel = ViewModelProviders.of(this).get(NoticiasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_noticias, container, false);
        listanoticias = root.findViewById(R.id.listView);
        noticiasbd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Noticia> lista = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Noticia noticia = data.getValue(Noticia.class);
                    lista.add(noticia);
                }
                Collections.reverse(lista);
                adapter = new NoticiaAdapter(getContext(),lista);
                listanoticias.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listanoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Noticia noticia = adapter.getItem(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(noticia.getLink()));
                startActivity(browserIntent);
            }
        });
        return root;

    }
}