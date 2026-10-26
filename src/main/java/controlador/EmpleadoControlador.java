
package controlador;

import modelo.clases.Empleado;
import modelo.dao.EmpleadoDAO;

import java.sql.SQLException;
import java.util.List;

public class EmpleadoControlador {

    private final EmpleadoDAO dao = new EmpleadoDAO();

    public List<Empleado> listar() throws SQLException {
        return dao.listar();
    }

    public Empleado buscar(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser positivo"
            );
        }

        return dao.buscarPorId(id);
    }

    public boolean crear(String nombre, String correo, String cargo)
            throws SQLException {

        validar(nombre, correo, cargo);

        Empleado empleado = new Empleado(
                nombre.trim(),
                correo.trim(),
                cargo.trim()
        );

        return dao.crear(empleado);
    }

    public boolean actualizar(int id, String nombre,
            String correo, String cargo) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser positivo"
            );
        }

        validar(nombre, correo, cargo);

        Empleado empleado = new Empleado(
                id,
                nombre.trim(),
                correo.trim(),
                cargo.trim()
        );

        return dao.actualizar(empleado);
    }

    public boolean eliminar(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser positivo"
            );
        }

        return dao.eliminar(id);
    }

    private void validar(String nombre, String correo, String cargo) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio"
            );
        }

        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo es obligatorio"
            );
        }

        if (!correo.trim().matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {

            throw new IllegalArgumentException(
                    "El formato del correo no es válido"
            );
        }

        if (cargo == null || cargo.isBlank()) {
            throw new IllegalArgumentException(
                    "El cargo es obligatorio"
            );
        }
    }
}
