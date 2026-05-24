package ejercicios111;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 7- Consigue que el usuario pueda decidir qué registro borrar. Cuando lo
 * seleccione, se preguntará por seguridad si desea borrar ese registro, y si
 * acepta se eliminará.
 * 
 * @author Mario Gutiérrez
 * @see
 * https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        Scanner reader = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3307/BASE-DE-DATOS";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("CONSULTA A LA BASE DE DATOS"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            /*
                Si lo que queremos es realizar sentencias DDL: Create - Drop - Alter - etc
                 tenemos que usar 
                stmt.executeUpdate("SENTENCIA DDL") ya que no devuelve un result set, solamente el número de registros modificados
            */
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
