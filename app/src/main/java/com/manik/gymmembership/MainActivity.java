package com.manik.gymmembership;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.manik.gymmembership.AppDatabase.AppDatabase;

public class MainActivity extends AppCompatActivity {

    TextView tMember;
    AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tMember=findViewById(R.id.tmembers);

        mDb = AppDatabase.getInstance(this);
        int m = mDb.gymDao().getTotalMembers();
        tMember.setText(String.valueOf(m));
    }


    public void AddMember(View view) {

        Intent intent = new Intent(MainActivity.this , AddMember.class);
        finish();
        startActivity(intent);
    }

    public void DisplayMembersList(View view) {

        Intent intent = new Intent(MainActivity.this, DisplayMembers.class);
        finish();
        startActivity(intent);

    }
}
