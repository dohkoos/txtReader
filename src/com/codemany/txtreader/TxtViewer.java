package com.codemany.txtreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TxtViewer extends Activity {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        updateTxtViewer();
    }

    private void updateTxtViewer() {
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));
        text = (TextView)findViewById(R.id.text);
        text.setText(readText(intent.getStringExtra("file")));
    }

    private String readText(String file) {
        try {
            InputStreamReader isr =
                new InputStreamReader(getAssets().open(file), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            isr.close();
            return sb.toString();
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}
