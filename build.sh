mkdir jars
wget -P jars http://jsoup.org/packages/jsoup-1.8.3.jar
javac -cp jars/jsoup-1.8.3.jar -d . src/com/lukecraig/XKCDViewer/*.java