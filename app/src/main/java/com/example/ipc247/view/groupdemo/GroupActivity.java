package com.example.ipc247.view.groupdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.ipc247.R;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    List<Section> sectionList = new ArrayList<>();
    RecyclerView mainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        initData();

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(sectionList);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initData() {

        String sectionOneName = "Action";
        List<String> sectionOneItems = new ArrayList<>();
        sectionOneItems.add("Captain America");
        sectionOneItems.add("Iron Man");
        sectionOneItems.add("Endgame");

        String sectionTwoName = "Adventure";
        List<String> sectionTwoItems = new ArrayList<>();
        sectionTwoItems.add("Pirates of the Caribbean");
        sectionTwoItems.add("King Kong");
        sectionTwoItems.add("Life of Pie");

        String sectionThreeName = "Epic";
        List<String> sectionThreeItems = new ArrayList<>();
        sectionThreeItems.add("Titanic");
        sectionThreeItems.add("Gandhi");
        sectionThreeItems.add("Ben-Hur");

        String sectionFourName = "War";
        List<String> sectionFourItems = new ArrayList<>();
        sectionFourItems.add("Saving Private Ryan");
        sectionFourItems.add("1917");
        sectionFourItems.add("Valkyrie");
        sectionFourItems.add("The Hurt Locker");

        sectionList.add(new Section(sectionOneName, sectionOneItems));
        sectionList.add(new Section(sectionTwoName, sectionTwoItems));
        sectionList.add(new Section(sectionThreeName, sectionThreeItems));
        sectionList.add(new Section(sectionFourName, sectionFourItems));

        Log.d(TAG, "initData: " + sectionList);
    }
}