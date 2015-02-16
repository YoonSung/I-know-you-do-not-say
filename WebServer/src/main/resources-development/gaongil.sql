-- MySQL Workbench Forward Engineering
CREATE DATABASE IF NOT EXISTS gaongil;
USE gaongil;


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`tbl_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_user` (
  `pid` INT NOT NULL AUTO_INCREMENT,
  `phoneNumber` INT NULL,
  `is_member` TINYINT(1) NOT NULL,
  `nickname` VARCHAR(80) NOT NULL,
  `image_path` VARCHAR(255) NULL,
  `created_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`pid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_member` (
  `pid` INT NOT NULL,
  `memberId` VARCHAR(255) NULL,
  `password` VARCHAR(45) NULL,
  `sex` VARCHAR(45) NULL,
  `age` VARCHAR(45) NULL,
  `created_time` TIMESTAMP NULL,
  `tbl_user_pid` INT NOT NULL,
  PRIMARY KEY (`pid`),
  INDEX `fk_tbl_member_tbl_user_idx` (`tbl_user_pid` ASC),
  CONSTRAINT `fk_tbl_member_tbl_user`
    FOREIGN KEY (`tbl_user_pid`)
    REFERENCES `mydb`.`tbl_user` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_group` (
  `pid` INT NOT NULL,
  `name` VARCHAR(100) NULL,
  `count` INT NULL,
  `created_time` TIMESTAMP NULL,
  PRIMARY KEY (`pid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_safezone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_safezone` (
  `pid` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `latitude` DECIMAL(10,8) NOT NULL,
  `longitude` DECIMAL(11,8) NOT NULL,
  `distance` INT NOT NULL,
  `created_time` TIMESTAMP NOT NULL,
  `tbl_user_pid` INT NOT NULL,
  PRIMARY KEY (`pid`),
  INDEX `fk_tbl_safezone_tbl_user1_idx` (`tbl_user_pid` ASC),
  CONSTRAINT `fk_tbl_safezone_tbl_user1`
    FOREIGN KEY (`tbl_user_pid`)
    REFERENCES `mydb`.`tbl_user` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_chat_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_chat_log` (
  `pid` INT NOT NULL,
  `message` TEXT NULL,
  `created_time` TIMESTAMP NULL,
  PRIMARY KEY (`pid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_location_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_location_log` (
  `pid` INT NOT NULL,
  `name` VARCHAR(100) NULL,
  `latitude` DECIMAL(10,8) NULL,
  `longitude` DECIMAL(11,8) NULL,
  `created_time` TIMESTAMP NULL,
  `tbl_user_pid` INT NOT NULL,
  PRIMARY KEY (`pid`),
  INDEX `fk_tbl_location_log_tbl_user1_idx` (`tbl_user_pid` ASC),
  CONSTRAINT `fk_tbl_location_log_tbl_user1`
    FOREIGN KEY (`tbl_user_pid`)
    REFERENCES `mydb`.`tbl_user` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`servert`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`servert` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_cctv`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_cctv` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_convenience_store`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_convenience_store` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_police_station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_police_station` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_fire_station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_fire_station` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_shelter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_shelter` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tbl_group_has_tbl_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tbl_group_has_tbl_user` (
  `tbl_group_pid` INT NOT NULL,
  `tbl_user_pid` INT NOT NULL,
  PRIMARY KEY (`tbl_group_pid`, `tbl_user_pid`),
  INDEX `fk_tbl_group_has_tbl_user_tbl_user1_idx` (`tbl_user_pid` ASC),
  INDEX `fk_tbl_group_has_tbl_user_tbl_group1_idx` (`tbl_group_pid` ASC),
  CONSTRAINT `fk_tbl_group_has_tbl_user_tbl_group1`
    FOREIGN KEY (`tbl_group_pid`)
    REFERENCES `mydb`.`tbl_group` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_group_has_tbl_user_tbl_user1`
    FOREIGN KEY (`tbl_user_pid`)
    REFERENCES `mydb`.`tbl_user` (`pid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
