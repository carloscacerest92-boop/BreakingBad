package modelo;

public class Sensores {

    private float temperaturaActual;
    private int presionActual;
    private double voltajeCerco;

    public Sensores(float temperaturaActual, int presionActual, double voltajeCerco) {
        this.temperaturaActual = temperaturaActual;
        this.presionActual = presionActual;
        this.voltajeCerco = voltajeCerco;
    }

    public float getTemperaturaActual() {
        return temperaturaActual;
    }

    public int getPresionActual() {
        return presionActual;
    }

    public double getVoltajeCerco() {
        return voltajeCerco;
    }
}