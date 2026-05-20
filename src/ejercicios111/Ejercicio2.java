package ejercicios111;
import java.sql.*;

/**
 * 2- Genera 10 bases de datos que se llamen bd1,bd2?  Hazlo de forma recursiva (con un bucle). 
 *  Ha de contabilizarse cuántas acciones se han hecho en total.
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3307/";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement();
                
            ){

            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //creamos las 10 bases de datos
            int contadorCreaciones = 0;
            for (int i=1; i <= 10; i++){
                stmt.executeUpdate("create database ejdb"+i);
                contadorCreaciones++;
            }
            
            System.out.println("Se han realizado un total de " + contadorCreaciones + " creaciones.");
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
