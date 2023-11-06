CREATE SCHEMA `spring` ;
CREATE TABLE `spring`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `category` VARCHAR(45) NULL,
  `price` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
