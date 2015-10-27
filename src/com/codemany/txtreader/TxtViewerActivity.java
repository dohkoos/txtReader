package com.codemany.txtreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class TxtViewerActivity extends Activity {
    private static final String TAG = "TxtViewerActivity";

    private static final int MIN_ZOOM = -3;
    private static final int MAX_ZOOM = 6;
    private int zoom = 0;

    private MainApp app;

    private TextView textView;
    private ImageButton btnToc;
    private ImageButton btnPrev;
    private ImageButton btnNext;
    private ImageButton btnZoomOut;
    private ImageButton btnZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer);

        app = (MainApp)getApplication();

        btnToc = (ImageButton)findViewById(R.id.btn_toc);
        btnToc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoToc();
            }
        });
        btnPrev = (ImageButton)findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPrev();
            }
        });
        btnNext = (ImageButton)findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext();
            }
        });
        btnZoomIn = (ImageButton)findViewById(R.id.btn_zoomin);
        btnZoomIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIn();
            }
        });
        btnZoomOut = (ImageButton)findViewById(R.id.btn_zoomout);
        btnZoomOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOut();
            }
        });
        textView = (TextView)findViewById(R.id.text);

        updateTxtViewer();
    }

    private void updateTxtViewer() {
        setTitle(app.getTitle());
        textView.setText(readText(app.getFile()));

        updateNavigationButtonState();
    }

    private void updateNavigationButtonState() {
        updateButtonState(btnPrev, btnNext, app.getIndex(), 0, app.getSize() - 1);
    }

    private void updateButtonState(ImageButton btnLeft, ImageButton btnRight,
            int current_value, int min_value, int max_value) {
        if (current_value <= min_value) {
            btnLeft.setEnabled(false);
        } else if (current_value >= max_value) {
            btnRight.setEnabled(false);
        } else {
            btnLeft.setEnabled(true);
            btnRight.setEnabled(true);
        }
    }

    private void gotoToc() {
        finish();
    }

    private void gotoPrev() {
        if (app.getIndex() > 0) {
            app.setIndex(app.getIndex() - 1);
        }
        updateTxtViewer();
    }

    private void gotoNext() {
        if (app.getIndex() < app.getSize() - 1) {
            app.setIndex(app.getIndex() + 1);
        }
        updateTxtViewer();
    }

    private void zoomIn() {
        if (zoom >= MAX_ZOOM) {
            return;
        }
        zoom++;
        updateZoomButtonState();

        float size = textView.getTextSize();

        Log.d(TAG, "Font size before zoom in: " + size);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 0.1f * size);
        Log.d(TAG, "Font size after zoom in: " + textView.getTextSize());
    }

    private void zoomOut() {
        if (zoom <= MIN_ZOOM) {
            return;
        }
        zoom--;
        updateZoomButtonState();

        float size = textView.getTextSize();

        Log.d(TAG, "Font size before zoom out: " + size);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 0.1f * size);
        Log.d(TAG, "Font size after zoom out: " + textView.getTextSize());
    }

    private void updateZoomButtonState() {
        updateButtonState(btnZoomOut, btnZoomIn, zoom, MIN_ZOOM, MAX_ZOOM);
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
