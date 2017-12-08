package com.example.kubra.settingsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      textView= (TextView) findViewById(R.id.textView);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        loadParametersFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem=item.getItemId();
        if(selectedItem==R.id.settings_item){
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
public void loadParametersFromPreferences(SharedPreferences sharedPreferences){
    String key_text=getString(R.string.key_text);
    setText(sharedPreferences,key_text);
    String key_allcaps=getString(R.string.key_allcaps);
    setAllCaps(sharedPreferences,key_allcaps);
    String key_color=getString(R.string.key_list);
    setColor(sharedPreferences,key_color);
}

    private void setColor(SharedPreferences sharedPreferences,String key) {
        String default_color=getString(R.string.value_red);
        String value_Color=sharedPreferences.getString(key,default_color);
        int colorCode=getColorCode(value_Color);
        textView.setTextColor(colorCode);
    }

    private void setAllCaps(SharedPreferences sharedPreferences,String key) {
        boolean value_allcaps=sharedPreferences.getBoolean(key,false);
        textView.setAllCaps(value_allcaps);
    }

    private void setText(SharedPreferences sharedPreferences,String key) {
        String default_text=getString(R.string.defaultValue_text);
        String text=sharedPreferences.getString(key,default_text);
        textView.setText(text);
    }

    private int getColorCode(String value_color) {
        String red=getString(R.string.value_red);
        String green=getString(R.string.value_green);
        String blue=getString(R.string.value_blue);
        int colorCode = 0;
        if(value_color.equals(red)){
            colorCode=getResources().getColor(R.color.red);
        }
        else  if(value_color.equals(green)){
            colorCode=getResources().getColor(R.color.green);
        }
       else if(value_color.equals(blue)){
            colorCode=getResources().getColor(R.color.blue);
        }
    return colorCode;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String key_text=getString(R.string.key_text);
        String key_allcaps=getString(R.string.key_allcaps);
        String key_color=getString(R.string.key_list);

        if(key.equals(key_text)){setText(sharedPreferences,key);}
       else if(key.equals(key_allcaps)){setAllCaps(sharedPreferences,key);}
       else if(key.equals(key_color)){setColor(sharedPreferences, key);}

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
