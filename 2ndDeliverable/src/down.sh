#!/usr/bin/env bash

db=hw_1_2
glob=queries/*.sql

cd /var/src

echo ""
echo "Tearing down database & tables..."
mysql -ppass sys < down.sql
mysql -ppass -e "show databases;"
