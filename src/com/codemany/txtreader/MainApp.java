package com.codemany.txtreader;

import java.util.List;
import java.util.Map;

import android.app.Application;

public class MainApp extends Application {
    public static final String TITLE = "title";
    public static final String FILE = "file";

    private List<Map<String, String>> toc;
    private int position;

    public void setToc(List<Map<String, String>> toc) {
        this.toc = toc;
    }

    public List<Map<String, String>> getToc() {
        return toc;
    }

    public int getSize() {
        return toc.size();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return toc.get(position).get(TITLE);
    }

    public String getFile() {
        return toc.get(position).get(FILE);
    }
}
