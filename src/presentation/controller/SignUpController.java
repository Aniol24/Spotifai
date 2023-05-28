package presentation.controller;

import business.BusinessLogicUser;
import persistance.exceptions.*;
import presentation.view.SignUpView;
import presentation.view.ViewsController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Sign up controller class
 */
public class SignUpController implements ActionListener {
    private final SignUpView signUpView;
    private final BusinessLogicUser businessLogicUser;
    private final ViewsController viewsController;

    /**
     * Sign up controller
     * @param signUpView sign up view
     * @param businessLogicUser business logic user
     * @param viewsController views controller
     */
    public SignUpController(SignUpView signUpView, BusinessLogicUser businessLogicUser, ViewsController viewsController) {
        this.signUpView = signUpView;
        this.businessLogicUser = businessLogicUser;
        this.viewsController = viewsController;
    }

    /**
     * Action performed
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SignUpView.REGISTER_COMMAND)) {
            try {
                if (businessLogicUser.registerUser(signUpView.getEmail(), signUpView.getUsername(), signUpView.getFirstPassword(), signUpView.getSecondPassword())) {
                    signUpView.userRegisteredSuccessfully();
                    viewsController.createViewReproductor();
                    return;
                }
                businessLogicUser.cleanUserInfoFile();
            } catch (PasswordException ex) {
                signUpView.wrongPasswordError();
            } catch (PasswordMismatchException ex) {
                signUpView.passwordsMismatchError();
            } catch (EmailException ex) {
                signUpView.wrongEmailError();
            } catch (UsernameException ex) {
                signUpView.wrongUserError();
            } catch (UserAlreadyExistsException ex) {
                signUpView.userRegistrationFailed();
            }
        }
        if (e.getActionCommand().equals(SignUpView.BACK_FROM_SIGNUP_COMMAND)){
            viewsController.setWelcomeView();
        }
    }
}
