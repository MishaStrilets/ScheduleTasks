package strilets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<Task> tasksList;
    DBManager db;
    final String TASK_DELETE = "Task deleted.";
    final String TASK_EDIT = "Task edited.";

    public TaskAdapter(Context context, List<Task> tasksList) {
        this.context = context;
        this.tasksList = tasksList;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        db = new DBManager(context);

        final Task currentTask = (Task) getItem(position);
        viewHolder.textId.setText(String.valueOf(position + 1));
        viewHolder.editDescription.setText(currentTask.getDescription());
        viewHolder.editStatus.setText(currentTask.getStatus());
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task editTask = new Task();
                editTask.setId(currentTask.getId());
                editTask.setDescription(viewHolder.editDescription.getText().toString());
                editTask.setStatus(viewHolder.editStatus.getText().toString());

                db.updateTask(editTask);
                Toast.makeText(context, TASK_EDIT, Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteTask(currentTask);
                Toast.makeText(context, TASK_DELETE, Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView textId;
        EditText editDescription;
        EditText editStatus;
        Button btnEdit;
        Button btnDelete;

        public ViewHolder(View view) {
            textId = (TextView)view.findViewById(R.id.textId);
            editDescription = (EditText)view.findViewById(R.id.editDescription);
            editStatus = (EditText)view.findViewById(R.id.editStatus);
            btnEdit = (Button)view.findViewById(R.id.btnEdit);
            btnDelete = (Button)view.findViewById(R.id.btnDelete);
        }
    }
}
