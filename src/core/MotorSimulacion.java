package core;

import excepciones.CatastrofeQuimicaException;
import modelo.Sensores;
import persistencia.GestorBinario;
import persistencia.GestorTexto;
import persistencia.GestorXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MotorSimulacion {

    private static final Logger logger = LogManager.getLogger(MotorSimulacion.class);

    private GestorTexto bitacora;
    private GestorBinario telemetria;
    private GestorXML instalaciones;

    public MotorSimulacion(GestorTexto bitacora, GestorBinario telemetria, GestorXML instalaciones) {
        this.bitacora = bitacora;
        this.telemetria = telemetria;
        this.instalaciones = instalaciones;
    }

    public void ejecutarCiclo() {
        logger.info("Iniciando ciclo de simulacion del motor operativo.");

        instalaciones.guardarInstalacion("Autocaravana", 120, true);
        bitacora.registrarOperacion("Infraestructura inicializada y configuracion XML generada desde el motor.");

        Sensores lecturaNormal = new Sensores(85.5f, 100, 12.4);
        Sensores lecturaCritica = new Sensores(105.2f, 150, 11.8);

        telemetria.guardarTelemetria(lecturaNormal);
        logger.debug("Procesamiento base de telemetria completado.");
        bitacora.registrarOperacion("Datos de telemetria base procesados.");

        telemetria.guardarTelemetria(lecturaCritica);
        logger.warn("Se ha inyectado un registro con valores termicos atipicos en el flujo binario.");
        bitacora.registrarOperacion("Anomalia termica detectada y registrada en binario.");

        try {
            evaluarSeguridad(lecturaCritica.getTemperaturaActual());
        } catch (CatastrofeQuimicaException e) {
            bitacora.registrarOperacion("ALERTA DEL MOTOR: " + e.getMessage());
            logger.error("Interrupcion por exceso de temperatura. Detalle tecnico: {}", e.getMessage());
        }
    }

     void evaluarSeguridad(float temperatura) throws CatastrofeQuimicaException {
        if (temperatura > 100.0f) {
            throw new CatastrofeQuimicaException("Nivel de temperatura superior a la tolerancia del reactor.");
        }
    }
}