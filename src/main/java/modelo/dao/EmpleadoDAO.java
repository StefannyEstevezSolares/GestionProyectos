
package modelo.dao;

import configuracion.Conexion;
import modelo.clases.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public boolean crear(Empleado empleado) throws SQLException {

        String sql = "INSERT INTO empleados (nombre, correo, cargo) "
                + "VALUES (?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getCorreo());
            ps.setString(3, empleado.getCargo());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Empleado> listar() throws SQLException {

        List<Empleado> empleados = new ArrayList<>();

        String sql = "SELECT id, nombre, correo, cargo "
                + "FROM empleados ORDER BY id";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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

        return empleados;
    }

    public Empleado buscarPorId(int id) throws SQLException {

        String sql = "SELECT id, nombre, correo, cargo "
                + "FROM empleados WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("cargo")
                    );
                }
            }
        }

        return null;
    }

    public boolean actualizar(Empleado empleado) throws SQLException {

        String sql = "UPDATE empleados "
                + "SET nombre = ?, correo = ?, cargo = ? "
                + "WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getCorreo());
            ps.setString(3, empleado.getCargo());
            ps.setInt(4, empleado.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean tieneAsignaciones(int empleadoId) throws SQLException {

        String sql = "SELECT 1 FROM empleado_proyecto "
                + "WHERE empleado_id = ? LIMIT 1";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, empleadoId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean eliminar(int id) throws SQLException {

        if (tieneAsignaciones(id)) {
            throw new SQLException(
                    "No se puede eliminar el empleado porque tiene asignaciones."
            );
        }

        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}

