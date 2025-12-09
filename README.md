# video-game

Repository for developing video game. Cohort 3, Team 8.

## Java version

Note you must use **Adoptium's Temurin® Java 17**:

- This is the version the modules technical requirements state we must use, check **JAVASETUP.md** for setup
  instructions
- To test you are using the right version, run the file: **CheckJDKVersion.java**
    - Expect results like this:
        - Java Version: 17.0.16
        - Java Vendor: Eclipse Adoptium

## Format for commit messages

- **Type: Message**
    - Type:
        - **feat** - changes that add a feature or modify one
        - **fix**  - changes that fix bugs or issues
        - **refactor** - code improvements that do not affect behaviour
        - **doc**  - changes to documentation
        - **conf**  - changes to file structure
        - **test** - changes to code tests
        - **dep** - changes to dependencies
        - **ci** - changes to continuous integration setup

    - Message: short explaining change
- Example:
    - *fix: data validation bug corrected*

## Running things

This project uses [Gradle](https://gradle.org/) to manage dependencies. The
Gradle wrapper was included, so you can run Gradle tasks in the command line
using `gradlew.bat` on Windows or `./gradlew` on other platforms.

After the Gradle command, add one of the following words to choose what you
would like to run:

- `lwjgl3:run`: compile and launch the game.
- `test`: run automated tests.
- `jacocoTestReport`: produce test coverage report.

Alternatively, you can start one of these tasks from the sidebar in VSCode.

## Where to start

the controller class of the game is MazeGame which inherits from applicationAdapter to access the libgdx framework
this then calls every other class and controls them as the game goes on in render
some helper functions are also found within gameController
most complex methods are class methods within their respective classes

## Notes about improvements

the base code should be very good starter
made to be as transferable as possible to any new tasks
potential areas that assessment 2 may take and what you need to do:

- new level - this is easy, just make a new Map through Tiled and pass it to Maze class
- difficult - not as simple but still easy, just change the time given, speed of bob, harshness of events or the map
  level that title screen moves to when the player starts
- replay - this is something that you will have to implement yourself, start with making some of the insides of create()
  a new function that is called to reset the game then call this from the lose and win screens
- scoreboard - this is one we thought might be coming up, you'll probably need to start by adding the stuff from above
  but, it's quite trivial after that, just take the event counters or time and write a method to calculate a score and
  add
  it to a new screen
- new entities - this should be very easy to do. use whichever class describes the entity the best and either make a
  subclass or just instance of that class depending on what you want to do
- if anything else comes up, and we're still in the handover week, come and talk to us, we'll be happy to give a rundown
  of the existing code and/or what you need to do to implement the new feature
