
package modelo.dao;

import configuracion.Conexion;
import modelo.clases.Proyecto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

    public boolean crear(Proyecto proyecto) throws SQLException {

        String sql = "INSERT INTO proyectos "
                + "(nombre, descripcion, fecha_inicio, fecha_fin, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, proyecto.getNombre());
            ps.setString(2, proyecto.getDescripcion());
            ps.setDate(3, Date.valueOf(proyecto.getFechaInicio()));

            if (proyecto.getFechaFin() == null) {
                ps.setNull(4, java.sql.Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(proyecto.getFechaFin()));
            }

            ps.setString(5, proyecto.getEstado());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Proyecto> listar() throws SQLException {

        List<Proyecto> proyectos = new ArrayList<>();

        String sql = "SELECT id, nombre, descripcion, fecha_inicio, "
                + "fecha_fin, estado FROM proyectos ORDER BY id";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                proyectos.add(convertirProyecto(rs));
            }
        }

        return proyectos;
    }

    public Proyecto buscarPorId(int id) throws SQLException {

        String sql = "SELECT id, nombre, descripcion, fecha_inicio, "
                + "fecha_fin, estado FROM proyectos WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return convertirProyecto(rs);
                }
            }
        }

        return null;
    }

    public boolean actualizar(Proyecto proyecto) throws SQLException {

        String sql = "UPDATE proyectos SET nombre = ?, descripcion = ?, "
                + "fecha_inicio = ?, fecha_fin = ?, estado = ? "
                + "WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, proyecto.getNombre());
            ps.setString(2, proyecto.getDescripcion());
            ps.setDate(3, Date.valueOf(proyecto.getFechaInicio()));

            if (proyecto.getFechaFin() == null) {
                ps.setNull(4, java.sql.Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(proyecto.getFechaFin()));
            }

            ps.setString(5, proyecto.getEstado());
            ps.setInt(6, proyecto.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean tieneAsignaciones(int proyectoId) throws SQLException {

        String sql = "SELECT 1 FROM empleado_proyecto "
                + "WHERE proyecto_id = ? LIMIT 1";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, proyectoId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean eliminar(int id) throws SQLException {

        if (tieneAsignaciones(id)) {
            throw new SQLException(
                    "No se puede eliminar un proyecto que tiene asignaciones."
            );
        }

        String sql = "DELETE FROM proyectos WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    private Proyecto convertirProyecto(ResultSet rs) throws SQLException {

        Date fechaInicio = rs.getDate("fecha_inicio");
        Date fechaFin = rs.getDate("fecha_fin");

        return new Proyecto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                fechaInicio.toLocalDate(),
                fechaFin == null ? null : fechaFin.toLocalDate(),
                rs.getString("estado")
        );
    }
}
