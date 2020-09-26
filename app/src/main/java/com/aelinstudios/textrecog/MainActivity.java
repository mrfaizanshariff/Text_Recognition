package com.aelinstudios.textrecog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity {
    private Button camera_button;
    // request code for the camera permission
    private final static int REQUEST_CAMERA_CAPTURE=123;
    // creating instance of FirebaseVisionImage
    private FirebaseVisionImage image;
    //create instance of FirebaseVisionTextRecognizer
    private FirebaseVisionTextRecognizer recognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the firebase app
        FirebaseApp.initializeApp(this);
        camera_button=findViewById(R.id.button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firing up the camera
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!= null){
                    startActivityForResult(intent,REQUEST_CAMERA_CAPTURE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CAMERA_CAPTURE&&resultCode==RESULT_OK){
            //getting the data aS BUNDLE then into bitmap

            Bundle extras = data.getExtras();
            Bitmap bitmap= (Bitmap) extras.get("data");

            // call user defined method
            recognizeText(bitmap);
        }
    }

    private void recognizeText(Bitmap bitmap) {
        try {
            image= FirebaseVisionImage.fromBitmap(bitmap);
            recognizer= FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        recognizer.processImage(image)
                  .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                      @Override
                      public void onSuccess(FirebaseVisionText firebaseVisionText) {
                          // create a result String
                          String result_text = firebaseVisionText.getText();

                          if(result_text.isEmpty()){
                              Toast.makeText(MainActivity.this,"No text detected",Toast.LENGTH_SHORT).show();
                          } else {
                              Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                              intent.putExtra(TextRecognition.Result_text,result_text);
                              startActivity(intent);
                          }
                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          // to show that the process has failed
                          Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                      }
                  });
    }
}