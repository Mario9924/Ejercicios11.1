package ejercicios111;


import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 6- Realiza un cambio de uno de los datos en la tabla anterior, pero a
 * petición del usuario: primero se mostrarán todas las líneas, y luego el
 * usuario decidirá qué línea y cuál de sus campos quiere modificar.
 *
 * @author Mario Gutiérrez
 * @see
 * https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        Scanner reader = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3307/frikadas";
        String user = "root";
        String pass = "";
        ArrayList<String> listadoCampos = new ArrayList<>();
        int personajeSeleccionado = 0;
        int posicionUltimoPersonaje = 0;
        int campoIn = 0;
        String datoIn = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass); 
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                ResultSet rs = stmt.executeQuery("select * from personajesdb"); // USAR PARA CONSULTAS
                ) {

            // Se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            ResultSetMetaData rsmd = rs.getMetaData(); // Para obtener el nombre de las columnas
            
            // Guardo los datos de las columnas en un arraylist para usarlo de nuevo más adelante
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                listadoCampos.add(rsmd.getColumnName(i));
            }
            
            //Mostramos los personajes disponibles y 
            do {
                posicionUltimoPersonaje = 0;// En caso de que se elija un número de personajes incorrecto, necesitamos comenzar de nuevo en cero
                System.out.println("Elige uno de los siguientes personajes");
               
                // Mostramos por pantalla los campos
                for (int i = 0; i < listadoCampos.size(); i++) {
                    if (i ==  listadoCampos.size()-1) {
                        System.out.print(listadoCampos.get(i));
                    } else {
                        System.out.print(listadoCampos.get(i) + " - ");
                    }
                }
                System.out.println("");
                // Mostramos los personajes
                while (rs.next()) {
                    System.out.print(rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(4) + " - " + rs.getString(5));
                    System.out.println("");
                    posicionUltimoPersonaje++;
                }
                System.out.println("");
                personajeSeleccionado = reader.nextInt();
            } while (personajeSeleccionado <= 0 || personajeSeleccionado > posicionUltimoPersonaje);

            
            rs.absolute(personajeSeleccionado); // Para ir a la fila concreta del personaje
            // Una vez elegido el personaje, damos a elegir que campos vamos a mostrar (menos el ID)
            // Mostramos los campos disponibles para cambiar
            do {
                System.out.println("Elige uno de los campos disponibles por favor");
                for (int i = 1; i < listadoCampos.size(); i++) {
                    System.out.println((i + 1) + " - " + listadoCampos.get(i));
                }
                campoIn = reader.nextInt();
            } while (campoIn < 2 || campoIn > 5);
            
            reader.nextLine();
            // Según lo que se haya elegido, pedimos por pantalla los datos
            switch (campoIn) {
                case 2:
                    System.out.println("Has elegido modificar el campo \"Nombre\""
                            + "\nIntroduce el nuevo dato:");
                    datoIn = reader.nextLine();
                    rs.updateString(2, datoIn);
                    break;
                case 3:
                    do {
                        System.out.println("Has elegido modificar el campo \"Poder\". Recuerda que ha de ser mayor a 0");
                        datoIn = reader.nextLine();
                    } while (Integer.parseInt(datoIn) <= 0);
                    rs.updateInt(3, Integer.parseInt(datoIn));
                    break;
                case 4:
                    System.out.println("Has elegido modificar el campo \"Procedencia\"");
                    datoIn = reader.nextLine();
                    rs.updateString(4, datoIn);
                    break;
                case 5:
                    do {
                        System.out.println("Has elegido modificar el campo \"Activo\"."
                                + "\n\"0\" significa Inactivo mientras que \"1\" significa activo");
                        datoIn = reader.nextLine();
                    } while (Integer.parseInt(datoIn) < 0 || Integer.parseInt(datoIn) > 1);
                    rs.updateInt(5, Integer.parseInt(datoIn));
                    break;
            }

            // Lanzamos la acción de update
            rs.updateRow();
            
            System.out.println("Información actualizada");
            rs.absolute(personajeSeleccionado);
            System.out.println("La nueva información del personaje es: ");            
            for (int i = 1; i < 5; i++){
                System.out.println(listadoCampos.get(i) + " - " + rs.getString((i+1)));
            }
            
            
        } catch (SQLException sqlex) {
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        } catch (InputMismatchException ime) {
            System.err.println("Error al introducir el dato: " + ime);
            reader.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}
