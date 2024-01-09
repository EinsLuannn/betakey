package com.luan.betakey.manager;

import com.luan.betakey.mysql.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BetaKeyManager {

    private static MySQL mySQL;
    public BetaKeyManager() {
        mySQL = new MySQL();
    }

    public void create(String key, UUID uuid){
        mySQL.update("INSERT INTO `betakeys` (`key`,`uuid`) VALUES ('"+key+"','"+uuid+"')");
    }
    public void delete(UUID uuid){
        mySQL.update("DELETE FROM `betakeys` WHERE `uuid` = '"+uuid+"'");
    }

    public void delete(String key){
        mySQL.update("DELETE FROM `betakeys` WHERE `key` = '"+key+"'");
    }

    public boolean isExist(String uuid){
        try {
            ResultSet resultSet = mySQL.qry("SELECT * FROM `betakeys` WHERE `uuid` ='"+uuid+"'");
            while (resultSet.next())
                return (resultSet.getString("uuid") != null);
            return false;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public String getKey(UUID uuid){
        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("SELECT * from `betakeys` WHERE `uuid` = '"+uuid+"'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getString("key");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}