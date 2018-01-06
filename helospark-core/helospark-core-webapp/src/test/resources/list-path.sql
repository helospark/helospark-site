-- Common IT test data
INSERT INTO article_detail (id, modification_time, description, data) values (2, '2017-01-02 00:00:01', 'description', RAWTOHEX('details'));
INSERT INTO article (id, creation_time, details_id, category_id, url_id, title) values (1, '2017-01-01 00:00:01', 2, 1, 'pretty-url-id', 'Pretty id');


INSERT INTO article_detail (id, modification_time, description, data) values (3, '2017-01-02 00:00:01', 'BlogEntry1', RAWTOHEX('BlogEntry1'));
INSERT INTO article (id, creation_time, details_id, category_id, url_id, title) values (2, '2017-01-01 00:00:01', 3, 3, 'pretty-url-id-2', 'Pretty id');
INSERT INTO article_detail (id, modification_time, description, data) values (4, '2017-01-02 00:00:01', 'BlogEntry2', RAWTOHEX('BlogEntry2'));
INSERT INTO article (id, creation_time, details_id, category_id, url_id, title) values (3, '2017-01-01 00:00:01', 4, 3, 'pretty-url-id-3', 'Pretty id');
INSERT INTO article_detail (id, modification_time, description, data) values (5, '2017-01-02 00:00:01', 'BlogEntry3', RAWTOHEX('BlogEntry3'));
INSERT INTO article (id, creation_time, details_id, category_id, url_id, title) values (4, '2017-01-01 00:00:01', 5, 3, 'pretty-url-id-4', 'Pretty id');