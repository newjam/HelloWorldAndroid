package com.github.newjam.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by newjam on 8/22/14.
 */
public class RoleCallSqliteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rolecall";


    public RoleCallSqliteOpenHelper(Context context) {
        super(context, "DATABASE_NAME", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StudentsDataSource.CREATE_STUDENT_TABLE);

        StudentsDataSource studentsDataSource = new StudentsDataSource(db);
        studentsDataSource.insertStudents(Student.sampleData());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL(StudentsDataSource.DROP_STUDENT_TABLE);
        onCreate(db);
    }
}
