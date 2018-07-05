package com.manik.gymmembership;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.manik.gymmembership.AppDatabase.AppDatabase;
import com.manik.gymmembership.AppDatabase.Member;
import com.manik.gymmembership.R;

import java.util.List;

public class DisplayMembers extends AppCompatActivity implements RecAdapter.ItemClickListener {

    RecyclerView mRecyclerView;
    RecAdapter mRecAdapter;

    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_members);

        mRecyclerView = findViewById(R.id.recylerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecAdapter = new RecAdapter(this ,  this);
        mRecyclerView.setAdapter(mRecAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int positon = viewHolder.getAdapterPosition();
                        List<Member> members =mRecAdapter.getMembersEntries();
                        mDb.gymDao().deleteTask(members.get(positon));
                        reteriveMembers();
                    }
                });

            }
        }).attachToRecyclerView(mRecyclerView);

        mDb= AppDatabase.getInstance(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();

        reteriveMembers();

    }

    private void reteriveMembers() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Member> list = mDb.gymDao().loadAllTasks();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecAdapter.setTasks(list);
                    }
                });
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent

        Intent intent = new Intent(DisplayMembers.this, AddMember.class);
        intent.putExtra(AddMember.EXTRA_TASK_ID , itemId);
        startActivity(intent);
    }
}
