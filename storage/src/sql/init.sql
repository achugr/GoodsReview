/*
Created: 04.10.2011
Modified: 02.11.2011
Project: goodsreview_permanent
Model: GoodsReviews
Author: Sergey Serebryakov
Database: MySQL 5.5
*/

DROP DATABASE IF EXISTS goodsreview_permanent;
CREATE DATABASE goodsreview_permanent CHARACTER SET utf8 COLLATE utf8_bin;

use goodsreview_permanent;

-- Drop relationships section -------------------------------------------------

-- ALTER TABLE query DROP FOREIGN KEY query like unique
-- ;
-- ALTER TABLE thesis DROP FOREIGN KEY thesis_like_unique
-- ;
-- ALTER TABLE category DROP FOREIGN KEY has_parent
-- ;
-- ALTER TABLE product DROP FOREIGN KEY belongs_to
-- ;
-- ALTER TABLE thesis DROP FOREIGN KEY extracted_from
-- ;
-- ALTER TABLE review DROP FOREIGN KEY provides
-- ;
-- ALTER TABLE review DROP FOREIGN KEY has_review
-- ;
-- ALTER TABLE shop_link DROP FOREIGN KEY hosts
-- ;
-- ALTER TABLE specification_value DROP FOREIGN KEY is_value_of
-- ;
-- ALTER TABLE specification_value DROP FOREIGN KEY has_spec
-- ;
-- ALTER TABLE shop_link DROP FOREIGN KEY has_link_to
-- ;
--
--


-- Drop keys for tables section -------------------------------------------------

-- ALTER TABLE query_unique DROP PRIMARY KEY
-- ALTER TABLE query_unique DROP KEY id
-- ALTER TABLE thesis_unique DROP PRIMARY KEY
-- ALTER TABLE vote DROP PRIMARY KEY
-- ALTER TABLE query DROP PRIMARY KEY
-- ALTER TABLE query DROP KEY id
-- ALTER TABLE category DROP PRIMARY KEY
-- ALTER TABLE thesis DROP PRIMARY KEY
-- ALTER TABLE source DROP PRIMARY KEY
-- ALTER TABLE review DROP PRIMARY KEY
-- ALTER TABLE shop DROP PRIMARY KEY
-- ALTER TABLE shop_link DROP PRIMARY KEY
-- ALTER TABLE specification_value DROP PRIMARY KEY
-- ALTER TABLE specification_name DROP PRIMARY KEY
-- ALTER TABLE product DROP PRIMARY KEY



-- Drop tables section ---------------------------------------------------

DROP TABLE IF EXISTS vote
;
DROP TABLE IF EXISTS query_unique
;
DROP TABLE IF EXISTS thesis_unique
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
DROP TABLE IF EXISTS product
;

-- Create tables section -------------------------------------------------

-- Table product

CREATE TABLE product
(
  id Int NOT NULL AUTO_INCREMENT,
  category_id Int NOT NULL,
  name Varchar(100) NOT NULL,
  popularity Int UNSIGNED
  COMMENT 'Сколько раз смотрели товар',
  description Text,
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
  url Varchar(300) NOT NULL,
  price Double,
 PRIMARY KEY (id)
)
;

-- Table shop

CREATE TABLE shop
(
  id Int NOT NULL AUTO_INCREMENT,
  name Varchar(100) NOT NULL,
  main_page_url Varchar(100) NOT NULL,
 PRIMARY KEY (id)
)
;

-- Table review

CREATE TABLE review
(
  id Int NOT NULL AUTO_INCREMENT,
  product_id Int NOT NULL,
  source_id Int NOT NULL,
  content Text NOT NULL,
  date Timestamp NOT NULL,
  author Varchar(100),
  description Text,
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
  main_page_url Varchar(100) NOT NULL,
 PRIMARY KEY (id)
)
;

-- Table thesis

CREATE TABLE thesis
(
  id Int NOT NULL AUTO_INCREMENT,
  review_id Int NOT NULL,
  thesis_unique_id Int NOT NULL,
  content Varchar(100) NOT NULL,
  frequency Int UNSIGNED NOT NULL DEFAULT 0,
  positivity Double,
  importance Double,
 PRIMARY KEY (id)
)
;

-- Table category

CREATE TABLE category
(
  id Int NOT NULL AUTO_INCREMENT,
  name Varchar(100) NOT NULL,
  description Varchar(100) NOT NULL,
  parent_category_id Int NOT NULL,
 PRIMARY KEY (id)
)
;

-- Table query

CREATE TABLE query
(
  id Int UNSIGNED NOT NULL AUTO_INCREMENT,
  query_unique_id Int UNSIGNED NOT NULL,
  text Varchar(200) NOT NULL,
  date Timestamp NOT NULL,
 PRIMARY KEY (id),
 UNIQUE id (id)
)
;

-- Table thesis_unique

CREATE TABLE thesis_unique
(
  id Int NOT NULL AUTO_INCREMENT,
  content Varchar(100) NOT NULL
  COMMENT 'acaca',
  frequency Int UNSIGNED NOT NULL,
  last_scan Timestamp NOT NULL,
  positivity Double,
  importance Double,
 PRIMARY KEY (id)
)
;

-- Table query_unique

CREATE TABLE query_unique
(
  id Int UNSIGNED NOT NULL AUTO_INCREMENT,
  text Varchar(200) NOT NULL,
  last_scan Timestamp NOT NULL,
  frequency Int NOT NULL,
 PRIMARY KEY (id),
 UNIQUE id (id)
)
;

-- Table vote

CREATE TABLE vote
(
  id Int NOT NULL AUTO_INCREMENT,
  candidate_id Int NOT NULL,
  type Int NOT NULL,
  date Timestamp NOT NULL,
  agreement Double NOT NULL,
  importance Double NOT NULL,
  positivity Double NOT NULL,
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

ALTER TABLE thesis ADD CONSTRAINT thesis_like_unique FOREIGN KEY (thesis_unique_id) REFERENCES thesis_unique (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE query ADD CONSTRAINT query_like_unique FOREIGN KEY (query_unique_id) REFERENCES query_unique (id) ON DELETE NO ACTION ON UPDATE NO ACTION
;




