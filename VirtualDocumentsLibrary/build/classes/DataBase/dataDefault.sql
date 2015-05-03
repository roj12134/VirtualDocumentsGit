

USE virtualDocuments;
-- -----------------------------------------------------
-- Insertar Modulos del programa 
-- -----------------------------------------------------

INSERT INTO Modulo (Nombre) values ('Usuarios');
INSERT INTO Modulo (Nombre) values ('Configuraciones');
INSERT INTO Modulo (Nombre) values ('Estructura de Datos');
INSERT INTO Modulo (Nombre) values ('Documentos');
INSERT INTO Modulo (Nombre) values ('Reportes');

-- -----------------------------------------------------
-- Insertar permisos de Modulos  
-- -----------------------------------------------------

-- Insertar Permiso del Modulo Usuario, separado (ver, crear, modificar, eliminar)
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Ver usuarios','Ver los usuarios',1);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Crear usuarios','Crear nuevos usuarios',1);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Modificar usuarios','Modificar usuarios existentes',1);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Eliminar usuarios','Eliminar usuarios existentes, de forma PERMANENTE',1);


-- Insertar Permisos de Configuraciones (ver, modificar)
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Ver configuraciones','Ver las configuraciones',2);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Modificar configuraciones','Modificar las configuraciones',2);


-- Insetar Permisos de Estructura de Datos (ver, crear, modificar, eliminar)
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Ver Estructura de Datos','Ver la estructura de Datos',3);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Crear Estructura de Datos','Nos deja crear más sub-fondos o fondos en la estructura de datos',3);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Modificar Estructura de Datos','Modificar nombres de los sub-fondos o fondos de la estructura de Datos',3);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Eliminar Estructura de Datos','Eliminar sub-fondos o fondos de la estructura de Datos',3);


-- Insertar Permisos de Documentos (Ver, crear, modificar, eliminar)
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Ver documentos','Ver los Documentos ya existentes',4);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Crear documentos','Crear los Documentos',4);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Modificar documentos','Modificar los Documentos ya existentes',4);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Eliminar documentos','Eliminar los Documentos de forma PERMANENTE',4);


-- Insetar Permisos de Reportes (ver, crear, exportar)
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Ver reportes','Ver los reportes',5);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Crear reportes','Crear reportes',5);
INSERT INTO PERMISO (Nombre,Descripcion,Codigo_Modulo) 
    values ('Exportar reportes','Exportar los reportes a excel',5);

-- -----------------------------------------------------
-- Insertar default Tipo_Documental 
-- -----------------------------------------------------

INSERT INTO tipo_documental (idTipo_Documental,Nombre)
    values (1,'Anulado');

-- -----------------------------------------------------
-- Insertar default User 
-- -----------------------------------------------------

INSERT INTO USUARIO (idUsuario,Nombre,Apellido,Contrasena,email) values (1,"Default","Default","1234","admin");

-- Permisos del usuario de Default 

-- Permisos relacionados al modulo de usuarios
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (1,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (2,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (3,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (4,1,1);



-- Permisos relacionados al modulo Configuraciones
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (5,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (6,1,1);


-- Permisos relacionados al modulo Estructura de Datos
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (7,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (8,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (9,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (10,1,1);


-- Permiso relacionados al modulo de documentos
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (11,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (12,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (13,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (14,1,1);


-- Permiso relacionados al modulo de Reportes
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (15,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (16,1,1);
INSERT INTO USUARIO_PERMISO (Codigo_Permiso,Codigo_Usuario,Acces) 
     values (17,1,1);




-- -----------------------------------------------------
-- Insertar Los filtros del nuevo Documento, inicialmente 
-- son 35 de estos
-- -----------------------------------------------------
INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Tipo Documental','Codigo_tipo_documental',1,142);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null','Fecha Documento',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null1','Fecha Extrema Inicial',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null2','Fecha Extrema Final',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Asunto','Asunto',1,330);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('<html><center><table><tr><td align=\"center\" > Campos </td></tr><tr><td align=\"center\"> Especificos </td> </tr></table></center></html>','Campos_Especificos',1,330);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Observaciones','Observaciones',1,330);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Local','Codigo_Local',1,142);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Area','Codigo_Area',1,142);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Ambiente','Codigo_Ambiente',1,142);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Estanteria','Codigo_Estanteria',1,142);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Caja','Caja',1,150);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null3','Fecha Extrema Caja Inicial',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null4','Fecha Extrema Caja Final',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('Legajo','Legajo',1,116);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null5','Fecha Extrema Legajo Inicial',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null6','Fecha Extrema Legajo Final',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('<html><center><table><tr><td align=\"center\" > Orden </td></tr><tr><td align=\"center\"> Alfabetico </td> </tr></table></center></html>','Orden_Alfabetico',1,122);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('<html><center><table><tr><td align=\"center\" > Orden </td></tr><tr><td align=\"center\"> Correlativo </td> </tr></table></center></html>','Orden_Correlativo',1,119);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('null7','Otra Fecha',1,75);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('<html><center><table><tr><td align=\"center\" > ID Documento  </td></tr><tr><td align=\"center\"> 1 </td> </tr></table></center></html>','Nuevo_Numero_Unico',1,155);

INSERT INTO Filtro_Nuevo_Documento (Nombre,Nombre_Columna,Acceso,Tamanio_Columna) 
     values ('<html><center><table><tr><td align=\"center\" > ID Documento </td></tr><tr><td align=\"center\"> 2 </td> </tr></table></center></html>','Nuevo_Numero_Unico_2',1,155);

-- -----------------------------------------------------
-- Inserta la estructura base Fondos, donde se iran a 
-- crear los fondos
-- -----------------------------------------------------

INSERT INTO sub_fondo VALUES(0,'Fondos','Fondos',null,'id1',0,null);

-- -----------------------------------------------------
-- Insertar los valores iniciales de cofiguraciones 
-- color blanco y resto null
-- -----------------------------------------------------

INSERT INTO configuracion VALUES(0,'255','255', '255', 'No Name', ' ', ' ', null,null);

