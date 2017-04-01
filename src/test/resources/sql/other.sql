insert into news_feeds (link, source, visible) values ('http://rss.cbc.ca/lineup/topstories.xml', 'CBC', 1);
insert into news_feeds (link,source,visible) values('http://rss.cbc.ca/lineup/world.xml', 'CBC', 0);
insert into news_feeds (link,source,visible) values('http://rss.cbc.ca/lineup/canada.xml', 'CBC', 1);

insert into banner_ads (link, source, visible) values ('https://www.dawsoncollege.qc.ca/', 'https://upload.wikimedia.org/wikipedia/commons/c/c6/Dawson_College_08.jpg', 1);
insert into banner_ads (link, source, visible) values ('http://www.grevin-montreal.com/fr/actus/l-univers-d-asterix', 'http://www.socwall.com/images/wallpapers/20346-1920x1080.jpg', 1);

insert into surveys (question, visible) values ('What is your favourite music genre?', 1);
insert into surveys (question, visible) values ('What is your favourite book genre?', 0);
insert into surveys(question,visible) values ('What is your favourite programming language', 1); 
insert into surveys(question,visible) values ('What is your favourite number', 0); 
insert into surveys(question,visible) values ('What is your favourite place', 1); 

insert into survey_choices (survey_id, choice, num_votes) values (1, 'rock', 0);
insert into survey_choices (survey_id, choice, num_votes) values (1, 'pop', 0);
insert into survey_choices (survey_id, choice, num_votes) values (1, 'rap/hip hop', 0);
insert into survey_choices (survey_id, choice, num_votes) values (1, 'punk', 0);

insert into survey_choices (survey_id, choice, num_votes) values (2, 'fantasy/science fiction', 0);
insert into survey_choices (survey_id, choice, num_votes) values (2, 'detective fiction', 0);
insert into survey_choices (survey_id, choice, num_votes) values (2, 'action/adventures', 0);
insert into survey_choices (survey_id, choice, num_votes) values (2, 'travel', 0);

insert into survey_choices(survey_id,choice,num_votes) values(3, 'Java', 50);
insert into survey_choices(survey_id,choice,num_votes) values(3, 'C#', 30);
insert into survey_choices(survey_id,choice,num_votes) values(3, 'C++', 20);
insert into survey_choices(survey_id,choice,num_votes) values(3, 'Javascript', 2);

insert into survey_choices(survey_id,choice,num_votes) values(4, '5', 10);
insert into survey_choices(survey_id,choice,num_votes) values(4, '8', 60);
insert into survey_choices(survey_id,choice,num_votes) values(4, '10', 2);
insert into survey_choices(survey_id,choice,num_votes) values(4, '753', 9);

insert into survey_choices(survey_id,choice,num_votes) values(5, 'Home', 10);
insert into survey_choices(survey_id,choice,num_votes) values(5, 'Not Here', 50);
insert into survey_choices(survey_id,choice,num_votes) values(5, 'In a Cave', 100);
insert into survey_choices(survey_id,choice,num_votes) values(5, 'School', 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, city, province_id, country, postal_code, home_tel, email, is_admin) values('DawsonConsumer', 0x70f23d706a4cc32b1d009aa8b423482f85f445327438ed588faade9f0c41ee60865867c3ef835c825379b48574339719b49478ff2591b72824ab3962ca8caef4, 'sugjqj200kpj3ku0e5kfv5v9f46r', 'Mr', 'Ken', 'Fogel', 'Dawson College', '3040 Sherbrooke St. W', 'Westmount', 11, 'Canada', 'H3Z 1A4', '514-931-8731', 'cst.send@gmail.com', 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, city, province_id, country, postal_code, home_tel, email, is_admin) values('DawsonManager', 0x2fa6940b864ef9dfd4c142ac33cf4f7d0b513836e59625c4bc02fdc5cfa38c0122e4e20336a61e48d85004ec2c025771a14a49436f5f5cb057d8be425228adac, 'aeopvtr7698cjr8ilka155u66lmg', 'Mr', 'Ken', 'Fogel', 'Dawson College', '3040 Sherbrooke St. W', 'Westmount', 11, 'Canada', 'H3Z 1A4', '514-931-8731', 'cst.receive@gmail.com', 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, city, province_id, country, postal_code, home_tel, email, is_admin) values('admin', 0x15014a65121d18e8b69666fa2202ac2f4a1a6d9f4dc14a5fd8e0ee6aa50a17d7c2734fdd564ebf6c41170cc0448a579bb9f3da43e5c234e029e0713449c7afb6, 'jgiqon62idp8o9lc4eneud3ndcpd', 'TLord', 'John', 'Smith', 'Noble Corporation', '1151 Richmond St', 'London', 9, 'Canada', 'N6A 3K7', '(519) 661-2111', 'fractals.inc@gmail.com', 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, city, province_id, country, postal_code, home_tel, email, is_admin) values('selenium', 0x36f22f7a0eafd9a19628ad1a4fd554a3afb1b0aff8ea76e5ffe2478b9d52849a0fdd86f7b18d762d50e790012a411ee7ce0f9c1d593a9bbc699535de5f0cdb5, 'a8a9kiq2mnsm04hsq8i3vsaqchrg', 'count', 'Selenium', 'HQ', 'Sauce Labs', '2325 Rue de l\'Université', 'Ville de Québec', 11, 'Canada', 'G1V 0A6', '(418) 656-2131', 'fractals.inc@gmail.com', 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, city, province_id, country, postal_code, home_tel, email, is_admin) values('user', 0xe3bcd4c267103b880694521c343cd620f37861d6632bdf8e250c2673a9e206f6cb8e0798123a77599352834f58b75f725d090bcbb3409e69e5a8536d13427b7a, '11n9m26p1m47o261bhu3rat1g9ui', 'mr', 'User', 'Client', 'Dawson College', '3040 Sherbrooke St. W', 'Westmount', 11, 'Canada', 'H3Z 1A4', '514-931-8731', 'cst.receive@gmail.com', 0);



