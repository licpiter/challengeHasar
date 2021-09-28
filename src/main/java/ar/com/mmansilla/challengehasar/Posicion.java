package ar.com.mmansilla.challengehasar;

public class Posicion {
    private Integer posicion;
    private Integer puntaje;
    private Boolean esAlice;

    public Boolean getEsAlice() {
        return esAlice;
    }

    public void setEsAlice(Boolean esAlice) {
        this.esAlice = esAlice;
    }

    public Posicion(Integer posicion, Integer puntaje, Boolean esAlice) {
        this.posicion = posicion;
        this.puntaje = puntaje;
        this.esAlice = esAlice;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Posicion(Integer posicion, Integer puntaje) {
        this.posicion = posicion;
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Posicion[" + posicion + "] --> " + puntaje + " Alice? " + esAlice;
    }

}
