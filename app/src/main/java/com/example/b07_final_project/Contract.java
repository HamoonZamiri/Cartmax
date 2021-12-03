package com.example.b07_final_project;

public interface Contract {
    public interface Model {
        public abstract String emailError(String email);
        public abstract String pwError(String password);
        public abstract void custLogin(LoginActivity view, LoginPresenter presenter, String email, String password);
        public abstract void storeLogin(LoginActivity view, LoginPresenter presenter, String email, String password);
    }

    public interface View {
        public abstract String getEmail();
        public abstract String getPassword();
        public abstract void handleError(String error, boolean emailError);
        public abstract void success(boolean isCustomer);
        public abstract void failure();
    }

    public interface Presenter {
        public abstract void login();
        public abstract void determiner(boolean result, boolean isCust);
    }
}
