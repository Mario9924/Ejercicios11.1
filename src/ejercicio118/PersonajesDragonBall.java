package ejercicio118;

import java.sql.*;

/**
 *
 * Esta clase permite instanciar objetos a partir de los datos de una base de datos o desde el constructor "estandar" de la clase
 * A mayores se generan una serie de métodos para poder realizar:
 *  - Guardar los datos en la BD (se modifican si el personaje ya existe, en caso contrario se crea desde cero)
 *  - Crear personaje en función del resultado anterior
 *  - Borrar el registro del personaje si este existe
 *  - Actualizar los datos de la BD del personaje por los datos que tenga en ese momento el objeto
 *  - Cargar los datos de un personaje de la BD en el objeto correspondiente 
 *      Por ejemplo el personaje "Goku" tiene una serie de campos en la BD y en el constructor hemos indicado otros distintos
 *          esta función modifica los atributos del objeto para que sean iguales que los datos de la BD
 *  - Comprobar que el personaje existe, lo que dará pie en la función correspondiente a la creación del personaje o al borrado del mismo
 * 
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class PersonajesDragonBall {
    private String nombre;
    private String procedencia;
    private int poder;
    private boolean activo;
    
    /**
     * Este constructor inicializa los valores del objeto con los datos que sean introducidos en el mismo
     * 
     * @param nombreIn (String) nombre del Personaje
     * @param procedenciaIn (String) procedencia del Personaje
     * @param poderIn (int) poder del Personaje
     * @param activoIn (boolean)si el personaje está activo o no
     */
    public PersonajesDragonBall(String nombreIn, String procedenciaIn, int poderIn, boolean activoIn){
        this.nombre = nombreIn;
        this.procedencia = procedenciaIn;
        this.poder = poderIn;
        this.activo = activoIn;
    }
    
    /**
     * Este constructor necesita el nombre de un personaje (ha de existir en la BD) y la BD de la que va a obtener la información
     * @param nombreBdIn nombre de la BD desde la que vamos a obtener la información
     * @param nombrePersonajeIn  nombre del personaje que ha de existir dentro de la BD
     */
    public PersonajesDragonBall(String nombreBdIn, String nombrePersonajeIn){
        if (comprobarExistenciaPersonaje(nombrePersonajeIn)){
            System.out.println("El personaje existe, pasamos a rescatar la información de dicho personaje");
            // Conectamos con nuestra base de datos y comenzamos a obtener los datos
            String url = "jdbc:mysql://localhost:3307/frikadas";
            String user = "root";
            String pass = "";
            try (
                    Connection conn = DriverManager.getConnection(url, user, pass); 
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + nombreBdIn); // USAR PARA CONSULTAS
                    ) {

                //se carga la clase del Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                while(rs.next()){
                    if (rs.getString("nombre").equalsIgnoreCase(nombrePersonajeIn)){
                        // Volcamos los datos de la BD al objeto
                        this.nombre = rs.getString("nombre");
                        this.procedencia = rs.getString("procedencia");
                        this.poder = rs.getInt("poder");
                        if (rs.getInt("activo")==0){
                            this.activo = false;
                        } else {
                            this.activo = true;
                        }
                        // terminado esto, no nos interesa sacar más información
                        break;
                    }
                }

            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } else {
            System.out.println("Lo sentimos, pero el personaje al que haces referencia no existe");
        }
    }

    
    
    // Setters y Getters de la clase
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    /*
        Métodos propios de la clase
    */
    
    
    private boolean comprobarExistenciaPersonaje(String nombrePersonajeIn){
        boolean resultado = false;
        // Conectamos con nuestra base de datos y comenzamos a obtener los datos
        String url = "jdbc:mysql://localhost:3307/frikadas";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            while(rs.next()){
                if (rs.getString("nombre").equalsIgnoreCase(nombrePersonajeIn)){
                    resultado = true;
                    break;
                }
            }
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }        
        return resultado;
    }
    
    
    public void borrarPersonaje(PersonajesDragonBall personajeIn){
        if (comprobarExistenciaPersonaje(personajeIn.getNombre())){
            System.out.println("El personaje existe por lo que podemos eliminarlo");
            // Conectamos con nuestra base de datos y comenzamos a obtener los datos
        String url = "jdbc:mysql://localhost:3307/frikadas";
        String user = "root";
        String pass = "";
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            int lineaPersonaje =0;
            while(rs.next()){
                if (rs.getString("Nombre").equalsIgnoreCase(personajeIn.getNombre())){
                    break;
                } else {
                    lineaPersonaje++;
                }
            }
            
            // Nos posicionamos en la fila adecuada
            rs.absolute(lineaPersonaje);
            
            // Eliminamos el registro
            rs.deleteRow();
            System.out.println("Se ha eliminado el registro");
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }        
        } else {
            System.out.println("Lo sentimos pero el personaje no existe");
        }
    }
    
    
}
