# Gestion de Proyectos

Sistema de consola desarrollado en Java para gestionar empleados, proyectos y sus asignaciones, utilizando MySQL como base de datos.

## Instalacion y configuracion

### 1. Crear la base de datos

1. Abre el archivo `base_datos.sql` en Visual Studio Code.
2. Copia todo su contenido.
3. Abre MySQL Workbench y conectate a tu servidor.
4. Crea una nueva pestaña SQL.
5. Pega el contenido del archivo.
6. Ejecuta el script con el boton del rayo.

Esto creara la base de datos `gestion_proyectos` y sus tablas.

### 2. Configurar la conexion

Abre el archivo `Conexion.java`, ubicado en el paquete `configuracion`.

Busca las siguientes variables y reemplaza sus valores con los de tu instalacion de MySQL:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/gestion_proyectos";
private static final String USUARIO = "root";
private static final String CLAVE = "TU_CONTRASEÑA";
```

Cambia `root` si utilizas otro usuario de MySQL y reemplaza `TU_CONTRASEÑA` por tu contraseña real.


### 3. Ejecutar el proyecto

1. Abre el proyecto en NetBeans.
2. Espera a que Maven descargue las dependencias.
3. Verifica que MySQL este iniciado y que la conexion este configurada.
4. Ejecuta la clase `Main.java`, ubicada en el paquete `vista`.

## Funcionalidades

- Registrar y listar empleados.
- Registrar y listar proyectos.
- Asignar empleados a proyectos.
- Consultar empleados de un proyecto.
- Consultar proyectos de un empleado.
- Modificar asignaciones.
- Eliminar asignaciones.


