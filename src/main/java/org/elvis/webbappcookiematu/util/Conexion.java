package org.elvis.webbappcookiematu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    //Inicializo 3 variables globales
    private static String url="jdbc:mysql://localhost:3306/trabajoenclase?serverTimezone=UTC";
    //nombre del usuario de la BBDD
    private static String username="root";
    //contraseña de la base de datos
    private static String password="";
    //implementamos un método para realizar la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
