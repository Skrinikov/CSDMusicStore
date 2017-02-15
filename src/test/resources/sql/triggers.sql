delimiter //

drop trigger if exists before_orders_insert //
create trigger before_orders_insert
before insert on orders
for each row
begin
    declare provinceid int;
    declare psttax float;
    declare gsttax float;
    declare hsttax float;

    select province_id into provinceid from users where users.id = new.user_id;
    select pst, gst, hst into psttax, gsttax, hsttax from provinces where provinces.id = provinceid;

    set new.gross_cost = new.net_cost + new.net_cost*psttax + new.net_cost*gsttax + new.net_cost*hsttax;

end //

delimiter ;
