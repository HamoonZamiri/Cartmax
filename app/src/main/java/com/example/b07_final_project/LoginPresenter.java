package com.example.b07_final_project;

public class LoginPresenter implements Contract.Presenter {
    private Contract.Model model;
    private LoginActivity view;

    public LoginPresenter(Contract.Model model, LoginActivity view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void login() {
        String email = view.getEmail();
        String password = view.getPassword();
        String emailError = model.emailError(email);
        String pwError = model.pwError(password);
        if(!emailError.isEmpty()) {
            view.handleError(emailError, true);
        }
        else if(!pwError.isEmpty()) {
            view.handleError(pwError, false);
        }
        else {
            model.custLogin(view, email, password);
            model.storeLogin(view, email, password);
        }
    }

}
