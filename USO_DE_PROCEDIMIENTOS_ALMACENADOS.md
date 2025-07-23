====================================================================
DOCUMENTO: USO DE PROCEDIMIENTOS ALMACENADOS EN EL PROYECTO
Sistema de Gestión de Colas SAT
====================================================================

Este documento detalla dónde se utilizan los procedimientos almacenados (PA) 
en el proyecto Java del Sistema de Gestión de Colas SAT.

====================================================================
1. GESTIÓN DE CLIENTES
====================================================================

• PA_CRUD_ListarCliente
  - Ubicación: Archivos de gestión de clientes
  - Función: Listar todos los clientes del sistema

• PA_CRUD_ListarClienteConFiltro
  - Ubicación: Archivos de gestión de clientes
  - Función: Buscar clientes con filtros de texto

• PA_CRUD_InsertarCliente
  - Ubicación: Archivos de gestión de clientes
  - Función: Registrar nuevos clientes en el sistema

• PA_CRUD_ModificarCliente
  - Ubicación: Archivos de gestión de clientes
  - Función: Actualizar datos de clientes existentes

• PA_CRUD_EliminarCliente
  - Ubicación: Archivos de gestión de clientes
  - Función: Eliminar clientes del sistema

====================================================================
2. GESTIÓN DE SERVICIOS
====================================================================

• PA_CRUD_ListarServicio
  - Ubicación: C02_VentanaCRUDTickets.java (método cargarServiciosEnCombo)
  - Función: Cargar servicios activos en combobox de filtros

• PA_CRUD_ListarServicioConFiltro
  - Ubicación: D05_VentanaCRUDServicios.java
  - Función: Listar servicios con filtros de estado y texto

• PA_CRUD_InsertarServicio
  - Ubicación: D05_VentanaCRUDServicios.java
  - Función: Crear nuevos servicios en el catálogo

• PA_CRUD_ModificarServicio
  - Ubicación: D05_VentanaCRUDServicios.java
  - Función: Actualizar servicios existentes con validación de tickets pendientes

• PA_CRUD_EliminarServicio
  - Ubicación: D05_VentanaCRUDServicios.java
  - Función: Eliminar servicios del catálogo

• PA_CRUD_ContarTicketsPendientes
  - Ubicación: D05_VentanaCRUDServicios.java
  - Función: Validar si un servicio tiene tickets pendientes antes de inactivarlo

====================================================================
3. GESTIÓN DE TRABAJADORES
====================================================================

• PA_CRUD_ListarTrabajador
  - Ubicación: C02_VentanaCRUDTickets.java (métodos solicitarTrabajadorParaAdmin, 
    verificarTrabajadorExiste, obtenerIdTrabajadorDelUsuario)
  - Función: Listar trabajadores activos para asignación de tickets

• PA_CRUD_ListarTrabajadorConFiltro
  - Ubicación: Archivos de gestión de trabajadores
  - Función: Buscar trabajadores con filtros de estado y tipo

• PA_CRUD_InsertarTrabajador
  - Ubicación: Archivos de gestión de trabajadores
  - Función: Registrar nuevos trabajadores en el sistema

• PA_CRUD_ModificarTrabajador
  - Ubicación: Archivos de gestión de trabajadores
  - Función: Actualizar datos de trabajadores existentes

• PA_CRUD_EliminarTrabajador
  - Ubicación: Archivos de gestión de trabajadores
  - Función: Eliminar trabajadores del sistema

====================================================================
4. GESTIÓN DE TICKETS
====================================================================

• PA_CRUD_ListarTicket
  - Ubicación: C02_VentanaCRUDTickets.java (método cargarDatos)
  - Función: Cargar todos los tickets en la tabla principal

• PA_CRUD_ListarTicketConFiltro
  - Ubicación: Archivos de gestión de tickets
  - Función: Filtrar tickets por estado y otros criterios

• PA_RegistrarTicket
  - Ubicación: C02_VentanaCRUDTickets.java (método crearTicket)
  - Función: Crear nuevos tickets de atención

• PA_CRUD_AtenderTicket
  - Ubicación: C02_VentanaCRUDTickets.java (método atenderTicket)
  - Función: Procesar la atención de tickets pendientes

• PA_CRUD_EliminarTicket
  - Ubicación: C02_VentanaCRUDTickets.java (método eliminarTicket)
  - Función: Eliminar tickets pendientes (con validación de estado)

• PA_CRUD_ModificarTicket
  - Ubicación: Archivos de gestión de tickets
  - Función: Actualizar datos de tickets existentes

====================================================================
5. HISTORIAL Y CONSULTAS ESPECIALES
====================================================================

• PA_CRUD_ListarHistorialTicketCliente
  - Ubicación: C01_VentanaCrearTicket.java (método cargarHistorialCliente)
  - Función: Mostrar historial completo de tickets de un cliente específico

====================================================================
6. PROCEDIMIENTOS DE DEBUGGING Y DIAGNÓSTICO
====================================================================

• PA_DEBUG_ListarTicketsDetallado
  - Ubicación: Usado para diagnóstico y reportes completos
  - Función: Mostrar información detallada de todos los tickets

• PA_DEBUG_ListarTicketsFiltrado
  - Ubicación: D06_VentanaGenerarReporte.java
  - Función: Generar reportes de tickets por rango de fechas

• PA_DEBUG_VerificarTicket
  - Ubicación: Usado para debugging de tickets específicos
  - Función: Verificar estado y datos de un ticket particular

