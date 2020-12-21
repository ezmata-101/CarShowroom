package Main;

import Controller.AdminController;

public class Manufacturer {
    private String userName;
    private String password;
    private AdminController adminController;
    public Manufacturer(AdminController adminController){
        this.adminController = adminController;
    }
    public Manufacturer(AdminController adminController, String s){
        this.adminController = adminController;
        String[] ss = s.split("/");
        userName = ss[1];
        password = ss[2];
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getString(){
        return "MANUFACTURER/"+userName+"/"+password;
    }
    public String delete(){
        return "DELETE_"+getString();
    }
}
