package com.example.game.Model;

import com.example.game.Model.Student;

public abstract class GameLevel {
    private Student student;

    public abstract void init();

    public abstract GameLevel makeCopy();

    public void setStudent(Student s){
        this.student = s;
    }

    public abstract void levelClear();
}
