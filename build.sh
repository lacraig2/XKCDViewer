mkdir libs
wget -P libs http://jsoup.org/packages/jsoup-1.8.3.jar
wget -P libs https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.8.11.1.jar
javac -cp "libs/*" -d . src/com/lukecraig/XKCDViewer/*.java