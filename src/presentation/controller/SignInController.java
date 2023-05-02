package presentation.controller;

import business.BusinessLogicUser;
import persistance.exceptions.EmailException;
import persistance.exceptions.PasswordException;
import persistance.exceptions.PasswordMismatchException;
import persistance.exceptions.UsernameException;
import presentation.view.SignInView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInController implements ActionListener {

    BusinessLogicUser businessLogicUser;

    SignInView signInView;

    public SignInController(SignInView signInView, BusinessLogicUser businessLogicUser){
        this.signInView = signInView;
        this.businessLogicUser = businessLogicUser;
    }

    public void signInButtonPressed(String nom_correu, char[] password) {
        //businessLogicUser.loginUser(nom_correu, password.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(signInView.LOGIN_COMMAND)) {

            //businessLogicUser.loginUser(signInView.getLoginUserMail(), signInView.getLoginUserPassword());
            System.out.println("works");
        }
    }

}
