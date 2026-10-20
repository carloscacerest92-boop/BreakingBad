package persistencia;

import modelo.Sensores;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestorBinario {

    private static final Path RUTA_BINARIO = Paths.get("datos", "telemetria_quimica.bin");

    // float (4 bytes) + int (4 bytes) + double (8 bytes) = 16 bytes por registro
    private static final int TAMANO_REGISTRO = 16;

    public GestorBinario() {
        try {
            if (!Files.exists(RUTA_BINARIO.getParent())) {
                Files.createDirectories(RUTA_BINARIO.getParent());
            }
        } catch (IOException e) {
            System.err.println("Error en la estructura de directorios binarios: " + e.getMessage());
        }
    }

    /**
     * Guarda una nueva lectura de sensores al final del archivo.
     */
    public void guardarTelemetria(Sensores sensores) {
        try (RandomAccessFile raf = new RandomAccessFile(RUTA_BINARIO.toFile(), "rw")) {
            raf.seek(raf.length());
            raf.writeFloat(sensores.getTemperaturaActual());
            raf.writeInt(sensores.getPresionActual());
            raf.writeDouble(sensores.getVoltajeCerco());
        } catch (IOException e) {
            System.err.println("Error de E/S en la escritura de telemetría: " + e.getMessage());
        }
    }

    /**
     * Acceso directo a memoria mediante seek() para optimizar la lectura de un registro específico.
     */
    public Sensores leerRegistro(int numeroRegistro) {
        try (RandomAccessFile raf = new RandomAccessFile(RUTA_BINARIO.toFile(), "r")) {
            long posicion = (long) numeroRegistro * TAMANO_REGISTRO;

            if (posicion >= raf.length()) {
                return null;
            }

            raf.seek(posicion);
            float temp = raf.readFloat();
            int presion = raf.readInt();
            double voltaje = raf.readDouble();

            return new Sensores(temp, presion, voltaje);
        } catch (IOException e) {
            System.err.println("Error al leer posición en la memoria binaria: " + e.getMessage());
            return null;
        }
    }
}