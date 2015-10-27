package com.codemany.txtreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class TxtBook {
    public static final String TITLE = "title";
    public static final String FILE = "file";

    private static TxtBook instance = new TxtBook();

    private List<Map<String, String>> toc;
    private int index;

    private Context context;

    protected TxtBook() {
    }

    public static TxtBook getInstance(Context context) {
        instance.context = context;
        return instance;
    }

    public List<Map<String, String>> getToc() {
        if (toc == null) {
            toc = new ArrayList<Map<String, String>>();

            try {
                InputStreamReader isr =
                    new InputStreamReader(context.getAssets().open("toc.txt"), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] items = line.split(",");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(TITLE, items[0]);
                    map.put(FILE, items[1]);
                    toc.add(map);
                }
                br.close();
                isr.close();
            } catch (IOException e) {
                // Should never happen!
                throw new RuntimeException(e);
            }
        }

        return toc;
    }

    public int getSize() {
        return getToc().size();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return getToc().get(index).get(TITLE);
    }

    public String getText() {
        try {
            String file = getToc().get(index).get(FILE);
            InputStreamReader isr =
                new InputStreamReader(context.getAssets().open(file), "utf-8");
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
