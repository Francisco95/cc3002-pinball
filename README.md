# Pinball

Homework for course CC3002 at U. of Chile (2018)

## Getting Started

Te current project was compile using Intellij IDE with Java 1.8 on Ubuntu 16.04, the project is constructed under a _maven_ framework. The whole project is composed of:

### Main (src/main) folder with all the classes and methods implemented:

* Package _controller_ with the implementation of Game methods(points, balls and how they change)
* Package _facade_ with the Facade Pattern used to reduce complexity on how to run the game.
* Package _interactions_ with all the interactions between classes/types, these interactions are implemented using Observer Pattern along together with Visitor Pattern to simulate a multiple dispact interactions.
* Package _logic_ which describe all the logic of the elements of the game and the bonuses of the game.
* Package _logic.bonus_ with the implementation of 3 kind of bonuses (ExtraBall, JackPot and DropTarget). These bonus are instanciate only once per game so they are implemented with Singleton Pattern.
* Package _logic.gameelements_ with the implementations of those elements in game that are tangibles and can be hit by something (a ball for example). There are two types defined:
	* _bumpers_: give points and bonuses, also they can be upgraded by hitting them.
	* _Targets_: give points and bonuses. The ball only can hit these elements once per time and then they change to non-active. A reset can be done to re-activate these elements.

* Package _logic.table_ where we construct the game table in which are the game elementes like targets and bumpers. We define 3 kinds of tables: an empty tables without elements, a table without targets and a full table with targets and bumpers. This package allow to controll some bonuses over the bumpers like a bonus that is triggered when all the bumpers are hit by the ball and also we define here a reset for all the targets when a special condition is satisfied.

#### Test folder (src/test) with all the tests created for the project:

Every test check one class exceptuating for the classes implemented in Package _facade_ and _interactions_, the first is not necessary to test because is just a Facade of all methods togheter and the second one is just an adaptation of methods created by interfaces declaring them as empty because the multi-dispatch implementation has as consequency a big amount of empty declared methods (we define interactions of all with all but this didn't happend at all).

### How to Compile

The recomended steps for compile the program are:

* Clone the project with:
```
git clone https://github.com/Francisco95/cc3002-pinball.git
```

* open Intellij IDE and choose import project with _maven_ choosing the cloned file.

* Compile using the current options in Intellij IDE.

### Prerequisites

To guarantee the correct behavior you need to run the project on Intellij IDE using java 1.8.

NOTE: We don't guarantee a correct behavior on other IDE's or terminal


## Running the tests

With the _maven_ framework you just need to right-click on the folder _src/test/java_ and choose _run_ or _run with coverage_.

The test here check behavior of all methods implemented, they are designed to find any error in the code, detecting for example, incorrect behavior in interactions between game elements, bonuses and game score/balls or incorect use of some methods.



