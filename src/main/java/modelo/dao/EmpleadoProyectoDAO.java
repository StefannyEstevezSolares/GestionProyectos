
package modelo.dao;

import configuracion.Conexion;
import modelo.clases.Empleado;
import modelo.clases.Proyecto;
import modelo.clases.EmpleadoProyecto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoProyectoDAO {

    // 1. Asignar un empleado a un proyecto
    public boolean asignar(EmpleadoProyecto asignacion) throws SQLException {
        String sql = "INSERT INTO empleado_proyecto "
                + "(empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, asignacion.getEmpleadoId());
            ps.setInt(2, asignacion.getProyectoId());
            ps.setString(3, asignacion.getRol());
            ps.setDate(4, Date.valueOf(asignacion.getFechaAsignacion()));
            ps.setInt(5, asignacion.getHorasSemanales());

            return ps.executeUpdate() > 0;
        }
    }

    // 2. Consultar los empleados asignados a un proyecto
    public List<Empleado> listarEmpleadosPorProyecto(int proyectoId)
            throws SQLException {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT e.id, e.nombre, e.correo, e.cargo "
                + "FROM empleados e "
                + "INNER JOIN empleado_proyecto ep "
                + "ON e.id = ep.empleado_id "
                + "WHERE ep.proyecto_id = ? "
                + "ORDER BY e.nombre";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, proyectoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Empleado empleado = new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("cargo")
                    );

                    empleados.add(empleado);
                }
            }
        }

        return empleados;
    }

    // 3. Consultar los proyectos en los que participa un empleado
    public List<Proyecto> listarProyectosPorEmpleado(int empleadoId)
            throws SQLException {

        List<Proyecto> proyectos = new ArrayList<>();

        String sql = "SELECT p.id, p.nombre, p.descripcion, "
                + "p.fecha_inicio, p.fecha_fin, p.estado "
                + "FROM proyectos p "
                + "INNER JOIN empleado_proyecto ep "
                + "ON p.id = ep.proyecto_id "
                + "WHERE ep.empleado_id = ? "
                + "ORDER BY p.nombre";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, empleadoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date fechaInicioSQL = rs.getDate("fecha_inicio");
                    Date fechaFinSQL = rs.getDate("fecha_fin");

                    Proyecto proyecto = new Proyecto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            fechaInicioSQL.toLocalDate(),
                            fechaFinSQL == null
                                    ? null
                                    : fechaFinSQL.toLocalDate(),
                            rs.getString("estado")
                    );

                    proyectos.add(proyecto);
                }
            }
        }

        return proyectos;
    }

    // 4. Modificar el rol y las horas de una asignación
    public boolean actualizarAsignacion(
            int empleadoId,
            int proyectoId,
            String rol,
            int horasSemanales) throws SQLException {

        String sql = "UPDATE empleado_proyecto "
                + "SET rol = ?, horas_semanales = ? "
                + "WHERE empleado_id = ? AND proyecto_id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, rol);
            ps.setInt(2, horasSemanales);
            ps.setInt(3, empleadoId);
            ps.setInt(4, proyectoId);

            return ps.executeUpdate() > 0;
        }
    }

    // 5. Eliminar solamente la asignación
    public boolean eliminarAsignacion(int empleadoId, int proyectoId)
            throws SQLException {

        String sql = "DELETE FROM empleado_proyecto "
                + "WHERE empleado_id = ? AND proyecto_id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);

            return ps.executeUpdate() > 0;
        }
    }

    // 6. Verificar si una asignación ya existe
    public boolean existeAsignacion(int empleadoId, int proyectoId)
            throws SQLException {

        String sql = "SELECT 1 FROM empleado_proyecto "
                + "WHERE empleado_id = ? AND proyecto_id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