• PA_DEBUG_VerificarTrabajador
  - Ubicación: Usado para debugging de trabajadores
  - Función: Verificar estado y datos de un trabajador específico

• PA_DEBUG_VerificarTicketsServicio
  - Ubicación: D05_VentanaCRUDServicios.java
  - Función: Verificar tickets asociados a un servicio específico

====================================================================
7. ARCHIVOS PRINCIPALES QUE USAN PROCEDIMIENTOS ALMACENADOS
====================================================================

• C01_VentanaCrearTicket.java
  - PA_CRUD_ListarHistorialTicketCliente

• C02_VentanaCRUDTickets.java
  - PA_CRUD_ListarServicio
  - PA_CRUD_ListarTicket
  - PA_RegistrarTicket
  - PA_CRUD_AtenderTicket
  - PA_CRUD_EliminarTicket
  - PA_CRUD_ListarTrabajador (múltiples usos)

• D05_VentanaCRUDServicios.java
  - PA_CRUD_ListarServicioConFiltro
  - PA_CRUD_InsertarServicio
  - PA_CRUD_ModificarServicio
  - PA_CRUD_EliminarServicio
  - PA_CRUD_ContarTicketsPendientes
  - PA_DEBUG_VerificarTicketsServicio

• D06_VentanaGenerarReporte.java
  - PA_DEBUG_ListarTicketsFiltrado

====================================================================
8. BENEFICIOS DE USAR PROCEDIMIENTOS ALMACENADOS
====================================================================

• Seguridad: Prevención de inyección SQL
• Rendimiento: Ejecución optimizada en el servidor de base de datos
• Mantenibilidad: Lógica centralizada en la base de datos
• Consistencia: Validaciones uniformes en todos los puntos de acceso
• Reutilización: Mismos procedimientos usados en múltiples ventanas
• Debugging: Procedimientos especiales para diagnóstico y verificación

====================================================================
9. ANÁLISIS DE SEGURIDAD: INYECCIÓN SQL
====================================================================

PUNTOS VULNERABLES A INYECCIÓN SQL EN EL PROYECTO:
• Ninguno detectado - El proyecto usa exclusivamente procedimientos almacenados
• Todas las consultas SQL están parametrizadas a través de CallableStatement
• No se encontraron concatenaciones directas de SQL con entrada de usuario

EJEMPLOS DE CÓMO SERÍA UNA INYECCIÓN SQL (SI EXISTIERA):

• Ejemplo 1 - Búsqueda de Cliente (VULNERABLE - NO USADO EN EL PROYECTO):
  String sql = "SELECT * FROM Cliente WHERE NomCliente = '" + nombreCliente + "'";
  
  Entrada maliciosa: "'; DROP TABLE Cliente; --"
  SQL resultante: "SELECT * FROM Cliente WHERE NomCliente = ''; DROP TABLE Cliente; --'"
  Resultado: Eliminaría toda la tabla Cliente

• Ejemplo 2 - Login de Usuario (VULNERABLE - NO USADO EN EL PROYECTO):
  String sql = "SELECT * FROM Trabajador WHERE IdTrabajador = '" + usuario + 
               "' AND Password = '" + password + "'";
  
  Entrada maliciosa usuario: "admin' OR '1'='1"
  SQL resultante: "SELECT * FROM Trabajador WHERE IdTrabajador = 'admin' OR '1'='1' AND Password = '...'"
  Resultado: Bypass de autenticación

• Ejemplo 3 - Filtro de Tickets (VULNERABLE - NO USADO EN EL PROYECTO):
  String sql = "SELECT * FROM Ticket WHERE FechaTicket >= '" + fechaDesde + "'";
  
  Entrada maliciosa: "2025-01-01'; UPDATE Ticket SET EstadoTicket = 0; --"
  SQL resultante: "SELECT * FROM Ticket WHERE FechaTicket >= '2025-01-01'; UPDATE Ticket SET EstadoTicket = 0; --'"
  Resultado: Marcaría todos los tickets como atendidos

PREVENCIÓN IMPLEMENTADA EN EL PROYECTO:

• Uso de CallableStatement con parámetros:
  CallableStatement stmt = conn.prepareCall("{call PA_RegistrarTicket(?, ?, ?)}");
  stmt.setString(1, dniCliente);
  stmt.setString(2, nombreCliente);
  stmt.setString(3, codigoServicio);

• Validaciones en Java antes de enviar a la base de datos:
  - Validación de formato de DNI (solo números, 8 dígitos)
  - Validación de longitud de campos
  - Validación de tipos de datos

• Procedimientos almacenados con validaciones adicionales:
  - Verificación de existencia de registros
  - Validaciones de reglas de negocio
  - Manejo de errores controlado

RECOMENDACIONES ADICIONALES:

• Mantener siempre el uso de procedimientos almacenados
• Nunca concatenar strings para formar consultas SQL
• Validar entrada de usuario tanto en frontend como backend
• Usar herramientas de análisis de código estático
• Realizar pruebas de penetración periódicas
• Mantener actualizadas las librerías de base de datos

====================================================================
CONCLUSIÓN: El proyecto actual está BIEN PROTEGIDO contra inyección SQL
debido al uso exclusivo de procedimientos almacenados parametrizados.
====================================================================
NOTA: Este documento refleja el estado actual del proyecto.
Los procedimientos almacenados están centralizados en el archivo:
CREATE DATABASE GestionColas, con todos sus PA.sql
====================================================================
