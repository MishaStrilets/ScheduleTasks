package strilets.scheduletasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSave, btnDelete;
    EditText editDescrpt, editStatus;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        editDescrpt = (EditText) findViewById(R.id.editDescrpt);
        editStatus = (EditText) findViewById(R.id.editStatus);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String description = editDescrpt.getText().toString();
        String status = editStatus.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId())
        {
            case R.id.btnSave:
                contentValues.put(DBHelper.KEY_DESCRIPTION, description);
                contentValues.put(DBHelper.KEY_STATUS, status);
                database.insert(DBHelper.TABLE_TASK, null, contentValues);
                break;

            case R.id.btnDelete:
                //TODO delete task
                break;
        }
        dbHelper.close();
    }
}

