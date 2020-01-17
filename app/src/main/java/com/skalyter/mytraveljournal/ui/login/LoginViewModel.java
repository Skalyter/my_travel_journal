package com.skalyter.mytraveljournal.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.skalyter.mytraveljournal.data.LoginRepository;
import com.skalyter.mytraveljournal.data.Result;
import com.skalyter.mytraveljournal.data.model.LoggedInUser;
import com.skalyter.mytraveljournal.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private String email, firstName, lastName, password;

    String getEmail(){
        return this.email;
    }

    String getFirstName(){
        return this.firstName;
    }

    String getLastName(){
        return this.lastName;
    }

    String getPassword(){
        return this.password;
    }
    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String lastName, String firstName, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, firstName, lastName, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            this.email = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            loginResult.setValue(new LoginResult(new LoggedInUserView(username, firstName,
                    lastName, password)));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String firstName, String lastName, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username,
                    null, null, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password,
                    null, null));
        } else if (!isNameValid(firstName)) {
            loginFormState.setValue(new LoginFormState(null, null,
                    R.string.invalid_first_name, null));
        } else if (!isNameValid(lastName)) {
            loginFormState.setValue(new LoginFormState(null, null,
                    null, R.string.invalid_last_name));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isNameValid(String name) {
        return name != null && name.length() > 2;
    }
}
