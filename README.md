# Monopoly-SYSC3110-
Authors:
 - Tooba Sheikh - 101028915
 - Kareem Abdo  - 101140382
 - Zinah Al-Najjar - 101108056

Deliverables: 
Zip File containing
 - executable jar file
 - Sequence diagram - Kareem
 - UML diagram - Zinah
 - Manual - Zinah
 - readme - Tooba

Classes:

Model:
 - Board: Creates all the properties (the board) and implaments moving the players position
 - Dice: Rolls the dice, can return sums of both die or individual values
 - Game: Initializes the whole game, incharge o the functionality of each command, and the loop that runs the game.  
 - Player: Holds all the properties of every "Player" created, add performs addition and subtraction to 
 - Property: Hold all the properties of every "Property" created
 - Command: Creates a valid command list 

View:
 - MonopolyView (interface)
 - MainFrame - Implements the main game view implementing MonopolyView
 - CardFrame - Implements the deed card, each CardFram considered its own view implementing MonopolyView
 - StartView - Start screen that asks the user to start the game by entering number of players and help button that prints instructions

Controller:
 - MonopolyController - connects view to model

