/*
Created: 04.10.2011
Modified: 23.10.2011
Project: goodsreview_permanent
Model: GoodsReviews
Author: Sergey Serebryakov
Database: MySQL 5.5
*/

-- Drop database section -----------------------------------------------

DROP DATABASE IF EXISTS goodsreview_permanent;

-- Create database section ---------------------------------------------

CREATE DATABASE goodsreview_permanent CHARACTER SET utf8 COLLATE utf8_bin;

USE goodsreview_permanent;

-- Create tables section -------------------------------------------------

-- Table product

CREATE TABLE product
(
  id Int NOT NULL AUTO_INCREMENT,
  category_id Int NOT NULL,
  name Varchar(100) NOT NULL,
  description Text,
  popularity Int UNSIGNED NOT NULL
  COMMENT "count of product's views ",
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
  product_id Int NOT NULL,
  spec_name_id Int NOT NULL,
  value Varchar(100) NOT NULL,
 PRIMARY KEY (id)
)
;

-- Table shop_link

CREATE TABLE shop_link
(
  id Int NOT NULL AUTO_INCREMENT,
  product_id Int NOT NULL,
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
  product_id Int NOT NULL,
  content Text,
  author Varchar(100),
  date Timestamp NULL,
  description Text,
  source_id Int NOT NULL,
  source_url Varchar(100),
  positivity Double,
  importance Double,
  votes_yes Int,
  votes_no Int,
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
  content Varchar(500),
  positivity Double,
  importance Double,
  votes_yes Int,
  votes_no Int,
 PRIMARY KEY (id)
)
;

-- Table category

CREATE TABLE category
(
  id Int NOT NULL AUTO_INCREMENT,
  name Varchar(100) NOT NULL,
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
  date Timestamp NULL,
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
  agreement Double,
  importance Double,
  positivity Double,
 PRIMARY KEY (id)
)
;

-- Create relationships section -------------------------------------------------

ALTER TABLE shop_link ADD CONSTRAINT has_link_to FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;



ALTER TABLE specification_value ADD CONSTRAINT has_spec FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE specification_value ADD CONSTRAINT is_value_of FOREIGN KEY (spec_name_id) REFERENCES specification_name (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE shop_link ADD CONSTRAINT hosts FOREIGN KEY (shop_id) REFERENCES shop (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE review ADD CONSTRAINT has_review FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE review ADD CONSTRAINT provides FOREIGN KEY (source_id) REFERENCES source (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE thesis ADD CONSTRAINT extracted_from FOREIGN KEY (review_id) REFERENCES review (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE product ADD CONSTRAINT belongs_to FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE category ADD CONSTRAINT has_parent FOREIGN KEY (parent_category_id) REFERENCES category (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;
