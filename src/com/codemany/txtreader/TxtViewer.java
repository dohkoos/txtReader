package com.codemany.txtreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class TxtViewer extends Activity {
    private MainApp app;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        app = (MainApp)getApplication();

        ImageButton btnToc = (ImageButton)findViewById(R.id.btn_toc);
        btnToc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoToc();
            }
        });
        ImageButton btnPrev = (ImageButton)findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPrev();
            }
        });
        ImageButton btnNext = (ImageButton)findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext();
            }
        });
        ImageButton btnZoomIn = (ImageButton)findViewById(R.id.btn_zoomin);
        btnZoomIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIn();
            }
        });
        ImageButton btnZoomOut = (ImageButton)findViewById(R.id.btn_zoomout);
        btnZoomOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOut();
            }
        });

        updateTxtViewer();
    }

    private void updateTxtViewer() {
        setTitle(app.getTitle());
        text = (TextView)findViewById(R.id.text);
        text.setText(readText(app.getFile()));

        // update state of previous and next buttons
        ImageButton btnPrev = (ImageButton)findViewById(R.id.btn_prev);
        ImageButton btnNext = (ImageButton)findViewById(R.id.btn_next);
        if (app.getPosition() <= 0) {
            btnPrev.setEnabled(false);
        } else if (app.getPosition() >= app.getSize() - 1) {
            btnNext.setEnabled(false);
        } else {
            btnPrev.setEnabled(true);
            btnNext.setEnabled(true);
        }
    }

    private void gotoToc() {
        finish();
    }

    private void gotoPrev() {
        if (app.getPosition() > 0) {
            app.setPosition(app.getPosition() - 1);
        }
        updateTxtViewer();
    }

    private void gotoNext() {
        if (app.getPosition() < app.getSize() - 1) {
            app.setPosition(app.getPosition() + 1);
        }
        updateTxtViewer();
    }

    private void zoomIn() {
        float size = text.getTextSize();
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 0.1f * size);
    }

    private void zoomOut() {
        float size = text.getTextSize();
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 0.1f * size);
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
