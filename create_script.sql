-- MySQL Script generated by MySQL Workbench
-- Thu Apr  2 10:30:43 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema recipes
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `recipes`;

-- -----------------------------------------------------
-- Schema recipes
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `recipes` DEFAULT CHARACTER SET utf8;
USE `recipes`;

-- -----------------------------------------------------
-- Table `recipes`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`Users`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(45) NOT NULL,
    `email`    VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `role`     VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`recipes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`recipes`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(45)  NULL,
    `description` VARCHAR(500) NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`ingredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`ingredients`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`recipes_has_ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`recipes_has_ingredient`
(
    `recipe_id`     INT         NOT NULL,
    `ingredient_id` INT         NOT NULL,
    `quantity`      float       NOT NULL,
    `unit`          VARCHAR(45) NOT NULL,
    PRIMARY KEY (`recipe_id`, `ingredient_id`),
    INDEX `fk_recipes_has_ingredient_ingredient1_idx` (`ingredient_id` ASC) VISIBLE,
    INDEX `fk_recipes_has_ingredient_recipes_idx` (`recipe_id` ASC) VISIBLE,
    CONSTRAINT `fk_recipes_has_ingredient_recipes`
        FOREIGN KEY (`recipe_id`)
            REFERENCES `recipes`.`recipes` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_recipes_has_ingredient_ingredient1`
        FOREIGN KEY (`ingredient_id`)
            REFERENCES `recipes`.`ingredients` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`favorites`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`favorites`
(
    `Users_id`   INT NOT NULL,
    `recipes_id` INT NOT NULL,
    PRIMARY KEY (`Users_id`, `recipes_id`),
    INDEX `fk_Users_has_recipes_recipes1_idx` (`recipes_id` ASC) VISIBLE,
    INDEX `fk_Users_has_recipes_Users1_idx` (`Users_id` ASC) VISIBLE,
    CONSTRAINT `fk_Users_has_recipes_Users1`
        FOREIGN KEY (`Users_id`)
            REFERENCES `recipes`.`Users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_Users_has_recipes_recipes1`
        FOREIGN KEY (`recipes_id`)
            REFERENCES `recipes`.`recipes` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
