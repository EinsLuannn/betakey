package com.luan.betakey.mysql;

import com.luan.betakey.BetaKey;

import java.sql.*;

public class MySQL {


    private static MySQL mySQL;

    private static Connection connection;
    private String host = BetaKey.getInstance().getConfig().getString("MySQL.host");
    private String port = BetaKey.getInstance().getConfig().getString("MySQL.port");
    private String database = BetaKey.getInstance().getConfig().getString("MySQL.database");
    private String username = BetaKey.getInstance().getConfig().getString("MySQL.username");
    private String password = BetaKey.getInstance().getConfig().getString("MySQL.password");


    public MySQL() {
    }

    public void connect() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database , username, password);
            System.out.println("§7[§bMySQL§7] §aDie verbindung zur MySQL-Datenbank wurde hergestellt.");
        }catch(SQLException exception){
            exception.fillInStackTrace();
            System.out.println("§7[§bMySQL§7] §cEin Fehler ist aufgetreten, bitte überprüfe deine config!");
        }
    }

    public void disconnect() {
        try{
            if(connection != null) connection.close();
        }catch(SQLException exception){
            exception.fillInStackTrace();
            System.out.println("§7[§bMySQL§7] §cEin Fehler ist aufgetreten, bitte überprüfe deine config!");
        }
    }

    public void update(String qry){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        }catch(SQLException exception){
            exception.fillInStackTrace();
            System.out.println("§7[§bMySQL§7] §cEin Fehler ist aufgetreten, Exeption: update error");
        }
    }

    public ResultSet qry(String query) {
        ResultSet rs = null;

        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            connect();
        }
        return rs;
    }

    public void createTabels() {
        update("CREATE TABLE IF NOT EXISTS `keys` ( `key` VARCHAR(16))");
        update("CREATE TABLE IF NOT EXISTS `betakeys` ( `key` VARCHAR(16), `uuid` VARCHAR(36))");
    }

    public Connection getConnection() {
        return connection;
    }
}