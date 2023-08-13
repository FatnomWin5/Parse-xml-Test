package org.example;

import java.sql.*;
import java.util.List;

public class DatabaseEntry {

    //Логин от базы данных
    private static final String DB_USERNAME = "postgres";

    //Пароль от базы данных
    private static final String DB_PASSWORD = "admin";

    //URL для подключения к базу данных
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Plant";
    private static final String time = " 00:00:00";
    private Integer id;

    public void databaseEntry() throws SQLException {

        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        // обьект для отправки запросов в БД
        PreparedStatement statement;

        String[] catalogAttributes = ParseXml.getCatalogAttributes();
        List<Plant> plantList = ParseXml.getPlantList();

        //Запрос для записи данных в d_cat_catalog
        String SQL_INSERT_D_CAT_CATALOG = "INSERT INTO public.d_cat_catalog (delivery_date, company, uuid) VALUES (?, ?, ?)";

        //Запрос для получения id из d_cat_catalog
        String SQL_MAX_ID = "SELECT MAX(id) FROM public.d_cat_catalog";

        //Запрос для записи данных в f_cat_catalog
        String SQL_INSERT_F_CAT_CATALOG = "INSERT INTO public.f_cat_plants (common, botanical, zone, light, price, availability, catalog_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        //запись данных в d_cat_catalog
        statement = connection.prepareStatement(SQL_INSERT_D_CAT_CATALOG);

        statement.setTimestamp(1, Timestamp.valueOf(catalogAttributes[0] + time));
        statement.setString(2, catalogAttributes[1]);
        statement.setString(3, catalogAttributes[2]);
        statement.executeUpdate();

        // получения id только что добавленной строки в d_cat_catalog
        statement = connection.prepareStatement(SQL_MAX_ID);

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            id = result.getInt("max");
        }

        // запись данных в f_cat_catalog
        statement = connection.prepareStatement(SQL_INSERT_F_CAT_CATALOG);

        for (Plant c : plantList) {
            statement.setString(1, c.getCommon());
            statement.setString(2, c.getBotanical());
            statement.setInt(3, c.getZone());
            statement.setString(4, c.getLight());
            statement.setFloat(5, c.getPrice());
            statement.setInt(6, c.getAvailability());
            statement.setInt(7, id);
            statement.executeUpdate();
        }
    }
}
