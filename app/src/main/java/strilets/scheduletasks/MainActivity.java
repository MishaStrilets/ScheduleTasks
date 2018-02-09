package strilets.scheduletasks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCreate;
    TextView text;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        text = (TextView) findViewById(R.id.textData);
    }

    public void onStart(){
        super.onStart();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        text.setText("");
        Cursor cursor = database.query(DBHelper.TABLE_TASK, null, null, null,null,null,null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            int statusIndex = cursor.getColumnIndex(DBHelper.KEY_STATUS);

            do {
                String descriptionStr = cursor.getString(descriptionIndex);
                String statusStr = cursor.getString(statusIndex);

                text.append("Description: " + descriptionStr + "." + '\n' +"Status: " + statusStr + "." + '\n');
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }
}
