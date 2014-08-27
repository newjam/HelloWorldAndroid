package com.github.newjam.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;


public class HelloActivity extends Activity {

    private final static String TAG = HelloActivity.class.getSimpleName();

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");

        mStudents = studentsSource.getAllStudents();

        Log.i(TAG, "Retrieving students from database.");
        for(Student s : mStudents) {
            Log.i(TAG, s.toString());
        }

        mStudentsAdapter.clear();
        mStudentsAdapter.addAll(mStudents);
        mStudentsAdapter.notifyDataSetChanged();

        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");

        studentsSource.updateStudents(mStudents);

        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }


    private List<Student> mStudents = new ArrayList<Student>();

    private StudentsDataSource studentsSource;

    private ArrayAdapter mStudentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();

        RemoteStudentResource remoteStudentResource = restAdapter.create(RemoteStudentResource.class);
        mStudents = remoteStudentResource.listStudents();
        */

        //Intent i = new Intent(Intent.ACTION_VIEW);
        //i.setData(Uri.parse("http://www.pixatel.com"));
       // startActivity(i);

        studentsSource = new StudentsDataSource(this);

        //studentsSource.insertStudents(Student.sampleData());

        setContentView(R.layout.activity_hello);

        ListView studentListView = (ListView) findViewById(R.id.student_list_view);

         mStudentsAdapter = new ArrayAdapter<Student>(
                this
              , R.layout.student_list_item
              , mStudents
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // get the current student
                final Student student = getItem(position);

                // either reuse or inflate a view
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);
                }

                // get views
                TextView tvName = (TextView) convertView.findViewById(R.id.student_name_textview);
                CheckBox cbPresent = (CheckBox) convertView.findViewById(R.id.student_checkbox);

                // populate views
                cbPresent.setChecked(student.isPresent());
                tvName.setText(student.toString());

                // handle checking students
                cbPresent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckBox cbPresent = (CheckBox) view;
                        student.setPresent(cbPresent.isChecked());
                        Log.i(TAG, student.toString() + " marked as " + (student.isPresent() ? "present" : "absent"));
                    }
                });

                // return the view
                return convertView;

            }
        };

        studentListView.setAdapter( mStudentsAdapter);

        // Spinner
        Spinner spinner = (Spinner) findViewById(R.id.class_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.classes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //
        Button submitButton = (Button) findViewById(R.id.submit_rolecall);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int presentCount = 0;
                for(Student student : mStudents) {
                    presentCount += student.isPresent() ? 1 : 0;
                }

                Log.i(TAG, presentCount + " of " + mStudents.size() + " students present");
            }
        });

        //TextView tv = (TextView) findViewById(R.id.hello_tv_1);
        //tv.setText("Replaced Text!");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Log.i(TAG, "clicked settings menu option");

            startActivity(new Intent(HelloActivity.this, HelloWorldPreferenceActivity.class));


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
