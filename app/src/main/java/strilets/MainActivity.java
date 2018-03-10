package strilets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listTask;
    Button btnAdd;
    DBManager db;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBManager(this);

        List<Task> tasksList = db.getAllTasks();
        taskAdapter = new TaskAdapter(this, tasksList);
        listTask  = (ListView) findViewById(R.id.listTasks);
        listTask.setAdapter(taskAdapter);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    public void addTask(){
        Intent saveTask = new Intent(this, SaveTaskActivity.class);
        startActivity(saveTask);
    }

}
