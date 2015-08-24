# XKCD Viewer
XKCD Viewer is a lightweight Java application that reads comics from the website [xkcd.com](http://xkcd.com) and displays them on screen. The program gives you the ability to scroll through the comics in a small window.  The program has been updated with the ability to tell you, at startup, the number of comics that the use has missed since their last time using the program.

####Navigation:
* Use the left arrow (or "a" key) to move to the previous comic
* Use the right arrow (or "d" key) to move to the next comic
* Use the "r" key to move to a random comic
* Use the Escape key to exit the program.

####Dependencies/Requirements:
1. Java Runtime Environment at least 1.8
2. Connection to the internet (specifically access to xkcd.com)
3. Jsoup 1.8.3 (HTML parser) (the program will download this at build time (only for Unix operating systems), but you will need access to the website)
4. SQLite Database  (the program will download this at build time (only for Unix operating systems), but you will need access to the website)
4. At least 6 mb of storage space for the program itself
5. Ample storage space for temporary downloaded images.

####To build this project:

**On a Unix-based operating system (OSX, Linux, or anything running bash):**

1. run "build.sh" in the main project (you should only need to run this once).
2. run "run.sh" to start the program.

**On a Windows machine:**

1. Download [Jsoup 1.8.3](http://jsoup.org/download) ([Direct link](http://jsoup.org/packages/jsoup-1.8.3.jar))
2. Download [SQLite Database 3.8.11.1](https://bitbucket.org/xerial/sqlite-jdbc/downloads) ([Direct Link](https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.8.11.1.jar))
2. Make two folders: libs and bin directly inside the main XKCDViewer folder
3. Put both jar files in the libs folder.
4. To build the project run the following command from the base of your project

  > javac -cp "libs/\*" -d "bin" src/\*.java

5. To run the project run the following command from the bin folder of your project

  > java -classpath "../libs/*:." Main
  

####Features to be added:

1. A background worker that checks if a new comic has been added every x minutes (x set by user). 
2. The ability to use the program at startup on multiple operating systems.

####Screenshots:

![picture 1](http://i.imgur.com/QxP972S.png)
![picture 2](http://i.imgur.com/14aYWAd.png)
