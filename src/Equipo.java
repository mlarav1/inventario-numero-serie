public class Equipo {
    private final String numeroSerie;
    private final String descripcion;
    private final String estado;

    public Equipo(String numeroSerie, String descripcion, String estado) {
        this.numeroSerie = numeroSerie;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "[Serie: " + numeroSerie + " | Descripcion: " + descripcion + " | Estado: " + estado + "]";
    }
}
