package ejercicios111;
import java.sql.*;

/**
 * 3- Listar todas las bases de datos existentes. 
 * Después, listar todas las tablas existentes en cada una de las bases de datos. 
 * Al final se han de decir cuántas bases de datos y tablas hay en total.
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        String url = "jdbc:mysql://localhost:3307";
        String user = "root";
        String pass = "";
        int contadorBases = 0;
        int contadorTablas = 0;
        try (
                Connection conn = DriverManager.getConnection(url, user, pass);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("show databases"); // USAR PARA CONSULTAS
                
            ){
            
            //se carga la clase del Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            /*
                Si lo que queremos es realizar sentencias DDL: Create - Drop - Alter - etc
                 tenemos que usar 
                stmt.executeUpdate("SENTENCIA DDL") ya que no devuelve un result set, solamente el número de registros modificados
            */
            
            while (rs.next()){
                //System.out.println(rs.getString(1)); Si se quisera sacar el nombre de la base de datos
                String url2 = "jdbc:mysql://localhost:3307/" + rs.getString(1);
                try (Connection conn2 = DriverManager.getConnection(url2, user, pass);
                        Statement stmt2 = conn2.createStatement()) {
                    ResultSet rs2 = stmt2.executeQuery("show tables");
                    while (rs2.next()){
                        contadorTablas++;
                    }
                }
                contadorBases++;
            }
            
            System.out.println("Hay un total de " + contadorBases + " bases de datos total"); // 15
            System.out.println("Hay un total de " + contadorTablas + " tablas en total"); // 180
            
        }  catch(SQLException sqlex){
            System.err.println("Ha habido un error a la hora de trabajar con la base de datos: " + sqlex);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
