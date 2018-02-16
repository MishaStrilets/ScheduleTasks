package strilets.scheduletasks;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> tasks;
    DBManager db;
    Button btnCreate;
    PopupWindow pWind;
    Activity activity;
    ListView listView;
    TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        db = new DBManager(this);

        listView = (ListView) findViewById(android.R.id.list);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPopUp();
            }
        });

        tasks = (ArrayList) db.getAllTasks();

        TaskList taskList = new TaskList(this, tasks, db);
        listView.setAdapter(taskList);
    }

    public void addPopUp() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.edit,
                (ViewGroup) activity.findViewById(R.id.popup_element));
        pWind = new PopupWindow(layout, 500, 200, true);
        pWind.showAtLocation(layout, Gravity.CENTER, 0, 0);
        final EditText descriptionEdit = (EditText) layout.findViewById(R.id.editDescription);
        final EditText statusEdit = (EditText) layout.findViewById(R.id.editStatus);

        Button save = (Button) layout.findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = descriptionEdit.getText().toString();
                String status = statusEdit.getText().toString();
                Task task = new Task(description, status);
                db.addTask(task);
                if(taskList==null)
                {
                    taskList = new TaskList(activity, tasks, db);
                    listView.setAdapter(taskList);
                }
                taskList.tasks = (ArrayList) db.getAllTasks();
                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                pWind.dismiss();
            }
        });
    }
}
