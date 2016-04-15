package com.kmshack.library.exceptiontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by kmshack on 16. 4. 14..
 */
public class LogActivity extends AppCompatActivity {

    private TextView mTxtDesc;
    private TextView mTxtLog;

    private static final String KEY_DESC = "key_desc";
    private static final String KEY_MESSAGE = "key_log";

    public static Intent launch(Context context, String desc, String log) {
        Intent intent = new Intent(context, LogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_MESSAGE, log);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mTxtDesc = (TextView) findViewById(R.id.txt_desc);
        mTxtLog = (TextView) findViewById(R.id.txt_log);

        mTxtDesc.setText(getIntent().getStringExtra(KEY_DESC));
        mTxtLog.setText(getIntent().getStringExtra(KEY_MESSAGE));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent intent=new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            intent.putExtra(Intent.EXTRA_SUBJECT, mTxtDesc.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, mTxtLog.getText().toString());

            startActivity(Intent.createChooser(intent, "로그 내보내기"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
