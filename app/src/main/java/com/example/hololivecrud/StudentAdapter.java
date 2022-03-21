package com.example.hololivecrud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends  RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> mListStudent;
    public void SetData(List<Student> list){
        this.mListStudent = list;
        notifyDataSetChanged();
    }

    private IClickItemStudent iClickItemStudent;

    public interface IClickItemStudent{
        void updateStudent(Student student);
        void deleteStudent(Student student);
    }

    public StudentAdapter(IClickItemStudent iClickItemStudent) {
        this.iClickItemStudent = iClickItemStudent;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        final Student student = mListStudent.get(position);
        if(student == null){
            return;
        }

        holder.tvStudentName.setText(student.studentName);
        holder.tvInfo.setText(student.info);
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemStudent.updateStudent(student);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemStudent.deleteStudent(student);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListStudent != null){
            return mListStudent.size();
        }
        return 0;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{

        private TextView tvStudentName;
        private TextView tvInfo;
        private Button btnUpdate;
        private Button btnDelete;


        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStudentName = itemView.findViewById(R.id.tv_studentName);
            tvInfo = itemView.findViewById((R.id.tv_info));
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
