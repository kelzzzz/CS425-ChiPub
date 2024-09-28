#!/usr/bin/env bash

db=chi_pub
glob=mock-data/*.csv

cd "$(dirname "$0")"

echo "Setting up database & tables..."
mysql -ppass sys < schema.sql
mysql -ppass -e "show databases;"
mysql -ppass $db -e "show tables;"
 
mysql -ppass $db -e "set global local_infile=true;"
echo "Loading data from csv source..."
# loop over data files & load each one
for f in $glob; do
    b=${f##*/}
    n=${b%.*}
    t=${n##*-}
    echo "loading data from $f into $t"
    mysql --local-infile=1 -ppass $db -e "load data local infile '$f' into table $t fields terminated by ',' ignore 1 lines;" # load data from csv
    mysql -ppass $db -e "select * from $t limit 10;"
done

mysql -ppass sys < util_data.sql
mysql -ppass sys < util_func.sql