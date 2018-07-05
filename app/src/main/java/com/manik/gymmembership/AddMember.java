package com.manik.gymmembership;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import com.manik.gymmembership.AppDatabase.AppDatabase;
import com.manik.gymmembership.AppDatabase.Member;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddMember extends AppCompatActivity {

    private static final int DEFAULT_TASK_ID = -1;
    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private int mTaskId = DEFAULT_TASK_ID;
    AppDatabase mDatabase;

    private String gender;
    private String branch;
    private Spinner spinner;
    private static final String[] paths = {"Janakpuri D Block", "Kirti Nagar", "Sagarpur"};
    private TextView mJoinDate;
    private EditText et_Amount, et_name, et_mobile, et_months;
    private RadioGroup radioGroup;
    private Button btn_add;
    private ArrayAdapter<String> sAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mJoinDate = findViewById(R.id.tv_joindate);
        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        mJoinDate.setText(currentDate);
        et_Amount = findViewById(R.id.et_amount);
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_months = findViewById(R.id.et_months);
        btn_add = findViewById(R.id.bt_addmember);
        radioGroup = findViewById(R.id.radioGroup);


        spinner = findViewById(R.id.spinner);
        sAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, paths);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        branch = "Janakpuri D Block";
                        break;
                    case 1:
                        branch = "Kirti Nagar";
                        break;
                    case 2:
                        branch = "Sagarpur";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mDatabase = AppDatabase.getInstance(getApplicationContext());
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final Member member =mDatabase.gymDao().loadMemberById(mTaskId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUi(member);
                            }
                        });
                    }
                });
            }
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = et_name.getText().toString();
                    String mobile = et_mobile.getText().toString();
                    Date date = new Date();
                    String m = et_months.getText().toString();
                    int months = Integer.parseInt(m);
                    String a = et_Amount.getText().toString();
                    int amount = Integer.parseInt(a);

                    final Member member = new Member(name, date, currentDate, branch, mobile, amount, months, gender);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (mTaskId == DEFAULT_TASK_ID){
                                mDatabase.gymDao().insertTask(member);
                            }else {
                                member.setId(mTaskId);
                                mDatabase.gymDao().updateTask(member);
                            }
                        }
                    });
                    Intent intent = new Intent(AddMember.this, MainActivity.class);
                    finish();
                    startActivity(intent);

                } catch (NumberFormatException n) {

                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.rb_male:
                        gender = "Male";
                        break;
                    case R.id.rb_female:
                        gender = "Female";
                        break;
                }
            }
        });

    }

    private void populateUi(Member member) {
        if(member == null){
            return;
        }
        et_name.setText(member.getName());
        et_Amount.setText(String.valueOf(member.getAmount()));
        et_mobile.setText(member.getMobile());
        et_months.setText(String.valueOf(member.getMonths()));
        int spos = sAdapter.getPosition(member.getBranch());
        spinner.setSelection(spos);

    }


    public void onBackPressed() {
        Intent intent = new Intent(AddMember.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

}




