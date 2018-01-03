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

query = ("SELECT id_stockDetalle,id_stock,id_remito,id_ajusteStock,id_producto,cantidad,dc,lote,serie,pm,vencimiento,costoPesos,id_moneda,cotizacion,observaciones,id_zona from stockDetalle")

cursor = cnx.cursor()

cursor.execute('set autocommit = 0')

cnx.commit()

cursor.execute(query)

r = cursor.fetchall()

add_stockDetalle = ("INSERT INTO stockDetalle_copy "
               "(id_stock,id_remito,id_ajusteStock,id_producto,cantidad,dc,lote,serie,pm,vencimiento,costoPesos,id_moneda,cotizacion,observaciones,id_zona) "
               "VALUES (%s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s)")
data_stockDetalle = []

for (id_stockDetalle,id_stock,id_remito,id_ajusteStock,id_producto,cantidad,dc,lote,serie,pm,vencimiento,costoPesos,id_moneda,cotizacion,observaciones,id_zona) in r:
	for i in range(0,cantidad):
		data_stockDetalle.append((id_stock,id_remito,id_ajusteStock,id_producto,1,dc,lote,'',pm,vencimiento,costoPesos,id_moneda,cotizacion,observaciones,id_zona))

cursor.executemany(add_stockDetalle, data_stockDetalle)

cnx.commit()

cursor.close()

cnx.close()

#id_stockDetalle,id_stock,id_remito,id_ajusteStock,id_producto,cantidad,dc,gtin,serie,lote,pm,vencimiento,costoPesos,id_moneda,cotizacion,observaciones,id_zona