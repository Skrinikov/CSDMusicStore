
insert into news_feeds (link,source,visible) Values('http://rss.cbc.ca/lineup/topstories.xml', 'cbc', 1);
insert into news_feeds (link,source,visible) Values('http://rss.cbc.ca/lineup/world.xml', 'cbc', 0);
insert into news_feeds (link,source,visible) Values('http://rss.cbc.ca/lineup/canada.xml', 'cbc', 1);



insert into surveys(question,visible) Values ('Fav Programming Lang', 1); 
insert into surveys(question,visible) Values ('Fav Number', 0); 
insert into surveys(question,visible) Values ('Fav Place', 1); 

insert into survey_choices(survey_id,choice,num_votes) values(1, 'Java', 50);
insert into survey_choices(survey_id,choice,num_votes) values(1, 'C#', 30);
insert into survey_choices(survey_id,choice,num_votes) values(1, 'C++', 20);
insert into survey_choices(survey_id,choice,num_votes) values(1, 'Javascript', 2);

insert into survey_choices(survey_id,choice,num_votes) values(2, '5', 10);
insert into survey_choices(survey_id,choice,num_votes) values(2, '8', 60);
insert into survey_choices(survey_id,choice,num_votes) values(2, '10', 2);
insert into survey_choices(survey_id,choice,num_votes) values(2, '753', 9);

insert into survey_choices(survey_id,choice,num_votes) values(3, 'Home', 10);
insert into survey_choices(survey_id,choice,num_votes) values(3, 'Not Here', 50);
insert into survey_choices(survey_id,choice,num_votes) values(3, 'In a Cave', 100);
insert into survey_choices(survey_id,choice,num_votes) values(3, 'School', 0);