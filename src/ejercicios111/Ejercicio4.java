package ejercicios111;

import java.sql.*;
import java.util.HashMap;

/**
 *
 * 4 - Se nos pide conectarnos a la bd 'frikadas' sobre dicha bd mostrar una
 * serie de registros
 *
 *
 * @author Mario Gutiérrez
 * @see
 * https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
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
                Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb"); // USAR PARA CONSULTAS
                ) {

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
            while (rs.next()) {
                int poder = rs.getInt("poder");
                int posicion = rs.getInt("id");
                if (poder > maxPoder) {
                    maxPoder = poder;
                    posicionPersonajeMaxPoder = posicion;
                }
            }

            rs.absolute(posicionPersonajeMaxPoder);
            System.out.println("El personaje con más poder es " + rs.getString("nombre") + " con el poder " + rs.getString("poder"));

            // - El personaje con menos poder de entre los que sí están activos para combatir(Activo = 1).
            int minPoder = 999;
            int posicionPersonajeMinPoder = 0;
            rs.beforeFirst();
            while (rs.next()) {
                int poder = rs.getInt("poder");
                int posicion = rs.getInt("id");
                if (rs.getInt("activo") == 1 && poder < minPoder) {
                    minPoder = poder;
                    posicionPersonajeMinPoder = posicion;
                }
            }

            rs.absolute(posicionPersonajeMinPoder);
            System.out.println("El personaje con menos poder es " + rs.getString("nombre") + " con el poder " + rs.getString("poder"));

            // - El personaje nacido en La Tierra que tiene más poder.
            int maxPoderTierra = 0;
            int posicionMaxPoderTierra = 0;
            rs.beforeFirst();
            while (rs.next()) {
                String planeta = rs.getString("procedencia");
                int poder = rs.getInt("poder");
                if (planeta.equalsIgnoreCase("La Tierra") && poder >= maxPoderTierra) {
                    maxPoderTierra = poder;
                    posicionMaxPoderTierra = rs.getInt("id");
                }
            }

            rs.absolute(posicionMaxPoderTierra);
            System.out.println("El personaje con más poder de \"La Tierra\" es " + rs.getString("Nombre"));

            /*
                En esta parte existe cierta disparidad ya que en realidad son dos los personajes que tienen ese poder
                    - Androide 17
                    - Gohan 88
             */
            // - Los 10 personajes que tienen más poder.
            System.out.println("\n\nA continuación mostramos los personajes cuyo poder es mayor a la media: ");
            // Primero calculamos la media de poder para todos los personajes
            int mediaPoderGlobal = 0;
            int numeroLineas = 0;
            rs.beforeFirst();

            while (rs.next()) {
                mediaPoderGlobal += rs.getInt("poder");
                numeroLineas++;
            }

            int mediaPoder = mediaPoderGlobal / numeroLineas;

            System.out.println("La media de poder es: " + mediaPoder);

            // Recorremos de nuevo el resultSet para obtener los personajes cuyo poder sea superior a la media
            rs.beforeFirst();
            while (rs.next()) {
                if (rs.getInt("activo") == 1 && rs.getInt("poder") > mediaPoder) {
                    System.out.println("Nombre personaje: " + rs.getString("nombre") + " -  con poder " + rs.getInt("poder"));
                }
            }

            // - El número de personajes de cada una de las procedencias.
            System.out.println("\n\nEl listado de procdencias es el siguiente: ");

            HashMap<String, Integer> personajesProcedencias = new HashMap<>();

            rs.beforeFirst();
            while (rs.next()) {
                String procedencia = rs.getString("procedencia");
                if (personajesProcedencias.containsKey(procedencia)) {
                    personajesProcedencias.replace(procedencia, personajesProcedencias.get(procedencia) + 1);
                } else {
                    personajesProcedencias.put(procedencia, 1);
                }
            }

            // Mostramos por pantalla la información
            for (String key : personajesProcedencias.keySet()) {
                System.out.println(key + " - " + personajesProcedencias.get(key));
            }

            // - Todos los nombres de los personajes en orden alfabético.
            
            System.out.println("\n\nMostramos los nombres de los personajes ordenados alfabéticamente: \n");
            String nombresPersonajes = "";
            boolean primerPersonaje = false;
            // Primero necesito obtener de algún modo los nombres de los personajes
            rs.beforeFirst();
            while (rs.next()) {
                if (primerPersonaje == false){
                    // Si no hago esto el comienzo siempre será ;nombre1;nombre2 Provocando errores al continuar más adelante
                    nombresPersonajes += rs.getString("nombre");
                    primerPersonaje = true;
                } else {
                    nombresPersonajes += ";" + rs.getString("nombre");
                }
            }
            
            // Una vez tengamos los nombres, separo los valores por el delimitador (se podría haber guardado en un arrayList directamente)
            String[] arrayNombres = nombresPersonajes.split(";");
            /*
                Un String es una cadena de char, el cuál tiene un valor en la tabla Ascii
                Si aprovechamos esto, tenemos que la primera letra de cada nombre tiene un valor distinto (en referencia al Ascii)
            
                Si a mayores, tratamos esas letras como números ¿no podríamos ordenar los elementos con un algoritmo?
                Con esta idea en mente, podemos comprobar nombre por nombre que valor tiene su primer caracter y con ello, 
                    compararlo con el resto de nombres (como si estuviesemos ordenando números)
            
                El resultado final es un array con los nombres de los personajes pero ordenados, sin necesidad de indicarlo en la sentencia SQL
            */
            for (int i = 1; i < arrayNombres.length; i++) {
                String auxString = arrayNombres[i];
                int j = i - 1;
                while (j >= 0 && (transformarCadena(arrayNombres[j]) > transformarCadena(auxString))) {
                    arrayNombres[j + 1] = arrayNombres[j];
                    j--;
                }

                arrayNombres[j + 1] = auxString;
            }

            for (int i = 0; i < arrayNombres.length; i++) {
                System.out.println(arrayNombres[i]);
            }

        } catch (SQLException sqlex) {
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static int transformarCadena(String cadenaIn) {
        int resultado = 0;

        resultado = (int) cadenaIn.charAt(0);

        return resultado;
    }
}
