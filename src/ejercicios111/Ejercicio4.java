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
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Comienza el ejercicio
            
            // - El nombre y el poder del primer personaje.
            
            rs.first();
            System.out.println("El nombre del primer personaje es: " + rs.getString("Nombre") + " y su poder es de " + rs.getString("Poder"));
            
            // - El poder del 44º personaje
            
            rs.absolute(44);
            System.out.println("El nombre del primer personaje es: " + rs.getString("Nombre") + " y su poder es de " + rs.getString("Poder"));
                
            // - El personaje con más poder de todos, con todos sus valores.
            int maxPoder = 0;
            int posicionPersonajeMaxPoder = 0;
            
            // Volvemos al inicio para poder recorrer toda la tabla
            rs.beforeFirst();
            while (rs.next()){
                int poder = rs.getInt("poder");
                int posicion = rs.getInt("id");
                if (poder > maxPoder){
                    maxPoder = poder;
                    posicionPersonajeMaxPoder = posicion;
                }
            }
            
            
            rs.absolute(posicionPersonajeMaxPoder);  
            System.out.println("El personaje con más poder es " + rs.getString("nombre") + " con el poder " + rs.getString("poder"));
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
