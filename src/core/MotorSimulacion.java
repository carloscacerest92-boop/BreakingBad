package core;

import excepciones.CatastrofeQuimicaException;
import modelo.Sensores;
import persistencia.GestorBinario;
import persistencia.GestorTexto;
import persistencia.GestorXML;

public class MotorSimulacion {

    private GestorTexto bitacora;
    private GestorBinario telemetria;
    private GestorXML instalaciones;

    public MotorSimulacion(GestorTexto bitacora, GestorBinario telemetria, GestorXML instalaciones) {
        this.bitacora = bitacora;
        this.telemetria = telemetria;
        this.instalaciones = instalaciones;
    }

    public void ejecutarCiclo() {
        instalaciones.guardarInstalacion("Autocaravana", 120, true);
        bitacora.registrarOperacion("Infraestructura inicializada y configuracion XML generada desde el motor.");

        Sensores lecturaNormal = new Sensores(85.5f, 100, 12.4);
        Sensores lecturaCritica = new Sensores(105.2f, 150, 11.8);

        telemetria.guardarTelemetria(lecturaNormal);
        bitacora.registrarOperacion("Datos de telemetria base procesados.");

        telemetria.guardarTelemetria(lecturaCritica);
        bitacora.registrarOperacion("Anomalia termica detectada y registrada en binario.");

        try {
            evaluarSeguridad(lecturaCritica.getTemperaturaActual());
        } catch (CatastrofeQuimicaException e) {
            bitacora.registrarOperacion("ALERTA DEL MOTOR: " + e.getMessage());
            System.err.println("Ejecutando protocolos de emergencia en el nucleo. " + e.getMessage());
        }
    }

    private void evaluarSeguridad(float temperatura) throws CatastrofeQuimicaException {
        if (temperatura > 100.0f) {
            throw new CatastrofeQuimicaException("Nivel de temperatura superior a la tolerancia del reactor.");
        }
    }
}