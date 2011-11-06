INSERT INTO category (name, description, parent_category_id) VALUES("All Products","Everything",1);
INSERT INTO category (name, description, parent_category_id) VALUES("Electronics","Gadgets",1);
INSERT INTO category (name, description, parent_category_id) VALUES("Notebooks","",2);

INSERT INTO product (name, popularity, category_id) VALUES("Asus eee pc", 1, 3);
INSERT INTO product (name, popularity, category_id) VALUES("Lenovo x220", 1, 3);
INSERT INTO product (name, popularity, category_id) VALUES("Asus u30", 1, 3);
INSERT INTO product (name, popularity, category_id) VALUES("Apple macbook air", 1, 3);
INSERT INTO product (name, popularity, category_id) VALUES("Sony vaio z", 1, 3);

INSERT INTO source (name, main_page_url) VALUES("Vans Home","brain.ru");

INSERT INTO review (product_id, content, author, date, description, source_id, source_url, positivity, importance, votes_yes, votes_no) VALUES(1,"Not bad notebook. It is very cheap, but have small batery","I am",'2011-10-01',NULL,1,NULL,NULL,NULL,NULL,NULL);
INSERT INTO review (product_id, content, author, date, description, source_id, source_url, positivity, importance, votes_yes, votes_no) VALUES(1,"SPAM","I am",'2011-10-01', NULL,1,NULL,NULL,NULL,NULL,NULL);

INSERT INTO thesis (review_id, thesis_unique_id, content, frequency, positivity, importance) VALUES(1,NULL,"Not bad notebook",2,5.0,1.0);
INSERT INTO thesis (review_id, thesis_unique_id, content,frequency, positivity, importance) VALUES(1,NULL,"It is very cheap",3,-1.0,10.0);
INSERT INTO thesis (review_id, thesis_unique_id, content, frequency, positivity, importance) VALUES(1,NULL, "but have small batery",5,-10.0,10.0);


