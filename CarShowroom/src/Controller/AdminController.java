package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends Controller implements Initializable {
    public ListView listView;
    public JFXTextField userName, password;
    public JFXButton sendButton;

    private ObservableList<String> manufacturers = FXCollections.observableArrayList();
    private String lastSelected;
    private String lastCommand;
    private ContextMenu menu;

    public void send(ActionEvent actionEvent) {
        if(lastCommand == null) return;
        if(lastCommand == "DELETE"){
            client.sendMessage("MANUFACTURER/DELETE/"+lastSelected);
            return;
        }
        if(userName.getText().equals("") || password.getText().equals("")) return;
        client.sendMessage("MANUFACTURER/"+lastCommand+"/"+lastSelected+"/"+userName.getText()+"/"+password.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu = new ContextMenu();
        manufacturers.addAll("Akash", "Batash", "Kamal");
        MenuItem editMenu = new MenuItem("Edit");
        editMenu.setOnAction((e) -> editManufacturer());
        MenuItem deleteMenu = new MenuItem("Delete");
        deleteMenu.setOnAction((e) -> deleteManufacturer());
        MenuItem addMenu = new MenuItem("Add New");
        addMenu.setOnAction((e) -> addManufacturer());
        menu.getItems().addAll(editMenu, deleteMenu, addMenu);
        listView.setItems(manufacturers);
        listView.setContextMenu(menu);
    }

    private void addManufacturer() {
        lastCommand = "ADD";
    }

    private void deleteManufacturer() {
        if(lastSelected != null)client.sendMessage("MANUFACTURER/DELETE/"+lastSelected);
    }

    private void editManufacturer() {
        lastCommand = "EDIT";
        userName.setText(lastSelected);
        password.setText("");
        password.setPromptText("Password");
        sendButton.setText("Edit");
    }

    public void onSelect(MouseEvent mouseEvent) {
        lastSelected = (String) listView.getSelectionModel().getSelectedItem();
    }

    public void addOrEdit(String prev, String current) {
        manufacturers.remove(prev);
        manufacturers.add(current);
    }

    public void delete(String name) {
        manufacturers.remove(name);
    }
}
