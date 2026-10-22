package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class GestorTexto {

    // Ruta donde se guardará el log del simulador
    private static final Path RUTA_LOG = Paths.get("datos", "bitacora_operaciones.txt");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public GestorTexto() {
        inicializarDirectorio();
    }

    //comprobamos que los directorios existen antes de utilizarlos
    private void inicializarDirectorio() {
        try {
            if (!Files.exists(RUTA_LOG.getParent())) {
                Files.createDirectories(RUTA_LOG.getParent());
            }
        } catch (IOException e) {
            System.err.println("Error al crear el directorio de datos: " + e.getMessage());
        }
    }

    // escribimos un evento en el fichero con su marca de tiempo
    public void registrarOperacion(String operacion) {
        String timestamp = LocalDateTime.now().format(FORMATO_FECHA);
        String lineaLog = "[" + timestamp + "] " + operacion + System.lineSeparator();

        try {
            // Se usa CREATE para crearlo si no existe, y APPEND para añadir al final
            Files.write(RUTA_LOG, lineaLog.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Fallo al escribir en la bitácora: " + e.getMessage());
        }
    }

    //nos creamos un metodo que utilizara el analizador foresne para revisar el historial de nuestro fichero
    public List<String> leerBitacoraCompleta() {
        try {
            if (Files.exists(RUTA_LOG)) {
                return Files.readAllLines(RUTA_LOG);
            }
        } catch (IOException e) {
            System.err.println("Error al leer la bitácora: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public void generarInformeForense(String contenido) {
        try (java.io.FileWriter fw = new java.io.FileWriter("datos/informe_forense.txt")) {
            fw.write(contenido);
        } catch (java.io.IOException e) {
            System.err.println("Fallo al escribir el informe: " + e.getMessage());
        }
    }
}