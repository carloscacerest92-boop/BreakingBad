package main;

import core.MotorSimulacion;
import persistencia.GestorBinario;
import persistencia.GestorTexto;
import persistencia.GestorXML;

public class MainSimulador {

    public static void main(String[] args) {
        GestorTexto bitacora = new GestorTexto();
        GestorBinario telemetria = new GestorBinario();
        GestorXML instalaciones = new GestorXML();

        MotorSimulacion motor = new MotorSimulacion(bitacora, telemetria, instalaciones);
        motor.ejecutarCiclo();

        System.out.println("Ciclo de simulacion delegado y completado. Analizando volcado de datos en el disco.");
    }
}