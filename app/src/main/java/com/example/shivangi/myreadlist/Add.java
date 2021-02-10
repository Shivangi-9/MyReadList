package com.example.shivangi.myreadlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Add extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.shivangi.myreadlist.EXTRA_ID";
    public static final String EXTRA_BOOK = "com.example.shivangi.myreadlist.EXTRA_BOOK";
    public static final String EXTRA_AUTHOR = "com.example.shivangi.myreadlist.EXTRA_AUTHOR";
    public static final String EXTRA_CATEGORY = "com.example.shivangi.myreadlist.EXTRA_CATEGORY";
    public static final String EXTRA_STATUS = "com.example.shivangi.myreadlist.EXTRA_STATUS";

    private EditText bookName;
    private EditText author;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bookName = findViewById(R.id.bookEditText);
        author = findViewById(R.id.authorEditText);
        spinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radioGroup);

        Button addBtn = findViewById(R.id.btnSecondAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            addBtn.setText("UPDATE");
            bookName.setText(intent.getStringExtra(EXTRA_BOOK));
            author.setText(intent.getStringExtra(EXTRA_AUTHOR));
            String spin = intent.getStringExtra(EXTRA_CATEGORY);

            int count = spinner.getCount();
            for(int i = 0; i < count; i++){
                String string = spinner.toString();
                if(string == spin){
                    spinner.setSelection(i);
                    return;
                }
            }

            String value = intent.getStringExtra(EXTRA_STATUS);

            int rad = radioGroup.getChildCount();
            for(int i = 0; i < rad; i++){
                radioButton = (RadioButton) radioGroup.getChildAt(i);
                String radioBtn = radioButton.toString();
                if(radioBtn == value){
                    radioGroup.check(i);
                }
            }
        }
        else {
            addBtn.setText("ADD");
        }
    }

    public void add(){
        String bkName = bookName.getText().toString();
        String bkAuthor = author.getText().toString();
        String category = spinner.getSelectedItem().toString();

        int radBtnID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radBtnID);
        String status = radioButton.getText().toString();

        if(bkName.isEmpty() || bkAuthor.isEmpty() || category.isEmpty() || status.isEmpty()){
            Toast.makeText(this,"Please insert all fields!",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_BOOK,bkName);
        data.putExtra(EXTRA_AUTHOR,bkAuthor);
        data.putExtra(EXTRA_CATEGORY,category);
        data.putExtra(EXTRA_STATUS,status);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id != -1) {
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }
}