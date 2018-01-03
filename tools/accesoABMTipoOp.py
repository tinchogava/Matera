from __future__ import print_function
import mysql.connector

config = {
  'user': 'matera',
  'password': 'matera',
  'host': '127.0.0.1',
  'database': 'matera',
  'raise_on_warnings': True,
  'use_pure': False,
}

cnx = mysql.connector.connect(user='matera', password='matera',
                              host='127.0.0.1',
                              database='matera')

query = ("SELECT id_usuario, id_menu, id_empresa from acceso where id_menu = 30")

cursor = cnx.cursor()

cursor.execute('set autocommit = 0')

cnx.commit()

cursor.execute(query)

r = cursor.fetchall()

add_acceso = ("INSERT INTO acceso "
               "(id_usuario, id_menu, id_empresa) "
               "VALUES (%s, %s, %s)")
data_acceso = []

for (id_usuario,id_menu,id_empresa) in r:
		data_acceso.append((id_usuario, 133, id_empresa))

cursor.executemany(add_acceso, data_acceso)

cnx.commit()

cursor.close()

cnx.close()

#id_stockDetalle,id_stock,id_remito,id_ajusteStock,id_producto,cantidad,dc,gtin,serie,lote,pm,vencimiento,costoPesos,id_moneda,cotizacion,observaciones,id_zona