import java.util.Scanner;

public class Main {

    private static Inventario cargarDatosIniciales() {
        Inventario inv = new Inventario(10);
        inv.agregarSinOrden(new Equipo("SN-4050", "Portatil Lenovo ThinkPad", "Disponible"));
        inv.agregarSinOrden(new Equipo("SN-1020", "Proyector Epson", "En uso"));
        inv.agregarSinOrden(new Equipo("SN-7788", "Impresora HP LaserJet", "Mantenimiento"));
        inv.agregarSinOrden(new Equipo("SN-3001", "Monitor Dell 24 pulgadas", "Disponible"));
        inv.agregarSinOrden(new Equipo("SN-9100", "Router Cisco", "En uso"));
        inv.agregarSinOrden(new Equipo("SN-2500", "Switch TP-Link", "Disponible"));
        return inv;
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== INVENTARIO POR NUMERO DE SERIE =====");
        System.out.println("1. Mostrar inventario");
        System.out.println("2. Buscar numero de serie (busqueda lineal)");
        System.out.println("3. Ordenar equipos por numero de serie");
        System.out.println("4. Buscar numero de serie (busqueda binaria)");
        System.out.println("5. Consultar punto de insercion");
        System.out.println("6. Insertar nuevo equipo manteniendo el orden");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
    }

    private static void reportarBusqueda(Inventario inv, int posicion) {
        if (posicion >= 0) {
            System.out.println("Encontrado en la posicion " + posicion + ": " + inv.obtener(posicion));
        } else {
            System.out.println("El numero de serie no existe en el inventario.");
        }
        System.out.println("Comparaciones realizadas: " + inv.getComparaciones());
    }

    private static void consultarPunto(Inventario inv, String serie) {
        int resultado = inv.busquedaBinaria(serie);
        if (resultado >= 0) {
            System.out.println("El numero de serie ya existe en la posicion " + resultado + ", no requiere insercion.");
        } else {
            System.out.println("No existe. Punto de insercion: " + (-resultado - 1));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventario inv = cargarDatosIniciales();
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            String linea = sc.nextLine().trim();
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                opcion = -1;
            }
            switch (opcion) {
                case 1:
                    inv.mostrar();
                    System.out.println("Ordenado: " + (inv.estaOrdenado() ? "si" : "no"));
                    break;
                case 2:
                    System.out.print("Numero de serie: ");
                    reportarBusqueda(inv, inv.busquedaLineal(sc.nextLine().trim()));
                    break;
                case 3:
                    inv.ordenarPorNumeroSerie();
                    System.out.println("Inventario ordenado.");
                    inv.mostrar();
                    break;
                case 4:
                    if (!inv.estaOrdenado()) {
                        System.out.println("Debe ordenar el inventario antes de usar la busqueda binaria (opcion 3).");
                        break;
                    }
                    System.out.print("Numero de serie: ");
                    reportarBusqueda(inv, inv.busquedaBinaria(sc.nextLine().trim()));
                    break;
                case 5:
                    if (!inv.estaOrdenado()) {
                        System.out.println("Debe ordenar el inventario antes (opcion 3).");
                        break;
                    }
                    System.out.print("Numero de serie: ");
                    consultarPunto(inv, sc.nextLine().trim());
                    break;
                case 6:
                    if (!inv.estaOrdenado()) {
                        System.out.println("Debe ordenar el inventario antes (opcion 3).");
                        break;
                    }
                    System.out.print("Numero de serie: ");
                    String serie = sc.nextLine().trim();
                    System.out.print("Descripcion: ");
                    String descripcion = sc.nextLine().trim();
                    System.out.print("Estado: ");
                    String estado = sc.nextLine().trim();
                    if (serie.isEmpty()) {
                        System.out.println("El numero de serie no puede estar vacio.");
                    } else if (inv.insertarOrdenado(new Equipo(serie, descripcion, estado))) {
                        System.out.println("Equipo insertado manteniendo el orden.");
                        inv.mostrar();
                    } else {
                        System.out.println("Ya existe un equipo con ese numero de serie.");
                    }
                    break;
                case 0:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        sc.close();
    }
}
