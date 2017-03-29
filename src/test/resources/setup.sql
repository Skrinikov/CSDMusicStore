insert into genres (name) values ('rock');
insert into genres (name) values ('hip hop');
insert into genres (name) values ('pop');
insert into genres (name) values ('punk');
insert into genres (name) values ('rap');

INSERT INTO artists (name) VALUES ('Black Sabbath');
INSERT INTO artists (name) VALUES ('Blind Guardian');
INSERT INTO artists (name) VALUES ('Neil Young');
INSERT INTO artists (name) VALUES ('Deep Purple');
INSERT INTO artists (name) VALUES ('Ensiferum');
INSERT INTO artists (name) VALUES ('The Doors');
INSERT INTO artists (name) VALUES ('Iron Maiden');
INSERT INTO artists (name) VALUES ('Jethro Tull');
INSERT INTO artists (name) VALUES ('Judas Priest');
INSERT INTO artists (name) VALUES ('Kansas');
INSERT INTO artists (name) VALUES ('Led Zeppelin');
INSERT INTO artists (name) VALUES ('Pink Floyd');
INSERT INTO artists (name) VALUES ('Rush');
INSERT INTO artists (name) VALUES ('The Sisters of Mercy');
INSERT INTO artists (name) VALUES ('The Who');
INSERT INTO artists (name) VALUES ('Uriah Heep');
INSERT INTO artists (name) VALUES ('Queen');
INSERT INTO artists (name) VALUES ('The Rolling Stones');
INSERT INTO artists (name) VALUES ('Meat Loaf');
INSERT INTO artists (name) VALUES ('Blue Öyster Cult');

INSERT INTO artists (name) VALUES ('Drake');
INSERT INTO artists (name) VALUES ('Macklemore');
INSERT INTO artists (name) VALUES ('Eminem');

INSERT INTO artists (name) VALUES ('May\'n');
INSERT INTO artists (name) VALUES ('SEKAI NO OWARI');
INSERT INTO artists (name) VALUES ('Avril Lavigne');
INSERT INTO artists (name) VALUES ('Faky');
INSERT INTO artists (name) VALUES ('Celine Dion');
INSERT INTO artists (name) VALUES ('Delta Goodrem');
INSERT INTO artists (name) VALUES ('The Bangles');
INSERT INTO artists (name) VALUES ('Anna Tsuchiya');
INSERT INTO artists (name) VALUES ('AKINO with bless4');
INSERT INTO artists (name) VALUES ('Sting');
INSERT INTO artists (name) VALUES ('Kamen Rider Girls');
INSERT INTO artists (name) VALUES ('Walküre');
INSERT INTO artists (name) VALUES ('Kyo');

INSERT INTO artists (name) VALUES ('Sugarcult');
INSERT INTO artists (name) VALUES ('Ramones');
INSERT INTO artists (name) VALUES ('Green Day');
INSERT INTO artists (name) VALUES ('The Offspring');
INSERT INTO artists (name) VALUES ('blink-182');
INSERT INTO artists (name) VALUES ('Sex Pistols');
INSERT INTO artists (name) VALUES ('Social Distortion');
INSERT INTO artists (name) VALUES ('NOFX');
INSERT INTO artists (name) VALUES ('dropkick murphys');
INSERT INTO artists (name) VALUES ('Against Me!');
INSERT INTO artists (name) VALUES ('Fall Out Boy');
INSERT INTO artists (name) VALUES ('Paramore');
INSERT INTO artists (name) VALUES ('All Time Low');
INSERT INTO artists (name) VALUES ('My Chemical Romance');
INSERT INTO artists (name) VALUES ('Panic! At the Disco');
INSERT INTO artists (name) VALUES ('Good Charlotte');
INSERT INTO artists (name) VALUES ('New Found Glory');
INSERT INTO artists (name) VALUES ('Sum 41');
INSERT INTO artists (name) VALUES ('Rise Against');
INSERT INTO artists (name) VALUES ('AFI');
INSERT INTO artists (name) VALUES ('Anarbor');
INSERT INTO artists (name) VALUES ('Bowling For Soup');

INSERT INTO artists (name) VALUES ('N.W.A');
INSERT INTO artists (name) VALUES ('Tupac Shakur');
INSERT INTO artists (name) VALUES ('The Notorious B.I.G.');
INSERT INTO artists (name) VALUES ('Blackstreet');
INSERT INTO artists (name) VALUES ('Wu-Tang Clan');
INSERT INTO artists (name) VALUES ('Public Enemy');
INSERT INTO artists (name) VALUES ('Nas');
INSERT INTO artists (name) VALUES ('Gucci Mane');
INSERT INTO artists (name) VALUES ('Dr Dre');


INSERT INTO provinces (name, pst, gst, hst) VALUES ('Alberta', 0, 0.05, 0);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('British Columbia', 0.07, 0.05, 0);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Manitoba', 0.08, 0.05, 0);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('New Brunswick', 0, 0, 0.15);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Newfoundland and Labrador', 0, 0, 0.15);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Northwest Territories', 0, 0.05, 0);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Nova Scotia', 0, 0, 0.15);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Nunavut', 0, 0.05, 0);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Ontario', 0, 0, 0.13);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Prince Edward Island', 0, 0, 0.15);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Québec', 0.09975, 0.05, 0);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Saskatchewan', 0.05, 0.05, 0);
INSERT INTO provinces (name, pst, gst, hst) VALUES ('Yukon', 0, 0.05, 0);


INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (1, 'Heaven and Hell','1980-04-25','Vertigo', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (2, 'A Twist in the Myth','2006-09-01','Nuclear Blast', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (3, 'Re-ac-tor','1981-11-02','Reprise', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (4, 'Perfect Strangers','1984-10-29','Polydor', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (5, 'Ensiferum','2001-01-07','Spinefarm', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (6, 'Strange Days','1967-09-25','Elektra', '10', 4.99, 8.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (7, 'Seventh Son of a Seventh Son','1988-04-11','EMI', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (8, 'Thick as a Brick','1972-03-10','Reprise', '2', 1.99, 3.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (9, 'Painkiller','1990-09-03','Columbia', '10', 4.99, 8.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (10, 'Leftoverture','1976-10-01','Kirshner', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (11, 'Led Zeppelin III','1970-10-05','Atlantic', '10', 4.99, 8.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (11, 'Led Zeppelin IV','1971-11-08','Atlantic', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (12, 'The Wall','1979-11-30','Columbia', '6', 2.99, 4.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (13, 'A Farewell to Kings','1977-09-01','Anthem', '6', 2.99, 4.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (14, 'Vision Thing','1990-11-13','EastWest', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (15, 'Who\'s Next','1971-08-01','Track', '9', 4.59, 7.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (16, 'The Magicians Birthday','1972-11-01','Island', '8', 2.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (17, 'The Miracle','1989-05-22','Parlophone', '10', 4.99, 8.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (18, 'Aftermath','1966-06-20','London', '11', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (19, 'Bat Out of Hell','1977-10-21','Epic', '7', 2.99, 5.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (20, 'Agents of Fortune','1976-05-21','Columbia', '10', 4.99, 8.99);


INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (21, 'Views','2016-04-29','Republic Records', '8', 3.99, 6.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (22, 'The Heist','2012-10-04','Macklemore LLC', '11', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (23, 'Recovery','2010-06-18','Aftermath Entertainment', '6', 2.99, 4.99);


INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (24, 'Lion','2008-08-20','Victor Entertainment', '4', 1.99, 2.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (25, 'ANTI-HERO','2015-07-29','Toys Factory', '3', 0.99, 1.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (26, 'Let Go','2002-06-04','Arista', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (27, 'Candy','2015-10-25','Avex', '5', 1.49, 3.49);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (28, 'Falling into You','1996-03-08','Epic', '14', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (29, 'Delta','2007-10-20','Sony BMG', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (30, 'Different Light','1986-01-02','Columbia', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (31, 'Switch On!','2011-11-23','Avex', '4', 1.99, 2.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (32, 'Decennia','2015-03-25','Victor Entertainment', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (33, 'Brand New Day','1999-09-24','A&M', '10', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (34, 'Exploded','2014-03-19','Avex', '16', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (35, 'Walküre Attack!','2016-07-06','Victor Entertainment', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (35, 'Walküre Trap!','2016-09-28','Victor Entertainment', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (36, 'Le Chemin','2003-01-13','EMI', '11', 4.99, 9.99);


INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (37, 'Palm Trees And Power Lines','2004-04-13','Artemis Records', 13, 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (38, 'Ramones (extended)','1976-04-23','Sire', '22', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (39, 'Dookie','1994-02-01','Reprise', '15', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (40, 'Americana','1998-11-17','Columbia', '14', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (41, 'Enma Of The State','1999-06-01','MCA', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (42, 'Never Mind The Bollocks, Here\'s The Sex Pistols','1997-10-28','Virgin', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (43, 'Social Distortion','1990-03-27','Epic', '10', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (44, 'Punk In Drublic','1994-07-19','Epitaph', '17', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (45, 'The Meanest of Times Limited Edition','2007-09-09','Let Them Eat Vinyl', '20', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (46, 'New Wave (U.S. Version)','2007-07-10','Sire', '10', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (47, 'From Under The Cork Tree','2005-05-03','BMG DIrect Marketing Inc.', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (48, 'RIOT!','2007-06-12','Fueled By Ramen', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (49, 'So Wrong, It\'s Right (Deluxe Edition)','2008-07-29','Hopeless', '17', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (50, 'The Black Parade / Living With Ghosts (The 10th Anniversary Edition)','2016-09-23','Reorise', '25', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (51, 'A Fever You Can\'t Sweat Out','2005-09-27','Fueled By Ramen', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (52, 'The Young And The Hopeless','2002-10-01','Epic', '14', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (53, 'Sticks and Stones','2002-06-11','Island', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (54, 'All Killer No Filler','2001-05-08','Island', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (40, 'Rise And Fall, Rage And Grace','2008-06-17','Sony Records', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (50, 'Three Cheers For Sweet Revenge','2004-06-08','Reprise', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (55, 'Appeal To Reason','2008-10-07','DGC Records', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (56, 'DECEMBERUNDERGROUND','2006-06-06','Interscope', '12', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (39, 'American Idiot (Deluxe)','2004-09-20','Reprise', '9', 3.99, 7.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (57, 'Free Your Mind','2013-11-01','Modular Recordings', '7', 2.99, 5.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (58, 'A Hangover You Don\'t Deserve','2004-09-14','Jive Records', '18', 4.99, 9.99);


INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (59, 'Straight Outta Compton','1988-07-10','Ruthless Records', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (60, 'All Eyez On Me','1996-02-13','Death Row Records', '27', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (61, 'Ready To Die','1994-09-13','Bad Boy Records', '17', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (62, 'Another Level','1996-09-30','Interscope Records', '19', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (63, 'Enter the Wu-Tang (36 Chambers)','1993-09-09','Loud Records', '13', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (64, 'It Takes a Nation of Millions to Hold Us Back','1988-04-14','Def Jam', '16', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (60, 'Me against the world','1995-03-17','Interscope Records', '15', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (65, 'Illmatic','1994-04-19','Columbia Records', '10', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (66, 'The State vs. Radric Davis','2005-12-08','1017 Brick Squad', '20', 4.99, 9.99);
INSERT INTO albums (artist_id, title, release_date, record_label, num_tracks, cost_price, list_price) VALUES (67, '2001','1999-11-17','Aftermath', '22', 4.99, 9.99);



INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (1, 1, 'Children of the Sea','Ronnie James Dio, Tony Iommi, Geezer Butler, Bill Ward','05:34', '2', 'heaven_and_hell.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (2, 1, 'Turn the Page','André Olbrich, Hansi Kürsch','04:19', '3', 'a_twist_in_the_myth.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (3, 1, 'Shots','Neil Young','07:42', '8', 're-ac-tor.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (4, 1, 'Son of Alerik','Ritchie Blackmore','10:01', '10', 'perfect_strangers.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Treacherous Gods','Markus Toivonen, Jari Mäenpää','05:12', '9', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (6, 1, 'People Are Strange','Jim Morrison, Ray Manzarek, Robby Krieger, John Densmore','02:13', '7', 'strange_days.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (7, 1, 'Infinite Dreams','Steve Harris','06:08', '2', 'seventh_son_of_a_seventh_son.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (8, 1, 'Thick as a Brick, Part I','Ian Anderson','22:40', '1', 'thick_as_a_brick.jpg', 1.99, 2.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (9, 1, 'Hell Patrol','Glenn Tipton, Rob Halford, K. K. Downing','03:35', '2', 'painkiller.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (10, 1, 'Miracles Out of Nowhere','Kerry Livgren','06:28', '4', 'leftoverture.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Intro','Markus Toivonen, Jari Mäenpää','01:50', '1', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (11, 1, 'Immigrant Song','Jimmy Page, Robert Plan','02:26', '1', 'led_zeppelin_iii.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (12, 1, 'Stairway to Heaven','Jimmy Page, Robert Plant','08:02', '4', 'led_zeppelin_iv.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (13, 1, 'Hey You','Roger Waters','04:40', '1', 'the_wall.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (14, 1, 'A Farewell to Kings','Geddy Lee, Alex Lifeson, Neil Peart','05:51', '1', 'a_farewell_to_kings.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Hero in a Dream','Markus Toivonen, Jari Mäenpää','03:40', '2', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (15, 1, 'I Was Wrong','Andrew Eldritch','06:03', '8', 'vision_thing.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (16, 1, 'Baba O\'Riley','Pete Townshend','05:08', '1', 'whos_next.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Token of Time','Jari Mäenpää','04:16', '3', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Guardians of Fate','Markus Toivonen, Jari Mäenpää','03:34', '4', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Old Man','Markus Toivonen, Jari Mäenpää','05:33', '5', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Little Dreamer','Markus Toivonen, Jari Mäenpää, Oliver Fokin','05:21', '6', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Abandoned','Markus Toivonen, Jari Mäenpää','06:50', '7', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Windrider','Markus Toivonen, Jari Mäenpää','05:41', '8', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Eternal Wait','Markus Toivonen, Jari Mäenpää','05:14', '10', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Battle Song','Markus Toivonen, Jari Mäenpää','03:20', '11', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (5, 1, 'Goblins\' Dance','Valtias Mustatuuli','04:29', '12', 'ensiferum.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (17, 1, 'Blind Eye','Ken Hensley','03:33', '3', 'the_magicians_birthday.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (18, 1, 'I Want It All','Brian May','04:41', '4', 'the_miracle.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (19, 1, 'Paint It, Black','Mick Jagger, Keith Richards','03:22', '1', 'aftermath.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (20, 1, 'Bat Out of Hell','Jim Steinman','09:48', '1', 'bat_out_of_hell.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (21, 1, '(Don\'t Fear) The Reaper','Donald Roeser','05:08', '3', 'agents_of_fortune.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (8, 1, 'Thick as a Brick, Part II','Ian Anderson','21:06', '2', 'thick_as_a_brick.jpg', 1.99, 2.99);



INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'Hotling Bling','Aubrey Graham, Paul Jefferies, Timmy Thomas ','02:33', '1', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'For Free','Khaled Khaled, Aubrey Graham Paul, Jefferies','03:53', '2', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'Hold on We\'re Going Home ','Aubrey Graham, Noah Shebib','02:57', '3', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'Energy ','Aubrey Graham','03:51', '4', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'Started from the Bottom','Aubrey Graham, Noah Shebib','03:25', '5', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'Jumpman','Aubrey Graham,  Wilburn','03:03', '6', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'One Dance','Aubrey Drake Graham, Paul Jefferies ','03:12', '7', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (22, 2, 'Headlines ','Aubrey Graham','04:36', '8', 'views.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Can\'t Hold Us','Ben Haggerty, Ryan Lewis, Ray Dalton','02:33', '1', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Thrift Shop','Ben Haggerty, Ryan Lewis','03:51', '2', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Make the Money','Ryan Lewis','05:51', '3', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'White Walls','Ben Haggerty, Ryan Lewis','04:58', '4', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Wednesday Morning','Ben Haggerty, Ryan Lewis, Ray Dalton','03:01', '5', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Thin Line','Ryan Lewis','05:58', '6', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'The Town','Ryan Lewis','03:51', '7', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Otherside','Flea Frusciante, Kiedis Smith','04:36', '8', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Oh My Oh','Kiedis Smith','03:51', '9', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'Downtown','Jacob Dutton, Eric Nally','01:50', '10', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (23, 2, 'White Privilege','Jamila Woods, Hollis Wong-Wear','01:42', '11', 'the_heist.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (24, 2, 'Rap God','Marshall Mathers, Bigram Zayas','06:03', '1', 'recovery.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (24, 2, 'Lose Yourself','Marshall Mathers','02:51', '2', 'recovery.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (24, 2, 'Not Afraid','Marshall Mathers, Luis Resto, Matthew Samuels','04:45', '3', 'recovery.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (24, 2, 'Mockingbird','Marshall Mathers, Luis Resto','05:54', '4', 'recovery.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (24, 2, 'Stan','Paul Harmon','02:33', '5', 'recovery.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (24, 2, 'Without Me','Marshall Mathers, Jeff Bass','02:12', '6', 'recovery.jpg', 0.5, 0.99);



INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (25, 3, 'Northern Cross','Yoko Kanno','5:18', '2', 'lion.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (26, 3, 'ANTI-HERO','Nakajin, Fukase','3:35', '1', 'anti-hero.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (27, 3, 'Complicated','Avril Lavigne, Lauren Christy, Scott Spock, Graham Edwards','04:00', '2', 'let_go.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (28, 3, 'Pretty','Kanata Okajima, Becky Jeramy, Keir MacKenzie, Keir MacCulloch','03:11', '1', 'candy.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (28, 3, 'Afterglow','Kanata Okajima, Leon Paul Palmen','03:07', '2', 'candy.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (28, 3, 'Are You OK?','Kanata Okajima, Chris Wahle, Lisa Desmond','03:32', '3', 'candy.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (28, 3, 'You','Erik Lidbom, Anne Judith Wik, Nermin Harambasic','03:20', '4', 'candy.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (28, 3, 'Candy','HIRO, Gifty Dankwah, Bruce Fielder','03:23', '5', 'candy.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (29, 3, 'Make You Happy','Andy Marvel','04:31', '4', 'falling_into_you.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (30, 3, 'Believe Again','Delta Goodrem, Stuart Crichton, Tommy Lee James, Brian McFadden','05:48', '1', 'delta.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (30, 3, 'In This Life','Delta Goodrem, Stuart Crichton, Tommy Lee James, Brian McFadden','03:47', '2', 'delta.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (31, 3, 'Walk Like an Egyptian','Liam Sternberg','03:24', '4', 'different_light.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (32, 3, 'Switch On!','tatsuo','03:23', '1', 'switch_on.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (33, 3, 'Paradoxical Zoo','Yoko Kanno','04:34', '1', 'decennia.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (33, 3, 'Extra Magic Hour','Tokisawa Nao','04:14', '7', 'decennia.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (33, 3, 'Jet Coaster Ride','AKASHI','04:14', '8', 'decennia.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (34, 3, 'A Thousand Years','Kipper Sting','05:58', '1', 'brand_new_day.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (34, 3, 'After the Rain Has Fallen','Kipper Sting','05:03', '4', 'brand_new_day.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (35, 3, 'E-X-A (ExcitingxAttitude)','tatsuo','03:52', '3', 'exploded.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (35, 3, 'Go Get \'Em','Shuhei Naruse','04:42', '6', 'exploded.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (36, 3, 'GIRAFFE BLUES','h-wonder','05:14', '8', 'walküre_attack.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (36, 3, 'Walküre Attack!','Kotaro Odaka, UiNA','05:11', '9', 'walküre_attack.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (37, 3, 'Absolute 5','Takuya Watanabe','04:28', '1', 'walküre_trap.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (37, 3, 'Hear The Universe','Rasmus Faber','04:23', '7', 'walküre_trap.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (38, 3, 'Le Chemin (Duet with Sita)','Kyo','03:29', '1', 'le_chemin.jpg', 0.5, 0.99);



INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (39, 4, 'Memory','Sugarcult,  Marko DeSantis','03:46', '3', 'palm_trees_and_power_lines.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (40, 4, 'Blitzkrieg Bop - 2001 Remastered edition ','Ramones, Tommy Ramone, Dee Dee Ramone','02:13', '1', 'ramones.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (41, 4, 'Basket Case','Billie Joe Armostrong, Mike Dirnt, Tré Cool','03:01', '7', 'dookie.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (42, 4, 'The Kids Aren\'t Alright','Dexter Holland','02:59', '5', 'americana.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (43, 4, 'All The Small Things','Tom DeLonge, Mark Hoppus, Travis Barker','02:48', '8', 'enma_of_the_state.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (44, 4, 'Anarchy In The U.K.','Sex Pistols, John Lydon','03:31', '8', 'never_mind_the_bollocks_heres_the_sex_pistols.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (45, 4, 'Story Of My Life','Mike Ness','05:46', '3', 'social_distortion.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (46, 4, 'Linoleum','Fat Mike','02:10', '1', 'punk_in_drublic.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (47, 4, 'The State Of Massachusetts','Dropkick Murphys','03:52', '3', 'the_meanest_of_times.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (48, 4, 'Thrash Unreal','Laura Jane Grace','04:14', '3', 'new_wave.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (49, 4, 'Sugar, We\'re Going Down','Fall Out Boy','03:49', '4', 'from_under_the_cork_tree.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (50, 4, 'Misery Business','Paramore','03:31', '4', 'riot.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (51, 4, 'Dear Maria, Count Me In','Jack Barakat, Rian Dawson','03:02', '6', 'so_wrong_its_right.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (52, 4, 'Teenagers','My Chemical Romance','02:41', '11', 'the_black_parade.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (53, 4, 'I Write Sins Not Tragedies','Berndon Urie, Spencer Smith, Ryan Ross','03:07', '10', 'a_fever_you_cant_sweat_out.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (54, 4, 'The Anthem','Good Charlotte','02:55', '2', 'the_young_and_the_hopeless.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (55, 4, 'My Friends Over You','New Found Glory','03:40', '2', 'sticks_and_stones.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (56, 4, 'In Too Deep','Deryck Whibley, Greig Nori','03:37', '7', 'all_killer_no_filler.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (57, 4, 'You\'re Gonna Go Far, Kid','Dexter Holland','02:57', '3', 'rise_and_fall.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (58, 4, 'I\'m Not Okay (I Promise)','My Chemical Romance','03:06', '5', 'three_cheers_for_sweet_revenge.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (59, 4, 'Saviour','Rise Against','04:02', '11', 'appeal_to_reason.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (60, 4, 'Miss Murder','Havok, Puget, Carson, Burgan','03:26', '3', 'decemberunderground.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (61, 4, 'American Idiot','Mike Dirnt, Billie Joe Armstrong, Tré Cool','02:54', '1', 'american_idiot.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (62, 4, 'You And I','Anarbor','02:57', '5', 'free_your_mind.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (63, 4, '1985','Jaret Reddick, Mitch Allan, John Allen','03:13', '3', 'a_hangover_you_dont_deserve.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (62, 4, 'Let the Games Begin','Anarbor','03:20', '1', 'free_your_mind.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (62, 4, 'The Brightest Green','Anarbor','03:17', '2', 'free_your_mind.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (62, 4, 'Where the Wild Things Are','Anarbor','03:13', '3', 'free_your_mind.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (62, 4, 'Halfway Sober','Anarbor','03:55', '4', 'free_your_mind.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (62, 4, 'Passion For Publication','Anarbor','02:38', '6', 'free_your_mind.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (62, 4, 'Always Dirty, Never Clean','Anarbor','03:26', '7', 'free_your_mind.jpg', 0.5, 0.99);



INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (64, 5, 'Straight Outta Compton','Ice Cube, MC Ren, The D.O.C','04:19', '1', 'straight_outta_compton.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (65, 5, 'Only God Can Judge Me','Tupac Shakur','04:57', '10', 'all_eyez_on_me.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (65, 5, 'Tradin\' War Stories','Tupac Shakur','05:29', '11', 'all_eyez_on_me.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (65, 5, 'California Love (Remix)','2Pac, Dr. Dre, Roger Troutman','06:25', '12', 'all_eyez_on_me.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (65, 5, 'I Ain\'t Mad at Cha','Tupac Shakur','04:53', '13', 'all_eyez_on_me.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (65, 5, 'What\'z Ya Phone #','Tupac Shakur','05:10', '14', 'all_eyez_on_me.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (66, 5, 'Juicy','Wallace, Peter Philips, Combs, Jean Oliver','05:02', '10', 'ready_to_die.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (66, 5, 'Big Poppa','Wallace, Rudolph Isley, O\'Kelly Isley, Jr., Ronald Isley, Ernest Isley, Marvin Isley, Chris Jasper','04:13', '13', 'ready_to_die.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (66, 5, 'One More Chance','Wallace, Norm Glover, Reginald Ellis, Chucky Thompson, Combs','04:29', '7', 'ready_to_die.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (67, 5, 'No Diggity','Andre Young, Chauncey Hannibal, Teddy Riley, William Stewart, Lynise Walters, Richard Vick et Bill Withers','05:03', '3', 'another_level.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (68, 5, 'Clan in da Front','Wu-Tang Clan','04:33', '3', 'enter_the_wu-tang.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (68, 5, 'Method Man','Robert Diggs, Clifford Smith','05:50', '9', 'enter_the_wu-tang.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (69, 5, 'Don\'t Believe the Hype','Public Enemy','05:19', '3', 'it_takes_a_nation_of_millions.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (69, 5, 'Show \'Em Whatcha Got','Public Enemy','01:56', '9', 'it_takes_a_nation_of_millions.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (70, 5, 'Old School','Shakur, J. Buchanan, D. Tilery','04:40', '12', 'me_against_the_world.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (71, 5, 'Halftime','Jones, William Paul Mitchell, Gary Byrd','04:20', '5', 'illmatic.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (71, 5, 'One Love','Jones, Jonathan Davis, Jimmy Heath','05:25', '7', 'illmatic.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (72, 5, 'Bad bad bad','Davis, LaDamon Douglas, Keyshia Cole, Sean Garrett','03:38', '10', 'the_state_vs_radric_davis.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (72, 5, 'Worst Enemy','Davis, Gholson','04:04', '18', 'the_state_vs_radric_davis.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (73, 5, 'What\'s the Difference','Bradford, Marshall Mathers, Bembery, Alvin Joiner, Stefan Harris','04:04', '7', '2001.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (73, 5, 'The Watcher','Andre Young, Marshall Mathers','03:26', '2', '2001.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (73, 5, 'Bang bang','Young, Bailey, Mathers, Harbor','03:42', '21', '2001.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (73, 5, 'The Message','Ryan Montgomery','05:30', '22', '2001.jpg', 0.5, 0.99);

INSERT INTO tracks (album_id, genre_id, title, songwriter, duration, album_num, cover_file, cost_price, list_price) VALUES (73, 5, 'Murder Ink','Young, Bailey, Weaver','02:28', '15', '2001.jpg', 0.5, 0.99);



insert into artist_track (track_id, artist_id) values (1, 1);
insert into artist_track (track_id, artist_id) values (2, 2);
insert into artist_track (track_id, artist_id) values (3, 3);
insert into artist_track (track_id, artist_id) values (4, 4);
insert into artist_track (track_id, artist_id) values (5, 5);
insert into artist_track (track_id, artist_id) values (11, 5);
insert into artist_track (track_id, artist_id) values (16, 5);
insert into artist_track (track_id, artist_id) values (19, 5);
insert into artist_track (track_id, artist_id) values (20, 5);
insert into artist_track (track_id, artist_id) values (21, 5);
insert into artist_track (track_id, artist_id) values (22, 5);
insert into artist_track (track_id, artist_id) values (23, 5);
insert into artist_track (track_id, artist_id) values (24, 5);
insert into artist_track (track_id, artist_id) values (25, 5);
insert into artist_track (track_id, artist_id) values (26, 5);
insert into artist_track (track_id, artist_id) values (27, 5);
insert into artist_track (track_id, artist_id) values (6, 6);
insert into artist_track (track_id, artist_id) values (7, 7);
insert into artist_track (track_id, artist_id) values (8, 8);
insert into artist_track (track_id, artist_id) values (33, 8);
insert into artist_track (track_id, artist_id) values (9, 9);
insert into artist_track (track_id, artist_id) values (10, 10);
insert into artist_track (track_id, artist_id) values (12, 11);
insert into artist_track (track_id, artist_id) values (13, 11);
insert into artist_track (track_id, artist_id) values (14, 12);
insert into artist_track (track_id, artist_id) values (15, 13);
insert into artist_track (track_id, artist_id) values (17, 14);
insert into artist_track (track_id, artist_id) values (18, 15);
insert into artist_track (track_id, artist_id) values (28, 16);
insert into artist_track (track_id, artist_id) values (29, 17);
insert into artist_track (track_id, artist_id) values (30, 18);
insert into artist_track (track_id, artist_id) values (31, 19);
insert into artist_track (track_id, artist_id) values (32, 20);
insert into artist_track (track_id, artist_id) values (34, 21);
insert into artist_track (track_id, artist_id) values (35, 21);
insert into artist_track (track_id, artist_id) values (36, 21);
insert into artist_track (track_id, artist_id) values (37, 21);
insert into artist_track (track_id, artist_id) values (38, 21);
insert into artist_track (track_id, artist_id) values (39, 21);
insert into artist_track (track_id, artist_id) values (40, 21);
insert into artist_track (track_id, artist_id) values (41, 21);
insert into artist_track (track_id, artist_id) values (42, 22);
insert into artist_track (track_id, artist_id) values (43, 22);
insert into artist_track (track_id, artist_id) values (44, 22);
insert into artist_track (track_id, artist_id) values (45, 22);
insert into artist_track (track_id, artist_id) values (46, 22);
insert into artist_track (track_id, artist_id) values (47, 22);
insert into artist_track (track_id, artist_id) values (48, 22);
insert into artist_track (track_id, artist_id) values (49, 22);
insert into artist_track (track_id, artist_id) values (50, 22);
insert into artist_track (track_id, artist_id) values (51, 22);
insert into artist_track (track_id, artist_id) values (52, 22);
insert into artist_track (track_id, artist_id) values (53, 23);
insert into artist_track (track_id, artist_id) values (54, 23);
insert into artist_track (track_id, artist_id) values (55, 23);
insert into artist_track (track_id, artist_id) values (56, 23);
insert into artist_track (track_id, artist_id) values (57, 23);
insert into artist_track (track_id, artist_id) values (58, 23);
insert into artist_track (track_id, artist_id) values (59, 24);
insert into artist_track (track_id, artist_id) values (60, 25);
insert into artist_track (track_id, artist_id) values (61, 26);
insert into artist_track (track_id, artist_id) values (62, 27);
insert into artist_track (track_id, artist_id) values (63, 27);
insert into artist_track (track_id, artist_id) values (64, 27);
insert into artist_track (track_id, artist_id) values (65, 27);
insert into artist_track (track_id, artist_id) values (66, 27);
insert into artist_track (track_id, artist_id) values (67, 28);
insert into artist_track (track_id, artist_id) values (68, 29);
insert into artist_track (track_id, artist_id) values (69, 29);
insert into artist_track (track_id, artist_id) values (70, 30);
insert into artist_track (track_id, artist_id) values (71, 31);
insert into artist_track (track_id, artist_id) values (72, 32);
insert into artist_track (track_id, artist_id) values (73, 32);
insert into artist_track (track_id, artist_id) values (74, 32);
insert into artist_track (track_id, artist_id) values (75, 33);
insert into artist_track (track_id, artist_id) values (76, 33);
insert into artist_track (track_id, artist_id) values (77, 34);
insert into artist_track (track_id, artist_id) values (78, 34);
insert into artist_track (track_id, artist_id) values (79, 35);
insert into artist_track (track_id, artist_id) values (80, 35);
insert into artist_track (track_id, artist_id) values (81, 35);
insert into artist_track (track_id, artist_id) values (82, 35);
insert into artist_track (track_id, artist_id) values (83, 36);
insert into artist_track (track_id, artist_id) values (84, 37);
insert into artist_track (track_id, artist_id) values (85, 38);
insert into artist_track (track_id, artist_id) values (86, 39);
insert into artist_track (track_id, artist_id) values (106, 39);
insert into artist_track (track_id, artist_id) values (87, 40);
insert into artist_track (track_id, artist_id) values (102, 40);
insert into artist_track (track_id, artist_id) values (88, 41);
insert into artist_track (track_id, artist_id) values (89, 42);
insert into artist_track (track_id, artist_id) values (90, 43);
insert into artist_track (track_id, artist_id) values (91, 44);
insert into artist_track (track_id, artist_id) values (92, 45);
insert into artist_track (track_id, artist_id) values (93, 46);
insert into artist_track (track_id, artist_id) values (94, 47);
insert into artist_track (track_id, artist_id) values (95, 48);
insert into artist_track (track_id, artist_id) values (96, 49);
insert into artist_track (track_id, artist_id) values (97, 50);
insert into artist_track (track_id, artist_id) values (103, 50);
insert into artist_track (track_id, artist_id) values (98, 51);
insert into artist_track (track_id, artist_id) values (99, 52);
insert into artist_track (track_id, artist_id) values (100, 53);
insert into artist_track (track_id, artist_id) values (101, 54);
insert into artist_track (track_id, artist_id) values (104, 55);
insert into artist_track (track_id, artist_id) values (105, 56);
insert into artist_track (track_id, artist_id) values (107, 57);
insert into artist_track (track_id, artist_id) values (109, 57);
insert into artist_track (track_id, artist_id) values (110, 57);
insert into artist_track (track_id, artist_id) values (111, 57);
insert into artist_track (track_id, artist_id) values (112, 57);
insert into artist_track (track_id, artist_id) values (113, 57);
insert into artist_track (track_id, artist_id) values (114, 57);
insert into artist_track (track_id, artist_id) values (108, 58);
insert into artist_track (track_id, artist_id) values (115, 59);
insert into artist_track (track_id, artist_id) values (116, 60);
insert into artist_track (track_id, artist_id) values (117, 60);
insert into artist_track (track_id, artist_id) values (118, 60);
insert into artist_track (track_id, artist_id) values (119, 60);
insert into artist_track (track_id, artist_id) values (120, 60);
insert into artist_track (track_id, artist_id) values (129, 60);
insert into artist_track (track_id, artist_id) values (121, 61);
insert into artist_track (track_id, artist_id) values (122, 61);
insert into artist_track (track_id, artist_id) values (123, 61);
insert into artist_track (track_id, artist_id) values (124, 62);
insert into artist_track (track_id, artist_id) values (125, 63);
insert into artist_track (track_id, artist_id) values (126, 63);
insert into artist_track (track_id, artist_id) values (127, 64);
insert into artist_track (track_id, artist_id) values (128, 64);
insert into artist_track (track_id, artist_id) values (130, 65);
insert into artist_track (track_id, artist_id) values (131, 65);
insert into artist_track (track_id, artist_id) values (132, 66);
insert into artist_track (track_id, artist_id) values (133, 66);
insert into artist_track (track_id, artist_id) values (134, 67);
insert into artist_track (track_id, artist_id) values (135, 67);
insert into artist_track (track_id, artist_id) values (136, 67);
insert into artist_track (track_id, artist_id) values (137, 67);
insert into artist_track (track_id, artist_id) values (138, 67);



insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('tmason0', 'ZcHEhbUTS', 'U245rUCE', 'Mr', 'Tina', 'Mason', 'Photobug', '2039 Kennedy Place', null, 'Assiniboia', 12, 'Canada', 'H3C 1A4', '(973)434-1575', null, 'tmason0@hhs.gov', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('moliver1', 'm4rOXtyWfoZ', 'bySkyl6smA', 'Mrs', 'Marilyn', 'Oliver', 'Minyx', '984 Sheridan Center', null, 'Barrie', 9, 'Canada', 'L9J 1A4', '(307)968-6544', '(712)375-7837', 'moliver1@narod.ru', 2, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('hjones2', 'exO8oG1i', 'IFvV8s', 'Dr', 'Henry', 'Jones', 'Camido', '4433 Lien Alley', null, 'Fermont', 11, 'Canada', 'J1E 1A4', '(967)677-0688', '(426)873-2923', 'hjones2@aboutads.info', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('lmedina3', 'r7wfaErfxA', 'BCowj2G2xBz', 'Honorable', 'Lois', 'Medina', 'Snaptags', '8413 Arapahoe Way', null, 'Blainville', 11, 'Canada', 'J7J 1A4', '(625)478-5569', null, 'lmedina3@slate.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('tburton4', '5n7h5km', 'K6H7lpGW68', 'Dr', 'Teresa', 'Burton', 'Oyoloo', '6 Straubel Park', '5615 Fieldstone Way', 'Fort Smith', 6, 'Canada', 'L4S 1A4', '(670)881-3078', null, 'tburton4@yellowpages.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('dreyes5', 'q98KAQ', 'o5yXJ6heCPjG', 'Rev', 'Donna', 'Reyes', 'Skiba', '00 Fordem Place', null, 'Saint-Bruno-de-Guigues', 11, 'Canada', 'G6B 1A4', null, null, 'dreyes5@typepad.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('adunn6', 'uvK8slsY1l', 'nek8th', 'Dr', 'Anne', 'Dunn', 'Realfire', '9708 Blue Bill Park Hill', null, 'Maskinongé', 11, 'Canada', 'T7A 1A4', '(322)827-4062', null, 'adunn6@imdb.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('saustin7', 'oTzzBaX9J', 'XgrVHJl9wxGm', 'Ms', 'Steve', 'Austin', 'Trupe', '9809 Mockingbird Lane', null, 'Lethbridge', 1, 'Canada', 'T1K 1A4', null, null, 'saustin7@tuttocitta.it', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('bscott8', '0AWAIpijR', '6MmqC3oWH', 'Honorable', 'Betty', 'Scott', 'Thoughtmix', '97168 Glacier Hill Avenue', null, 'Oxbow', 12, 'Canada', 'T9M 1A4', '(938)246-2339', '(783)595-3396', 'bscott8@ycombinator.com', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('kbell9', 'hW8n5t3d', 'r4hPdvYs', 'Mr', 'Katherine', 'Bell', 'Skibox', '9707 Mosinee Court', null, 'Victoriaville', 11, 'Canada', 'G6T 1A4', '(630)367-7043', null, 'kbell9@php.net', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('showarda', 'wNsZBZpowg', '7NNwypvZP28e', 'Dr', 'Sharon', 'Howard', 'Jabbersphere', '906 Cambridge Street', '11266 Sheridan Hill', 'Ajax', 9, 'Canada', 'L1Z 1A4', null, '(738)816-4340', 'showarda@techcrunch.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('paustinb', 'J1TUNLxKPZ', 'srmixC3', 'Dr', 'Pamela', 'Austin', 'Blogspan', '14660 Ridge Oak Place', null, 'Rimouski', 11, 'Canada', 'G5N 1A4', '(800)954-3710', '(675)389-5754', 'paustinb@biblegateway.com', 2, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('dhayesc', 'zGZAfjFLs', 'mebRUI8Eps9', 'Honorable', 'Diana', 'Hayes', 'Eire', '736 Drewry Avenue', '69458 Loomis Center', 'Kindersley', 12, 'Canada', 'C1C 1A4', '(387)441-4142', '(354)946-8265', 'dhayesc@hud.gov', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('srusselld', 'PzOYW9xiRlWq', 'xlejUiU', 'Ms', 'Sharon', 'Russell', 'Jabberstorm', '53117 Esker Pass', null, 'Chilliwack', 2, 'Canada', 'N5L 1A4', '(768)513-3322', '(535)816-3062', 'srusselld@bizjournals.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('blanee', 'rtKVCLwA', 't29PGfVutSS', 'Honorable', 'Brenda', 'Lane', 'Kwimbee', '3 Sage Way', null, 'Niagara Falls', 9, 'Canada', 'L2J 1A4', '(697)630-3786', '(760)914-0067', 'blanee@stumbleupon.com', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jrileyf', 'uIdeMVD2v', 'hDdIejq', 'Dr', 'Jerry', 'Riley', 'Skynoodle', '1 Farmco Lane', null, 'Burgeo', 5, 'Canada', 'N9A 1A4', '(858)874-4563', null, 'jrileyf@networkadvertising.org', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('awagnerg', 'hrNZoI5zeRI', 'Q1e314hkYkV', 'Honorable', 'Amy', 'Wagner', 'Browsecat', '3881 Pepper Wood Center', '916 Spenser Point', 'Drayton Valley', 1, 'Canada', 'T7A 1A4', null, null, 'awagnerg@domainmarket.com', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('doliverh', 'ItA90U739bHM', '9caSc5', 'Dr', 'Deborah', 'Oliver', 'Voonix', '43 Melvin Park', null, 'Saint-Rémi', 11, 'Canada', 'K1G 1A4', '(940)579-2759', '(747)989-7938', 'doliverh@whitehouse.gov', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jparkeri', '8gyrpMfA', 'idO2sH', 'Rev', 'Jerry', 'Parker', 'Wordtune', '68541 Kingsford Park', null, 'Toronto', 9, 'Canada', 'M7A 1A4', null, null, 'jparkeri@pinterest.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('pgilbertj', 'SUlq7dd9Y', 'lk1XqYZ4NyT9', 'Mr', 'Paula', 'Gilbert', 'Flipopia', '900 Roxbury Terrace', null, 'Roberval', 11, 'Canada', 'G8H 1A4', null, null, 'pgilbertj@usda.gov', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('cromerok', 'qtKCRgUbsHms', 'GnjRHUPBG', 'Mr', 'Clarence', 'Romero', 'Vinder', '7167 Granby Point', null, 'Sherwood Park', 1, 'Canada', 'T8A 1A4', null, null, 'cromerok@fda.gov', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('rolsonl', 'eH2Fvo2cXT3l', 'PSg872Ma5Yy', 'Honorable', 'Ryan', 'Olson', 'Skyndu', '566 Clemons Lane', '74180 Knutson Pass', 'Swift Current', 12, 'Canada', 'S9H 1A4', '(200)769-1751', null, 'rolsonl@washingtonpost.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('stuckerm', 'tHAKwX5O2L', 'AYX1WJLEQkN2', 'Mrs', 'Sharon', 'Tucker', 'Brainbox', '83344 Farmco Way', null, 'St. Thomas', 9, 'Canada', 'N5R 1A4', '(128)460-4973', null, 'stuckerm@tuttocitta.it', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('lburken', '6tScaFk7RnhR', 'Y0L0UL4U', 'Dr', 'Larry', 'Burke', 'Lazzy', '09223 Evergreen Terrace', null, 'Ladner', 2, 'Canada', 'V7A 1A4', '(814)885-7579', '(237)540-2520', 'lburken@list-manage.com', 2, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('fhawkinso', 'NMdYQ6MTdg', 'NzJju9', 'Ms', 'Frances', 'Hawkins', 'Youopia', '36524 Main Alley', null, 'Hanover', 9, 'Canada', 'N4N 1A4', null, '(947)368-3034', 'fhawkinso@tripod.com', 4, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('rburkep', 'naRuNopgiu', 'iEkzRTQJ8MWz', 'Mrs', 'Ruby', 'Burke', 'Buzzster', '69 Bay Circle', null, 'Fort McMurray', 1, 'Canada', 'T9J 1A4', null, '(811)257-9596', 'rburkep@youku.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jsmithq', 'exxlnhK7uOGQ', 'MjXmjYVQpwk', 'Mrs', 'Joseph', 'Smith', 'Jazzy', '11231 Granby Avenue', null, 'Greater Napanee', 9, 'Canada', 'K7R 1A4', null, null, 'jsmithq@uol.com.br', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mjonesr', 'rtkBhJSW5gIK', 'W8R658e', 'Honorable', 'Margaret', 'Jones', 'Dynava', '5598 Logan Avenue', null, 'Vegreville', 1, 'Canada', 'T9C 1A4', '(734)971-8595', null, 'mjonesr@stanford.edu', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('bhawkinss', 'xZqi1SEBX8', 'WLte8Uz', 'Mr', 'Betty', 'Hawkins', 'JumpXS', '9297 Oak Hill', null, 'Markham', 9, 'Canada', 'L6E 1A4', '(154)181-3302', null, 'bhawkinss@angelfire.com', 2, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('calvarezt', 'WgYzGZlC', 'mORbEpUqa', 'Ms', 'Catherine', 'Alvarez', 'Wordify', '13 Anderson Place', null, 'Richibucto', 4, 'Canada', 'E4W 1A4', '(110)180-0505', null, 'calvarezt@omniture.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('bellisu', 'VctpJa', 'W6CSlHkN', 'Honorable', 'Bonnie', 'Ellis', 'Tanoodle', '5 Graceland Lane', null, 'Beaverlodge', 1, 'Canada', 'G8A 1A4', '(204)808-0343', null, 'bellisu@yellowpages.com', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jgilbertv', 'B57UJHf', '8utakcG', 'Mrs', 'Jack', 'Gilbert', 'Aibox', '7 Spohn Court', '8653 Moland Terrace', 'Napanee Downtown', 9, 'Canada', 'M5E 1A4', '(997)455-8500', null, 'jgilbertv@un.org', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('aryanw', '53aR9xkab3D4', '8SgSgWN', 'Mrs', 'Andrea', 'Ryan', 'Divavu', '52308 Shopko Trail', null, 'Asbestos', 11, 'Canada', 'J1T 1A4', '(438)689-5891', '(697)248-3328', 'aryanw@theatlantic.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('pgordonx', 'KZ7Q8J3', 'ILvkuHGH20', 'Mr', 'Paul', 'Gordon', 'Devpulse', '64 Boyd Park', null, 'Houston', 2, 'Canada', 'T9G 1A4', '(106)192-7617', '(904)929-6314', 'pgordonx@soundcloud.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('sharpery', 'zU3sEEGa1Q', '4bG4JOmtR', 'Mr', 'Shawn', 'Harper', 'Quatz', '0 John Wall Crossing', null, 'Kirkland Lake', 9, 'Canada', 'P2N 1A4', null, '(338)323-0113', 'sharpery@last.fm', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('arosez', 'OWeMNqRC', 'Fi7oFquTnqJ', 'Mrs', 'Annie', 'Rose', 'Devpulse', '7 Lien Point', null, 'Kugluktuk', 8, 'Canada', 'T5C 1A4', '(388)889-8333', '(228)653-8502', 'arosez@yolasite.com', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jharvey10', 'ImyW3x', '1rbjkhZQ3He', 'Rev', 'Joshua', 'Harvey', 'Voolia', '3144 Sycamore Park', null, 'Pembroke', 9, 'Canada', 'K8B 1A4', '(399)362-8597', '(154)728-5357', 'jharvey10@discovery.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('icastillo11', 'Sy74YzNVw0j2', 'rhHt4b0fpohu', 'Mr', 'Irene', 'Castillo', 'Zoombeat', '98 Moulton Pass', null, 'Two Hills', 1, 'Canada', 'J9H 1A4', '(163)636-0323', null, 'icastillo11@sohu.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('alee12', 'ldce9rp', 'opS3PaE', 'Mrs', 'Annie', 'Lee', 'Realcube', '33 Roth Pass', '0231 Upham Way', 'Winnipeg', 3, 'Canada', 'R3L 1A4', '(670)398-4899', null, 'alee12@mlb.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('cmeyer13', 'LAAeEeuo', 'HBytOBO7zP', 'Mrs', 'Christopher', 'Meyer', 'Yamia', '5492 Cambridge Point', null, 'Trail', 2, 'Canada', 'V1R 1A4', '(927)498-9268', null, 'cmeyer13@weebly.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('etucker14', '1yKoLTu', 'G2tI4xwL5GrW', 'Mrs', 'Eugene', 'Tucker', 'Gabvine', '3 Ruskin Crossing', null, 'Bells Corners', 9, 'Canada', 'K2R 1A4', '(544)369-9874', null, 'etucker14@alexa.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('cpalmer15', 'Qja04F', 'mEMbWJFuT', 'Dr', 'Catherine', 'Palmer', 'Fliptune', '284 Wayridge Way', null, 'Alma', 11, 'Canada', 'N6J 1A4', null, null, 'cpalmer15@t-online.de', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('nfox16', 'SKGY0xVr', 'TrktNjMy', 'Dr', 'Norma', 'Fox', 'Youtags', '0121 Birchwood Court', null, 'Alma', 11, 'Canada', 'N6J 1A4', '(936)506-9020', '(518)363-3571', 'nfox16@loc.gov', 4, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('ralexander17', 'aXnORvEgnFIz', 'sMRFetEELV', 'Ms', 'Roger', 'Alexander', 'Dynava', '5 Twin Pines Pass', null, 'East Angus', 11, 'Canada', 'N2B 1A4', '(334)971-7272', '(265)526-1508', 'ralexander17@census.gov', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('pwoods18', 'MsvuDMBWm', 'ReAngAP', 'Mrs', 'Peter', 'Woods', 'Avaveo', '2 West Alley', null, 'East Angus', 11, 'Canada', 'N2B 1A4', '(386)577-3263', '(658)212-9386', 'pwoods18@issuu.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('rlee19', 'yYfIr16P', 'LDevJW3Cp', 'Mrs', 'Roy', 'Lee', 'Browsecat', '4816 Doe Crossing Center', null, 'Mount Pearl', 5, 'Canada', 'A1N 1A4', '(867)144-4798', '(796)969-2231', 'rlee19@about.me', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jmarshall1a', 'VqVRyoin5VI', '1hJqCt6sqrz', 'Rev', 'Joseph', 'Marshall', 'Twitterlist', '063 Killdeer Terrace', null, 'Bassano', 1, 'Canada', 'M5G 1A4', '(504)163-0058', '(979)385-0023', 'jmarshall1a@vk.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jgreen1b', 'kdOpTIO', '6Kozq85c', 'Mrs', 'Jerry', 'Green', 'Meezzy', '1 Cody Hill', '4 Esch Parkway', 'Marystown', 5, 'Canada', 'L2N 1A4', null, null, 'jgreen1b@redcross.org', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('elawrence1c', 'yjXSvkY1', 'jmO78aG1', 'Ms', 'Emily', 'Lawrence', 'Tagfeed', '335 Melody Pass', null, 'Mont-Joli', 11, 'Canada', 'G5H 1A4', '(857)823-3613', null, 'elawrence1c@histats.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('dreynolds1d', 'dg1sGMaSlw', 'ZB0KigXJH', 'Mr', 'Donald', 'Reynolds', 'Aibox', '51 Ruskin Place', null, 'Baie-Saint-Paul', 11, 'Canada', 'G3Z 1A4', '(447)476-5791', null, 'dreynolds1d@ted.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jpowell1e', 'SJ52l7Hw0', 'G3ITcDisfg1', 'Rev', 'Joshua', 'Powell', 'Yozio', '1 Johnson Center', null, 'Lamont', 1, 'Canada', 'N2E 1A4', '(170)797-1892', '(742)923-8045', 'jpowell1e@usda.gov', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('aduncan1f', 'XNHyt0bgrc', 'Jxoxs9jhX', 'Dr', 'Amanda', 'Duncan', 'Zoovu', '60594 Sutherland Court', null, 'Sherwood Park', 1, 'Canada', 'T8A 1A4', '(781)679-8116', '(250)219-7462', 'aduncan1f@businesswire.com', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('lreynolds1g', 'kY0Rph0c', 'fX5ZSfw', 'Mr', 'Lillian', 'Reynolds', 'Izio', '26642 Johnson Court', null, 'Alma', 11, 'Canada', 'N6J 1A4', '(793)697-9543', '(855)882-6070', 'lreynolds1g@mapy.cz', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jkelley1h', 'Q3bGZw', 'D3JLu4', 'Mr', 'Jerry', 'Kelley', 'Riffpath', '42379 Sundown Hill', null, 'Thorold', 9, 'Canada', 'L2V 1A4', '(799)858-7497', null, 'jkelley1h@hibu.com', 4, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jwood1i', '7qyq1fJHCI', 'xbFL2R1Rre1', 'Mrs', 'Jessica', 'Wood', 'Feedmix', '35 Bluestem Drive', null, 'Amherst', 7, 'Canada', 'B4H 1A4', '(669)119-2705', '(654)183-1190', 'jwood1i@free.fr', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mkelly1j', 'WyEIXuL', 'XzITqrXn456T', 'Honorable', 'Mark', 'Kelly', 'Demimbu', '5 West Plaza', null, 'Saint-Eustache', 11, 'Canada', 'J7R 1A4', null, '(667)728-4079', 'mkelly1j@businessinsider.com', 2, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('gowens1k', 'bG2hF6pcqSH', 'bkzyee', 'Honorable', 'Gregory', 'Owens', 'Topicshots', '593 Bunker Hill Avenue', null, 'Sparwood', 2, 'Canada', 'M2J 1A4', '(111)970-2430', '(163)410-8663', 'gowens1k@sakura.ne.jp', 4, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('cgilbert1l', 'Nhgqzj3ZCu4', 'MDZDwjqa01', 'Mr', 'Christina', 'Gilbert', 'Eayo', '0552 Sommers Hill', null, 'Lumby', 2, 'Canada', 'P7L 1A4', '(553)640-0515', '(367)324-6899', 'cgilbert1l@spiegel.de', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('bjordan1m', 'TTw8uTb', 'Mkb6FVRT3A8', 'Mr', 'Benjamin', 'Jordan', 'Devcast', '894 Riverside Center', null, 'Powassan', 9, 'Canada', 'E3G 1A4', '(866)951-8475', null, 'bjordan1m@usa.gov', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mperkins1n', '4BzILQb8', 'a43W8TGvR5x', 'Mrs', 'Marie', 'Perkins', 'Dabfeed', '75880 Pankratz Lane', null, 'Ponoka', 1, 'Canada', 'T4J 1A4', null, '(722)913-1117', 'mperkins1n@toplist.cz', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('kross1o', 'DlhDpcCUKsi', '0yLxS0iGC', 'Rev', 'Karen', 'Ross', 'Katz', '61768 Maple Crossing', null, 'Dauphin', 3, 'Canada', 'R7N 1A4', null, null, 'kross1o@addthis.com', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('ktaylor1p', 'iz4ZFEEo2M', 'GWWbW7h6Y', 'Dr', 'Kenneth', 'Taylor', 'Zazio', '400 Londonderry Trail', null, 'Mont-Laurier', 11, 'Canada', 'J9L 1A4', '(359)672-3864', null, 'ktaylor1p@ox.ac.uk', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('cmatthews1q', 'Ldl5hBz3S6s5', 'yLEkzicllAu', 'Honorable', 'Cynthia', 'Matthews', 'Aivee', '68 Rusk Parkway', null, 'Rimouski', 11, 'Canada', 'G5N 1A4', '(761)585-7557', '(386)767-0101', 'cmatthews1q@storify.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('psimpson1r', 'jg7yMgGGOU', 'qDdf08jSgvz', 'Mrs', 'Pamela', 'Simpson', 'Meembee', '2 Florence Junction', null, 'Kitchener', 9, 'Canada', 'N2R 1A4', '(721)919-5886', null, 'psimpson1r@ustream.tv', 2, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mramos1s', 'B66huQZN', 'sMIXnyNywh', 'Rev', 'Mildred', 'Ramos', 'Bubblemix', '36104 Logan Alley', null, 'Baie-Saint-Paul', 11, 'Canada', 'G3Z 1A4', '(504)262-2129', '(910)157-5243', 'mramos1s@linkedin.com', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mharvey1t', 'Hfxxj2', 'VGCDG9RTRiIn', 'Dr', 'Maria', 'Harvey', 'Topicblab', '98171 Thackeray Park', null, 'Sainte-Anne-des-Plaines', 11, 'Canada', 'J4X 1A4', '(135)326-1750', null, 'mharvey1t@meetup.com', 2, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('estone1u', '8oHwKT6r', 'VmAhHDtg', 'Dr', 'Elizabeth', 'Stone', 'Fiveclub', '8 Briar Crest Crossing', null, 'Saint-Constant', 11, 'Canada', 'J5A 1A4', '(191)266-9133', '(226)875-1456', 'estone1u@answers.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('lmendoza1v', 'rc2QM4Ck', 'ImOKpCOHp', 'Mr', 'Lisa', 'Mendoza', 'Oyoloo', '289 Farmco Point', null, 'Hearst', 9, 'Canada', 'S4A 1A4', '(136)509-2148', null, 'lmendoza1v@unc.edu', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jalvarez1w', '5WS0Svc', 'lur6AbefUAx', 'Ms', 'Jerry', 'Alvarez', 'Buzzdog', '1002 Blackbird Plaza', null, 'Hanna', 1, 'Canada', 'T6L 1A4', '(503)446-1891', '(896)737-4805', 'jalvarez1w@a8.net', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jbutler1x', 'aQ4dmiI9y9e', 'TWN4xwCMA', 'Dr', 'Julie', 'Butler', 'Tagpad', '69 Bayside Pass', null, 'Owen Sound', 9, 'Canada', 'N4K 1A4', '(978)380-1651', '(710)925-6948', 'jbutler1x@imdb.com', 2, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('wgardner1y', 'TNjDPPM', 'BqC2ORky', 'Dr', 'Walter', 'Gardner', 'Feedfish', '19 Schurz Way', null, 'Claresholm', 1, 'Canada', 'V2T 1A4', '(405)392-3204', '(982)922-1838', 'wgardner1y@ox.ac.uk', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('creynolds1z', 'opWoj5sj', 'qvjAAiyps7S', 'Mr', 'Christina', 'Reynolds', 'Buzzshare', '7 Express Place', null, 'Princeville', 7, 'Canada', 'G6G 1A4', '(131)715-0518', '(505)286-1588', 'creynolds1z@hugedomains.com', 2, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('dcox20', 'YWbQBeIV', 'MTiQMw', 'Rev', 'Dennis', 'Cox', 'Youbridge', '34338 Spaight Street', null, 'Stratford', 9, 'Canada', 'C1B 1A4', null, null, 'dcox20@nba.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('relliott21', '1ax7jqMj', 'didRKFITR0yy', 'Ms', 'Rose', 'Elliott', 'Camimbo', '8403 Kinsman Avenue', null, 'Richibucto', 4, 'Canada', 'E4W 1A4', null, '(162)514-2954', 'relliott21@cnet.com', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jsullivan22', 'UsHgIi', 'NVSA0x', 'Mrs', 'Jeffrey', 'Sullivan', 'Jamia', '47536 Rutledge Parkway', null, 'Richibucto', 4, 'Canada', 'E4W 1A4', '(201)176-9364', null, 'jsullivan22@deviantart.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mevans23', 'NMVT2xIrBl', 'msQomRZ9Z', 'Honorable', 'Michael', 'Evans', 'Yabox', '1322 Sommers Lane', null, 'Saint-Augustin-de-Desmaures', 11, 'Canada', 'G3A 1A4', '(127)452-0036', '(582)130-4445', 'mevans23@t.co', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('trichards24', '8HP3vlKXU7', 'zJpBo6QKMe', 'Mr', 'Teresa', 'Richards', 'Yakidoo', '2 Corben Drive', null, 'Victoriaville', 11, 'Canada', 'G6T 1A4', '(450)223-6807', null, 'trichards24@forbes.com', 3, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('bfields25', '0oxR4xSZxK', '7aXbSEbg83Vw', 'Mr', 'Bonnie', 'Fields', 'Oyondu', '0 Forest Lane', null, 'L''Épiphanie', 11, 'Canada', 'J5X 1A4', '(503)477-4925', '(774)976-9282', 'bfields25@wikispaces.com', 4, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jjohnston26', 'hvAU910', '4NVB9VXd8yV', 'Rev', 'Juan', 'Johnston', 'Bubblebox', '7 Northview Circle', null, 'Saint-Bruno', 11, 'Canada', 'J3V 1A4', null, null, 'jjohnston26@eepurl.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('skennedy27', 'BIEqxEU2Q0mb', 'zM8nCq', 'Honorable', 'Sandra', 'Kennedy', 'Dabvine', '5977 Dapin Point', null, 'Okotoks', 1, 'Canada', 'T1S 1A4', '(688)323-2170', '(338)312-0165', 'skennedy27@facebook.com', 2, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('agreen28', 'K95JfyiIEyJx', 'MzU2GDUbZnI5', 'Mrs', 'Arthur', 'Green', 'Topdrive', '13055 Dahle Pass', '07974 Banding Avenue', 'Carbonear', 5, 'Canada', 'A1Y 1A4', '(617)762-3073', '(539)379-3708', 'agreen28@independent.co.uk', 2, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jadams29', 'AspDmmgU8ao', 'JOygmPI0', 'Ms', 'Julia', 'Adams', 'Gigabox', '2932 Melrose Hill', null, 'Sainte-Thérèse', 11, 'Canada', 'J7G 1A4', null, '(585)556-8258', 'jadams29@symantec.com', 2, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('lwilliamson2a', 'Klxi9hJNav', 'RiX8qlpx', 'Ms', 'Linda', 'Williamson', 'Topicstorm', '0295 Fulton Point', null, 'Neuville', 11, 'Canada', 'T9M 1A4', '(410)269-1301', null, 'lwilliamson2a@cisco.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('tgarcia2b', 'JjpxxtHpZE', 'TOMuTCyxTGN', 'Ms', 'Timothy', 'Garcia', 'Gabcube', '848 West Circle', null, 'Lions Bay', 2, 'Canada', 'K4K 1A4', '(611)284-9435', '(227)105-9888', 'tgarcia2b@reverbnation.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('bryan2c', 'dph9YMMqeIM', 'pPGExdPfdHO', 'Rev', 'Brandon', 'Ryan', 'Brainverse', '95881 Eastlawn Circle', null, 'Sainte-Anne-des-Monts', 11, 'Canada', 'L8P 1A4', '(183)803-2364', null, 'bryan2c@jigsy.com', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('arichardson2d', 'LseTCF', 'QOzjyd', 'Mrs', 'Amanda', 'Richardson', 'Fliptune', '4685 Talisman Hill', null, 'Carleton-sur-Mer', 11, 'Canada', 'P0T 1A4', '(821)155-1925', '(418)209-3471', 'arichardson2d@angelfire.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('lgarza2e', 'SB5S6D03jk', 'Jjg9TRSjM4', 'Dr', 'Lori', 'Garza', 'Camido', '609 Pennsylvania Hill', null, 'Kamloops', 2, 'Canada', 'H9P 1A4', '(716)635-3200', null, 'lgarza2e@hao123.com', 1, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jpowell2f', 'x8yKVUI0fF', '6DPeDZ5B0Zo8', 'Ms', 'Johnny', 'Powell', 'Thoughtsphere', '98887 Badeau Road', null, 'Coaticook', 11, 'Canada', 'J1A 1A4', '(152)613-5338', null, 'jpowell2f@irs.gov', 3, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('tcooper2g', 'H4qTPf', '6aTVaapW', 'Mr', 'Timothy', 'Cooper', 'Fatz', '259 Weeping Birch Terrace', null, 'Sussex', 4, 'Canada', 'E4E 1A4', '(169)930-4384', null, 'tcooper2g@fastcompany.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('eharrison2h', 'rEZLMymV', 'ZUNZqpoDBx', 'Dr', 'Elizabeth', 'Harrison', 'Lazzy', '120 Armistice Avenue', null, 'Aylmer', 9, 'Canada', 'N5H 1A4', '(187)330-9643', '(971)423-6890', 'eharrison2h@salon.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('tbennett2i', 'Pk1c1w', 'iZiWU1Tmk', 'Mr', 'Theresa', 'Bennett', 'Tazz', '17963 Macpherson Way', '393 Thackeray Center', 'Maniwaki', 11, 'Canada', 'J9E 1A4', '(182)133-0388', null, 'tbennett2i@squarespace.com', 2, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('jmartinez2j', '3zA9bEm', '7Et1nF77A', 'Ms', 'Joseph', 'Martinez', 'Aibox', '0006 Rowland Court', null, 'Sundre', 1, 'Canada', 'L9H 1A4', '(502)408-7663', '(934)731-1882', 'jmartinez2j@ebay.co.uk', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mhudson2k', 'QSkMD9z46', 'kP0qEegKx', 'Honorable', 'Margaret', 'Hudson', 'Wikibox', '29562 Bartelt Trail', null, 'Parrsboro', 7, 'Canada', 'L2A 1A4', '(674)338-7474', null, 'mhudson2k@google.co.jp', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('dwilliams2l', 'BG4Aoj', 'gYJudpfWs9', 'Honorable', 'Daniel', 'Williams', 'Snaptags', '713 Heffernan Plaza', null, 'Irricana', 1, 'Canada', 'B3T 1A4', null, '(881)932-0065', 'dwilliams2l@telegraph.co.uk', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('bmorris2m', 'sSa5fNu', 'KNCOxv', 'Mr', 'Brian', 'Morris', 'Tavu', '83942 Londonderry Terrace', null, 'Digby', 7, 'Canada', 'N3Y 1A4', '(310)157-0310', '(560)671-6918', 'bmorris2m@lycos.com', 1, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('gtucker2n', 'zYolcczPtl', 'yoQZs6GXH', 'Honorable', 'Gerald', 'Tucker', 'Kwimbee', '9 Londonderry Point', null, 'Whistler', 2, 'Canada', 'N0K 1A4', '(302)329-0205', null, 'gtucker2n@berkeley.edu', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('pmorgan2o', 'zWmrvW6Q', 'eiHEAJxryyQ', 'Mrs', 'Pamela', 'Morgan', 'Oyoloo', '14366 Ilene Center', '71 Lukken Terrace', 'Pasadena', 5, 'Canada', 'B2J 1A4', '(626)424-5680', null, 'pmorgan2o@mlb.com', 5, 1);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('mfisher2p', 'Ph1Nx6Ey4', 'xTka1f', 'Honorable', 'Mildred', 'Fisher', 'Yotz', '1 Algoma Lane', null, 'Osoyoos', 2, 'Canada', 'N9V 1A4', '(234)383-0056', null, 'mfisher2p@pagesperso-orange.fr', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('rschmidt2q', 'xpqioO', 'PAiougNe', 'Mrs', 'Robert', 'Schmidt', 'Vidoo', '3802 Clemons Road', null, 'Joliette', 11, 'Canada', 'J6E 1A4', '(108)545-1139', '(727)817-8611', 'rschmidt2q@myspace.com', 5, 0);

insert into users (username, password, salt, title, first_name, last_name, company, address_1, address_2, city, province_id, country, postal_code,  home_tel, cell_tel, email, last_genre, is_admin) values ('thunter2r', 'Dpw37JJJV01r', 'Kuc1wLxbR3oN', 'Honorable', 'Teresa', 'Hunter', 'Kaymbo', '3963 Hallows Terrace', null, 'La Sarre', 11, 'Canada', 'J9Z 1A4', '(848)690-3479', null, 'thunter2r@ameblo.jp', 5, 1);




INSERT INTO orders(user_id, net_cost) VALUES ('12','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('59','11.97');
INSERT INTO orders(user_id, net_cost) VALUES ('61','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('31','11.97');
INSERT INTO orders(user_id, net_cost) VALUES ('33','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('22','21.96');
INSERT INTO orders(user_id, net_cost) VALUES ('51','21.96');
INSERT INTO orders(user_id, net_cost) VALUES ('69','3.96');
INSERT INTO orders(user_id, net_cost) VALUES ('86','30.96');
INSERT INTO orders(user_id, net_cost) VALUES ('39','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('31','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('3','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('2','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('11','12.96');
INSERT INTO orders(user_id, net_cost) VALUES ('72','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('97','10.98');
INSERT INTO orders(user_id, net_cost) VALUES ('10','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('69','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('24','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('19','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('40','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('36','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('19','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('71','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('79','12.96');
INSERT INTO orders(user_id, net_cost) VALUES ('25','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('25','10.98');
INSERT INTO orders(user_id, net_cost) VALUES ('72','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('16','10.98');
INSERT INTO orders(user_id, net_cost) VALUES ('59','13.95');
INSERT INTO orders(user_id, net_cost) VALUES ('45','22.95');
INSERT INTO orders(user_id, net_cost) VALUES ('59','31.95');
INSERT INTO orders(user_id, net_cost) VALUES ('92','2.97');
INSERT INTO orders(user_id, net_cost) VALUES ('81','12.96');
INSERT INTO orders(user_id, net_cost) VALUES ('13','11.97');
INSERT INTO orders(user_id, net_cost) VALUES ('41','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('50','5.94');
INSERT INTO orders(user_id, net_cost) VALUES ('21','11.97');
INSERT INTO orders(user_id, net_cost) VALUES ('44','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('2','2.97');
INSERT INTO orders(user_id, net_cost) VALUES ('51','1.98');
INSERT INTO orders(user_id, net_cost) VALUES ('3','10.98');
INSERT INTO orders(user_id, net_cost) VALUES ('23','3.96');
INSERT INTO orders(user_id, net_cost) VALUES ('86','31.95');
INSERT INTO orders(user_id, net_cost) VALUES ('99','2.97');
INSERT INTO orders(user_id, net_cost) VALUES ('1','11.97');
INSERT INTO orders(user_id, net_cost) VALUES ('30','0.99');
INSERT INTO orders(user_id, net_cost) VALUES ('100','10.98');
INSERT INTO orders(user_id, net_cost) VALUES ('41','3.96');
INSERT INTO orders(user_id, net_cost) VALUES ('38','11.97');


INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('1','13','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('2','58','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('3','79','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('4','40','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('5','90','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('6','134','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('7','72','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('8','76','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('9','41','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('10','82','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('11','39','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('12','47','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('13','128','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('14','26','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('15','89','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('16','98','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('17','3','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('18','98','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('19','101','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('20','13','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('21','79','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('22','88','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('23','113','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('24','4','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('25','35','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('26','90','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('27','54','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('28','69','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('29','118','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('30','112','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('31','133','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('32','54','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('33','34','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('34','135','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('35','40','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('36','103','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('37','69','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('38','8','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('39','127','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('40','112','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('41','64','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('42','61','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('43','83','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('44','136','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('45','66','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('46','64','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('47','123','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('48','131','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('49','31','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('50','68','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('38','87','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('31','36','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('33','34','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('36','110','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('31','43','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('37','58','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('30','56','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('8','23','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('34','44','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('25','14','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('25','60','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('46','116','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('2','66','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('43','106','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('50','138','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('8','102','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('49','91','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('49','42','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('35','6','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('34','115','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('30','70','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('6','120','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('32','27','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('20','56','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('3','94','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('28','72','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('43','96','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('4','125','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('41','20','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('40','16','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('15','47','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('14','6','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('7','127','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('45','48','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('14','96','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('37','43','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('17','35','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('44','4','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('49','53','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('45','13','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('37','67','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('43','11','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('37','22','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('24','79','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('40','28','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('8','92','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('19','39','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('33','59','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('37','86','0.99', 0);
INSERT INTO order_items(order_id, track_id, cost, cancelled) VALUES ('30','72','0.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('38','26','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('27','57','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('44','50','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('32','66','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('4','34','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('9','13','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('50','46','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('44','66','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('32','26','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('7','43','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('14','14','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('30','16','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('48','13','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('7','37','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('35','56','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('42','66','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('46','51','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('34','32','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('31','31','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('29','54','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('16','69','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('9','26','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('9','63','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('31','27','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('25','52','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('32','34','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('6','38','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('2','27','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('44','68','9.99', 0);
INSERT INTO order_items(order_id, album_id, cost, cancelled) VALUES ('6','41','9.99', 0);


insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (38, 14, 4, '2017-01-12', 'In congue. Etiam justo. Etiam pretium iaculis justo. In hac habitasse platea dictumst.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (1, 62, 3, '2017-01-12', 'Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (41, 12, 5, '2017-01-05', 'Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis. Sed ante.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (46, 75, 3, '2017-01-12', 'Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (17, 48, 4, '2017-01-12', 'Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (27, 31, 5, '2017-01-12', 'Nulla ut erat id mauris vulputate elementum. Nullam varius.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (76, 88, 2, '2017-01-05', 'Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (37, 45, 2, '2017-01-12', 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem. Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (41, 60, 1, '2017-01-12', 'Fusce consequat. Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (62, 74, 2, '2017-01-12', 'Maecenas pulvinar lobortis est.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (41, 46, 3, '2017-01-04', 'Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (32, 90, 3, '2017-01-12', 'Proin interdum mauris non ligula pellentesque ultrices.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (52, 15, 1, '2017-01-12', 'Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (2, 9, 2, '2017-01-12', 'Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (93, 82, 5, '2017-01-12', 'Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (69, 100, 3, '2017-01-12', 'Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (61, 35, 3, '2017-01-12', 'Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (60, 54, 1, '2017-01-12', 'Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (29, 93, 2, '2017-01-08', 'Vivamus tortor. Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (31, 15, 4, '2017-01-12', 'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (88, 28, 3, '2017-01-12', 'Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (100, 49, 4, '2017-01-12', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (93, 12, 1, '2017-01-12', 'Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus. Pellentesque eget nunc.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (29, 47, 5, '2017-01-12', 'Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (95, 78, 4, '2017-01-12', 'In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (26, 2, 3, '2017-01-12', 'Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (66, 19, 4, '2017-01-12', 'Aenean lectus. Pellentesque eget nunc.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (79, 27, 5, '2017-01-12', 'Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (94, 30, 5, '2017-01-09', 'Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (100, 3, 1, '2017-01-12', 'Sed ante. Vivamus tortor. Duis mattis egestas metus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (96, 52, 5, '2017-2-1', 'Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (79, 93, 3, '2017-01-12', 'In sagittis dui vel nisl.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (11, 23, 4, '2017-01-12', 'Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (16, 56, 1, '2017-01-12', 'Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (38, 46, 3, '2017-01-12', 'Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (9, 32, 3, '2017-01-12', 'In sagittis dui vel nisl. Duis ac nibh.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (9, 53, 3, '2017-01-12', 'Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (68, 50, 3, '2017-2-1', 'Maecenas pulvinar lobortis est. Phasellus sit amet erat.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (91, 61, 4, '2017-01-12', 'Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (51, 96, 2, '2017-1-1', 'Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus. Phasellus in felis. Donec semper sapien a libero. Nam dui.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (94, 54, 2, '2017-01-12', 'Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (33, 49, 5, '2017-01-12', 'Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (67, 33, 1, '2017-01-12', 'Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (5, 99, 1, '2017-01-12', 'Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (12, 61, 1, '2017-1-1', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (64, 17, 2, '2017-01-12', 'Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (53, 12, 2, '2017-01-12', 'Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (17, 12, 3, '2017-01-12', 'Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (72, 7, 2, '2017-01-12', 'Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (79, 68, 2, '2017-01-12', 'Duis bibendum. Morbi non quam nec dui luctus rutrum.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (80, 99, 3, '2017-01-12', 'Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (84, 58, 2, '2017-01-04', 'Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (62, 22, 2, '2017-01-12', 'Etiam pretium iaculis justo. In hac habitasse platea dictumst.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (62, 15, 5, '2017-01-06', 'Donec ut mauris eget massa tempor convallis.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (75, 76, 1, '2017-01-12', 'Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (68, 36, 5, '2017-01-12', 'In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (21, 94, 4, '2017-01-12', 'Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (81, 39, 4, '2017-01-12', 'Vivamus tortor. Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget massa tempor convallis.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (7, 7, 5, '2017-01-12', 'Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (99, 53, 1, '2017-01-12', 'Nam nulla.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (11, 74, 1, '2017-01-12', 'Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (95, 84, 2, '2017-01-06', 'Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros. Vestibulum ac est lacinia nisi venenatis tristique.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (61, 92, 1, '2017-01-12', 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue. Vestibulum rutrum rutrum neque.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (74, 50, 1, '2017-01-12', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (57, 95, 5, '2017-01-12', 'Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (20, 50, 1, '2017-01-12', 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (54, 58, 5, '2017-01-06', 'In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (82, 19, 4, '2017-01-12', 'Nulla suscipit ligula in lacus.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (97, 42, 3, '2017-01-12', 'Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem. Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (59, 80, 3, '2017-01-12', 'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (54, 100, 1, '2017-01-12', 'In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (7, 39, 4, '2017-01-12', 'Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (20, 31, 4, '2017-01-12', 'Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (100, 17, 4, '2017-01-12', 'Vivamus in felis eu sapien cursus vestibulum. Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (94, 27, 4, '2017-01-12', 'Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (23, 94, 2, '2017-01-12', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (96, 2, 3, '2017-01-12', 'Morbi non lectus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (66, 65, 2, '2017-01-07', 'Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (3, 56, 1, '2017-01-12', 'Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (94, 73, 5, '2017-01-12', 'Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (7, 41, 2, '2017-01-12', 'Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (97, 71, 4, '2017-01-12', 'Morbi non quam nec dui luctus rutrum.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (86, 12, 3, '2017-01-12', 'Sed accumsan felis.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (94, 34, 3, '2017-01-12', 'Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (35, 68, 5, '2017-3-1', 'Quisque ut erat.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (66, 10, 3, '2017-01-12', 'Nullam molestie nibh in lectus. Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (81, 82, 1, '2017-01-12', 'Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (20, 24, 4, '2017-01-12', 'Donec posuere metus vitae ipsum.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (31, 32, 2, '2017-01-12', 'Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (23, 27, 3, '2017-01-12', 'Maecenas rhoncus aliquam lacus.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (85, 22, 3, '2017-01-12', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (89, 34, 3, '2017-01-12', 'In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (28, 55, 5, '2017-01-04', 'Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (15, 75, 2, '2017-01-12', 'Curabitur gravida nisi at nibh. In hac habitasse platea dictumst.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (27, 25, 5, '2017-1-1', 'Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (67, 91, 2, '2017-01-12', 'Vivamus in felis eu sapien cursus vestibulum. Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', 0, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (50, 63, 2, '2017-01-12', 'Proin at turpis a pede posuere nonummy. Integer non velit.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (64, 51, 2, '2017-1-1', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi.', 0, 1);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (14, 75, 5, '2017-01-12', 'Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst.', 1, 0);
insert into reviews (track_id, user_id, rating, review_date, text, approved, pending) values (31, 23, 3, '2017-01-12', 'Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante.', 0, 1);

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



