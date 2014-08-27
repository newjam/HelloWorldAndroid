package com.github.newjam.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newjam on 8/25/14.
 */
public class StudentsDataSource {


    private final SQLiteDatabase database;

    public StudentsDataSource(Context context) {
        this(new RoleCallSqliteOpenHelper(context).getWritableDatabase());
    }

    public StudentsDataSource(SQLiteDatabase database) {
        this.database = database;
    }

    protected static final String CREATE_STUDENT_TABLE = "CREATE TABLE " + Student.STUDENT_TABLE + "( "
            + Student.FIRST_NAME + " TEXT, "
            + Student.LAST_NAME + " TEXT, "
            + Student.PRESENT + " INTEGER, "
            + Student.ID + " TEXT PRIMARY KEY)";

    protected  static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + Student.STUDENT_TABLE;

    public void close() {
        database.close();
    }

    public Cursor getAllStudentsCursor() {
        return database.query(
                Student.STUDENT_TABLE,
                new String[]{
                          Student.ID
                        , Student.PRESENT
                        , Student.FIRST_NAME
                        , Student.LAST_NAME
                }, null, null, null, null, null);
    }

    private static Student deserializeStudent(Cursor c) {
        return new Student(
                c.getString(c.getColumnIndexOrThrow(Student.ID))
                , c.getString(c.getColumnIndexOrThrow(Student.FIRST_NAME))
                , c.getString(c.getColumnIndexOrThrow(Student.LAST_NAME))
                , c.getInt(c.getColumnIndexOrThrow(Student.PRESENT)) == 1
        );
    }

    private static ContentValues serializeStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put(Student.ID        , student.getId());
        values.put(Student.FIRST_NAME, student.getFirstName());
        values.put(Student.LAST_NAME , student.getLastName());
        values.put(Student.PRESENT   , student.isPresent());
        return values;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<Student>();
        Cursor c = getAllStudentsCursor();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            students.add(deserializeStudent(c));
        }
        return students;
    }

    public void updateStudent(Student student) {
        database.update(Student.STUDENT_TABLE, serializeStudent(student), Student.ID + " = ?" , new String[]{student.getId()});
    }

    public void updateStudents(List<Student> students) {
        for(Student s : students) {
            updateStudent(s);
        }
    }

    public void insertStudent(Student student) {
        database.insert(Student.STUDENT_TABLE, null, serializeStudent(student));
    }

    public void insertStudents(List<Student> students) {
        for(Student s : students) {
            insertStudent(s);
        }
    }

}
