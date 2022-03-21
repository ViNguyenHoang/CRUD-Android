package com.example.hololivecrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hololivecrud.database.StudentDatabase;

public class UpdateActivity extends AppCompatActivity {

    private EditText editStudentName;
    private EditText editInfo;
    private Button btnUpdate;

    private Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initUi();

        mStudent = (Student) getIntent().getExtras().get("object_student");
        if(mStudent != null){
            editStudentName.setText(mStudent.getStudentName());
            editInfo.setText(mStudent.getInfo());
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudent();
            }
        });
    }

    private void updateStudent(){
        String studentName = editStudentName.getText().toString().trim();
        String info = editInfo.getText().toString().trim();

        // Check Empty
        if(TextUtils.isEmpty(studentName) || TextUtils.isEmpty(info)){
            return;
        }

        mStudent.setStudentName(studentName);
        mStudent.setInfo(info);

        StudentDatabase.getInstance(this).studentDAO().updateStudent(mStudent);

        Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();

        Intent result = new Intent();
        setResult(Activity.RESULT_OK, result);
        finish();

    }

    // Init UI
    private void initUi(){
        editStudentName = findViewById(R.id.edt_studentName);
        editInfo = findViewById(R.id.edt_info);
        btnUpdate = findViewById(R.id.btn_update);
    }
}