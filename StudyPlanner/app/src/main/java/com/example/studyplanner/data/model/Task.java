package com.example.studyplanner.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.studyplanner.data.local.Converters;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "tasks")
@TypeConverters(Converters.class)
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int taskId;

    public int subjectId;
    public String title;
    public String description;
    public String taskType;
    public Date startTime;
    public Date deadline;
    public String status; // e.g., "Not Started", "In Progress", "Completed"

    public Task() {}

    // --- Parcelable Implementation ---

    protected Task(Parcel in) {
        taskId = in.readInt();
        subjectId = in.readInt();
        title = in.readString();
        description = in.readString();
        taskType = in.readString();
        long tmpStart = in.readLong();
        startTime = tmpStart == -1 ? null : new Date(tmpStart);
        long tmpDeadline = in.readLong();
        deadline = tmpDeadline == -1 ? null : new Date(tmpDeadline);
        status = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(taskId);
        dest.writeInt(subjectId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(taskType);
        dest.writeLong(startTime != null ? startTime.getTime() : -1);
        dest.writeLong(deadline != null ? deadline.getTime() : -1);
        dest.writeString(status);
    }

    // --- equals() and hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId &&
                subjectId == task.subjectId &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(taskType, task.taskType) &&
                Objects.equals(startTime, task.startTime) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, subjectId, title, description, taskType, startTime, deadline, status);
    }
}
