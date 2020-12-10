package Main;

import Main.Main;

public class Client {
    Main main;
    Client(Main main){this.main = main;}
    public void changePane(String fxml, String stageName){
        main.changePane(fxml, stageName, this);
    }
}
