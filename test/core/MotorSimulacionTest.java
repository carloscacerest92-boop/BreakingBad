package core;

import excepciones.CatastrofeQuimicaException;
import org.junit.jupiter.api.Test;
import persistencia.GestorBinario;
import persistencia.GestorTexto;
import persistencia.GestorXML;

import static org.junit.jupiter.api.Assertions.*;

public class MotorSimulacionTest {

    @Test
    void evaluarSeguridad_NivelTolerable_NoLanzaExcepcion() {
        MotorSimulacion motor = new MotorSimulacion(new GestorTexto(), new GestorBinario(), new GestorXML());

        assertDoesNotThrow(() -> motor.evaluarSeguridad(85.0f));
    }

    @Test
    void evaluarSeguridad_NivelCritico_LanzaExcepcion() {
        MotorSimulacion motor = new MotorSimulacion(new GestorTexto(), new GestorBinario(), new GestorXML());

        assertThrows(CatastrofeQuimicaException.class, () -> motor.evaluarSeguridad(105.0f));
    }
}