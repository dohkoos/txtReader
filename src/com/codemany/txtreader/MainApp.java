package com.codemany.txtreader;

import java.util.List;
import java.util.Map;

import android.app.Application;

public class MainApp extends Application {
    public static final String TITLE = "title";
    public static final String FILE = "file";

    private List<Map<String, String>> toc;
    private int index;

    public void setToc(List<Map<String, String>> toc) {
        this.toc = toc;
    }

    public List<Map<String, String>> getToc() {
        return toc;
    }

    public int getSize() {
        return toc.size();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return toc.get(index).get(TITLE);
    }

    public String getFile() {
        return toc.get(index).get(FILE);
    }
}
