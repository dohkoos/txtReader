package com.codemany.txtreader;

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

    private TxtBook txtBook;

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

        txtBook = ((MainApp)getApplication()).getTxtBook();

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
        setTitle(txtBook.getTitle());
        textView.setText(txtBook.getText());

        updateNavigationButtonState();
    }

    private void updateNavigationButtonState() {
        updateButtonState(btnPrev, btnNext, txtBook.getIndex(), 0, txtBook.getSize() - 1);
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
        if (txtBook.getIndex() > 0) {
            txtBook.setIndex(txtBook.getIndex() - 1);
        }
        updateTxtViewer();
    }

    private void gotoNext() {
        if (txtBook.getIndex() < txtBook.getSize() - 1) {
            txtBook.setIndex(txtBook.getIndex() + 1);
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
}
