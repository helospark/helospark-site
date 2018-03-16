-- article 1
INSERT INTO article_detail (id, modification_time, description, data) values (2, '2017-01-02 00:00:01', 'description', RAWTOHEX('details'));
INSERT INTO article (id, creation_time, details_id, category_id, url_id, title) values (1, '2017-01-01 00:00:01', 2, 1, 'pretty-url-id', 'Pretty id');

-- article 2
INSERT INTO article_detail (id, modification_time, description, data) values (3, '2017-01-02 00:00:01', 'description', RAWTOHEX('details'));
INSERT INTO article (id, creation_time, details_id, category_id, url_id, title) values (2, '2017-01-01 00:00:01', 3, 1, 'pretty-url-id', 'Pretty id');

-- article 3
INSERT INTO article_detail (id, modification_time, description, data) values (4, '2017-01-02 00:00:01', 'description', RAWTOHEX('details'));
INSERT INTO article (id, creation_time, details_id, category_id, url_id, title) values (3, '2017-01-01 00:00:01', 4, 1, 'pretty-url-id', 'Pretty id');

-- user
INSERT INTO application_user (id, username, password) values (1, 'user', 'password');
INSERT INTO application_user (id, username, password) values (2, 'user2', 'password');
INSERT INTO application_user (id, username, password) values (3, 'user3', 'password');

-- predifined comments
INSERT INTO article_comment (id, text, comment_time, article_id, commenter_id) values (1, 'comment 1','2017-01-02 00:00:01', 1, 1);
INSERT INTO article_comment (id, text, comment_time, article_id, commenter_id) values (2, 'comment 2','2017-01-03 00:00:01', 1, 1);
INSERT INTO article_comment (id, text, comment_time, article_id, commenter_id) values (3, 'comment 3','2017-01-03 00:00:01', 3, 1);

INSERT INTO article_comment_vote (user_id, comment_id, direction) values (1, 3, 1);
INSERT INTO article_comment_vote (user_id, comment_id, direction) values (2, 3, 1);
INSERT INTO article_comment_vote (user_id, comment_id, direction) values (3, 3, -1);