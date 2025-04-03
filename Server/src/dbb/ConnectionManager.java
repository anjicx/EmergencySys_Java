/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbb;
import exception.ServerException;
import java.sql.*;

/**
 *
 * @author USER
 */
public class ConnectionManager {
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

   public static Connection getConnection() throws ServerException {
    Connection connection = threadLocalConnection.get();
    if (connection == null) {
        try {
            connection = DBBrokerGenerics.getInstance().getConnection();
            threadLocalConnection.set(connection);
            System.out.println("NOVA KONEKCIJA KREIRANA U THREAD");//kreirana
        } catch (SQLException ex) {
            throw new ServerException("GRESKA KOD KONEKCIJE: " + ex.getMessage());
        }
    } else {
            System.out.println(" KONEKCIJA VRACENA U THREAD");//koriisti se
    }
    return connection;
}
public static void removeConnection() {
    //ako connection.close() onda bi za klijenta vracao u 
    threadLocalConnection.remove();
}
//CONNECTION.CLOSE()
}