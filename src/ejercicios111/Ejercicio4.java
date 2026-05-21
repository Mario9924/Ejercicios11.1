package ejercicios111;

import java.sql.*;

/**
 * 
 * 4 - Se nos pide conectarnos a la bd 'frikadas' sobre dicha bd mostrar una serie de registros
 * 
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */


public class Ejercicio4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3307/frikadas";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
