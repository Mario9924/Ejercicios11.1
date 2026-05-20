
package ejercicios111;

import java.sql.*;

/**
 *
 * @author Mario Gutiérrez
 * @see
 * @version 1.0
 */
public class Plantilla {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3307/BASE-DE-DATOS";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("CONSULTA A LA BASE DE DATOS");
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
