package main;

import modelo.Sensores;
import persistencia.GestorBinario;
import persistencia.GestorTexto;
import persistencia.GestorXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MainAnalizador {

    private static final Logger logger = LogManager.getLogger(MainAnalizador.class);

    public static void main(String[] args) {
        logger.info("Iniciando secuencia de auditoria forense.");

        GestorTexto bitacora = new GestorTexto();
        GestorBinario telemetria = new GestorBinario();
        GestorXML instalaciones = new GestorXML();

        logger.debug("Cargando evidencias persistidas por el motor de simulacion.");
        List<String> registros = bitacora.leerBitacoraCompleta();
        Sensores sensorCritico = telemetria.leerRegistro(1);
        instalaciones.leerConfiguracionEscenario();

        boolean alertaTermicaDetectada = false;
        for (String linea : registros) {
            if (linea.contains("ALERTA DEL MOTOR")) {
                alertaTermicaDetectada = true;
                break;
            }
        }

        if (alertaTermicaDetectada && sensorCritico != null && sensorCritico.getTemperaturaActual() > 100.0f) {
            logger.warn("Patron de catastrofe quimica confirmado. Generando ficheros de conclusion.");

            bitacora.generarInformeForense("INCIDENTE DETECTADO: Falla de contencion termica en el reactor principal.\nEFECTOS: Riesgo critico de fusion. Cierre de seguridad activado en Autocaravana.");
            telemetria.exportarAnomaliaBinaria(sensorCritico);
            instalaciones.exportarElementosAfectados("Autocaravana");

            System.out.println("Auditoria finalizada. Revisa el directorio 'datos' para consultar los archivos resultantes.");
        } else {
            logger.info("Los datos procesados no coinciden con ningun incidente clasificado.");
        }
    }
}