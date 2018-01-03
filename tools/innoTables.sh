#!/bin/bash
DB=matera
TABLES=$(mysql --password='matera' -u matera --skip-column-names -B -D $DB -e 'show tables')
for T in $TABLES
do
    echo "ALTER TABLE $T ENGINE=INNODB;" >> inno
done
