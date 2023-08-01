Authors:
 - Tooba Sheikh - 101028915
 - Kareem Abdo  - 101140383
 - Zinah Al-Najjar - 101108056

Deliverables: 
Zip File containing
 - executable jar file
 - UML diagram
 - Manual
 - Sequence diagrams
 - readme

Classes:

Model:
- Board: Calls the XML Handler to create tiles array and implaments moving the players position
- XmlFileHandler: Parses all the xml files to create the baord (includes three xml files with three different versions)
- Dice: Rolls the dice, can return sums of both die or individual values
- Game: Initializes the whole game, incharge o the functionality of each command, and the loop that runs the game.
- Player: Holds all the properties of every "Player" created, add performs addition and subtraction to
- Command: Creates a valid command list
- FileUtil: Implements reading and writing via Serializable for save and load
- Tile: abstract class extended by PropertyTile and CornerTile
- PropertyTile: all the properties that can be bough and sold including railroads and Utilities
- CornerTile: all the corner tile that are not purchasable
- TileType: Enum defining the types of properties
- PropertyStates: Enum holds the state of the property, whether that be the number of houses bought or the Rent level of the railroads/utilities

View:

- MonopolyView (interface)
- MainFrame - Implements the main game view implementing MonopolyView
- CardFrame - Implements the deed card, each CardFram considered its own view implementing MonopolyView
- ChooseVersionScreen - lets you choose the version to play
- CornerFrame: view for the corner tiles
- JailFrame: view for the Jail tile
- WelcomeScreen - Start screen that asks the user to start the game by entering number of players and help button that prints instructions

Controller:
- MonopolyController - connects view to model

Contributions:

- Kareem - AI
- Zinah: - save/load, Uml, Manual, Javadoc
- Tooba: Xml international versions, working on smelly code feedback, Sequence Diagrams, javadoc, readme

Changes From last Version:

- All the colored properties, Railroads and Utilities are now implemented within one class called PropertyTiles.
- All cornerTiles are now implemented within one class called cornertilesTiles.
- Removed Railroad view and Utility view and implemeted them into One class CardFrame
- Got rid of the board instance from all the views, and instead sent just property as a parameter.
- Refactored MainFrame to get property names for buttons from the board instead of manually labelling them
- Refactored MainFrame handleMonopolyupdatestatus() to call on mnay repeated JoptionPanes.
- Refactored the Monopoly view to minimize the number of empty methods appearing in the view classes
- Refactored Game class to have constants for each command
- Lastly all the features (save and load, xml versions and Ai) implemented

Known Issues:

- There are still a few 3 empty methods (handle sell, buy, jail) in the view classes. Could not cast them in the Game class because the for loop went over all the instances of view, and casting errors popped up of not being able to cast on MainFrame.
- Test classes not completed because of lack of participation from other members and workload
