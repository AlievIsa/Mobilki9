package com.example.mobilki9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilki9.adapterClass.AffirmationFragment;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ListView toDoList;
    String[] list;
    ArrayList<String> selectedItems = new ArrayList<String>();
    ArrayList<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting stringArray from resource
        list = getResources().getStringArray(R.array.list);
        // adding initial list to ArrayList
        Collections.addAll(items, list);
        // getting element ListView
        toDoList = findViewById(R.id.toDoList);
        // creating adapter
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, items);
        // setting adapter for to-so list
        toDoList.setAdapter(adapter);

        // click handling
        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // setting strike_thru_text_flag
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                boolean isStrikethrough = textView.getPaintFlags() ==
                        (textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                if (isStrikethrough) {
                    textView.setPaintFlags(textView.getPaintFlags() &
                            (~Paint.STRIKE_THRU_TEXT_FLAG));
                } else {
                    textView.setPaintFlags(textView.getPaintFlags() |
                            Paint.STRIKE_THRU_TEXT_FLAG);
                }

                // adding and removing items from selectedItems
                String item = adapter.getItem(i);
                if(toDoList.isItemChecked(i))
                    selectedItems.add(item);
                else
                    selectedItems.remove(item);
            }
        });
    }

    public void add(View view){
        EditText editItem = findViewById(R.id.toDoItemEdit);
        String item = editItem.getText().toString();
        if(!item.isEmpty()){
            adapter.add(item);
            editItem.setText("");
            adapter.notifyDataSetChanged();
        }
    }
    public void remove(View view){
        // getting and removing strike_thru flags
        for(int i=0; i< selectedItems.size();i++){
            int index = items.indexOf(selectedItems.get(i).toString());
            View itemView = toDoList.getChildAt(index);
            TextView textView = (TextView) itemView.findViewById(android.R.id.text1);
            textView.setPaintFlags(textView.getPaintFlags() &
                    (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        // getting and removing items from adapter
        for(int i=0; i< selectedItems.size();i++){
            adapter.remove(selectedItems.get(i));
        }
        // remove all previously set marks and clear selectedItems
        toDoList.clearChoices();
        selectedItems.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.affirmationFragmentId){
            //fragment = new AffirmationFragment();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, AffirmationFragment.class, null).
                    addToBackStack(null).
                    commit();
        }

        return super.onOptionsItemSelected(item);
    }
}