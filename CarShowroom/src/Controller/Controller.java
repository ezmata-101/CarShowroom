package Controller;

import Main.Client;
import Main.Main;

public class Controller {
    Client client;
    boolean isManufacturer;
    MainController mainController;
    Main main;

    public void setMain(Main main){
        this.main = main;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setNotification(String notification) {
    }
}
