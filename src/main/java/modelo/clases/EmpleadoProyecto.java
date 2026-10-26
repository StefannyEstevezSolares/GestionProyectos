


package modelo.clases;
import java.time.LocalDate;

public class EmpleadoProyecto {

    private int empleadoId;
    private int proyectoId;
    private String rol;
    private LocalDate fechaAsignacion;
    private int horasSemanales;

    public EmpleadoProyecto(int empleadoId, int proyectoId,
            String rol, LocalDate fechaAsignacion, int horasSemanales) {

        this.empleadoId = empleadoId;
        this.proyectoId = proyectoId;
        this.rol = rol;
        this.fechaAsignacion = fechaAsignacion;
        this.horasSemanales = horasSemanales;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    @Override
    public String toString() {
        return "EmpleadoProyecto{empleadoId=" + empleadoId
                + ", proyectoId=" + proyectoId
                + ", rol='" + rol + '\''
                + ", fechaAsignacion=" + fechaAsignacion
                + ", horasSemanales=" + horasSemanales
                + '}';
    }
}

