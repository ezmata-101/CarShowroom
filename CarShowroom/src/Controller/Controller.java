package Controller;

import Main.Client;

public class Controller {
    Client client;
    boolean isManufacturer;
    MainController mainController;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setIsManufacturer(boolean isManufacturer) {
        this.isManufacturer = isManufacturer;
    }
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }
}
