package com.example.hololivecrud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hololivecrud.database.StudentDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editStudentName;
    private EditText editInfo;
    private Button btnAdd;
    private RecyclerView rcvStudent;

    private StudentAdapter studentAdapter;
    private List<Student> mListStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        studentAdapter = new StudentAdapter(new StudentAdapter.IClickItemStudent() {
            @Override
            public void updateStudent(Student student) {
                clickUpdateStudent(student);
            }

            @Override
            public void deleteStudent(Student student) {
                clickDeleteStudent(student);
            }
        });


        mListStudent = new ArrayList<>();
        studentAdapter.SetData(mListStudent);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvStudent.setLayoutManager(linearLayoutManager);
        rcvStudent.setAdapter(studentAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });

        loadData();
    }

    // Button Insert Student to Database
    private void addStudent() {
        String studentName = editStudentName.getText().toString().trim();
        String info = editInfo.getText().toString().trim();

        // Check Empty
        if(TextUtils.isEmpty(studentName) || TextUtils.isEmpty(info)){
            return;
        }

        // Create new Student
        Student student = new Student(studentName, info);

        if (isStudentExist(student)){
            Toast.makeText(this, "User Existed", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert student to Database
        StudentDatabase.getInstance(this).studentDAO().insertStudent(student);
        Toast.makeText(this, "Add successful", Toast.LENGTH_SHORT).show();

        editStudentName.setText("");
        editInfo.setText("");

        loadData();

    }

    // Button Update Student to Database
    private void clickUpdateStudent(Student student) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_student", student);
        intent.putExtras(bundle);

        startActivityForResult(intent ,9);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 9 && resultCode == Activity.RESULT_OK){
            loadData();
        }
    }

    private void clickDeleteStudent(Student student){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("ARE YOU SURE!!!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StudentDatabase.getInstance(MainActivity.this).studentDAO().deleteStudent(student);
                        Toast.makeText(MainActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                }).setNegativeButton("No", null)
                .show();
    }

    // Init UI
    private void initUi(){
        editStudentName = findViewById(R.id.edt_studentName);
        editInfo = findViewById(R.id.edt_info);
        btnAdd = findViewById(R.id.btn_add);
        rcvStudent = findViewById(R.id.rcv_student);
    }

    // Load Data from Database
    private void loadData(){
        mListStudent = StudentDatabase.getInstance(this).studentDAO().getListStudent();
        studentAdapter.SetData(mListStudent);
    }

    private boolean isStudentExist(Student student){
        List<Student> list = StudentDatabase.getInstance(this).studentDAO().checkStudent(student.getStudentName());
        return list != null && !list.isEmpty();
    }
}