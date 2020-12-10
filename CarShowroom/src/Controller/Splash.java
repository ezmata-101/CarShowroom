package Controller;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Splash extends Controller implements Initializable {
    @FXML
    public ImageView wheel, curve;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRotation(wheel, 1);
        addRotation(curve, -1);
    }

    private void addRotation(ImageView image, int i){
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(image);
        rotateTransition.setDuration(Duration.seconds((i==1?5:3)));
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle((i==1?10*360: -360));
        rotateTransition.setAutoReverse(i==-1);
        rotateTransition.play();
    }
}
