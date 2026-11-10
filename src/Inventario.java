public class Inventario {
    private Equipo[] equipos;
    private int cantidad;
    private int comparaciones;

    public Inventario(int capacidadInicial) {
        this.equipos = new Equipo[Math.max(capacidadInicial, 1)];
        this.cantidad = 0;
        this.comparaciones = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getComparaciones() {
        return comparaciones;
    }

    public Equipo obtener(int indice) {
        return equipos[indice];
    }

    public void agregarSinOrden(Equipo equipo) {
        asegurarCapacidad();
        equipos[cantidad++] = equipo;
    }

    private void asegurarCapacidad() {
        if (cantidad == equipos.length) {
            Equipo[] nuevo = new Equipo[equipos.length * 2];
            for (int i = 0; i < cantidad; i++) {
                nuevo[i] = equipos[i];
            }
            equipos = nuevo;
        }
    }

    public int busquedaLineal(String numeroSerie) {
        comparaciones = 0;
        for (int i = 0; i < cantidad; i++) {
            comparaciones++;
            if (equipos[i].getNumeroSerie().compareTo(numeroSerie) == 0) {
                return i;
            }
        }
        return -1;
    }

    public void ordenarPorNumeroSerie() {
        for (int i = 1; i < cantidad; i++) {
            Equipo actual = equipos[i];
            int j = i - 1;
            while (j >= 0 && equipos[j].getNumeroSerie().compareTo(actual.getNumeroSerie()) > 0) {
                equipos[j + 1] = equipos[j];
                j--;
            }
            equipos[j + 1] = actual;
        }
    }

    public boolean estaOrdenado() {
        for (int i = 1; i < cantidad; i++) {
            if (equipos[i - 1].getNumeroSerie().compareTo(equipos[i].getNumeroSerie()) > 0) {
                return false;
            }
        }
        return true;
    }

    public int busquedaBinaria(String numeroSerie) {
        if (!estaOrdenado()) {
            throw new IllegalStateException("El inventario debe estar ordenado por numero de serie");
        }
        comparaciones = 0;
        int bajo = 0;
        int alto = cantidad - 1;
        while (bajo <= alto) {
            int medio = bajo + ((alto - bajo) >>> 1);
            comparaciones++;
            int c = equipos[medio].getNumeroSerie().compareTo(numeroSerie);
            if (c == 0) {
                return medio;
            }
            if (c < 0) {
                bajo = medio + 1;
            } else {
                alto = medio - 1;
            }
        }
        return -(bajo) - 1;
    }

    public void mostrar() {
        if (cantidad == 0) {
            System.out.println("El inventario esta vacio.");
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            System.out.println(i + " -> " + equipos[i]);
        }
    }
}
