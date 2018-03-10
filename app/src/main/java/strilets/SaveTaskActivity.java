package strilets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SaveTaskActivity extends AppCompatActivity {

    final String TASK_SAVE = "Task saved.";
    Button btnSave;
    EditText editDescription, editStatus;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_task);

        db = new DBManager(this);
        editDescription = (EditText) findViewById(R.id.editDescription);
        editStatus = (EditText) findViewById(R.id.editStatus);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    public void saveTask(){
        Task task = new Task();
        task.setDescription(editDescription.getText().toString());
        task.setStatus(editStatus.getText().toString());

        db.addTask(task);
        Toast.makeText(this, TASK_SAVE, Toast.LENGTH_LONG).show();
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
