package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Login extends Controller implements Initializable {
    public JFXPasswordField password, password1;
    public JFXTextField userName,userName1;
    public JFXButton loginButton, signUpButton;
    public Pane signInPane, loginPane;


    public void onLogin(ActionEvent actionEvent) {
        System.out.println("Meo");
        if(userName.getText().equals("")){
            userName.setStyle("-fx-prompt-text-fill: darkred");
            return;
        }
        client.sendMessage("login/"+userName.getText()+"/"+password.getText());
    }

    public void onSignUp(ActionEvent actionEvent) {
        if(userName1.getText().equals("")){
            userName1.setStyle("-fx-prompt-text-fill: darkred");
            return;
        }
        client.sendMessage("signUp/"+userName1.getText()+"/"+password1.getText());
    }

    public void goToLogIn(MouseEvent mouseEvent) {
        translateTransition(false);
    }

    public void goToSignUp(MouseEvent mouseEvent) {
        translateTransition(true);
    }
    private void translateTransition(boolean flag){
        signInPane.setVisible(true);
        double len = signInPane.getWidth();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1));
        translateTransition.setNode(loginPane);
        translateTransition.setFromX(flag ? 0:-len);
        translateTransition.setToX(flag ? -len: 0);
        translateTransition.play();
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1));
        translateTransition2.setNode(signInPane);
        translateTransition2.setFromX(flag ? len : 0);
        translateTransition2.setToX(flag ? 0 : len);
        translateTransition2.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInPane.setVisible(false);
    }
}
