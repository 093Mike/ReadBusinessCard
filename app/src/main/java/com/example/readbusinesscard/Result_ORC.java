package com.example.readbusinesscard;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class Result_ORC extends AppCompatActivity {
    String imageText = "";
    TextView t_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_text);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        t_results = findViewById(R.id.result);

        final TextRecognizer textR = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textR.isOperational()) {
            Log.w("TextRecognizer", "DETECTOR NO DISPONIBLE");
        } else {
            Frame imageFrame = new Frame.Builder()

                    .setBitmap(MainActivity.getInstance().bitmap)
                    .build();
            final SparseArray<TextBlock> textBlocks = textR.detect(imageFrame);

            if (textBlocks.size() != 0) {
                t_results.post(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder stringB = new StringBuilder();
                        for (int i = 0; i < textBlocks.size(); i++) {
                            TextBlock item = textBlocks.valueAt(i);
                            stringB.append(item.getValue());
                            stringB.append("\n");
                        }
                        t_results.setText(stringB.toString());
                    }
                });
            }
        }
    }
}
