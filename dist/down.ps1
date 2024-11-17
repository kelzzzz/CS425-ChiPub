#!/usr/bin/env bash

$db="chipub"
$passwordcommand="-padmin"
$sqldir="sqlscripts"

echo "Tearing down database & tables..."
& cmd.exe /c "mysql -u root $passwordcommand < $sqldir\down.sql"

mysql -u root $passwordcommand -e "show databases;"