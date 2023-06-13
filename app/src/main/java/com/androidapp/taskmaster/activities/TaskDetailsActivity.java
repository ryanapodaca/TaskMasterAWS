package com.androidapp.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.LanguageType;
import com.androidapp.taskmaster.MainActivity;
import com.androidapp.taskmaster.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TaskDetailsActivity extends AppCompatActivity {
    public static final String TAG = "taskDetailsActivity";
    public static final String TASK_Title_TAG = "taskName";
    public static final String TASK_Body_TAG = "taskBody";
    SharedPreferences preferences;
    private String taskId;

    private final MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        setUpSpeakButton();
        setUpSpanishButton();



        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra(TaskDetailsActivity.TASK_Title_TAG);

        TextView taskDetailsTextView = findViewById(R.id.taskDetailsActivityInputName);
        taskDetailsTextView.setText(taskTitle);

        String taskBody = intent.getStringExtra(TaskDetailsActivity.TASK_Body_TAG);

        TextView taskDetailsBodyView = findViewById(R.id.taskDetailsTaskBodyTextView);
        taskDetailsBodyView.setText(taskBody);



    }

    protected void onResume () {
        super.onResume();

//        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
//        String task = preferences.getString(TaskDetailsActivity.TASK_Title_TAG, "No task");
//        ((TextView) findViewById(R.id.taskDetailsActivityInputName)).setText(task);
    }

    private void displayTaskImage() {
        //does task have good s3 key?
        //if, no display.

    }

    public void setUpSpeakButton() {
        Button speakButton = findViewById(R.id.taskDetailsActivityActivitySpeakButton);
        speakButton.setOnClickListener(v -> {
            String taskDetails; // set this from a TextView or EditTest or whatever is holding your product name
            taskDetails = ((TextView)findViewById(R.id.taskDetailsTaskBodyTextView)).getText().toString();

            Amplify.Predictions.convertTextToSpeech(
                    taskDetails,
                    success -> playAudio(success.getAudioData(), taskDetails),
                    failure -> Log.e(TAG, "Audio conversion of product, " + taskDetails + ", failed", failure)
            );


        });
    }

    public void setUpSpanishButton() {
        Button spanishButton = findViewById(R.id.taskDetailsActivitySpanishButton);
        spanishButton.setOnClickListener(v -> {
            String taskDetailsString;
            taskDetailsString = ((TextView)findViewById(R.id.taskDetailsTaskBodyTextView)).getText().toString();
            TextView taskDetails = ((TextView)findViewById(R.id.taskDetailsTaskBodyTextView));


            Amplify.Predictions.translateText(
                    taskDetailsString,
                    LanguageType.ENGLISH,
                    LanguageType.SPANISH,
                    result -> taskDetails.setText(result.getTranslatedText()),
//                            Log.i("MyAmplifyApp", result.getTranslatedText()),
                    error -> Log.e("MyAmplifyApp", "Translation failed", error)
            );

        });

    }


    private void playAudio(InputStream data, String textToSpeak) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try(OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;

            while((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            Log.i(TAG, "audio file finished reading");

            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();

            Log.i(TAG, "Audio played");
            Log.i(TAG, "text to speak: " + textToSpeak);
        } catch(IOException error) {
            Log.e(TAG, "Error writing audio file");
        }
    }


}
