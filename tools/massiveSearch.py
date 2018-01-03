import mysql.connector
from collections import OrderedDict

cadena = "id_producto"

cnx = mysql.connector.connect(user='matera', password='matera',
                              host='127.0.0.1',
                              database='matera')

stb = ("SHOW TABLE STATUS")

cursor = cnx.cursor()

cursor.execute('set autocommit = 0')

cnx.commit()

cursor.execute(stb)

tables = cursor.fetchall()


for table in tables:
    tablename = table[0]
    q = "SELECT * FROM " + tablename + " LIMIT 0"
    cursor.execute(q)
    for a in cursor.description:
        if a[0] == "id_producto":
            print tablename
    cursor.fetchall()     