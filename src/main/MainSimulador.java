package main;

import excepciones.CatastrofeQuimicaException;
import modelo.Sensores;
import persistencia.GestorBinario;
import persistencia.GestorTexto;
import persistencia.GestorXML;

public class MainSimulador {

    public static void main(String[] args) {
        GestorTexto bitacora = new GestorTexto();
        GestorBinario telemetria = new GestorBinario();
        GestorXML instalaciones = new GestorXML();

        instalaciones.guardarInstalacion("Autocaravana", 120, true);
        bitacora.registrarOperacion("Infraestructura inicializada y configuracion XML exportada.");

        Sensores lecturaNormal = new Sensores(85.5f, 100, 12.4);
        Sensores lecturaCritica = new Sensores(105.2f, 150, 11.8);

        telemetria.guardarTelemetria(lecturaNormal);
        bitacora.registrarOperacion("Datos de telemetria base registrados en el archivo binario.");

        telemetria.guardarTelemetria(lecturaCritica);
        bitacora.registrarOperacion("Anomalia termica registrada en memoria binaria.");

        try {
            evaluarSeguridad(lecturaCritica.getTemperaturaActual());
        } catch (CatastrofeQuimicaException e) {
            bitacora.registrarOperacion("ALERTA DEL SISTEMA: " + e.getMessage());
            System.err.println("Ejecutando protocolos de emergencia. " + e.getMessage());
        }

        System.out.println("Ciclo de simulacion completado. Analizando volcado de datos en el disco.");
    }

    private static void evaluarSeguridad(float temperatura) throws CatastrofeQuimicaException {
        if (temperatura > 100.0f) {
            throw new CatastrofeQuimicaException("Nivel de temperatura superior a la tolerancia del reactor.");
        }
    }
}