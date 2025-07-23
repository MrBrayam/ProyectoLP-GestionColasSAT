package com.colassat.GestionColas.Test;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.colassat.GestionColas.BaseDeDatos.ConexionBD;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("Probando conexión a la base de datos...");
        
        try {
            Connection conn = ConexionBD.getConexion();
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos");
                
                System.out.println("\nProbando procedimiento PA_CRUD_ListarTrabajador...");
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
                ResultSet rs = stmt.executeQuery();
                
                System.out.println("Columnas disponibles:");
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.println("  " + i + ": " + rs.getMetaData().getColumnName(i));
                }
                
                System.out.println("\nDatos de trabajadores:");
                while (rs.next()) {
                    System.out.println("  ID: " + rs.getString("Codigo"));
                    System.out.println("  Nombre: " + rs.getString("Nombre Trabajador"));
                    System.out.println("  Activo: " + rs.getBoolean("Trabajando"));
                    System.out.println("  Admin: " + rs.getBoolean("Administrador"));
                    System.out.println("  ---");
                }
                
                rs.close();
                stmt.close();
                conn.close();
                
                System.out.println("Procedimiento ejecutado correctamente");
                
            } else {
                System.out.println("No se pudo conectar a la base de datos");
            }
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
