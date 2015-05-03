SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `virtualDocuments` DEFAULT CHARACTER SET utf8 ;
USE `virtualDocuments` ;

-- -----------------------------------------------------
-- Table `virtualDocuments`.`Ambiente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Ambiente` (
  `idAmbiente` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`idAmbiente`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Area` (
  `idArea` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`idArea`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Tipo_Documental`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Tipo_Documental` (
  `idTipo_Documental` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`idTipo_Documental`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Local`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Local` (
  `idLocal` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`idLocal`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NULL DEFAULT NULL,
  `Apellido` VARCHAR(250) NULL DEFAULT NULL,
  `Contrasena` VARCHAR(250) NOT NULL,
  `email` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Estanteria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Estanteria` (
  `idEstanteria` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`idEstanteria`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Sub_Fondo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Sub_Fondo` (
  `idSub_Fondo` INT(11) NOT NULL AUTO_INCREMENT,
  `Identificador` VARCHAR(250) NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `Padre` VARCHAR(45) NULL DEFAULT NULL,
  `Identificador_Por_Usuario` VARCHAR(45) NOT NULL,
  `isSerie` INT NOT NULL,
  `Fondo` VARCHAR(250) NULL,
  PRIMARY KEY (`idSub_Fondo`),
  UNIQUE INDEX `Identificador_UNIQUE` (`Identificador` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Documento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Documento` (
  `idDocumento` INT(11) NOT NULL,
  `Path` VARCHAR(250) NOT NULL,
  `Nombre_Archivo` VARCHAR(250) NOT NULL,
  `Fecha_Hora_Escaneo` DATETIME NOT NULL,
  `Usuario_Escaneo` INT(11) NULL,
  `Numero_Folios` INT(11) NOT NULL,
  `Codigo_tipo_documental` INT(11) NULL DEFAULT NULL,
  `Fecha_Documento_Dia` INT(11) NULL DEFAULT NULL,
  `Fecha_Documento_Mes` INT(11) NULL DEFAULT NULL,
  `Fecha_Documento_Anio` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Dia_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Mes_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Anio_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Dia_Final` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Mes_Final` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Anio_Final` INT(11) NULL DEFAULT NULL,
  `Asunto` TEXT NULL DEFAULT NULL,
  `Campos_Especificos` TEXT NULL DEFAULT NULL,
  `Observaciones` TEXT NULL DEFAULT NULL,
  `Codigo_Local` INT(11) NULL DEFAULT NULL,
  `Codigo_Area` INT(11) NULL DEFAULT NULL,
  `Codigo_Ambiente` INT(11) NULL DEFAULT NULL,
  `Codigo_Estanteria` INT(11) NULL DEFAULT NULL,
  `Caja` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Caja_Dia_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Caja_Mes_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Caja_Anio_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Caja_Dia_Final` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Caja_Mes_Final` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Caja_Anio_Final` INT(11) NULL DEFAULT NULL,
  `Legajo` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Legajo_Dia_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Legajo_Mes_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Legajo_Anio_Inicial` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Legajo_Dia_Final` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Legajo_Mes_Final` INT(11) NULL DEFAULT NULL,
  `Fecha_Extrema_Legajo_Anio_Final` INT(11) NULL DEFAULT NULL,
  `Orden_Alfabetico` VARCHAR(250) NULL DEFAULT NULL,
  `Orden_Correlativo` VARCHAR(250) NULL DEFAULT NULL,
  `Otra_Fecha_Dia` INT(11) NULL DEFAULT NULL,
  `Otra_Fecha_Mes` INT(11) NULL DEFAULT NULL,
  `Otra_Fecha_Anio` INT(11) NULL DEFAULT NULL,
  `Nuevo_Numero_Unico` VARCHAR(250) NULL,
  `Nuevo_Numero_Unico_2` VARCHAR(250) NULL DEFAULT NULL,
  `Codigo_Serie` INT NULL,
  UNIQUE INDEX `Nombre_Archivo_UNIQUE` (`Nombre_Archivo` ASC),
  INDEX `Documento_Tipo_Documental_idx` (`Codigo_tipo_documental` ASC),
  INDEX `Documento_Local_idx` (`Codigo_Local` ASC),
  INDEX `Documento_Usuario_idx` (`Usuario_Escaneo` ASC),
  INDEX `Documento_Area_idx` (`Codigo_Area` ASC),
  INDEX `Documento_Ambiente_idx` (`Codigo_Ambiente` ASC),
  INDEX `Documento_Estanteria_idx` (`Codigo_Estanteria` ASC),
  INDEX `Documento_Codigo_serie_idx` (`Codigo_Serie` ASC),
  CONSTRAINT `Documento_Tipo_Documental`
    FOREIGN KEY (`Codigo_tipo_documental`)
    REFERENCES `virtualDocuments`.`Tipo_Documental` (`idTipo_Documental`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `Documento_Local`
    FOREIGN KEY (`Codigo_Local`)
    REFERENCES `virtualDocuments`.`Local` (`idLocal`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `Documento_Usuario`
    FOREIGN KEY (`Usuario_Escaneo`)
    REFERENCES `virtualDocuments`.`Usuario` (`idUsuario`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `Documento_Area`
    FOREIGN KEY (`Codigo_Area`)
    REFERENCES `virtualDocuments`.`Area` (`idArea`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `Documento_Ambiente`
    FOREIGN KEY (`Codigo_Ambiente`)
    REFERENCES `virtualDocuments`.`Ambiente` (`idAmbiente`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `Documento_Estanteria`
    FOREIGN KEY (`Codigo_Estanteria`)
    REFERENCES `virtualDocuments`.`Estanteria` (`idEstanteria`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `Documento_Codigo_serie`
    FOREIGN KEY (`Codigo_Serie`)
    REFERENCES `virtualDocuments`.`Sub_Fondo` (`idSub_Fondo`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Filtro_Nuevo_Documento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Filtro_Nuevo_Documento` (
  `idFiltro_Nuevo_Documento` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NOT NULL,
  `Nombre_Columna` VARCHAR(250) NOT NULL,
  `Acceso` INT(11) NOT NULL DEFAULT '0',
  `Tamanio_Columna` INT(11) NOT NULL,
  PRIMARY KEY (`idFiltro_Nuevo_Documento`),
  UNIQUE INDEX `Nombre_Columna_UNIQUE` (`Nombre_Columna` ASC),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Modulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Modulo` (
  `idModulos` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`idModulos`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Permiso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Permiso` (
  `idPermiso` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(250) NOT NULL,
  `Descripcion` TEXT NULL DEFAULT NULL,
  `Codigo_Sub_Nivel` INT(11) NULL DEFAULT NULL,
  `Codigo_Modulo` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idPermiso`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC),
  INDEX `PCodigo_Sub_Nivel_idx` (`Codigo_Sub_Nivel` ASC),
  INDEX `PCodigo_Modulo_idx` (`Codigo_Modulo` ASC),
  CONSTRAINT `PCodigo_Sub_Nivel`
    FOREIGN KEY (`Codigo_Sub_Nivel`)
    REFERENCES `virtualDocuments`.`Sub_Fondo` (`idSub_Fondo`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `PCodigo_Modulo`
    FOREIGN KEY (`Codigo_Modulo`)
    REFERENCES `virtualDocuments`.`Modulo` (`idModulos`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8
PACK_KEYS = Default;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Usuario_Permiso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Usuario_Permiso` (
  `idUsuario_Permiso` INT(11) NOT NULL AUTO_INCREMENT,
  `Codigo_Permiso` INT(11) NULL DEFAULT NULL,
  `Codigo_Usuario` INT(11) NULL DEFAULT NULL,
  `Acces` INT(11) NULL DEFAULT '0',
  PRIMARY KEY (`idUsuario_Permiso`),
  INDEX `UP_Codigo_Permiso_idx` (`Codigo_Permiso` ASC),
  INDEX `UP_Codigo_Usuario_idx` (`Codigo_Usuario` ASC),
  CONSTRAINT `UP_Codigo_Usuario`
    FOREIGN KEY (`Codigo_Usuario`)
    REFERENCES `virtualDocuments`.`Usuario` (`idUsuario`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `UP_Codigo_Permiso`
    FOREIGN KEY (`Codigo_Permiso`)
    REFERENCES `virtualDocuments`.`Permiso` (`idPermiso`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `virtualDocuments`.`Configuracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtualDocuments`.`Configuracion` (
  `idConfiguracion` INT NOT NULL AUTO_INCREMENT,
  `colorRed` VARCHAR(45) NULL,
  `colorGreen` VARCHAR(45) NULL,
  `colorBlue` VARCHAR(45) NULL,
  `institutionName` VARCHAR(250) NULL,
  `motherPath` VARCHAR(1000) NULL,
  `motherUser` VARCHAR(1000) NULL,
  `motherPassword` VARCHAR(250) NULL,
  `logo` BLOB NULL,
  PRIMARY KEY (`idConfiguracion`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
