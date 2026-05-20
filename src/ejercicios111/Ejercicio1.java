package ejercicios111;
import java.sql.*;

/**
 *
 * 1- Genera una base de datos ejbd1 desde código java. 
 * Después, añade la sentencia para eliminarla y bórrala. ¿Es esto seguro? ¡Ojo con lo que hacemos!
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3307/ejdb1";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement(); 
            ){

            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Creamos la base de datos 
            if (stmt.executeUpdate("CREATE DATABASE ejdb1") == 1){ // la consulta devuelve un 1 indicando que ha habido 1 cambio
                System.out.println("Se ha creado correctamente la base de datos");
                // Eliminamos la base de datos
                
                /*
                    Esto no es recomendable puesto que no hay opción para volver hacia atrás ni aviso o mensaje de confirmar
                     cambios en caso de no querer hacerlo
                    
                */
                if (stmt.executeUpdate("Drop database ejdb1") == 0){ // devuelve un 0 tras ejecutarse correctamente
                    System.out.println("Se ha eliminado la base de datos");
                } else {
                    System.out.println("Algo ha salido mal y no hemos podido eliminar la base de datos");
                }
                
            } else {
                System.out.println("Algo ha salido mal y no hemos podido crear la base de datos");
                System.out.println(stmt.executeUpdate("Drop database ejdb1"));
            }
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
