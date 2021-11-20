# CS 175 Checkers AI Project

An AI project written in Java and using the ucigame library that was created for the CS 175 Spring 2011 course. Authors are Derek Mescheder and Derek Elder.

## Installation & Configuration

1. Download the source code from this repository
1. Download and install Java 8 from the Oracle website: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
1. Download the Ucigame library: http://www.ucigame.org/ucigame.jar
1. Move the Ucigame library into the lib folder in the project folder and into the location of where you installed Java 8 (ex: C:\Program Files (x86)\Java\jre1.8.0_151)
1. Add the ucigame.jar to the Project
   1. Project > Java Build Path > Libraries > Add JARs
1. In the IDE of your choice, configure Java and supply the following parameters (Eclipse will be used as an example)
   1. Run > Run Configurations > Click Parameters > Change width and height to 700
   1. Run > Run Configurations > Click Arguments > In Program arguments put: java Checkers Checkers
   1. Ensure Build Automatically is checked
1. Click Run to start the application in the IDE
1. To run this program from the commandline, add the ucigame.jar to the classpath and use: java Checkers Checkers

Note: Since draws are determined by judges at Checkers tournaments, it is not hardcoded into a system. Currently the game will not end if either player is unable to capture all of his/her opponent's pieces. This typically occurs when 2 AIs are engaged and the depth limit is set to a sufficiently large value.

## Open Issues

1. If playing the AI against itself, don't set the plies to more than six for both sides. The board sometimes won't redraw the entire GUI as the computer is choosing a move. If you want to see the current board state without relying on the GUI, simply uncomment the System.out.println(currentboard.toString()) line in the getAIMove method in the CheckersGame class.
1. Since Applets were deprecated after Java 8 it will take some work to move this to Java 9+
  
# Links and References

* https://stackoverflow.com/questions/51666387/migrating-applet-application-to-java-11
* https://stackoverflow.com/questions/54505959/error-could-not-find-or-load-main-class-sun-applet-appletviewer
