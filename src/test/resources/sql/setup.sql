source /home/lynn/Desktop/sem6/java-625/project/sql/schema.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/triggers.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/artists_genres_provinces.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/albums.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/tracks.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/artist_track.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/users.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/orders_orderitems.sql
source /home/lynn/Desktop/sem6/java-625/project/sql/reviews.sql
\! clear


-- select title, (select title from albums where albums.id=album_id) as album, (select name from artists join artist_track on artists.id=artist_track.artist_id where artist_track.track_id=tracks.id) as artist, (select name from genres where genres.id=genre_id) as genre from tracks;


