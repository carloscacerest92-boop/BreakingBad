package main;

import modelo.Sensores;
import persistencia.GestorBinario;
import persistencia.GestorTexto;
import java.util.List;

public class MainAnalizador {

    public static void main(String[] args) {
        GestorTexto bitacora = new GestorTexto();
        GestorBinario telemetria = new GestorBinario();

        System.out.println("--- EXTRACCIÓN DE LA BITÁCORA ---");
        List<String> registros = bitacora.leerBitacoraCompleta();
        for (String linea : registros) {
            System.out.println(linea);
        }

        System.out.println("\n--- AUDITORÍA DE TELEMETRÍA BINARIA ---");
        Sensores sensorCritico = telemetria.leerRegistro(1);

        if (sensorCritico != null) {
            System.out.println("Datos del sector crítico recuperados mediante salto de memoria:");
            System.out.println("Temperatura del núcleo: " + sensorCritico.getTemperaturaActual() + " ºC");
            System.out.println("Presión interna: " + sensorCritico.getPresionActual() + " PSI");
            System.out.println("Voltaje perimetral: " + sensorCritico.getVoltajeCerco() + " V");
        }
    }
}