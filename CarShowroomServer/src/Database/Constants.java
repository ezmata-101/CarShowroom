package Database;

import java.io.File;

interface Constants {
    String DATABASE_PATH = "jdbc:sqlite:Asset"+File.separator;
    String DATABASE_NAME = "carshowroom.db";

    String MANUFACTURERS_TABLE = "manufacturers";
    String CARS_TABLE = "cars";

    String ID_COLUMN = "_id";
    String REG_NUM_COLUMN = "reg_num";
    String MAKE_COLUMN = "make";
    String MODEL_COLUMN = "model";
    String COLORS_COLUMN = "colors";
    String YEAR_COLUMN = "year";
    String PRICE_COLUMN = "price";
    String QUANTITY_COLUMN = "quantity";
    String IMAGE_COLUMN = "image";

    String NAME_COLUMN = "name";
    String PASSWORD_COLUMN = "pass";

    String INSERT_INTO_CAR =
            "INSERT INTO "+ CARS_TABLE+" ("+
                    REG_NUM_COLUMN+", "+
                    MAKE_COLUMN+", "+
                    MODEL_COLUMN+", "+
                    COLORS_COLUMN+", "+
                    YEAR_COLUMN+", "+
                    PRICE_COLUMN+", "+
                    QUANTITY_COLUMN+", "+
                    IMAGE_COLUMN+") "+
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String GET_ALL_CARS = "SELECT * FROM "+CARS_TABLE;
    String updateCarStart = "UPDATE "+CARS_TABLE+ " SET ";
    String updateCarEnd = " = ? "+ " WHERE "+ID_COLUMN+" = ? ";

    String UPDATE_CAR_REG_NUM = updateCarStart+REG_NUM_COLUMN+updateCarEnd;
    String UPDATE_CAR_MAKE_NAME = updateCarStart+MAKE_COLUMN+updateCarEnd;
    String UPDATE_CAR_MODEL_NAME = updateCarStart+MODEL_COLUMN+updateCarEnd;
    String UPDATE_CAR_COLORS = updateCarStart+COLORS_COLUMN+updateCarEnd;
    String UPDATE_CAR_YEARS = updateCarStart+YEAR_COLUMN+updateCarEnd;
    String UPDATE_CAR_PRICE = updateCarStart+PRICE_COLUMN+updateCarEnd;
    String UPDATE_CAR_QUANTITY = updateCarStart+QUANTITY_COLUMN+updateCarEnd;
    String UPDATE_CAR_IMAGE = updateCarStart+IMAGE_COLUMN+updateCarEnd;

    String GET_ID_FOR_REG_NUM =
            "SELECT COUNT(*), "+ID_COLUMN+" FROM "+CARS_TABLE+" WHERE "+REG_NUM_COLUMN+" = ?";


    String INSERT_INTO_MANUFACTURERS =
            "INSERT INTO "+ MANUFACTURERS_TABLE+" ("+
                    NAME_COLUMN+", "+
                    PASSWORD_COLUMN+") "+
                    "VALUES (?, ?)";
    String GET_ID_FOR_NAME =
            "SELECT COUNT(*), "+ID_COLUMN+" FROM "+MANUFACTURERS_TABLE+ " WHERE "+NAME_COLUMN+" ? ";
    String CHECK_PASSWORD_FOR_ID =
            "SELECT COUNT(*), "+ID_COLUMN+" FROM "+MANUFACTURERS_TABLE+ " WHERE "+ID_COLUMN+" = ? AND "+PASSWORD_COLUMN+" = ?";
}
