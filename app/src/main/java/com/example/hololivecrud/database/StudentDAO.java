package com.example.hololivecrud.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hololivecrud.Student;

import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    void insertStudent(Student student);

    @Query("Select * FROM student")
    List<Student> getListStudent();

    @Query("Select * FROM student where student_name = :studentName")
    List<Student> checkStudent(String studentName);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);
}
