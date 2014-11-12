package com.codemany.txtreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final List<Map<String, String>> toc = getTableOfContents();
        ListView lv = (ListView)findViewById(R.id.toc);
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                toc,
                R.layout.list_item,
                new String[] {"title"},
                new int[] {R.id.title});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TxtViewer.class);
                intent.putExtra("title", toc.get(position).get("title"));
                intent.putExtra("file", toc.get(position).get("file"));
                startActivity(intent);
            }
        });
    }

    private List<Map<String, String>> getTableOfContents() {
        try {
            InputStreamReader isr =
                new InputStreamReader(getAssets().open("toc.txt"), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] items = line.split(",");
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", items[0]);
                map.put("file", items[1]);
                list.add(map);
            }
            br.close();
            isr.close();
            return list;
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
