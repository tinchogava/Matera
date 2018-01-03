DELIMITER $$
DROP PROCEDURE IF EXISTS populateHistoricalPriceProducts $$

CREATE PROCEDURE populateHistoricalPriceProducts()
BEGIN
	declare done int default false;
	
	declare before_product_id int;
	declare before_stock_id int;
	declare before_user_id int;
	declare before_actual_price decimal(12,3);
	declare before_date date;
	declare before_real_date timestamp;

	declare product_id int;
	declare stock_id int;
	declare user_id int;
	declare actual_price decimal(12,3);
	declare actual_date date;
	declare real_date timestamp;

    declare cur cursor for SELECT fecha, costoPesos, id_producto, stock.id_stock, usuario.id_usuario, MAX(fechaReal)
	 FROM matera.stockdetalle inner join stock on stockdetalle.id_stock = stock.id_stock inner join usuario on stock.usuario = usuario.nombre 
	 group by costoPesos, id_producto order by id_producto desc,fecha desc, fechaReal desc;
	declare continue handler for not found set done = TRUE;

    set before_product_id=-1;

    open cur;

    start_loop: loop
        fetch cur into actual_date, actual_price, product_id, stock_id, user_id, real_date;
			
			if done then
				leave start_loop;
			end if;

			if product_id <> before_product_id then 
                set before_product_id = product_id;
				set before_stock_id = stock_id;
				set before_user_id = user_id;
				set before_actual_price = actual_price;
				set before_date = actual_date;
				set before_real_date = real_date;
            else
				INSERT INTO historico_precio_producto (id_producto, precio_anterior, precio_nuevo, fecha_actualizacion, id_stock, id_usuario)
				VALUES (before_product_id, actual_price, before_actual_price, before_real_date, before_stock_id, before_user_id);
				set before_product_id = product_id;
				set before_stock_id = stock_id;
				set before_user_id = user_id;
				set before_actual_price = actual_price;
				set before_real_date = real_date;
            end if;

    end loop;

    close cur;

END $$
CALL populateHistoricalPriceProducts() $$
DROP PROCEDURE IF EXISTS populateHistoricalPriceProducts $$
DELIMITER ;
