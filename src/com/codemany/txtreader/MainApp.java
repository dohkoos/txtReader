package com.codemany.txtreader;

import android.app.Application;

public class MainApp extends Application {

    public TxtBook getTxtBook() {
        return TxtBook.getInstance(this);
    }
}
