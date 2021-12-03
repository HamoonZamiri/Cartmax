package com.example.b07_final_project;

import org.junit.Test;

import static org.junit.Assert.*;

import android.view.View;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    private LoginPresenter presenter;

    @Mock
    private Contract.Model model;

    @Mock
    private LoginActivity view;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenter(model,view);
    }

    @Test
    public void test1() {
        String email = "";
        String password = "testtest";

        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);
        when(model.emailError(email)).thenReturn("Email is required");
        when(model.pwError(password)).thenReturn("");
        Mockito.doNothing().when(model).custLogin(view,presenter,email,password);
        Mockito.doNothing().when(model).storeLogin(view,presenter,email,password);
        presenter.login();

        Mockito.verify(view).handleError("Email is required", true);
    }

    @Test
    public void test2() {
        String email = "test@gmail.com";
        String password = "";

        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);
        when(model.emailError(email)).thenReturn("");
        when(model.pwError(password)).thenReturn("Password is required");
        Mockito.doNothing().when(model).custLogin(view,presenter,email,password);
        Mockito.doNothing().when(model).storeLogin(view,presenter,email,password);
        presenter.login();

        Mockito.verify(view).handleError("Password is required", false);
    }

    @Test
    public void test3() {
        String email = "test@gmail.com";
        String password = "testtest";

        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);
        when(model.emailError(email)).thenReturn("");
        when(model.pwError(password)).thenReturn("");
        Mockito.doNothing().when(model).custLogin(view,presenter,email,password);
        Mockito.doNothing().when(model).storeLogin(view,presenter,email,password);
        presenter.login();
        presenter.determiner(false,false);

        Mockito.verify(view).failure();
    }

    @Test
    public void test4() {
        String email = "cust@mail.com";
        String password = "testp5";

        when(view.getEmail()).thenReturn(email);
        when(view.getPassword()).thenReturn(password);
        when(model.emailError(email)).thenReturn("");
        when(model.pwError(password)).thenReturn("");
        Mockito.doNothing().when(model).custLogin(view,presenter,email,password);
        Mockito.doNothing().when(model).storeLogin(view,presenter,email,password);
        presenter.login();
        presenter.determiner(true,true);

        Mockito.verify(view).success(true);
    }
}