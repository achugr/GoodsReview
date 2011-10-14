/*
Created: 04.10.2011
Modified: 11.10.2011
Project: review_permanent
Model: GoodsReviews
Author: Sergey Serebryakov
Database: MySQL 5.5
*/

-- Drop tables section ---------------------------------------------------

DROP TABLE IF EXISTS vote
;
DROP TABLE IF EXISTS query
;
DROP TABLE IF EXISTS category
;
DROP TABLE IF EXISTS thesis
;
DROP TABLE IF EXISTS source
;
DROP TABLE IF EXISTS review
;
DROP TABLE IF EXISTS shop
;
DROP TABLE IF EXISTS shop_link
;
DROP TABLE IF EXISTS specification_value
;
DROP TABLE IF EXISTS specification_name
;
DROP TABLE IF EXISTS article
;

-- Create tables section -------------------------------------------------

-- Table article

CREATE TABLE article
(
  id Int NOT NULL AUTO_INCREMENT,
  category_id Int,
  name Varchar(100),
  description Text,
  popularity Int UNSIGNED NOT NULL
  COMMENT 'Сколько раз смотрели товар',
 PRIMARY KEY (id)
)
;

-- Table specification_name

CREATE TABLE specification_name
(
  id Int NOT NULL AUTO_INCREMENT,
  name Varchar(100) NOT NULL,
  unit Varchar(20),
 PRIMARY KEY (id)
)
;

-- Table specification_value

CREATE TABLE specification_value
(
  id Int NOT NULL AUTO_INCREMENT,
  article_id Int NOT NULL,
  spec_name_id Int NOT NULL,
  value Varchar(100) NOT NULL,
 PRIMARY KEY (id)
)
;

-- Table shop_link

CREATE TABLE shop_link
(
  id Int NOT NULL AUTO_INCREMENT,
  article_id Int NOT NULL,
  shop_id Int NOT NULL,
  price Double,
  url Varchar(300),
 PRIMARY KEY (id)
)
;

-- Table shop

CREATE TABLE shop
(
  id Int NOT NULL AUTO_INCREMENT,
  name Varchar(100) NOT NULL,
  main_page_url Varchar(100),
 PRIMARY KEY (id)
)
;

-- Table review

CREATE TABLE review
(
  id Int NOT NULL AUTO_INCREMENT,
  article_id Int NOT NULL,
  content Text,
  author Varchar(100),
  date Timestamp NULL,
  source_id Int NOT NULL,
  source_url Varchar(100),
  score Int,
 PRIMARY KEY (id)
)
;

-- Table source

CREATE TABLE source
(
  id Int NOT NULL AUTO_INCREMENT,
  name Varchar(100) NOT NULL,
  main_page_url Varchar(100),
 PRIMARY KEY (id)
)
;

-- Table thesis

CREATE TABLE thesis
(
  id Int NOT NULL AUTO_INCREMENT,
  review_id Int,
  content Text,
  score Int,
 PRIMARY KEY (id)
)
;

-- Table category

CREATE TABLE category
(
  id Int NOT NULL AUTO_INCREMENT,
  name Varchar(100),
  description Varchar(100),
  parent_category_id Int NOT NULL,
 PRIMARY KEY (id)
)
;

-- Table query

CREATE TABLE query
(
  id Int NOT NULL AUTO_INCREMENT,
  text Varchar(300),
  popularity Int,
 PRIMARY KEY (id)
)
;

-- Table vote

CREATE TABLE vote
(
  id Int NOT NULL AUTO_INCREMENT,
  candidate_id Int,
  type Int,
  date Timestamp NULL,
  score Int,
  importance Int,
 PRIMARY KEY (id)
)
;

-- Create relationships section -------------------------------------------------

ALTER TABLE shop_link ADD CONSTRAINT has link to FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE specification_value ADD CONSTRAINT has spec FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE specification_value ADD CONSTRAINT is value of FOREIGN KEY (spec_name_id) REFERENCES specification_name (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE shop_link ADD CONSTRAINT hosts FOREIGN KEY (shop_id) REFERENCES shop (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE review ADD CONSTRAINT has review FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE review ADD CONSTRAINT provides FOREIGN KEY (source_id) REFERENCES source (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE thesis ADD CONSTRAINT extracted from FOREIGN KEY (review_id) REFERENCES review (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE article ADD CONSTRAINT belongs to FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE category ADD CONSTRAINT has parent FOREIGN KEY (parent_category_id) REFERENCES category (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

