#!/usr/bin/env bash

db=chi_pub

cd "$(dirname "$0")"

echo ""
echo "Tearing down database & tables..."
mysql -ppass sys < down.sql
mysql -ppass -e "show databases;"
