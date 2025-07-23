-- ====================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS - SISTEMA DE GESTIÓN DE COLAS SAT
-- =====================================================================
-- Fecha de creación: 2025-06-07
-- Descripción: Sistema de gestión de colas para Servicio de Administración Tributaria
-- Funcionalidades: Gestión de clientes, trabajadores, servicios y tickets de atención
-- =====================================================================

-- Crear la base de datos principal del sistema
CREATE DATABASE GestionColas
GO

-- Seleccionar la base de datos para trabajar
USE GestionColas
GO

-- =====================================================================
-- DEFINICIÓN DE TABLAS PRINCIPALES
-- =====================================================================

-- Tabla Cliente: Almacena información de los usuarios del sistema
-- Cada cliente se identifica por su DNI (8 dígitos)
CREATE TABLE Cliente(
    CodCliente VARCHAR(8) NOT NULL,    -- DNI del cliente (8 dígitos)
    NomCliente VARCHAR(50),            -- Nombre completo del cliente
    CONSTRAINT PK_Cliente PRIMARY KEY (CodCliente)
);

-- Tabla Trabajador: Almacena información del personal que atiende tickets
-- Incluye estados para control de permisos y disponibilidad
CREATE TABLE Trabajador(
    IdTrabajador VARCHAR(10) NOT NULL,     -- ID único del trabajador (formato: TR########)
    NomTrabajador VARCHAR(50),             -- Nombre completo del trabajador
    EstadoTrabajador BIT NOT NULL,         -- Estado activo/inactivo (1=Activo, 0=Inactivo)
    EstadoMaster BIT NOT NULL DEFAULT 0,   -- Permisos de administrador (1=Admin, 0=Usuario normal)
    CONSTRAINT PK_Trabajador PRIMARY KEY (IdTrabajador)
);

-- Tabla Servicio: Catálogo de servicios disponibles en el SAT
-- Permite activar/desactivar servicios según disponibilidad
CREATE TABLE Servicio(
    CodServ VARCHAR(8) NOT NULL,       -- Código único del servicio (formato: SER#####)
    NomServ VARCHAR(60) NOT NULL,      -- Nombre descriptivo del servicio
    EstadoServicio BIT NOT NULL,       -- Estado del servicio (1=Activo, 0=Inactivo)
    CONSTRAINT PK_Servicio PRIMARY KEY (CodServ)
);

-- Tabla Ticket: Registro principal de atención de servicios
-- Controla el flujo completo desde la creación hasta la atención
CREATE TABLE Ticket (
    FechaTicket DATE NOT NULL,             -- Fecha de creación del ticket
    NumTicket VARCHAR(4) NOT NULL,         -- Número secuencial del ticket (formato: ####)
    HoraEmisionTicket TIME NOT NULL,       -- Hora exacta de creación del ticket
    CodCliente VARCHAR(8) NOT NULL,        -- DNI del cliente que solicita el servicio
    CodServ VARCHAR(8) NOT NULL,           -- Código del servicio solicitado
    IdTrabajador VARCHAR(10) NULL,         -- ID del trabajador asignado (NULL hasta ser atendido)
    HoraAtencionTicket TIME NULL,          -- Hora de atención (NULL hasta ser atendido)
    EstadoTicket BIT NOT NULL DEFAULT 1,   -- Estado del ticket (1=Pendiente, 0=Atendido)
    
    -- Definición de claves primarias y foráneas
    CONSTRAINT PK_Ticket PRIMARY KEY (FechaTicket, NumTicket),  -- Clave primaria compuesta
    CONSTRAINT FK_Cliente_Ticket FOREIGN KEY (CodCliente) REFERENCES Cliente (CodCliente),
    CONSTRAINT FK_Servicio_Ticket FOREIGN KEY (CodServ) REFERENCES Servicio (CodServ),
    CONSTRAINT FK_Trabajador_Ticket FOREIGN KEY (IdTrabajador) REFERENCES Trabajador (IdTrabajador)
);

-- =====================================================================
-- DATOS DE PRUEBA - CLIENTES
-- =====================================================================
-- Inserción de 50 clientes de prueba con DNIs válidos de 8 dígitos
-- Estos datos permiten probar el sistema con información realista
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('76342098','Lucía Rodríguez');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('89432011','Carlos Mendoza');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('77219834','Ana Morales');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('88563427','Diego Silva');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('77654320','Valentina Ríos');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('81098765','Javier Núñez');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('80987654','Fernanda Gómez');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('81234567','Martín Herrera');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('79876543','Camila Vargas');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('78765432','Sofía Castro');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('76543210','Alonso Paredes');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('75432098','Daniela Quispe');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('74321087','Luis Contreras');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('73210976','Carla Jiménez');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('72109865','Miguel Torres');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('71098754','Isabela Ramírez');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('70987643','José Pacheco');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('69876532','Natalia Díaz');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('68765421','Eduardo Salas');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('67654310','Florencia Peña');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('66543219','Sebastián Meza');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('65432108','Paula Rivas');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('64321097','Ricardo Villanueva');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('63210986','Antonella Bravo');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('62109875','Andrés Gallardo');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('61098764','Julieta Araya');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('60987653','Tomás Loyola');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('59876542','Gabriela Saavedra');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('58765431','Manuel Figueroa');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('57654320','Carmen Olivares');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('56543219','Mateo Alcántara');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('55432108','Renata Esquivel');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('54321097','Bruno Cáceres');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('53210986','Elena Zamora');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('52109875','Pablo Riquelme');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('51098764','María del Mar López');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('50987653','Emilio Tapia');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('49876542','Rocío Benítez');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('48765431','Fabián Córdova');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('47654320','Patricia Huamán');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('46543219','Gonzalo Chacón');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('45432108','Martina Guevara');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('44321097','Cristóbal Ibarra');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('43210986','Laura Valverde');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('42109875','Ignacio Camargo');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('41098764','Marina Loayza');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('40987653','Raúl Cuenca');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('39876542','Teresa Maldonado');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('38765431','Samuel Peralta');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('37654320','Ariana Guevara');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('71490956','ARISTA');
INSERT INTO Cliente (CodCliente, NomCliente) VALUES ('12345678','Iberico');
GO

-- =====================================================================
-- DATOS DE PRUEBA - TRABAJADORES
-- =====================================================================
-- Inserción de 50 trabajadores con diferentes estados y roles
-- Algunos están activos, otros inactivos para probar filtros
-- Formato ID: TR######## (10 caracteres total)

-- Insertar 50 trabajadores (cada uno con INSERT individual)
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR87654321','María Herrera',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR12345678','José Ramírez',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR78451236','Ana López',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR99887766','Luis González',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR65432109','Camila Díaz',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR45678901','Pablo Salazar',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR32165498','Natalia Fuentes',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR76543210','Juan Paredes',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR11098765','Valeria Castillo',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR84930217','Gonzalo Ríos',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR77889900','Daniela Cáceres',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR21436587','Tomás Mejía',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR13245768','Lucía Zambrano',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR78945612','Carlos Medina',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR31415926','Florencia Vega',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR91234567','Emilio Bravo',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR12039847','Julieta Molina',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR45612378','Martín Aguilar',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR22334455','Antonia Guzmán',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR66778899','Ignacio Córdova',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR33445566','Patricia Navarro',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR90817263','Esteban Arce',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR19283746','Renata Farfán',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR29384756','Santiago Escobar',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR56473829','Marina Olivos',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR83746592','Felipe Galindo',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR47586920','Paola Rivera',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR91827364','Damián Tello',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR01928374','Gabriela Lazo',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR14523678','Álvaro Quiroz',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR65498712','Romina Sosa',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR31928374','Benjamín Acevedo',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR99881234','Ariana Beltrán',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR11223344','Cristian Rosales',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR44556677','Flor Del Valle',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR33221144','Marcos Linares',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR90909090','Silvana Herrera',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR12349876','Nicolás Ponce',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR90123456','Camilo Bustamante',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR56781234','Tatiana Miranda',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR34561234','Julio Salcedo',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR78901234','Viviana Rey',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR11112222','Elías Ordóñez',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR87651234','Karina Montalvo',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR56473829','Hugo Merino',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR09871234','Micaela Torres',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR24681357','Félix Cornejo',1);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR10293847','Lorena Suárez',0);
INSERT INTO Trabajador (IdTrabajador, NomTrabajador, EstadoTrabajador) VALUES ('TR38475629','Joaquín Tejada',1);

-- =====================================================================
-- DATOS DE PRUEBA - SERVICIOS SAT
-- =====================================================================
-- Catálogo de 27 servicios típicos del Servicio de Administración Tributaria
-- Incluye servicios de caja, registro, multas, fiscalización, etc.
-- Algunos activos y otros inactivos para probar estados del sistema
-- Formato código: SER##### (8 caracteres total)
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00001','Caja',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00002','Mesa de Partes',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00003','Registro',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00004','Multas de Tránsito',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00005','Mintra',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00006','Coactivo',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00007','Gerencia de Registro, Cobranza Ordinaria y Fiscalización',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00008','Cobranza Ordinaria',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00009','Fiscalización',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00010','Asesoría Jurídica',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00011','Otras Áreas',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00012','Caja',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00013','Mesa de Partes',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00014','Registro',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00015','Multas de Tránsito',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00016','Mintra',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00017','Coactivo',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00018','Gerencia de Registro, Cobranza Ordinaria y Fiscalización',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00019','Cobranza Ordinaria',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00020','Fiscalización',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00021','Asesoría Jurídica',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00022','Otras Áreas',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00023','Caja',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00024','Mesa de Partes',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00025','Registro',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00026','Multas de Tránsito',1);
INSERT INTO Servicio (CodServ, NomServ, EstadoServicio) VALUES ('SER00027','Mintra',1);

-- =====================================================================
-- DATOS DE PRUEBA - TICKETS DE ATENCIÓN
-- =====================================================================
-- 100+ tickets de prueba que incluyen:
-- - Tickets históricos ya atendidos (con trabajador y hora de atención)
-- - Tickets pendientes para pruebas de atención en tiempo real
-- - Tickets del usuario de prueba "Iberico" (DNI: 12345678)
-- Formato fecha: DD/MM/YYYY | Números de ticket secuenciales por día
SET DATEFORMAT DMY
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('24/05/2010','0345','08:12','76342098','SER00001','TR87654321','08:25',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('18/11/2003','0182','09:45','89432011','SER00002','TR12345678','10:00',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('09/08/2020','0049','07:30','77219834','SER00003','TR78451236','07:50',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('15/04/2013','0023','10:05','88563427','SER00004','TR99887766','10:20',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('30/07/2007','0487','11:15','77654320','SER00005','TR65432109','11:30',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('06/12/2011','0114','12:00','81098765','SER00006','TR45678901','12:15',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('21/01/2017','0042','13:42','80987654','SER00007','TR32165498','13:57',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('04/10/2022','0105','14:20','81234567','SER00008','TR76543210','14:35',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('29/02/2000','0250','15:10','79876543','SER00009','TR11098765','15:25',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('10/06/2024','0093','08:50','78765432','SER00010','TR84930217','09:05',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('13/03/2009','0471','09:25','76543210','SER00011','TR77889900','09:40',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('11/05/2002','0408','10:40','75432098','SER00012','TR21436587','10:55',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('16/08/2016','0430','11:30','74321087','SER00013','TR13245768','11:45',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('28/09/2018','0302','07:45','73210976','SER00014','TR78945612','08:00',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('05/01/2025','0455','13:05','72109865','SER00015','TR31415926','13:20',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('03/07/2014','0466','14:35','71098754','SER00016','TR91234567','14:50',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('27/03/2021','0228','16:10','70987643','SER00017','TR12039847','16:25',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('08/11/2006','0312','17:20','69876532','SER00018','TR45612378','17:35',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('12/02/2015','0190','07:55','68765421','SER00019','TR22334455','08:10',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('23/04/2004','0484','15:50','67654310','SER00020','TR66778899','16:05',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('17/09/2019','0127','08:30','66543219','SER00021','TR33445566','08:45',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('22/07/2008','0366','09:15','65432108','SER00022','TR90817263','09:30',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('14/12/2012','0078','10:25','64321097','SER00023','TR19283746','10:40',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('19/05/2001','0419','11:50','63210986','SER00024','TR29384756','12:05',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('25/10/2023','0056','13:15','62109875','SER00025','TR56473829','13:30',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('07/03/2005','0293','14:40','61098764','SER00026','TR83746592','14:55',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('01/06/2018','0174','15:55','60987653','SER00027','TR47586920','16:10',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('26/08/2011','0391','08:05','59876542','SER00001','TR91827364','08:20',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('09/11/2009','0248','09:20','58765431','SER00002','TR01928374','09:35',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('20/02/2017','0337','10:35','57654320','SER00003','TR14523678','10:50',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('05/04/2002','0169','11:45','56543219','SER00004','TR65498712','12:00',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('30/09/2020','0421','12:55','55432108','SER00005','TR31928374','13:10',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('12/01/2015','0083','14:05','54321097','SER00006','TR99881234','14:20',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('23/07/2003','0375','15:15','53210986','SER00007','TR11223344','15:30',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('18/12/2019','0206','16:25','52109875','SER00008','TR44556677','16:40',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('07/05/2007','0442','07:40','51098764','SER00009','TR33221144','07:55',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('28/02/2014','0138','08:50','50987653','SER00010','TR90909090','09:05',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('15/10/2010','0264','10:00','49876542','SER00011','TR12349876','10:15',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('03/08/2006','0353','11:10','48765431','SER00012','TR90123456','11:25',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('21/04/2012','0097','12:20','47654320','SER00013','TR56781234','12:35',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('06/09/2018','0380','13:30','46543219','SER00014','TR34561234','13:45',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('14/07/2004','0215','14:40','45432108','SER00015','TR78901234','14:55',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('29/11/2016','0329','15:50','44321097','SER00016','TR11112222','16:05',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('02/03/2021','0064','17:00','43210986','SER00017','TR87651234','17:15',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('11/10/2005','0401','08:15','42109875','SER00018','TR56473829','08:30',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('24/06/2013','0152','09:25','41098764','SER00019','TR09871234','09:40',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('17/01/2008','0278','10:35','40987653','SER00020','TR24681357','10:50',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('08/05/2017','0437','11:45','39876542','SER00021','TR10293847','12:00',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('22/09/2000','0146','12:55','38765431','SER00022','TR38475629','13:10',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('24/05/2011','0346','08:12','37654320','SER00023','TR87654321','08:25',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('18/11/2004','0183','09:45','76342098','SER00024','TR12345678','10:00',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('09/08/2021','0050','07:30','89432011','SER00025','TR78451236','07:50',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('15/04/2014','0024','10:05','77219834','SER00026','TR99887766','10:20',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('30/07/2008','0488','11:15','88563427','SER00027','TR65432109','11:30',0);
INSERT INTO Ticket (FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket) VALUES ('06/12/2012','0115','12:00','77654320','SER00001','TR45678901','12:15',0);
go

-- =====================================================================
-- PROCEDIMIENTOS ALMACENADOS - GESTIÓN DE CLIENTES
-- =====================================================================
-- Los siguientes procedimientos proporcionan operaciones CRUD completas
-- para la gestión de clientes del sistema SAT

-- =====================================================================
-- PA_CRUD_InsertarCliente: Registra un nuevo cliente en el sistema
-- Parámetros: @CodCliente (DNI), @NomCliente (Nombre completo)
-- Validaciones: Verifica que el DNI no exista previamente
-- =====================================================================
Create or alter Procedure PA_CRUD_InsertarCliente
(@CodCliente Varchar(8),
@NomCliente Varchar(50))
As 
BEGIN
	-- Verificar si el cliente ya existe en la base de datos
	IF EXISTS(Select * From Cliente Where CodCliente = @CodCliente) Begin
		Raiserror('El codigo de cliente ya existe en la tabla Cliente',16,1)
		Return @@Error
	End
	
	-- Insertar el nuevo cliente
	Insert Into dbo.Cliente(CodCliente, NomCliente)
		Values (@CodCliente, @NomCliente)
End
Go

-- =====================================================================
-- PA_CRUD_ModificarCliente: Actualiza datos de un cliente existente
-- Parámetros: @CodCliente (DNI), @NomCliente (Nuevo nombre)
-- Validaciones: Verifica que el cliente exista antes de modificar
-- =====================================================================
Create or alter Procedure PA_CRUD_ModificarCliente
(@CodCliente Varchar(8),
@NomCliente Varchar(50))
As
Begin 
	-- Verificar que el cliente existe antes de modificar
	If NOT EXISTS(Select * From Cliente Where CodCliente = @CodCliente) Begin
		Raiserror('El codigo de cliente no existe en la tabla Cliente',16,1)
		Return @@Error
	End
	
	-- Actualizar los datos del cliente
	Update dbo.Cliente
		Set NomCliente = @NomCliente
			Where CodCliente = @CodCliente
End
Go

-- =====================================================================
-- PA_CRUD_EliminarCliente: Elimina un cliente del sistema
-- Parámetros: @CodCliente (DNI), @NomCliente (Para verificación)
-- Validaciones: Verifica que el cliente exista antes de eliminar
-- =====================================================================
Create or alter Procedure PA_CRUD_EliminarCLiente
(@CodCliente Varchar(8),
@NomCliente Varchar (50))
As
Begin
    -- Verificar que el cliente existe antes de eliminar
    If NOT EXISTS(Select * From Cliente Where CodCliente = @CodCliente) Begin
        Raiserror ('El codigo de cliente no existe en la tabla Cliente',16,1)
        Return @@Error
    End

    -- Verificar si el cliente tiene tickets registrados
    If EXISTS(Select * From Ticket Where CodCliente = @CodCliente) Begin
        Raiserror ('Este cliente no puede ser borrado porque tiene tickets registrados',16,1)
        Return @@Error
    End

    -- Eliminar el cliente del sistema
    Delete from Cliente
        Where CodCliente = @CodCliente
End
Go

-- =====================================================================
-- VISTAS Y CONSULTAS - CLIENTES
-- =====================================================================
-- Vista que presenta los datos de clientes en formato user-friendly
CREATE or alter VIEW Vw_ListarCliente
AS
	SELECT CodCliente AS 'Codigo', NomCliente AS 'Nombre Cliente'
	FROM Cliente
GO
---- Listar Clientes: Obtiene todos los clientes del sistema
CREATE or alter PROCEDURE PA_CRUD_ListarCliente
AS
BEGIN
SELECT * FROM Vw_ListarCliente
END
GO

-- Listar Clientes con Filtro: Búsqueda de clientes con texto parcial
CREATE or alter PROCEDURE PA_CRUD_ListarClienteConFiltro
(@Filtro Varchar(150) = '')  -- Texto a buscar en código o nombre
AS
BEGIN
SELECT * FROM Vw_ListarCliente
WHERE [Codigo]+[Nombre Cliente] LIKE '%'+@Filtro+'%'
END
GO

-- =====================================================================
-- PROCEDIMIENTOS ALMACENADOS - GESTIÓN DE SERVICIOS
-- =====================================================================
-- Operaciones CRUD para el catálogo de servicios del SAT

-- PA_CRUD_InsertarServicio: Registra un nuevo servicio en el catálogo
-- Parámetros: Código, nombre y estado del servicio
CREATE or alter PROCEDURE PA_CRUD_InsertarServicio
(@CodServ Varchar(8), 
@NomServ Varchar(60),
@EstadoServicio BIT)
AS
BEGIN

IF EXISTS(SELECT * FROM Servicio WHERE CodServ=@CodServ) BEGIN
	RAISERROR('Codigo ya existe en la tabla Servicio',16,1)
	RETURN @@ERROR
END

INSERT INTO dbo.Servicio(CodServ,NomServ,EstadoServicio)
VALUES (@CodServ,@NomServ,@EstadoServicio)
END
GO
--Modificar Servicio
CREATE or alter PROCEDURE PA_CRUD_ModificarServicio
(@CodServ AS Varchar(8), 
@NomServ AS Varchar(60),
@EstadoServicio AS BIT)
AS
BEGIN
    SET NOCOUNT ON;

    -- Verificar si el servicio existe
    IF NOT EXISTS(SELECT * FROM Servicio WHERE CodServ=@CodServ) BEGIN
        RAISERROR('Codigo no existe en la tabla Servicio',16,1)
        RETURN @@ERROR
    END

    -- Solo verificar tickets pendientes si se intenta inactivar el servicio
    IF @EstadoServicio = 0 BEGIN
        -- Contar tickets pendientes
        DECLARE @TicketsPendientes INT
        SELECT @TicketsPendientes = COUNT(*) 
        FROM Ticket 
        WHERE CodServ = @CodServ AND EstadoTicket = 1
        
        -- Si hay tickets pendientes, no permitir inactivar
        IF @TicketsPendientes > 0 BEGIN
            RAISERROR('No se puede inactivar este servicio porque tiene %d ticket(s) pendiente(s)',16,1, @TicketsPendientes)
            RETURN @@ERROR
        END
    END

    -- Actualizar el servicio
    UPDATE dbo.Servicio
    SET NomServ = @NomServ, EstadoServicio = @EstadoServicio
    WHERE CodServ=@CodServ

    -- Confirmar el cambio
    SELECT 'Servicio actualizado exitosamente' AS Mensaje
END
GO
--Eliminar Servicio
CREATE or alter PROCEDURE PA_CRUD_EliminarServicio
(@CodServ Varchar(8))
AS
BEGIN

IF NOT EXISTS(SELECT * FROM Servicio WHERE CodServ=@CodServ) BEGIN
	RAISERROR('Codigo no existe en la tabla Servicio',16,1)
	RETURN @@ERROR
END

IF exists (Select * from Ticket where CodServ = @CodServ) Begin
	Raiserror('El servicio no se puede eliminar porque tiene tickets registrados',16,1)
	return @@error
End

DELETE FROM dbo.Servicio 
WHERE CodServ=@CodServ
END
GO
-- Vista de Servicios
CREATE Or alter VIEW Vw_ListarServicio
AS
	SELECT CodServ AS 'Codigo', NomServ AS 'Nombre Servicio', EstadoServicio As 'Estado Activo'
	FROM Servicio
GO
--Listar servicios
CREATE Or alter PROCEDURE PA_CRUD_ListarServicio
AS
BEGIN
SELECT * FROM Vw_ListarServicio
END
GO
--Listar servicios con Filtros
CREATE OR ALTER PROCEDURE PA_CRUD_ListarServicioConFiltro
(@Filtro VARCHAR(150) = '',
@Estado BIT = NULL)
AS
BEGIN
    SELECT 
        CodServ AS [Código],
        NomServ AS [Nombre Servicio],
        CASE WHEN EstadoServicio = 1 THEN 'Activo' ELSE 'Inactivo' END AS [Estado]
    FROM Servicio
    WHERE 
        (@Estado IS NULL OR EstadoServicio = @Estado)
        AND (
            @Filtro = '' 
            OR CodServ LIKE '%' + @Filtro + '%'
            OR NomServ LIKE '%' + @Filtro + '%'
        )
END
GO

--Insertar trabajador
CREATE or alter PROCEDURE PA_CRUD_InsertarTrabajador
(@IdTrabajador Varchar(10), 
@NomTrabajador Varchar(50),
@EstadoTrabajador BIT,
@EstadoMaster BIT)
AS
BEGIN

IF EXISTS(SELECT * FROM Trabajador WHERE IdTrabajador=@IdTrabajador) BEGIN
	RAISERROR('Codigo ya existe en la tabla Trabajador',16,1)
	RETURN @@ERROR
END

INSERT INTO dbo.Trabajador(IdTrabajador,NomTrabajador,EstadoTrabajador, EstadoMaster)
VALUES (@IdTrabajador,@NomTrabajador,@EstadoTrabajador,@EstadoMaster)
END
GO

--Modificar Trabajador
CREATE or alter PROCEDURE PA_CRUD_ModificarTrabajador
(@IdTrabajador Varchar(10), 
@NomTrabajador Varchar(50) null,
@EstadoTrabajador BIT null,
@EstadoMaster Bit null)
AS
BEGIN

IF NOT EXISTS(SELECT * FROM Trabajador WHERE IdTrabajador=@IdTrabajador) BEGIN
	RAISERROR('Codigo no existe en la tabla Trabajador',16,1)
	RETURN @@ERROR
END

-- Verificar si el trabajador tiene tickets pendientes antes de cambiar su estado
IF @EstadoTrabajador = 0 AND EXISTS(SELECT * FROM Ticket WHERE IdTrabajador = @IdTrabajador AND EstadoTicket= 1) BEGIN
    RAISERROR('No se puede cambiar el estado del trabajador porque tiene tickets pendientes',16,1)
    RETURN @@ERROR
END

UPDATE dbo.Trabajador
   SET NomTrabajador = @NomTrabajador, EstadoTrabajador = @EstadoTrabajador, EstadoMaster = @EstadoMaster
WHERE IdTrabajador=@IdTrabajador

END
GO

--Eliminar Trabajador
CREATE or alter PROCEDURE PA_CRUD_EliminarTrabajador
(@IdTrabajador Varchar(10))
AS
BEGIN

IF NOT EXISTS(SELECT * FROM Trabajador WHERE IdTrabajador=@IdTrabajador) BEGIN
	RAISERROR('Codigo no existe en la tabla Trabajador',16,1)
	RETURN @@ERROR
END
IF exists (Select * from Trabajador where IdTrabajador = @IdTrabajador and EstadoMaster = 1) begin
	Raiserror('No puedes eliminar a un administrador',16,1)
	return @@error
end

-- Verificar si el trabajador tiene tickets atendidos o pendientes
IF EXISTS(SELECT * FROM Ticket WHERE IdTrabajador = @IdTrabajador) BEGIN
    RAISERROR('No se puede eliminar este trabajador porque tiene tickets atendidos o pendientes',16,1)
    RETURN @@ERROR
END

DELETE FROM Trabajador
WHERE IdTrabajador=@IdTrabajador
END
GO

-- Vistar trabajador
CREATE or alter VIEW Vw_ListarTrabajador
AS
	SELECT IdTrabajador AS 'Codigo', NomTrabajador AS 'Nombre Trabajador', EstadoTrabajador As 'Trabajando', EstadoMaster As 'Administrador'
	FROM Trabajador
GO
--Listar Trabajador
CREATE or alter PROCEDURE PA_CRUD_ListarTrabajador
AS
BEGIN
SELECT * FROM Vw_ListarTrabajador
END
go

--Listar Trabajador con filtro
CREATE OR ALTER PROCEDURE PA_CRUD_ListarTrabajadorConFiltro
(@Filtro VARCHAR(150) = '',
@Estado BIT = NULL,
@EsAdministrador BIT = NULL 
)
AS
BEGIN
    SELECT 
        [Codigo],
        [Nombre Trabajador],
        CASE WHEN [Trabajando] = 1 THEN 'Activo' ELSE 'Inactivo' END AS [Estado],
        CASE WHEN [Administrador] = 1 THEN 'Sí' ELSE 'No' END AS [Es Administrador]
    FROM Vw_ListarTrabajador
    WHERE 
        (@Estado IS NULL OR [Trabajando] = @Estado)
        AND (@EsAdministrador IS NULL OR [Administrador] = @EsAdministrador)
        AND (
            @Filtro = '' 
            OR [Codigo] LIKE '%' + @Filtro + '%'
            OR [Nombre Trabajador] LIKE '%' + @Filtro + '%'
            OR CASE WHEN [Trabajando] = 1 THEN 'Activo' ELSE 'Inactivo' END LIKE '%' + @Filtro + '%'
            OR CASE WHEN [Administrador] = 1 THEN 'Administrador' ELSE 'Usuario' END LIKE '%' + @Filtro + '%'
        )
END
GO

GO

-- =====================================================================
-- PROCEDIMIENTOS ALMACENADOS - GESTIÓN DE TICKETS
-- =====================================================================
-- Núcleo principal del sistema: gestión completa del flujo de atención

-- PA_RegistrarTicket: Crea un nuevo ticket de atención
-- FUNCIÓN PRINCIPAL: Permite a los usuarios solicitar servicios
-- Parámetros: DNI del cliente, nombre, código de servicio
-- Características:
--   - Crea cliente automáticamente si no existe
--   - Genera número de ticket secuencial por día
--   - Asigna fecha y hora actual automáticamente
--   - Inicia en estado "Pendiente" sin trabajador asignado
CREATE or alter PROCEDURE PA_RegistrarTicket
(@CodCliente AS Varchar(8),
@NomCliente AS Varchar(50),
@CodServ AS Varchar(8))
AS
BEGIN
    -- Verificar si el cliente existe, si no existe, crearlo automáticamente
    IF NOT EXISTS(SELECT * FROM Cliente WHERE CodCliente = @CodCliente) BEGIN
        INSERT INTO Cliente(CodCliente, NomCliente) VALUES (@CodCliente, @NomCliente)
    END

    -- Verificar que el servicio existe y está activo
    IF NOT EXISTS(SELECT * FROM Servicio WHERE CodServ = @CodServ AND EstadoServicio = 1) BEGIN
        RAISERROR('El servicio no existe o no está activo', 16, 1)
        RETURN @@ERROR
    END

    -- Variables para generar el ticket automáticamente
    DECLARE @FechaTicket DATE = GETDATE()           -- Fecha actual del sistema
    DECLARE @HoraEmision TIME = CONVERT(TIME, GETDATE())  -- Hora actual del sistema
    DECLARE @NumTicket VARCHAR(4)

    -- Generar número de ticket secuencial para el día actual
    -- Busca el último número usado en la fecha y le suma 1
    SELECT @NumTicket = RIGHT('000' + CAST(ISNULL(MAX(CAST(NumTicket AS INT)), 0) + 1 AS VARCHAR), 4)
    FROM Ticket 
    WHERE FechaTicket = @FechaTicket

    -- Insertar ticket en estado pendiente (será asignado cuando un trabajador lo atienda)
    INSERT INTO dbo.Ticket(FechaTicket, NumTicket, HoraEmisionTicket, CodCliente, CodServ, IdTrabajador, HoraAtencionTicket, EstadoTicket)
    VALUES (@FechaTicket, @NumTicket, @HoraEmision, @CodCliente, @CodServ, NULL, NULL, 1)

    -- Retornar información del ticket creado para mostrar al usuario
    SELECT @NumTicket AS NumeroTicket, @FechaTicket AS FechaTicket
END
GO

-- =====================================================================
-- PROCEDIMIENTOS DE DEBUGGING Y VERIFICACIÓN
-- =====================================================================
-- Herramientas de diagnóstico para desarrollo y mantenimiento del sistema
-- Estos procedimientos ayudan a verificar el estado de datos y diagnosticar problemas

-- PA_DEBUG_VerificarTicket: Muestra información detallada de un ticket específico
-- Útil para debugging cuando un ticket no se comporta como se espera

-- =====================================================================
-- PA_CRUD_AtenderTicket: Procesa la atención de un ticket pendiente
-- FUNCIÓN CRÍTICA: Permite a los trabajadores atender tickets
-- Parámetros: Fecha, número de ticket, ID del trabajador
-- Validaciones:
--   - Verifica que el ticket existe y está pendiente
--   - Verifica que el trabajador existe y está activo
--   - Asigna trabajador, hora de atención y cambia estado a "Atendido"
-- =====================================================================
CREATE OR ALTER PROCEDURE PA_CRUD_ModificarTicket
(@FechaTicket Date, 
@NumTicket Varchar(4),
@HoraEmisionTicket Time,
@HoraAtencionTicket Time,
@EstadoTicket bit
)
AS
BEGIN
    IF NOT EXISTS(SELECT * FROM Ticket WHERE FechaTicket = @FechaTicket AND NumTicket = @NumTicket) BEGIN
        RAISERROR('Numero y fecha de ticket no existe en la tabla Ticket', 16, 1)
        RETURN @@ERROR
    END
    IF @HoraAtencionTicket IS NOT NULL AND @HoraAtencionTicket < @HoraEmisionTicket BEGIN
        RAISERROR('La hora de atención no puede ser menor que la hora de emisión', 16, 1)
        RETURN @@ERROR
    END

    UPDATE dbo.Ticket
    SET HoraEmisionTicket = @HoraEmisionTicket,HoraAtencionTicket = @HoraAtencionTicket,EstadoTicket = @EstadoTicket
    WHERE FechaTicket = @FechaTicket AND NumTicket = @NumTicket
END
GO

--Eliminar Ticket
CREATE or alter PROCEDURE PA_CRUD_EliminarTicket
(@FechaTicket AS DATE,
@NumTicket AS Varchar(4))
AS
BEGIN
    SET NOCOUNT ON;

    -- Validar que el ticket existe
    IF NOT EXISTS(SELECT * FROM Ticket WHERE FechaTicket=@FechaTicket AND NumTicket=@NumTicket) BEGIN
        RAISERROR('Numero y fecha no existe en la tabla Ticket',16,1)
        RETURN @@ERROR
    END

    -- Validar que el ticket no haya sido atendido
    IF EXISTS(SELECT * FROM Ticket WHERE FechaTicket=@FechaTicket AND NumTicket=@NumTicket AND EstadoTicket = 0) BEGIN
        RAISERROR('El ticket ya fue atendido y no puede ser eliminado',16,1)
        RETURN @@ERROR
    END

    -- Eliminar el ticket
    DELETE FROM Ticket
    WHERE FechaTicket=@FechaTicket AND NumTicket=@NumTicket
END
GO

CREATE or alter VIEW Vw_ListarTicket
AS
	/* MODIFICADO: Vista actualizada para mostrar el estado correcto */
	SELECT DISTINCT FechaTicket AS 'Fecha', NumTicket AS 'Numero Ticket', 
	CASE WHEN EstadoTicket = 1 THEN 'Pendiente' ELSE 'Atendido' END AS 'Estado',
	HoraEmisionTicket As 'Hora Emision', 
	HoraAtencionTicket As 'Hora Atencion',
	CodCliente As 'Dni Cliente', 
	CodServ As 'Codigo Servicio',
	IdTrabajador As 'Codigo Trabajador'
	FROM Ticket
GO

CREATE or alter PROCEDURE PA_CRUD_ListarTicket
AS
BEGIN
SELECT * FROM Vw_ListarTicket
END
GO

CREATE or alter PROCEDURE PA_CRUD_ListarTicketConFiltro
(@Filtro Varchar(150)='',
@Estado Bit = null)
AS
BEGIN
	/* MODIFICADO: Filtros actualizados para el nuevo esquema */
	Select 
		[Fecha],
		[Numero Ticket],
		[Estado],
		[Hora Emision],
		[Hora Atencion],
		[Dni Cliente],
		[Codigo Servicio],
		[Codigo Trabajador]
		From Vw_ListarTicket
		where 
			(@Estado is null or (@Estado = 1 AND [Estado] = 'Pendiente') or (@Estado = 0 AND [Estado] = 'Atendido'))
				And (@Filtro = '' or [Fecha] LIKE '%' + @Filtro + '%'
					or [Numero Ticket] LIKE '%' + @Filtro + '%'
					or [Estado] LIKE '%' + @Filtro + '%'
					or [Dni Cliente] LIKE '%' + @Filtro + '%'
					or [Codigo Servicio] LIKE '%' + @Filtro + '%')
End
go

/* NUEVO: Procedimiento para que los trabajadores atiendan tickets */
CREATE OR ALTER PROCEDURE PA_CRUD_AtenderTicket
(@FechaTicket DATE,
@NumTicket VARCHAR(4),
@IdTrabajador VARCHAR(10))
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Variables de control para validaciones
    DECLARE @TicketExiste INT = 0;
    DECLARE @TrabajadorValido INT = 0;
    DECLARE @HoraActual TIME;
    
    -- Obtener la hora actual del sistema para registrar la atención
    SET @HoraActual = CONVERT(TIME, GETDATE());
    
    BEGIN TRY
        -- VALIDACIÓN 1: Verificar que el ticket existe y está pendiente
        SELECT @TicketExiste = COUNT(*)
        FROM Ticket 
        WHERE FechaTicket = @FechaTicket 
          AND NumTicket = @NumTicket 
          AND EstadoTicket = 1; -- 1 = Pendiente
        
        IF @TicketExiste = 0 BEGIN
            SELECT 'Error: El ticket no existe o ya fue atendido' AS Mensaje;
            RETURN;
        END
        
        -- VALIDACIÓN 2: Verificar que el trabajador existe y está activo
        SELECT @TrabajadorValido = COUNT(*)
        FROM Trabajador 
        WHERE IdTrabajador = @IdTrabajador 
          AND EstadoTrabajador = 1; -- 1 = Activo
        
        
        IF @TrabajadorValido = 0 BEGIN
            SELECT 'Error: El trabajador no existe o no está activo' AS Mensaje;
            RETURN;
        END
        
        -- ACTUALIZACIÓN: Procesar la atención del ticket
        -- Asigna el trabajador, registra hora de atención y cambia estado
        UPDATE Ticket 
        SET IdTrabajador = @IdTrabajador,        -- Asignar trabajador que atiende
            HoraAtencionTicket = @HoraActual,    -- Registrar hora exacta de atención
            EstadoTicket = 0                     -- Cambiar estado a "Atendido"
        WHERE FechaTicket = @FechaTicket 
          AND NumTicket = @NumTicket;
        
        -- Verificar que la actualización fue exitosa y retornar resultado
        IF @@ROWCOUNT > 0 BEGIN
            SELECT 'Ticket atendido exitosamente por trabajador: ' + @IdTrabajador + 
                   ' a las ' + CONVERT(VARCHAR(8), @HoraActual, 108) AS Mensaje;
        END ELSE BEGIN
            SELECT 'Error: No se pudo actualizar el ticket' AS Mensaje;
        END
        
    END TRY
    BEGIN CATCH
        SELECT 'Error en el procedimiento: ' + ERROR_MESSAGE() AS Mensaje;
    END CATCH
END
GO

CREATE OR ALTER PROCEDURE PA_DEBUG_VerificarTicket
(@FechaTicket DATE,
@NumTicket VARCHAR(4))
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Obtener información completa del ticket solicitado
    SELECT 
        FechaTicket AS 'Fecha',
        NumTicket AS 'Numero',
        CASE WHEN EstadoTicket = 1 THEN 'Pendiente' ELSE 'Atendido' END AS 'Estado',
        HoraEmisionTicket AS 'Hora Emision',
        HoraAtencionTicket AS 'Hora Atencion',
        CodCliente AS 'Cliente',
        CodServ AS 'Servicio',
        IdTrabajador AS 'Trabajador',
        EstadoTicket AS 'Estado Numerico'
    FROM Ticket
    WHERE FechaTicket = @FechaTicket AND NumTicket = @NumTicket;
    
    -- Mostrar mensaje si no se encuentra el ticket
    IF @@ROWCOUNT = 0
        SELECT 'Ticket no encontrado' AS Mensaje;
END
GO

-- PA_DEBUG_VerificarTrabajador: Verifica el estado de un trabajador específico
-- Útil para verificar por qué un trabajador no puede atender tickets
CREATE OR ALTER PROCEDURE PA_DEBUG_VerificarTrabajador
(@IdTrabajador VARCHAR(10))
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Mostrar información del trabajador solicitado
    SELECT 
        IdTrabajador AS 'ID',
        NomTrabajador AS 'Nombre',
        CASE WHEN EstadoTrabajador = 1 THEN 'Activo' ELSE 'Inactivo' END AS 'Estado',
        EstadoTrabajador AS 'Estado Numerico'
    FROM Trabajador
    WHERE IdTrabajador = @IdTrabajador;
    
    -- Mostrar mensaje si no se encuentra el trabajador
    IF @@ROWCOUNT = 0
        SELECT 'Trabajador no encontrado' AS Mensaje;
END
GO

-- PA_DEBUG_ListarTicketsDetallado: Vista completa de todos los tickets con joins
-- Proporciona información completa para análisis del sistema y reportes
CREATE OR ALTER PROCEDURE PA_DEBUG_ListarTicketsDetallado
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Consulta completa con joins para obtener nombres descriptivos
    -- Útil para reportes y análisis del rendimiento del sistema
    SELECT 
        t.FechaTicket AS 'Fecha',
        t.NumTicket AS 'Numero',
        CASE WHEN t.EstadoTicket = 1 THEN 'Pendiente' ELSE 'Atendido' END AS 'Estado',
        t.HoraEmisionTicket AS 'Hora Emision',
        t.HoraAtencionTicket AS 'Hora Atencion',
        t.CodCliente AS 'DNI Cliente',
        c.NomCliente AS 'Nombre Cliente',
        t.CodServ AS 'Codigo Servicio',
        s.NomServ AS 'Nombre Servicio',
        t.IdTrabajador AS 'ID Trabajador',
        tr.NomTrabajador AS 'Nombre Trabajador',
        t.EstadoTicket AS 'Estado Activo'
    FROM Ticket t
    LEFT JOIN Cliente c ON t.CodCliente = c.CodCliente           -- Información del cliente
    LEFT JOIN Servicio s ON t.CodServ = s.CodServ               -- Información del servicio
    LEFT JOIN Trabajador tr ON t.IdTrabajador = tr.IdTrabajador -- Información del trabajador
    ORDER BY t.FechaTicket DESC, t.NumTicket DESC;              -- Más recientes primero
END
GO

-- PA_DEBUG_ListarTicketsFiltrado: Lista tickets detallados con filtro de fechas
-- Parámetros: @FechaDesde, @FechaHasta
-- Descripción: Versión filtrada del procedimiento de listado de tickets
-- =====================================================================
CREATE OR ALTER PROCEDURE PA_DEBUG_ListarTicketsFiltrado
(@FechaDesde DATE, @FechaHasta DATE)
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Validar que la fecha desde no sea mayor que fecha hasta
    IF @FechaDesde > @FechaHasta BEGIN
        RAISERROR('La fecha desde no puede ser mayor que la fecha hasta', 16, 1)
        RETURN @@ERROR
    END
    
    -- Consulta filtrada por rango de fechas
    SELECT 
        t.FechaTicket AS 'Fecha',
        t.NumTicket AS 'Numero',
        CASE WHEN t.EstadoTicket = 1 THEN 'Pendiente' ELSE 'Atendido' END AS 'Estado',
        t.HoraEmisionTicket AS 'Hora Emision',
        t.HoraAtencionTicket AS 'Hora Atencion',
        t.CodCliente AS 'DNI Cliente',
        c.NomCliente AS 'Nombre Cliente',
        t.CodServ AS 'Codigo Servicio',
        s.NomServ AS 'Nombre Servicio',
        t.IdTrabajador AS 'ID Trabajador',
        tr.NomTrabajador AS 'Nombre Trabajador',
        t.EstadoTicket AS 'Estado Activo'
    FROM Ticket t
    LEFT JOIN Cliente c ON t.CodCliente = c.CodCliente           -- Información del cliente
    LEFT JOIN Servicio s ON t.CodServ = s.CodServ               -- Información del servicio
    LEFT JOIN Trabajador tr ON t.IdTrabajador = tr.IdTrabajador -- Información del trabajador
    WHERE t.FechaTicket >= @FechaDesde AND t.FechaTicket <= @FechaHasta -- Filtro de fechas
    ORDER BY t.FechaTicket DESC, t.NumTicket DESC;              -- Más recientes primero
END
GO



-- =====================================================================
-- VISTA Y PROCEDIMIENTO - HISTORIAL DE TICKETS POR CLIENTE
-- =====================================================================
GO

-- Vista que muestra el historial completo de tickets con información detallada
CREATE OR ALTER VIEW Vw_HistorialTicketCliente
AS
SELECT 
    t.CodCliente AS DNI_Cliente,
    t.NumTicket AS Numero_Ticket,
    CONVERT(VARCHAR, t.FechaTicket, 103) + ' ' + CONVERT(VARCHAR, t.HoraEmisionTicket, 108) AS Fecha_Creacion,
    s.NomServ AS Nombre_Servicio,
    CASE WHEN t.EstadoTicket = 1 THEN 'PENDIENTE' ELSE 'ATENDIDO' END AS Estado,
    ISNULL(tr.NomTrabajador, 'No asignado') AS Nombre_Trabajador,
    CASE WHEN t.HoraAtencionTicket IS NOT NULL 
         THEN CONVERT(VARCHAR, t.FechaTicket, 103) + ' ' + CONVERT(VARCHAR, t.HoraAtencionTicket, 108) 
         ELSE 'Pendiente' END AS Fecha_Atencion,
    t.FechaTicket AS Fecha_Ordenamiento,
    t.HoraEmisionTicket AS Hora_Ordenamiento
FROM Ticket t 
INNER JOIN Cliente c ON t.CodCliente = c.CodCliente 
INNER JOIN Servicio s ON t.CodServ = s.CodServ 
LEFT JOIN Trabajador tr ON t.IdTrabajador = tr.IdTrabajador
GO

-- Procedimiento para obtener el historial de tickets de un cliente específico
CREATE OR ALTER PROCEDURE PA_CRUD_ListarHistorialTicketCliente
(@DNI_Cliente VARCHAR(8))
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Validar que el cliente existe
    IF NOT EXISTS(SELECT 1 FROM Cliente WHERE CodCliente = @DNI_Cliente) BEGIN
        RAISERROR('El cliente con DNI %s no existe en el sistema', 16, 1, @DNI_Cliente)
        RETURN @@ERROR
    END
    
    -- Obtener historial ordenado por fecha más reciente
    SELECT 
        Numero_Ticket,
        Fecha_Creacion,
        Nombre_Servicio,
        Estado,
        Nombre_Trabajador,
        Fecha_Atencion
    FROM Vw_HistorialTicketCliente 
    WHERE DNI_Cliente = @DNI_Cliente 
    ORDER BY Fecha_Ordenamiento DESC, Hora_Ordenamiento DESC
END
GO

-- Procedimiento para contar tickets pendientes de un servicio específico
CREATE OR ALTER PROCEDURE PA_CRUD_ContarTicketsPendientes
(@CodServ VARCHAR(8))
AS
BEGIN
    SET NOCOUNT ON;

    -- Validar que el servicio existe
    IF NOT EXISTS(SELECT 1 FROM Servicio WHERE CodServ = @CodServ) BEGIN
        RAISERROR('El servicio con código %s no existe en el sistema', 16, 1, @CodServ)
        RETURN @@ERROR
    END

    -- Contar tickets pendientes para el servicio especificado
    SELECT COUNT(*) AS TicketsPendientes
    FROM Ticket 
    WHERE CodServ = @CodServ 
    AND EstadoTicket = 1  -- 1 = Pendiente
END
GO

-- Procedimiento para verificar detalle de tickets por servicio
CREATE OR ALTER PROCEDURE PA_DEBUG_VerificarTicketsServicio
(@CodServ VARCHAR(8))
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Mostrar información detallada de tickets del servicio
    SELECT 
        NumTicket,
        CodCliente,
        EstadoTicket,
        CASE WHEN EstadoTicket = 1 THEN 'PENDIENTE' ELSE 'ATENDIDO' END AS Estado_Texto,
        FechaTicket,
        HoraEmisionTicket,
        HoraAtencionTicket
    FROM Ticket 
    WHERE CodServ = @CodServ
    ORDER BY FechaTicket DESC, HoraEmisionTicket DESC;
    
    -- Contar tickets por estado
    SELECT 
        COUNT(*) AS Total_Tickets,
        SUM(CASE WHEN EstadoTicket = 1 THEN 1 ELSE 0 END) AS Tickets_Pendientes,
        SUM(CASE WHEN EstadoTicket = 0 THEN 1 ELSE 0 END) AS Tickets_Atendidos
    FROM Ticket 
    WHERE CodServ = @CodServ;
END
GO
-- =====================================================================
exec PA_CRUD_InsertarTrabajador 1,sa,1,1