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
        int posicionPersonaje = 0;
        int posicionUltimoPersonaje = 0;
        String opcionIn = "";
        String url = "jdbc:mysql://localhost:3307/frikadas";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = stmt.executeQuery("select * from personajesdb"); // USAR PARA CONSULTAS
                ) {

            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            do {
                try {
                    System.out.println("Elige uno de los personajes por favor: ");
                // Mostramos por pantalla los personajes disponibles
                while (rs.next()) {

                    System.out.print(rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(4) + " - " + rs.getString(5));
                    System.out.println("");
                    posicionUltimoPersonaje++;
                }
                System.out.println("Introduce el número del personaje: ");
                posicionPersonaje = reader.nextInt();
                } catch (InputMismatchException ime){
                    System.err.println("Error, el tipo de dato no está permitido: " + ime);
                    reader.nextLine();
                } catch (Exception ex){
                    System.err.println("ERROR INESPERADO: " + ex);
                }
            } while (posicionPersonaje <= 0 || posicionPersonaje > posicionUltimoPersonaje);
            
            // Vamos a la línea correspondiente
            rs.absolute(posicionPersonaje);
            
            System.out.println("¿Seguro que quieres eliminar el registro?"
                    + "\nPulsa \"S\" para continuar, pulsa otra cosa para cancelar");
            opcionIn = reader.next();
            
            if (opcionIn.charAt(0) == 'S' || opcionIn.charAt(0) == 's'){
                System.out.println("Se procede a eliminar el registro");
                rs.deleteRow();
            } else {
                System.out.println("Se ha cancelado el borrado");
            }
            
        } catch (SQLException sqlex) {
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}
