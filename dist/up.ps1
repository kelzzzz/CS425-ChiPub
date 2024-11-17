$db="chipub"
$passwordcommand="-pPASSHERE"
$properties_file="user_defined_properties.properties"
$sqldir="sqlscripts"
$glob = Get-ChildItem "PATH\TO\YOUR\CLONE\CS425-ChiPub\dist\mock-data"

echo "Setting up database & tables..."
# start in sys database since a database has to
# be selected to execute a sql source
& cmd.exe /c "mysql -u root $passwordcommand < $sqldir\schema.sql"
# verify database created
mysql -u root $passwordcommand -e "show databases;"
# verify tables created
mysql -u root $passwordcommand $db -e "show tables;"
 
# enable loading csv from file
mysql -u root $passwordcommand $db -e "set global local_infile=true;"
echo "Loading data from csv source..."
# loop over data files & load each one
foreach ($f in $glob){
    $n=[io.path]::GetFileNameWithoutExtension($f)  # strip extension from file, giving basename only
    $t=$n
    $t=$t -replace '\d+-',''
    echo "loading data from $f into $t"
    # load data from csv
    $l="load data local infile 'mock-data/$f' into table $t fields terminated by ',' ignore 1 lines;"
    mysql --local-infile=1 -u root $passwordcommand $db -e "$l" 
    # select some records to verify data loaded
    mysql -u root $passwordcommand $db -e "select * from $t limit 15;"
}

& cmd.exe /c "mysql -u root $passwordcommand < $sqldir\util_data.sql"
& cmd.exe /c "mysql -u root $passwordcommand < $sqldir\util_func.sql"
& cmd.exe /c "mysql -u root  $passwordcommand < $sqldir\cardholder_copy_nulloutzerodatetimefields.sql"
& cmd.exe /c Java -jar -Dprops=$properties_file chipubapp-1.0-SNAPSHOT-shaded.jar