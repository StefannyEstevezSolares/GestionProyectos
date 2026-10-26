
package controlador;

import modelo.clases.Proyecto;
import modelo.dao.ProyectoDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class ProyectoControlador {

    private final ProyectoDAO dao = new ProyectoDAO();

    public List<Proyecto> listar() throws SQLException {
        return dao.listar();
    }

    public Proyecto buscar(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser positivo"
            );
        }

        return dao.buscarPorId(id);
    }

    public boolean crear(String nombre, String descripcion,
            LocalDate fechaInicio, LocalDate fechaFin, String estado)
            throws SQLException {

        validar(nombre, fechaInicio, fechaFin, estado);

        Proyecto proyecto = new Proyecto(
                nombre.trim(),
                descripcion == null ? "" : descripcion.trim(),
                fechaInicio,
                fechaFin,
                estado.trim().toUpperCase(Locale.ROOT)
        );

        return dao.crear(proyecto);
    }

    public boolean actualizar(int id, String nombre,
            String descripcion, LocalDate fechaInicio,
            LocalDate fechaFin, String estado) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser positivo"
            );
        }

        validar(nombre, fechaInicio, fechaFin, estado);

        Proyecto proyecto = new Proyecto(
                id,
                nombre.trim(),
                descripcion == null ? "" : descripcion.trim(),
                fechaInicio,
                fechaFin,
                estado.trim().toUpperCase(Locale.ROOT)
        );

        return dao.actualizar(proyecto);
    }

    public boolean eliminar(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El id debe ser positivo"
            );
        }

        return dao.eliminar(id);
    }

    private void validar(String nombre, LocalDate fechaInicio,
            LocalDate fechaFin, String estado) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del proyecto es obligatorio"
            );
        }

        if (fechaInicio == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio es obligatoria"
            );
        }

        if (fechaFin != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException(
                    "La fecha final no puede ser anterior a la inicial"
            );
        }

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException(
                    "El estado es obligatorio"
            );
        }

        String estadoNormalizado =
                estado.trim().toUpperCase(Locale.ROOT);

        if (!estadoNormalizado.equals("PLANIFICADO")
                && !estadoNormalizado.equals("EN_CURSO")
                && !estadoNormalizado.equals("FINALIZADO")) {

            throw new IllegalArgumentException(
                    "Estado inválido. Usa PLANIFICADO, EN_CURSO "
                    + "o FINALIZADO."
            );
        }
    }
}
