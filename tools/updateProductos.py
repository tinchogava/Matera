import mysql.connector

cadena = "id_producto"

cnx = mysql.connector.connect(user='matera', password='matera',
                              host='127.0.0.1',
                              database='matera')

cursor = cnx.cursor()

cursor.execute('set autocommit = 0')

#Actualiza registros repetidos en tabla Producto
query_trim = ("UPDATE producto SET codigo = TRIM(codigo)")

cursor.execute(query_trim)

cnx.commit()

print "TRIM Realizado\n\r"

#Toma los registros de productos repetidos en tabla Producto
query_codigos = ("SELECT id_producto, codigo FROM producto WHERE codigo = ANY(SELECT codigo FROM producto GROUP BY codigo HAVING count(*) > 1) ORDER BY codigo")

cursor.execute(query_codigos)

codigos = cursor.fetchall()

print "Obtenidos los registros de productos repetidos\n\r"

filtered = []

id_prod = []

id_obs = []

for codigo in codigos:

  duplicates = [a for a in codigos if codigo[0] != a[0] and codigo[1] == a[1]]

  id_prod.append(codigo[0])

  filtered.insert(codigo[0], duplicates)

  for d in duplicates:

    id_obs.append(d[0])

    codigos.remove(d)

for id in id_obs:

  update_query = ("UPDATE producto SET codigo = CONCAT('(OBSOLETO)', codigo) WHERE id_producto =" + str(id))

  cursor.execute(update_query)

  cnx.commit()

print "Registros duplicados actualizados a OBSOLETOS\r\n"

#Cambia referencia de id_producto en tablas CajaComposicion y StockDetalle

tables = ['cajacomposicion', 'stockdetalle']

for table in tables:

  for idx, id in enumerate(id_obs):

    update_query_tables = ("UPDATE " + table + " SET id_producto = " + str(id_prod[idx]) + " WHERE id_producto = " + str(id))

    cursor.execute(update_query_tables)

    cnx.commit()

print "Registros en tablas CajaComposicion y StockDetalle actualizados\r\n"

