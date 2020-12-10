package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CarEdit extends Controller implements Initializable {
    public ImageView imageView;
    public Text regNum, makeView, modelView, colorView, yearView, quantityView;
    public Pane viewPane, editPane;
    public JFXTextField makeEdit, modelEdit, colorEdit1, colorEdit2, colorEdit3, yearEdit, quantityEdit;
    public JFXButton saveButton, editButton;
    public ImageView saveImageView, editImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editPane.setVisible(false);
        saveButton.setGraphic(saveImageView);
        editButton.setGraphic(editImageView);
    }

    public void onSaveButton(ActionEvent mouseEvent) {
        editPane.setVisible(false);
        viewPane.setVisible(true);
    }

    public void onEditButton(ActionEvent mouseEvent) {
        makeEdit.setText(makeView.getText());
        modelEdit.setText(modelView.getText());
        yearEdit.setText(yearView.getText());
        quantityEdit.setText(quantityView.getText());
        System.out.println(colorView.getText());
        String colors[] = colorView.getText().split(",");
        System.out.println(colors.length);
        if(colors.length > 0 && colors[0]!=null && !colors[0].equals(""))colorEdit1.setText(colors[0]);
        if(colors.length > 1 && colors[1]!=null && !colors[1].equals(""))colorEdit2.setText(colors[1]);
        if(colors.length > 2 && colors[2]!=null && !colors[2].equals(""))colorEdit3.setText(colors[2]);
        editPane.setVisible(true);
        viewPane.setVisible(false);
    }

    public void onCancelButton(ActionEvent actionEvent) {
    }

    public void onConfirmbutton(ActionEvent actionEvent) {
    }
}
