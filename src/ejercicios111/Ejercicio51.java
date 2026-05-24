package ejercicios111;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * 5 1 - Se realiza el ejercicio mediante el uso de un prepared statement
 *
 * @author Mario Gutiérrez
 * @see
 * https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio51 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        Scanner reader = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3307/frikadas";
        String user = "root";
        String pass = "";
        int id = 60;
        try (
                Connection conn = DriverManager.getConnection(url, user, pass); 
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `personajesdb` "
                + "() VALUES (?, ?, ?, ?, ?);");) {

            // Introducimos datos mediante una prepared Statement
            //2- relleno las columnas que necesite
            // ID - Nombre - Poder - Procedencia - Activo
            //  1 -     2 -     3  -       4  -       5 
            /*
            ShenLong, con un poder de 333, que proviene de
            * La Tierra y que NO está activo. Monna, con un poder de 54, que proviene del
            * Universo 4 y SÍ está activo. Monaka, con un poder de 6, que proviene del
            * Planeta Wagashi y SÍ está activo).
             */
            for (int i = 0; i < 3; i++) {
                try {
                    id++;
                    
                    // Pasamos al siguiente ID correspondiente
                    pstmt.setInt(1, id);
                    
                    System.out.println("Introduce el nombre del personaje");
                    pstmt.setString(2, reader.nextLine());

                    System.out.println("Introduce el poder del personaje");
                    pstmt.setInt(3, reader.nextInt());
                    
                    reader.nextLine(); // Al pasar de un tipo de dato a otro, nuestro Scanner almacena en el buffer info extra innecesaria
                    System.out.println("Introduce la procedencia del personaje");
                    pstmt.setString(4, reader.nextLine());

                    System.out.println("Introduce \"0\" si el personaje está inactivo o introduce \"1\" si está activo");
                    pstmt.setInt(5, reader.nextInt());
                    
                    pstmt.executeUpdate();
                    
                    reader.nextLine();
                } catch (InputMismatchException ime) {
                    System.err.println("No puedes introducir ese dato: " + ime);
                    reader.nextLine();
                    i--;
                    id--;
                } catch (SQLException sqlex) {
                    System.err.println("Erro al insertar los datos : " + sqlex);
                }
            }

            // Mostramos los nuevos campos mediante una nueva conexión
            System.out.println("\nSe han añadido los siguientes personajes:\n");
            try (
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("Select * from personajesdb");
                ) 
            {
                rs.absolute(60); // sabemos que el último valor antes de las insercciones es el 60
                while (rs.next()){
                    System.out.println("Nombre: " + rs.getString("nombre"));
                }
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

        } catch (SQLException sqlex) {
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}
