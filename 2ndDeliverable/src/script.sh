#!/usr/bin/env bash

db=hw_1_2
glob=queries/*.sql

cd /var/src

echo "Setting up database & tables..."
mysql -ppass sys < up.sql
mysql -ppass -e "show databases;"
mysql -ppass $db -e "show tables;"

echo "Loading data from csv source..."
mysql -ppass $db -e "set global local_infile=true;"
mysql --local-infile=1 -ppass $db -e "load data local infile '/var/data/Team.csv' into table Team fields terminated by ',' ignore 1 lines;"
mysql -ppass $db -e "select * from Team limit 5;"
mysql --local-infile=1 -ppass $db -e "load data local infile '/var/data/Person.csv' into table Person fields terminated by ';' ignore 1 lines;"
mysql -ppass $db -e "select * from Person limit 5;"
mysql --local-infile=1 -ppass $db -e "load data local infile '/var/data/Player.csv' into table Player fields terminated by ';' ignore 1 lines;"
mysql -ppass $db -e "select * from Player limit 5;"
mysql --local-infile=1 -ppass $db -e "load data local infile '/var/data/Coach.csv' into table Coach fields terminated by ',' ignore 1 lines;"
mysql -ppass $db -e "select * from Coach limit 5;"

# loop over queries & execute each one
for f in $glob; do
    c=$(<"$f") # get file contents as variable
    cat $f # also print query to stdout
           # directly echoing $c above ends up stripping newlines, so cat is prettier
    mysql -ppass $db -e "$c" # passing contents as string to get prettier output
done

echo ""
echo "Tearing down database & tables..."
mysql -ppass sys < down.sql
mysql -ppass -e "show databases;"
