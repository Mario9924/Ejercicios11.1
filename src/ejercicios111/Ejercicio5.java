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
 * https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio5 {

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
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                ResultSet rs = stmt.executeQuery("Select * from personajesdb");
            ) 
        {

            // Introducimos datos siguiendo los siguientes pasos;
            //1- desplazo a la fila de inserción reservada por Java
            rs.moveToInsertRow();

            //2- relleno las columnas que necesite
            // ID - Nombre - Poder - Procedencia - Activo
            //  1 -     2 -     3  -       4  -       5 
            /*
            ShenLong, con un poder de 333, que proviene de
            * La Tierra y que NO está activo. Monna, con un poder de 54, que proviene del
            * Universo 4 y SÍ está activo. Monaka, con un poder de 6, que proviene del
            * Planeta Wagashi y SÍ está activo).
             */
            rs.updateInt(1, 61);
            rs.updateString(2, "ShenLong");
            rs.updateInt(3, 333);
            rs.updateString(4, "La Tierra");
            rs.updateInt(5, 0);

            rs.insertRow(); // Se tiene que hacer para cada insercción, de otro modo sólo inserta los datos del último rs.update

            rs.updateInt(1, 62);
            rs.updateString(2, "Monna");
            rs.updateInt(3, 54);
            rs.updateString(4, "Universo 4");
            rs.updateInt(5, 1);

            rs.insertRow();

            rs.updateInt(1, 63);
            rs.updateString(2, "Monaka");
            rs.updateInt(3, 6);
            rs.updateString(4, "Planeta Wagashi");
            rs.updateInt(5, 1);

            rs.insertRow();

            // Mostramos la información introducida:
            rs.absolute(61);
            System.out.println(rs.getString("nombre")); // ShenLong

            rs.next(); // ID 62
            System.out.println(rs.getString("nombre")); // Monna

            rs.next(); // ID 63
            System.out.println(rs.getString("nombre")); // Monaka

            /*
                Para eliminar las filas introducidas anteriormente
                Tenemos que ir en orden de la más nueva (la última creada "Monaka") y luego a la más antigua (ShenLong)
                    Ya que si no, estamos intentando borrar la última posición "63" que ya no lo es, será la 62 o la 61
            
                 Habría dos opciones:
                 
                 A- Eliminar 3 veces "la misma fila", la 61. 
                    Por orden se han creado la 61-62-63, si eliminamos la 61, está queda libre y pasa a ocuparse por la 62. 
                    Lo mismo pasa con la 63, ahora pasa a ser la 62. Es como el acertijo "Si voy en 3 posicion y adelanto al 2 que posición tengo?"
                    Podemos hacer esto for (i < 3) rs.absolute(61); y luego rs.deleteRow();
            
                 B - Recorremos todos los resultados y filtramos por el campo Nombre, cuando se encuentre pasamos a eliminar dicho registro
                      Por el momento funciona y no devuelve ningún tipo de excepción como pasaba al intentar eliminar un elemento de una colección
                      Esto se puede realizar gracias a la concurrencia Updatable que nos permite actualizar los datos en tiempo real (en un sentido o en otro)
             */
 /* 
            //1- desplazo a la posición que yo quiera eliminar
            rs.absolute(63);

            //2- La borro
            rs.deleteRow(); // Eliminamos a ShenLong
            
            rs.absolute(62);
            rs.deleteRow(); // Eliminamos a Monna
            
            
            rs.absolute(61);
            rs.deleteRow(); // Eliminamos a Monaka
            
            Opción A 
            
            for (int i = 0; i < 3 ; i++){
                rs.absolute(61);
                rs.deleteRow();
            }
            
            Opción B
            
            
            rs.beforeFirst();
            while (rs.next()){
                String nombre = rs.getString("Nombre");
                if (nombre.equalsIgnoreCase("ShenLong") || nombre.equalsIgnoreCase("Monna") || nombre.equalsIgnoreCase("Monaka")){
                    rs.deleteRow();
                }
            }
            
             */
        } catch (SQLException sqlex) {
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

}
