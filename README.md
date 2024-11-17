# CS425-ChiPub
This application is a group project that makes a basic mockup of a library catalog database.

This application can spin up a simple book database full of mock data, connect to it, and provides a GUI for interacting with books based on certain permissions. This application was written in Java using Swing and JDBC.

## Features:
- Able to log in as an admin or any user stored in the database.
- As a user, able to browse books, place and cancel holds, and view your own information.
- As an admin, able to browse books, cardholders, check-in and check-out books, update, delete, and register cardholders.  

## Run Instructions:
- Clone the project to any directory you see fit
- Right now, you can only run this project out the box from Windows Powershell
- First, you must edit the `up.ps1` file and the `user_defined_properties.properties` file
- In the `up.ps1` file: update the `$passwordcommand` and `$glob` variables. The password command should be formatted like -pDBPASSWORDHERE and the glob variable should point to the mock-data directory in your clone's dist folder.
  - ![screenshots8.png](https://github.com/kelzzzz/CS425-ChiPub/blob/main/screenshots8.png)
- In the `user_defined_properties.properties` file, update the `db.jdbc.url`, `db.admin.username` and `db.admin.password` to match that of your desired MySQL database instance. The chipub table will be inserted and accessed here. If you aren't sure how to get the JDBC url, refer here: https://github.com/kelzzzz/CS425-ChiPub/blob/kels_gui_wip/chipubapp/docs/DEV_FAQ.md
  - ![screenshot9.png](https://github.com/kelzzzz/CS425-ChiPub/blob/main/screenshot9.png)
- In Powershell, cd into the \dist directory
- From powershell run `powershell.exe -noprofile -executionpolicy bypass -file .\up.ps1`
- The GUI should start running
- When you are finished, exit out of the gui and run `powershell.exe -noprofile -executionpolicy bypass -file .\down.ps1`

## Usage Instructions:
- You can login using your MySQL admin credentials (the ones in the properties file) or as any user registered in the database. Their username will be their cardholder ID followed by their last name.
- Some convenient users to test logging in with:
  - `username: 0Greenholt, password: 050aa6ffb4f5a8a4fbb048f649385de95a51780f`
  - `username: 11Cremin, password: 6edd646bc5b04ed98e2ab8f15de2e4e4f7f36eb7`
- As a user you can only place and cancel holds as well as browse the books database, make searches, and view your own information.
- As an admin you can check in and check out books by providing a cardholder ID number. In the cardholder search view, that is the numbers before the first space in the cardholder number.
- ![screenshot7.png](https://github.com/kelzzzz/CS425-ChiPub/blob/main/screenshot7.png) In this case, the ID you use to check in/check out is 19137

## Screenshots:
![screenshot1.png](https://github.com/kelzzzz/CS425-ChiPub/blob/main/screenshot1.png) ![screenshot2.png](https://github.com/kelzzzz/CS425-ChiPub/blob/main/screenshot2.png)
![screenshot4.png](https://github.com/kelzzzz/CS425-ChiPub/blob/main/screenshot4.png)
![screenshot6.png](https://github.com/kelzzzz/CS425-ChiPub/blob/main/screenshot6.png)
