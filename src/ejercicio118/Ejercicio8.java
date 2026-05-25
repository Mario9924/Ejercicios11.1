package ejercicio118;

/**
 * Esta clase main de Java tiene como propósito el instanciar objetos de tipo PersonajesDragonBall
 *  ya sea desde el constructor "estándar" como desde el constructor específico de la clase (mediante la tabla de la bd concreta)
 * 
 * 
 * @author Mario Gutiérrez
 * @see https://classroom.google.com/u/1/c/ODA3NDY5OTY5MTcz/a/ODY0OTY0NTU5Mjg0/details
 * @version 1.0
 */
public class Ejercicio8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaración de variables
        
        // Instanciamos dos objetos haciendo uso de los dos constructores disponibles
        PersonajesDragonBall p1 = new PersonajesDragonBall("Goku", "personajesdb");
        PersonajesDragonBall p2 = new PersonajesDragonBall("Vegeta", 94, "Planeta Vegeta", true);
        System.out.println("Información del primer personaje:\n" + p1 + "\n");
        System.out.println("Información del segundo personaje:\n" + p2 + "\n");
        
        // Ahora probamos a guardar los datos del personaje "Vegeta", los cuáles son distintos que los almacenados en este momento
        // Dato "poder" actual --> 90
        // Dato "poder" del constructor --> 94
        p2.guardarPersonaje(p2); // Actualizará el poder del personaje a 94
        
        // Si ahora instanciamos un nuevo personaje "Vegeta" con el constructor de la base de datos        
        PersonajesDragonBall p3 = new PersonajesDragonBall("Vegeta", "personajesdb");
        System.out.println("Información del tercer personaje:\n" + p3 + "\n"); // El poder cambia a 94
        
        
        // Ahora intentamos cargar los atributos del personaje desde los datos de la BD
        PersonajesDragonBall p4 = new PersonajesDragonBall("Freezer", 0,"Confines Universo N", false);
        System.out.println("Información del cuarto personaje:\n" + p4 + "\n");
        
        // Ahora cargamos los datos desde la BD, los cambios serán 
        //   poder --> 95 procedencia --> "Confines Universo 7" activo --> true
        p4.cargarAtributosPersonaje(p4);
        System.out.println("Información del cuarto personaje tras la recuperación de datos:\n" + p4 + "\n");
        
        
        //Ahora creamos un nuevo personaje que no existía previamente
        // Rescatamos de otro ejercicico a ShenLong, con un poder de 333, que proviene de La Tierra y que NO está activo.     
        PersonajesDragonBall p5 = new PersonajesDragonBall("ShenLong", 333, "La Tierra", false);
        p5.guardarPersonaje(p5);
        
        // Al no existir en la BD lo crea, para probarlo creamos un nuevo objeto mediante el otro constructor
        PersonajesDragonBall p6 = new PersonajesDragonBall("ShenLong", "personajesdb");
        System.out.println("Información del personaje 6:\n" + p6 + "\n");
        
    }
    
}
