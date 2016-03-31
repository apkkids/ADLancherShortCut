package com.dumaisoft.wxb.adlanchershortcut;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private final static String EXTRA_KEY = "com.dumaisoft.wxb.adlanchershortcut";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Resolve the intent
        final Intent intent = getIntent();
        final String action = intent.getAction();
        //if the intent is a request to create a shortcut, we'll do that and exit
        if(Intent.ACTION_CREATE_SHORTCUT.equals(action)){
            setupShortcut();
            finish();
            return;
        }

        // If we weren't launched with a CREATE_SHORTCUT intent, simply put up an informative
        // display.
        setContentView(R.layout.activity_main);
        TextView intentInfo = (TextView) this.findViewById(R.id.txt_shortcut_intent);
        String info = intent.toString();
        String extra = intent.getStringExtra(EXTRA_KEY);
        if (extra != null) {
            info = info + "\n " + extra;
        }else{
            info = info + "\n" + "this is not from shortcut";
        }
        intentInfo.setText(info);
    }

    private void setupShortcut() {
        //first ,set up the shortcut intent
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(this,this.getClass().getName());
        shortcutIntent.putExtra(EXTRA_KEY,"this is from my shortcut");

        // then, set up the container intent
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"WXBSHORTCUT");
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(this,R.drawable.app_sample_code);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,iconResource);

        // Now, return the result to the launcher

        setResult(RESULT_OK, intent);
    }
}
