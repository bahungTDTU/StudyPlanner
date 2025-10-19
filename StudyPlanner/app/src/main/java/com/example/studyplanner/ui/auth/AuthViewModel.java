package com.example.studyplanner.ui.auth;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.studyplanner.data.model.User;
import com.example.studyplanner.repository.StudyPlannerRepository;

public class AuthViewModel extends AndroidViewModel {

    private final StudyPlannerRepository repository;
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new StudyPlannerRepository(application);
    }

    // --- LiveData to be observed by UI ---

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    // --- User Actions ---

    public void register(String username, String email, String password) {
        User newUser = new User();
        newUser.username = username;
        newUser.email = email;
        newUser.password = password;
        repository.registerUser(newUser, registrationSuccess);
    }

    public LiveData<User> login(String email, String password) {
        return repository.loginUser(email, password);
    }
}