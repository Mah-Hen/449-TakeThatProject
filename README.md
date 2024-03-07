# 449-TakeThat-Prj
Hello! Welcome to the second installation of MSMU's CMSCI 449 (Intro to AI) project. Here we are using two types of searches to pit computer against computer in this game. 
Read below for me details:

For this project, you will write a program to play the game Take That! The game is played by two players on a board of size N by N. Each space has a number in the range from -25 to 75. The first player is called the row player, and the second is called the column player. At the start, a random row of the board is selected.

 

On the row player's turn, he or she selects any space in the current row that has not been previously selected. The number in that space is then added to the row player's score. The current column is now set to the column of that space.

 

The column player has a similar choice. He or she may choose any unpicked square in the current column. The points in the square are added to the column player's score, and the current row is updated.

 

The players alternate turns until the current player cannot make a move. This may leave some squares on the board unchosen. The player with the higher score is the winner; a tie is possible.

 

Here is some Java code to get started. My code supplies a GUI for the game and allows two players to play against each other. It also has a computer player option, but the computer player simply makes a random legal move. Surely you can do better.

 

When you run my code, you will be asked if you want to enter a seed or have the computer pick a seed. This sets the random number generator. If you pick the seed, then you can get the same board by entering the same seed number (a long integer). This is useful for testing and for the tournament we will have later in the semester. If you let the computer pick, it will then report what the seed is. For now, you can ignore it; it will come in handy with the tournament.

 

It will then ask for the names of the players and whether or not they are humans. Then the game begins. The spaces of the board are represented by buttons. The red numbers on the sides of the board indicate where the current player should click. The scores for the two players appear under the board. There is also some text next to the board that tells you whose turn it is, reports the winner, and gives other messages. The spacing of the GUI is not particularly pretty, but it gets the job done.

 

The code itself has four classes:

Game – this is the JFrame window; it sets everything up
Player – this mainly keeps track of the score and name of a player
Cell – one of the buttons of the board; it has methods to report its value and whether or not it is selected
Board – the panel with the board in it. This is where all the real work is done.
 

Board has a method called makeComputerMove that determines what move the current computer player will make and executes that move on the board. The determination is done by a method called makeComputerChoice. You will be replacing the code in that method. Right now, the method takes no parameters and returns the number of the square in the current row or column that the computer player should choose. (So if it is the row player's turn, it returns the column number of the square.) You can change what parameters are passed to the method; remember that it already has access to everything about the Board.

 

The code is heavily commented. You should be able to leave most of it as is. You can change the calls to makeComputerChoice and, of course, the body of the method. Feel free to add additional classes and methods as you deem necessary. I strongly suggest you make a BoardNode class that is built off objects from my Board class that stores the information necessary for the alphabeta search. Note that your BoardNode class would not need to extend JPanel.

 

Your code should allow either player to be a computer. The computer players should make their move choices within thirty seconds.
