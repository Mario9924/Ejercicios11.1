package ejercicios111;

import java.sql.*;

/**
 *
 * 5- Trata de introducir algún elemento en la tabla del ejercicio anterior con
 * INSERT desde el programa. Intenta hacerlo después pidiendo por pantalla los
 * datos. (posibles personajes: ShenLong, con un poder de 333, que proviene de
 * La Tierra y que NO está activo. Monna, con un poder de 54, que proviene del
 * Universo 4 y SÍ está activo. Monaka, con un poder de 6, que proviene del
 * Planeta Wagashi y SÍ está activo).
 * 
 * @author Mario Gutiérrez
 * @see
 * @version 1.0
 */
public class Ejercicio5 {

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
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("CONSULTA A LA BASE DE DATOS"); // USAR PARA CONSULTAS
                
            ){
            
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
