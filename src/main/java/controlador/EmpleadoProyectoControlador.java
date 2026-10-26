
package controlador;

import modelo.clases.Empleado;
import modelo.clases.Proyecto;
import modelo.clases.EmpleadoProyecto;
import modelo.dao.EmpleadoProyectoDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmpleadoProyectoControlador {

    private final EmpleadoProyectoDAO dao = new EmpleadoProyectoDAO();

    public boolean asignar(int empleadoId, int proyectoId,
            String rol, LocalDate fecha, int horas) throws SQLException {

        if (empleadoId <= 0 || proyectoId <= 0) {
            throw new IllegalArgumentException("Los IDs deben ser positivos.");
        }

        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }

        if (horas < 1 || horas > 40) {
            throw new IllegalArgumentException("Las horas deben estar entre 1 y 40.");
        }

        if (dao.existeAsignacion(empleadoId, proyectoId)) {
            throw new IllegalArgumentException("La asignación ya existe.");
        }

        EmpleadoProyecto asignacion = new EmpleadoProyecto(
                empleadoId, proyectoId, rol.trim(), fecha, horas
        );

        return dao.asignar(asignacion);
    }

    public List<Empleado> listarEmpleadosPorProyecto(int proyectoId)
            throws SQLException {
        return dao.listarEmpleadosPorProyecto(proyectoId);
    }

    public List<Proyecto> listarProyectosPorEmpleado(int empleadoId)
            throws SQLException {
        return dao.listarProyectosPorEmpleado(empleadoId);
    }

    public boolean actualizarAsignacion(int empleadoId, int proyectoId,
            String rol, int horas) throws SQLException {

        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }

        if (horas < 1 || horas > 40) {
            throw new IllegalArgumentException("Las horas deben estar entre 1 y 40.");
        }

        return dao.actualizarAsignacion(
                empleadoId, proyectoId, rol.trim(), horas
        );
    }

    public boolean eliminarAsignacion(int empleadoId, int proyectoId)
            throws SQLException {
        return dao.eliminarAsignacion(empleadoId, proyectoId);
    }
}
