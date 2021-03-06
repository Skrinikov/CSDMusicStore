DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS artist_track;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS tracks;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS news_feeds;
DROP TABLE IF EXISTS banner_ads;
DROP TABLE IF EXISTS survey_choices;
DROP TABLE IF EXISTS surveys;
DROP TABLE IF EXISTS albums;
DROP TABLE IF EXISTS artists;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS provinces;
DROP TABLE IF EXISTS genres;



CREATE TABLE provinces (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  pst FLOAT NOT NULL,
  gst FLOAT NOT NULL,
  hst FLOAT NOT NULL
);


CREATE TABLE genres (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);


CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password BINARY(64) NOT NULL,
  salt VARCHAR(255) NOT NULL,
  title VARCHAR(50) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  company VARCHAR(50),
  address_1 VARCHAR(50) NOT NULL,
  address_2 VARCHAR(50),
  city VARCHAR(50) NOT NULL,
  province_id INTEGER  NOT NULL, 
  country VARCHAR(50) NOT NULL,
  postal_code VARCHAR(50) NOT NULL,
  home_tel VARCHAR(20),
  cell_tel VARCHAR(20),
  email VARCHAR(255) NOT NULL,
  last_genre INTEGER,
  is_admin BOOLEAN DEFAULT false NOT NULL,
  FOREIGN KEY (province_id) REFERENCES provinces(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (last_genre) REFERENCES genres(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE orders (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  order_date DATETIME DEFAULT now() NOT NULL,
  user_id INTEGER  NOT NULL,
  net_cost FLOAT DEFAULT 0 NOT NULL,
  gross_cost FLOAT DEFAULT 0 NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE news_feeds (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  link VARCHAR(255) NOT NULL,
  source VARCHAR(255) NOT NULL,
  visible BOOLEAN DEFAULT false NOT NULL
);


CREATE TABLE banner_ads (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  link VARCHAR(255) NOT NULL,
  source VARCHAR(255) NOT NULL,
  visible BOOLEAN DEFAULT false NOT NULL
);


CREATE TABLE surveys (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  question VARCHAR(255) NOT NULL,
  visible BOOLEAN DEFAULT false NOT NULL
);


CREATE TABLE survey_choices (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  survey_id INTEGER,
  choice VARCHAR(255) NOT NULL,
  num_votes INTEGER DEFAULT 0 NOT NULL,
  FOREIGN KEY (survey_id) REFERENCES surveys(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE artists (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);


CREATE TABLE albums (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  artist_id INTEGER NOT NULL,
  title VARCHAR(255) NOT NULL,
  release_date DATE NOT NULL,
  record_label VARCHAR(255) NOT NULL,
  num_tracks INTEGER NOT NULL,
  created_at DATETIME DEFAULT now() NOT NULL,
  cost_price FLOAT DEFAULT 0 NOT NULL,
  list_price FLOAT DEFAULT 0 NOT NULL,
  sale_price FLOAT DEFAULT 0 NOT NULL,
  removed_at DATETIME,
  available BOOLEAN DEFAULT true NOT NULL,
  FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE tracks (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  album_id INTEGER NOT NULL,
  genre_id INTEGER NOT NULL,
  title VARCHAR(255) NOT NULL,
  songwriter VARCHAR(255) NOT NULL,
  duration VARCHAR(10) NOT NULL,
  album_num INTEGER NOT NULL,
  cover_file VARCHAR(255) NOT NULL,
  created_at DATETIME DEFAULT now() NOT NULL,
  cost_price FLOAT DEFAULT 0 NOT NULL,
  list_price FLOAT DEFAULT 0 NOT NULL,
  sale_price FLOAT DEFAULT 0 NOT NULL,
  removed_at DATETIME,
  available BOOLEAN DEFAULT true NOT NULL,
  is_individual BOOLEAN DEFAULT false NOT NULL,
  FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE artist_track (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  artist_id INTEGER NOT NULL,
  track_id INTEGER NOT NULL,
  FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (track_id) REFERENCES tracks(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE reviews (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  track_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  rating INTEGER NOT NULL,
  review_date DATETIME NOT NULL,
  text VARCHAR(2000) NOT NULL,
  approved BOOLEAN DEFAULT false NOT NULL,
  pending BOOLEAN DEFAULT true NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (track_id) REFERENCES tracks(id) ON DELETE CASCADE ON UPDATE CASCADE 
);


CREATE TABLE order_items (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  order_id INTEGER NOT NULL,
  track_id INTEGER,
  album_id INTEGER,
  cost FLOAT DEFAULT 0 NOT NULL,
  cancelled BOOLEAN DEFAULT false NOT NULL,
  FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (track_id) REFERENCES tracks(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE
);









