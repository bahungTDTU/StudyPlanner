package com.example.studyplanner.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {
        @Index(value = {"email"}, unique = true),
        @Index(value = {"username"}, unique = true)
})
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int userId;

    @NonNull
    public String username;

    @NonNull
    public String email;

    @NonNull
    public String password;

    // --- New Profile Fields ---
    public String fullName;

    public String dateOfBirth;

    public String schoolName;

    public String academicProcess;

    /**
     * Empty constructor required by Room and for Parcelable.
     */
    public User() {}


    // --- Parcelable Implementation ---
    // This part is essential for passing the User object between fragments.

    protected User(Parcel in) {
        userId = in.readInt();
        username = in.readString();
        email = in.readString();
        password = in.readString();
        fullName = in.readString();
        dateOfBirth = in.readString();
        schoolName = in.readString();
        academicProcess = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(dateOfBirth);
        dest.writeString(schoolName);
        dest.writeString(academicProcess);
    }
}
