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
    public PersonajesDragonBall(String nombreIn, int poderIn,  String procedenciaIn, boolean activoIn){
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
    public PersonajesDragonBall(String nombrePersonajeIn, String nombreBdIn){
        if (comprobarExistenciaPersonaje(nombrePersonajeIn)){
            System.out.println("El personaje existe, pasamos a rescatar la información de dicho personaje");
            if (nombreBdIn.equalsIgnoreCase("personajesdb")){
                // Conectamos con nuestra base de datos y comenzamos a obtener los datos
                String url = "jdbc:mysql://localhost:3307/frikadas";
                String user = "root";
                String pass = "";
                try (
                        Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = stmt.executeQuery("SELECT * FROM " + nombreBdIn); // USAR PARA CONSULTAS
                        ) {

                    //se carga la clase del Driver
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    while (rs.next()) {
                        if (rs.getString("nombre").equalsIgnoreCase(nombrePersonajeIn)) {
                            // Volcamos los datos de la BD al objeto
                            this.nombre = rs.getString("nombre");
                            this.procedencia = rs.getString("procedencia");
                            this.poder = rs.getInt("poder");
                            if (rs.getInt("activo") == 0) {
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
                System.out.println("El nombre de la tabla no es correcto, revisalo por favor."
                        + "\nEste objeto tendrá sus valores como nulos por defecto");
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

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
   
    @Override
    public String toString(){
        String informacion  = "";
        informacion += "Soy " + this.getNombre() + " cuyo poder es de " + this.getPoder() + "\nProcedo de " + this.getProcedencia() 
                + " Y mi estado es:";
        if (this.getActivo()){
            informacion += " activo";
        } else {
            informacion += " inactivo";
        }
        return informacion;
    }
    
    
    /*
        Métodos propios de la clase
    */
    
    /**
     * Esta función permite comprobar si el personaje existe en la BD o no
     * @param nombrePersonajeIn (String) nombre del Personaje
     * @return true si existe. False si no existe
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
    
    /**
     * Este método permite el borrado de un personaje siempre y cuando este, exista.
     * @param personajeIn  Nombre del personaje a eliminar
     */
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
    
    /**
     * Este método permite igualar los atributos del objeto actual con los que se tengan en la BD si el personaje existe
     * @param personajeIn nombre del personaje que vamos a consultar en la BD
     */
    public void cargarAtributosPersonaje(PersonajesDragonBall personajeIn){
        if (comprobarExistenciaPersonaje(personajeIn.getNombre())){
            System.out.println("Personaje encontrado! Pasamos a modificar los atributos");
            // Conectamos con nuestra base de datos y comenzamos a obtener los datos
            String url = "jdbc:mysql://localhost:3307/frikadas";
            String user = "root";
            String pass = "";
            try (
                    Connection conn = DriverManager.getConnection(url, user, pass); 
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
                    ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb" ); // USAR PARA CONSULTAS
                    ) {

                //se carga la clase del Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                while(rs.next()){
                    if (rs.getString("nombre").equalsIgnoreCase(personajeIn.getNombre())){
                        // Volcamos los datos de la BD al objeto
                        personajeIn.setNombre(rs.getString("nombre"));
                        personajeIn.setProcedencia(rs.getString("procedencia"));
                        personajeIn.setPoder(rs.getInt("poder"));
                        if (rs.getInt("activo")==0){
                            personajeIn.setActivo(false);
                        } else {
                            personajeIn.setActivo(true);
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
        }   else {
            System.out.println("Lo sentimos pero el personaje no existe...");
        }
    }
    
    /**
     * Este método permite actualizar los datos del personaje almacenado en la BD con los datos del objeto con su mismo nombre.
     * Para poder funcionar el personaje ha de existir
     * @param personajeIn nombre del personaje
     */
    public void actualizarDatosPersonaje(PersonajesDragonBall personajeIn){
        if (comprobarExistenciaPersonaje(personajeIn.getNombre())){
            System.out.println("El personaje existe!!");
            // Conectamos con nuestra base de datos y comenzamos a obtener los datos
            String url = "jdbc:mysql://localhost:3307/frikadas";
            String user = "root";
            String pass = "";
            try (
                    Connection conn = DriverManager.getConnection(url, user, pass); 
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                    ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb" ); // USAR PARA CONSULTAS
                    ) {

                //se carga la clase del Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                int lineaPersonaje = 1; // La fila siempre empieza en 1 NO EN 0 como cabría esperar
                while(rs.next()){
                    if (rs.getString("nombre").equalsIgnoreCase(personajeIn.getNombre())){
                        // terminado esto, no nos interesa sacar más información
                        break;
                    } else {
                        lineaPersonaje++;
                    }
                }
                
                // Nos vamos a la línea del personaje
                rs.absolute(lineaPersonaje);
                
                // Modificamos todos los datos
                rs.updateString(2, personajeIn.getNombre());
                rs.updateInt(3, personajeIn.getPoder());
                rs.updateString(4, personajeIn.getProcedencia());
                if (personajeIn.getActivo()){
                    rs.updateInt(5, 1);
                } else {
                    rs.updateInt(5,0);
                }
                // Una vez terminamos, realizamos el Update correspondiente
                rs.updateRow();
                System.out.println("Se han modificado los datos del personaje en la BD");
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } else {
            System.out.println("El personaje no existe, comprueba que el nombre sea correcto");
        }
    }
    
    /**
     * Este método permite guardar a un personaje nuevo si no existe en la BD, en caso de existir, se actualiza la información
     *  con la del estado del objeto sobre la que se ejecuta el método
     * @param personajeIn  nombre del personaje
     */
    public void guardarPersonaje(PersonajesDragonBall personajeIn){
        if (comprobarExistenciaPersonaje(personajeIn.getNombre())){
            System.out.println("El personaje existe así que vamos a actualizar sus datos:");
            this.actualizarDatosPersonaje(personajeIn);
        } else {
            System.out.println("El personaje no existe, pasamos a incluirle en la Base de Datos");
            // Conectamos con nuestra base de datos y comenzamos a obtener los datos
            String url = "jdbc:mysql://localhost:3307/frikadas";
            String user = "root";
            String pass = "";
            try (
                    Connection conn = DriverManager.getConnection(url, user, pass); 
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
                    ResultSet rs = stmt.executeQuery("SELECT * FROM personajesdb" ); // USAR PARA CONSULTAS
                    ) {

                //se carga la clase del Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                
                // Nos vamos a la línea reservadad por Java para introducir el dato
                rs.moveToInsertRow();
                
                // Modificamos todos los datos
                rs.updateString(2, personajeIn.getNombre());
                rs.updateInt(3, personajeIn.getPoder());
                rs.updateString(4, personajeIn.getProcedencia());
                if (personajeIn.getActivo()){
                    rs.updateInt(5, 1);
                } else {
                    rs.updateInt(5,0);
                }
                // Una vez terminamos, realizamos el insert correspondiente
                rs.insertRow();
                System.out.println("Se ha añadido el personaje la BD");
            } catch (SQLException sqlex) {
                System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
