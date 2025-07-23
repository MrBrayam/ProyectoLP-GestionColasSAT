package com.colassat.GestionColas.BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD { //adaptar la URL segun su entorno
    private static final String URL = "jdbc:sqlserver://???\\SQLEXPRES:???;databaseName=GestionColas;user=???;password=???;encrypt=false";
    private static Connection conexion = null;
    
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
    
    public static boolean verificarConexion() {
        try {
            Connection conn = getConexion();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al verificar la conexión: " + e.getMessage());
            return false;
        }
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}