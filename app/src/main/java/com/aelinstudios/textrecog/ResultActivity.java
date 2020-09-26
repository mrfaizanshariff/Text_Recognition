package com.aelinstudios.textrecog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private Button backButton;
    private TextView textView;
    private String result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    backButton=findViewById(R.id.back_button);
    textView=findViewById(R.id.result_textview);
    //populate the result_text with result frm the TextRecognition.java class
        result_text=getIntent().getStringExtra(TextRecognition.Result_text);
        textView.setText(result_text);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}