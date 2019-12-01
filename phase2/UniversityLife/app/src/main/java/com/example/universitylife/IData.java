package com.example.universitylife;


import com.example.universitylife.Student.StudentData;

public interface IData {
    void addStudentData(StudentData student);

    void updateStudentData(StudentData student);

    StudentData getStudentByUserName(String userName);
}
