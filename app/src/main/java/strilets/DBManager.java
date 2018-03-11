package strilets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "schedule_task";
    public static final String TABLE_TASK = "task";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_STATUS = "status";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_TASK + "(" + COLUMN_ID
                + " integer primary key," + COLUMN_DESCRIPTION + " text," +  COLUMN_STATUS + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TASK);
        onCreate(db);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(COLUMN_DESCRIPTION, task.getDescription());
        contentValue.put(COLUMN_STATUS, task.getStatus());
        db.insert(TABLE_TASK, null, contentValue);
        db.close();
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        List<Task> tasksList = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM " + TABLE_TASK;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setDescription(cursor.getString(1));
                task.setStatus(cursor.getString(2));
                tasksList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tasksList;
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_STATUS, task.getStatus());
        db.update(TABLE_TASK, values, COLUMN_ID + " = ?", new String[] { String.valueOf(task.getId()) });
        db.close();
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, COLUMN_ID + " = ?", new String[] { String.valueOf(task.getId())});
        db.close();
    }
}
