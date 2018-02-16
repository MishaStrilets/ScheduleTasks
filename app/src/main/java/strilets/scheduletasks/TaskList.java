package strilets.scheduletasks;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskList extends BaseAdapter {
    private Activity context;
    ArrayList<Task> tasks;
    private PopupWindow pWind;
    DBManager db;

    public TaskList(Activity context, ArrayList<Task> tasks, DBManager db) {
        this.context = context;
        this.tasks = tasks;
        this.db = db;
    }

    public static class ViewHolder {
        TextView textId, textDescription, textStatus;
        Button btnEdit, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
            LayoutInflater inflater = context.getLayoutInflater();
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                row = inflater.inflate(R.layout.item, null, true);
                vh.textId = (TextView) row.findViewById(R.id.textId);
                vh.textDescription = (TextView) row.findViewById(R.id.textDescription);
                vh.textStatus = (TextView) row.findViewById(R.id.textStatus);
                vh.btnEdit = (Button) row.findViewById(R.id.btnEdit);
                vh.btnDelete = (Button) row.findViewById(R.id.btnDelete);
                row.setTag(vh);
            } else {
                    vh = (ViewHolder) convertView.getTag();

            }

        vh.textId.setText("" + (position + 1));
        vh.textDescription.setText(tasks.get(position).getDescription());
        vh.textStatus.setText("" + tasks.get(position).getStatus());
        final int positionPopup = position;
        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editPopup(positionPopup);
                }
            });
            vh.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteTask(tasks.get(positionPopup));
                    tasks = (ArrayList) db.getAllTasks();
                    notifyDataSetChanged();
                }
            });
        return  row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {
        return tasks.size();
    }

  public void editPopup(final int positionPopup)
  {
      LayoutInflater inflater = context.getLayoutInflater();
      View layout = inflater.inflate(R.layout.edit,
              (ViewGroup) context.findViewById(R.id.popup_element));
      pWind = new PopupWindow(layout, 500, 200, true);
      pWind.showAtLocation(layout, Gravity.CENTER, 0, 0);
      final EditText descriptionEdit = (EditText) layout.findViewById(R.id.editDescription);
      final EditText statusEdit = (EditText) layout.findViewById(R.id.editStatus);
      descriptionEdit.setText(tasks.get(positionPopup).getDescription());
      statusEdit.setText("" + tasks.get(positionPopup).getStatus());
      Button save = (Button) layout.findViewById(R.id.btnSave);
      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String description = descriptionEdit.getText().toString();
              String status = statusEdit.getText().toString();
              Task task = tasks.get(positionPopup);
              task.setDescription(description);
              task.setStaus(status);
              db.updateTask(task);
              tasks = (ArrayList) db.getAllTasks();
              notifyDataSetChanged();
              pWind.dismiss();
          }
      });
  }
}
