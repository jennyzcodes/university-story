package com.example.game.DataHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.game.Contract.IData;
import com.example.game.Model.Student;

import java.util.ArrayList;

/** inspired by
 * Android SQLite - Very basic SQLite Contact App
 * https://www.youtube.com/watch?v=pXn9s-2YsIw
 */

public class DataHandler extends SQLiteOpenHelper implements IData {

    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "studentData";
    private	static final String TABLE_STUDENTS = "students";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LEVEL = "currentLevel";
    private static final String COLUMN_CREDIT ="credit";
    private static final String COLUMN_GPA = "gpa";
    private static final String COLUMN_HP = "hp";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_APPEARANCE = "appearance";
    private static final String COLUMN_LANGUAGE = "language";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_CONTACTS_TABLE = "CREATE	TABLE " + TABLE_STUDENTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD +
                " TEXT," + COLUMN_LEVEL + " TEXT," + COLUMN_CREDIT + " TEXT,"+ COLUMN_GPA + " TEXT," + COLUMN_HP + " TEXT," + COLUMN_NAME + " TEXT," +
                COLUMN_APPEARANCE + " TEXT,"+ COLUMN_LANGUAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    @Override
    public ArrayList<Student> listStudents(){
        String sql = "select * from " + TABLE_STUDENTS + " ORDER BY " + COLUMN_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Student> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String currentLevel = cursor.getString(3);
                String credit = cursor.getString(4);
                String gpa = cursor.getString(5);
                String hp = cursor.getString(6);
                String name = cursor.getString(7);
                String appearance = cursor.getString(8);
                String language = cursor.getString(9);

                storeContacts.add(new Student(username, password, Integer.parseInt(currentLevel), Integer.parseInt(credit)
                , Double.parseDouble(gpa), Double.parseDouble(hp), name, Integer.parseInt(appearance), language));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    @Override
    public void addStudentData(Student student){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, student.getUsername());
        values.put(COLUMN_PASSWORD, student.getPassword());
        values.put(COLUMN_LEVEL, Integer.toString(student.getCurrentLevel()));
        values.put(COLUMN_CREDIT, Integer.toString(student.getCredit()));
        values.put(COLUMN_GPA, Double.toString(student.getGpa()));
        values.put(COLUMN_HP, Double.toString(student.getHp()));
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_APPEARANCE, Integer.toString(student.getAppearance()));
        values.put(COLUMN_LANGUAGE, student.getLanguage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_STUDENTS, null, values);
    }

    @Override
    public void updateStudentData(Student student){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, student.getUsername());
        values.put(COLUMN_PASSWORD, student.getPassword());
        values.put(COLUMN_LEVEL, Integer.toString(student.getCurrentLevel()));
        values.put(COLUMN_CREDIT, Integer.toString(student.getCredit()));
        values.put(COLUMN_GPA, Double.toString(student.getGpa()));
        values.put(COLUMN_HP, Double.toString(student.getHp()));
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_APPEARANCE, Integer.toString(student.getAppearance()));
        values.put(COLUMN_LANGUAGE, student.getLanguage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_STUDENTS, values, COLUMN_USERNAME	+ "	= ?", new String[] { student.getUsername()});
    }

    @Override
    public Student getStudentByUserName(String userName){
        String query = "Select * FROM "	+ TABLE_STUDENTS + " WHERE " + COLUMN_USERNAME + " = " + "userName";
        SQLiteDatabase db = this.getWritableDatabase();
        Student student = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String currentLevel = cursor.getString(3);
            String credit = cursor.getString(4);
            String gpa = cursor.getString(5);
            String hp = cursor.getString(6);
            String name = cursor.getString(7);
            String appearance = cursor.getString(8);
            String language = cursor.getString(9);
            student = new Student(username, password, Integer.parseInt(currentLevel), Integer.parseInt(credit)
                    , Double.parseDouble(gpa), Double.parseDouble(hp), name, Integer.parseInt(appearance), language);
        }
        cursor.close();
        return student;
    }


}