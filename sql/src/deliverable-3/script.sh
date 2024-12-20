#!/usr/bin/env bash

db=chi_pub
glob=queries/*.sql

cd /var/src/deliverable-3

# loop over queries & execute each one
for f in $glob; do
    c=$(<"$f") # get file contents as variable
    cat $f # also print query to stdout
           # directly echoing $c above ends up stripping newlines, so cat is prettier
    mysql -ppass $db -e "$c" # passing contents as string to get prettier output
done
