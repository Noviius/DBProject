-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `simple_company` DEFAULT CHARACTER SET utf8 ;
USE `simple_company` ;

-- -----------------------------------------------------
-- Table `simple_company`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `prodName` VARCHAR(45) NOT NULL,
  `prodDescription` VARCHAR(1024) NOT NULL,
  `prodCategory` INT NOT NULL,
  `prodUPC` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `dob` DATE NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`purchase` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `productID` INT NOT NULL,
  `customerID` INT NOT NULL,
  `purchaseDate` DATE NOT NULL,
  `purchaseAmount` DECIMAL(9,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `productId_idx` (`productID` ASC) VISIBLE,
  INDEX `customerId_idx` (`customerID` ASC) VISIBLE,
  CONSTRAINT `productId_`
    FOREIGN KEY (`productID`)
    REFERENCES `simple_company`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `customerId_`
    FOREIGN KEY (`customerID`)
    REFERENCES `simple_company`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`address` (
  `address1` VARCHAR(45) NOT NULL,
  `address2` VARCHAR(45) NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  `customer_id` INT NOT NULL,
  INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `simple_company`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`creditcard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`creditcard` (
  `name` VARCHAR(45) NOT NULL,
  `ccNumber` VARCHAR(45) NOT NULL,
  `expDate` VARCHAR(45) NOT NULL,
  `securityCode` VARCHAR(45) NOT NULL,
  `customer_id` INT NOT NULL,
  INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `customer_idCX`
    FOREIGN KEY (`customer_id`)
    REFERENCES `simple_company`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
