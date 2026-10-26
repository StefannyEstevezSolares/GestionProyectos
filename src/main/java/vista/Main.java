
import controlador.EmpleadoControlador;
import controlador.ProyectoControlador;
import controlador.EmpleadoProyectoControlador;
import modelo.clases.Empleado;
import modelo.clases.Proyecto;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final EmpleadoControlador empleados =
            new EmpleadoControlador();

    private static final ProyectoControlador proyectos =
            new ProyectoControlador();

    private static final EmpleadoProyectoControlador asignaciones =
            new EmpleadoProyectoControlador();

    public static void main(String[] args) {

        int opcion = 0;

        do {
            System.out.println("\n=== GESTION DE PROYECTOS ===");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Registrar proyecto");
            System.out.println("4. Listar proyectos");
            System.out.println("5. Asignar empleado a proyecto");
            System.out.println("6. Ver empleados de un proyecto");
            System.out.println("7. Ver proyectos de un empleado");
            System.out.println("8. Modificar asignacion");
            System.out.println("9. Eliminar asignacion");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1 -> registrarEmpleado();
                    case 2 -> listarEmpleados();
                    case 3 -> registrarProyecto();
                    case 4 -> listarProyectos();
                    case 5 -> asignarEmpleado();
                    case 6 -> verEmpleadosProyecto();
                    case 7 -> verProyectosEmpleado();
                    case 8 -> modificarAsignacion();
                    case 9 -> eliminarAsignacion();
                    case 0 -> System.out.println("Programa finalizado.");
                    default -> System.out.println("Opcion no valida.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void registrarEmpleado() throws Exception {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Correo: ");
        String correo = sc.nextLine();

        System.out.print("Cargo: ");
        String cargo = sc.nextLine();

        empleados.crear(nombre, correo, cargo);
        System.out.println("Empleado registrado.");
    }

    private static void listarEmpleados() throws Exception {
        for (Empleado e : empleados.listar()) {
            System.out.println(e);
        }
    }

    private static void registrarProyecto() throws Exception {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = sc.nextLine();

        System.out.print("Fecha de inicio (AAAA-MM-DD): ");
        LocalDate inicio = LocalDate.parse(sc.nextLine());

        System.out.print("Fecha de finalizacion (AAAA-MM-DD o vacio): ");
        String fechaTexto = sc.nextLine();

        LocalDate fin = fechaTexto.isBlank()
                ? null : LocalDate.parse(fechaTexto);

        System.out.print("Estado (PLANIFICADO, EN_CURSO, FINALIZADO): ");
        String estado = sc.nextLine().toUpperCase();

        proyectos.crear(nombre, descripcion, inicio, fin, estado);
        System.out.println("Proyecto registrado.");
    }

    private static void listarProyectos() throws Exception {
        for (Proyecto p : proyectos.listar()) {
            System.out.println(p);
        }
    }

    private static void asignarEmpleado() throws Exception {
        System.out.print("ID del empleado: ");
        int empleadoId = Integer.parseInt(sc.nextLine());

        System.out.print("ID del proyecto: ");
        int proyectoId = Integer.parseInt(sc.nextLine());

        System.out.print("Rol en el proyecto: ");
        String rol = sc.nextLine();

        System.out.print("Fecha de asignacion (AAAA-MM-DD): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine());

        System.out.print("Horas semanales (1-40): ");
        int horas = Integer.parseInt(sc.nextLine());

        asignaciones.asignar(
                empleadoId, proyectoId, rol, fecha, horas
        );

        System.out.println("Empleado asignado al proyecto.");
    }

    private static void verEmpleadosProyecto() throws Exception {
        System.out.print("ID del proyecto: ");
        int id = Integer.parseInt(sc.nextLine());

        asignaciones.listarEmpleadosPorProyecto(id)
                .forEach(System.out::println);
    }

    private static void verProyectosEmpleado() throws Exception {
        System.out.print("ID del empleado: ");
        int id = Integer.parseInt(sc.nextLine());

        asignaciones.listarProyectosPorEmpleado(id)
                .forEach(System.out::println);
    }

    private static void modificarAsignacion() throws Exception {
        System.out.print("ID del empleado: ");
        int empleadoId = Integer.parseInt(sc.nextLine());

        System.out.print("ID del proyecto: ");
        int proyectoId = Integer.parseInt(sc.nextLine());

        System.out.print("Nuevo rol: ");
        String rol = sc.nextLine();

        System.out.print("Nuevas horas semanales (1-40): ");
        int horas = Integer.parseInt(sc.nextLine());

        boolean actualizado = asignaciones.actualizarAsignacion(
                empleadoId, proyectoId, rol, horas
        );

        System.out.println(actualizado
                ? "Asignacion actualizada."
                : "No se encontro la asignacion.");
    }

    private static void eliminarAsignacion() throws Exception {
        System.out.print("ID del empleado: ");
        int empleadoId = Integer.parseInt(sc.nextLine());

        System.out.print("ID del proyecto: ");
        int proyectoId = Integer.parseInt(sc.nextLine());

        boolean eliminado = asignaciones.eliminarAsignacion(
                empleadoId, proyectoId
        );

        System.out.println(eliminado
                ? "Asignacion eliminada."
                : "No se encontro la asignacion.");
    }
}
