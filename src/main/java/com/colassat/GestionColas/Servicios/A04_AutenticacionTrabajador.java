package com.colassat.GestionColas.Servicios;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.colassat.GestionColas.BaseDeDatos.ConexionBD;

public class A04_AutenticacionTrabajador {
    
    /**
     * Autentica un trabajador verificando si existe en la base de datos
     * @param nombreTrabajador Nombre del trabajador (usuario)
     * @param password Contraseña del trabajador (ID del trabajador)
     * @return true si la autenticación es exitosa, false en caso contrario
     */
    public static boolean autenticar(String nombreTrabajador, String password) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConexion();
            stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Trabajador");
                boolean activo = rs.getBoolean("Trabajando");
                
                if (nombre.equalsIgnoreCase(nombreTrabajador.trim()) && activo && password.equals(codigo)) {
                    return true;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al autenticar trabajador: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Obtiene la información del trabajador autenticado
     * @param idTrabajador ID del trabajador
     * @return Nombre del trabajador o null si no se encuentra
     */
    public static String obtenerInfoTrabajador(String idTrabajador) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConexion();
            
            // Usar el procedimiento almacenado para listar trabajadores
            stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Trabajador");
                boolean activo = rs.getBoolean("Trabajando");
                
                if (codigo.equals(idTrabajador) && activo) {
                    return nombre;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al obtener información del trabajador: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    /**
     * Obtiene el ID del trabajador basado en su nombre
     * @param nombreTrabajador Nombre del trabajador
     * @return ID del trabajador o null si no se encuentra
     */
    public static String obtenerIdTrabajador(String nombreTrabajador) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConexion();
            
            // Usar el procedimiento almacenado para listar trabajadores
            stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Trabajador");
                boolean activo = rs.getBoolean("Trabajando");
                
                if (nombre.equalsIgnoreCase(nombreTrabajador.trim()) && activo) {
                    return codigo;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al obtener ID del trabajador: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    /**
     * Verifica si el trabajador tiene permisos administrativos
     * @param idTrabajador ID del trabajador
     * @return true si tiene permisos de administrador (master = 1)
     */
    public static boolean esAdministrador(String idTrabajador) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConexion();
            
            // Usar el procedimiento almacenado para listar trabajadores
            stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                boolean activo = rs.getBoolean("Trabajando");
                boolean master = rs.getBoolean("Administrador"); // EstadoMaster en la vista aparece como "Administrador"
                
                if (codigo.equals(idTrabajador) && activo && master) {
                    return true;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al verificar permisos de administrador: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Verifica si el trabajador tiene permisos administrativos basado en su nombre
     * @param nombreTrabajador Nombre del trabajador
     * @return true si tiene permisos de administrador (master = 1)
     */
    public static boolean esAdministradorPorNombre(String nombreTrabajador) {
        String idTrabajador = obtenerIdTrabajador(nombreTrabajador);
        if (idTrabajador != null) {
            return esAdministrador(idTrabajador);
        }
        return false;
    }
    
    /**
     * Valida si un trabajador existe y está activo en el sistema
     * @param idTrabajador ID del trabajador
     * @return true si el trabajador existe y está activo
     */
    public static boolean validarTrabajador(String idTrabajador) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.getConexion();
            
            // Usar el procedimiento almacenado para listar trabajadores
            stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                boolean activo = rs.getBoolean("Trabajando");
                
                if (codigo.equals(idTrabajador) && activo) {
                    return true;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al validar trabajador: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Valida si un trabajador existe y está activo en el sistema basado en su nombre
     * @param nombreTrabajador Nombre del trabajador
     * @return true si el trabajador existe y está activo
     */
    public static boolean validarTrabajadorPorNombre(String nombreTrabajador) {
        String idTrabajador = obtenerIdTrabajador(nombreTrabajador);
        return idTrabajador != null && validarTrabajador(idTrabajador);
    }
    
    /**
     * Verifica si un trabajador puede acceder como administrador (master = 1)
     * @param idTrabajador ID del trabajador
     * @return true si el trabajador tiene permisos de master y está activo
     */
    public static boolean puedeAccederComoAdmin(String idTrabajador) {
        return validarTrabajador(idTrabajador) && esAdministrador(idTrabajador);
    }
    
    /**
     * Verifica si un trabajador puede acceder como administrador basado en su nombre
     * @param nombreTrabajador Nombre del trabajador
     * @return true si el trabajador tiene permisos de master y está activo
     */
    public static boolean puedeAccederComoAdminPorNombre(String nombreTrabajador) {
        return validarTrabajadorPorNombre(nombreTrabajador) && esAdministradorPorNombre(nombreTrabajador);
    }
    
    /**
     * Autenticación predeterminada para el sistema
     * Permite acceso con credenciales específicas y solo a trabajadores con master = 1
     * @param usuario Usuario (nombre del trabajador o "admin")
     * @param password Contraseña (ID del trabajador o "admin123")
     * @return true si la autenticación es exitosa y tiene permisos de master
     */
    public static boolean autenticacionSistema(String usuario, String password) {
        // Autenticación de administrador del sistema
        if (usuario.equals("admin") && password.equals("admin123")) {
            return true;
        }
        
        // Autenticación usando datos reales de trabajadores
        // Buscar por nombre y validar con ID, solo permitir acceso a trabajadores con master = 1
        String idTrabajador = obtenerIdTrabajador(usuario);
        if (idTrabajador != null && password.equals(idTrabajador) && esAdministrador(idTrabajador)) {
            return true;
        }
        
        return false;
    }
}
