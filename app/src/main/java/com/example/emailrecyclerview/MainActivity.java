package com.example.emailrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import adapters.EmailItemAdapter;
import io.bloco.faker.Faker;
import models.EmailItemModel;

public class MainActivity extends AppCompatActivity {

    List<EmailItemModel> items;
    Faker fake = new Faker();
    EditText editText;
    Button butFavorite;
    int dem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edittext_search);
        butFavorite = (Button) findViewById(R.id.button_favorite);

        items = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            items.add(new EmailItemModel("Long"+i,"De bai"+i,"help me"+i, "12:00 PM"));

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final EmailItemAdapter adapter = new EmailItemAdapter(items);
        recyclerView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().toString()=="")
                {
                    final EmailItemAdapter adapter = new EmailItemAdapter(items);
                    recyclerView.setAdapter(adapter);
                } else {
                    List<EmailItemModel> search = new ArrayList<>();
                    for (int i =0; i < items.size(); i++)
                    {
                        String ss = editText.getText().toString();
                        if (items.get(i).getName().indexOf(ss)!=-1||items.get(i).getSubject().indexOf(ss)!=-1||items.get(i).getContent().indexOf(ss)!=-1)
                        {
                            search.add(items.get(i));
                        }

                    }
                    final EmailItemAdapter adapter = new EmailItemAdapter(search);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        butFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<EmailItemModel> favorite = new ArrayList<>();
                for (int i = 0; i < items.size(); i++){
                    if (items.get(i).isFavorite()){
                        favorite.add(items.get(i));
                    }
                }
                if (dem % 2 == 0){
                    final EmailItemAdapter adapter = new EmailItemAdapter(favorite);
                    recyclerView.setAdapter(adapter);
                } else {
                    final EmailItemAdapter adapter = new EmailItemAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                dem++;

            }
        });
    }
}
