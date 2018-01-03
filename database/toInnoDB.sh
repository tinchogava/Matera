#!/bin/bash
PATH=/usr/local/mysql/bin
DB=matera
TABLES=$(mysql --password='matera' -u matera --skip-column-names -B -D $DB -e 'show tables')
for T in $TABLES
do
    mysql --password='matera' -u matera -D $DB -e "ALTER TABLE $T ENGINE=INNODB"
done
