public class Pruebas {
    private static int fallos = 0;

    private static void verificar(String nombre, boolean condicion) {
        System.out.println((condicion ? "OK     " : "FALLO  ") + nombre);
        if (!condicion) {
            fallos++;
        }
    }

    private static Inventario base() {
        Inventario inv = new Inventario(2);
        inv.agregarSinOrden(new Equipo("D-40", "Equipo D", "Disponible"));
        inv.agregarSinOrden(new Equipo("B-20", "Equipo B", "En uso"));
        inv.agregarSinOrden(new Equipo("E-50", "Equipo E", "Mantenimiento"));
        inv.agregarSinOrden(new Equipo("A-10", "Equipo A", "Disponible"));
        inv.agregarSinOrden(new Equipo("C-30", "Equipo C", "En uso"));
        return inv;
    }

    public static void main(String[] args) {
        Inventario inv = base();
        verificar("lineal encuentra existente", inv.busquedaLineal("E-50") == 2);
        verificar("lineal cuenta comparaciones", inv.getComparaciones() == 3);
        verificar("lineal no encuentra inexistente", inv.busquedaLineal("Z-99") == -1);
        verificar("lineal recorre todo el arreglo", inv.getComparaciones() == 5);
        verificar("antes de ordenar no esta ordenado", !inv.estaOrdenado());

        boolean excepcion = false;
        try {
            inv.busquedaBinaria("A-10");
        } catch (IllegalStateException e) {
            excepcion = true;
        }
        verificar("binaria rechaza datos desordenados", excepcion);

        inv.ordenarPorNumeroSerie();
        verificar("ordenamiento correcto", inv.estaOrdenado());
        verificar("primer elemento ordenado", inv.obtener(0).getNumeroSerie().equals("A-10"));
        verificar("ultimo elemento ordenado", inv.obtener(4).getNumeroSerie().equals("E-50"));

        verificar("binaria encuentra el centro", inv.busquedaBinaria("C-30") == 2);
        verificar("binaria encuentra el primero", inv.busquedaBinaria("A-10") == 0);
        verificar("binaria encuentra el ultimo", inv.busquedaBinaria("E-50") == 4);
        verificar("binaria inexistente es negativo", inv.busquedaBinaria("Z-99") < 0);

        verificar("punto de insercion intermedio", inv.puntoInsercion("B-25") == 2);
        verificar("punto de insercion al inicio", inv.puntoInsercion("0-00") == 0);
        verificar("punto de insercion al final", inv.puntoInsercion("Z-99") == 5);
        verificar("punto de insercion de existente", inv.puntoInsercion("B-20") == -1);

        verificar("insertar intermedio", inv.insertarOrdenado(new Equipo("B-25", "Nuevo", "Disponible")));
        verificar("orden tras insertar", inv.estaOrdenado() && inv.getCantidad() == 6);
        verificar("posicion del insertado", inv.busquedaBinaria("B-25") == 2);
        verificar("insertar al inicio", inv.insertarOrdenado(new Equipo("0-00", "Inicio", "Disponible")));
        verificar("insertar al final", inv.insertarOrdenado(new Equipo("Z-99", "Final", "Disponible")));
        verificar("orden tras varias inserciones", inv.estaOrdenado() && inv.getCantidad() == 8);
        verificar("rechaza duplicado", !inv.insertarOrdenado(new Equipo("A-10", "Dup", "Disponible")));
        verificar("cantidad intacta tras duplicado", inv.getCantidad() == 8);

        Inventario vacio = new Inventario(1);
        verificar("binaria en inventario vacio", vacio.busquedaBinaria("X") == -1);
        verificar("insertar en vacio", vacio.insertarOrdenado(new Equipo("X", "Unico", "Disponible")) && vacio.getCantidad() == 1);

        System.out.println(fallos == 0 ? "Todas las pruebas pasaron" : "Pruebas fallidas: " + fallos);
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
