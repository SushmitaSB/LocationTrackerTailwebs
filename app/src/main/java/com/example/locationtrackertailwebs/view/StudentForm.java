package com.example.locationtrackertailwebs.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.RealmManager;
import com.example.locationtrackertailwebs.controler.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class StudentForm extends AppCompatActivity {
    @BindView(R.id.input_name)
    EditText nameEt;
    @BindView(R.id.input_subject)
    EditText subEt;
    @BindView(R.id.input_marks)
    EditText marksEt;
    @BindView(R.id.btn_submit)
    Button button;
    private String name, sub, marks;
    private RealmManager realmManager;
    private Validation validation;
    private Realm realm;
    private boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        //initializing variables
        initializedVariables();

        //submit button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEt.getText().toString();
                sub = subEt.getText().toString();
                marks = marksEt.getText().toString();
                try {
                   status = validation.setFormValidation(realmManager, name, sub, marks, nameEt, subEt, marksEt);
                   if (status){
                        finish();
                   }
                }catch (Exception ex){
                    Toast.makeText(StudentForm.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initializedVariables() {
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this, realm);
        validation = new Validation(StudentForm.this);
    }
}