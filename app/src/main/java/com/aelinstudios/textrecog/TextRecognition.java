package com.aelinstudios.textrecog;
import android.app.Application;

import com.google.firebase.FirebaseApp;

public class TextRecognition extends Application{

    public static final String Result_text = "RESULT_TEXT";

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the firebase app here
        FirebaseApp.initializeApp(this);

    }
}
