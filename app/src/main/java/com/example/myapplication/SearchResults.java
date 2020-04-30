package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchResults extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ForumDataAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<DiscussionThread> forumEvents;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        Intent intent = getIntent();
        forumEvents = ClientCommunicator.searchThreadsByName(intent.getStringExtra("ForumThread"));
        forumEvents = ExpirationFilter.removeExpired(Sorter.sort(ServerResponseParser.parseDiscussionThreads()));

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ForumDataAdapter(forumEvents);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        final ArrayList<DiscussionThread> finalForumEvents = forumEvents;
        mAdapter.setOnItemClickListener(new ForumDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent2 = new Intent(SearchResults.this, ExpandForum.class);
                intent2.putExtra("ForumThread", finalForumEvents.get(position));
                startActivity(intent2);
            }
        });
    }
}
