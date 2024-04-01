import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private Board gameBoard;
    private Node parent;
    private int ply=0;
    //private int evalFunction;


public Node(Board brd, Node parent) {
    this.gameBoard = brd; // declare intial state
    this.parent = parent; // declare state parent
}

public Node(Board brd, Node parent, int ply){
    this.gameBoard = brd; // delcare intial state
    this.parent = parent; // declare state parent
    this.ply = ply; // declare path Cost/ply
}

private Board getBoard(){
    return this.gameBoard;
}

private int getPly(){
    return this.ply;
}


public int minimaxSearch(){
    Node gameBoardNode = new Node(gameBoard, null);

    String[] valueMoveList = maxValue(gameBoardNode);
    int utilValue = Integer.parseInt(valueMoveList[0]);

    if(valueMoveList[1]!= null){ // if there is a move
        int row = valueMoveList[1].charAt(0);
        int col = valueMoveList[1].charAt(1);
        if(gameBoardNode.gameBoard.getTurn()) // if rows turn
            return col;
        return row;}

    return -1; // Since we're returning a row/column position number. Our flag case
}

public String[] maxValue(Node brdNode){
    String [] maxValueMoveList = new String [2];

    if(isCutOff(brdNode, 1)){
        maxValueMoveList[0] = ""+evaluationFunction(brdNode);
        maxValueMoveList[1] = null; // a null flag equivalent value but outside the range of our potential utility values
        return maxValueMoveList;
    }
    double lowValue = Integer.MIN_VALUE;
    for(int[] action:Actions(brdNode.getBoard())){
        int row = action[0];
        int col = action[1];

        Board modifiedBoard = Result(brdNode.getBoard(), row, col);
        modifiedBoard.switchTurn();
        Node modifiedBoardNode = new Node(modifiedBoard, brdNode, brdNode.getPly()+1); // Parent is the brdNode
        String [] minValueMoveList = minValue(modifiedBoardNode);
        double minLowValue = Double.parseDouble(minValueMoveList[0]); // Utility Function
        if (minLowValue > lowValue){ // if the node's util function is greater than the low value
            lowValue = minLowValue;
            maxValueMoveList[0] = ""+lowValue;
            maxValueMoveList[1] = row+""+col; 
        }
    }
    return maxValueMoveList;
}

public String[] minValue(Node brdNode){
    String[] minValueMoveList = new String[2];

    if(isCutOff(brdNode, 1)){
        minValueMoveList[0] = ""+evaluationFunction(brdNode);
        minValueMoveList[1] = null;
        return minValueMoveList;
    }
    double highValue = Integer.MAX_VALUE;
    for(int[] action:Actions(brdNode.getBoard())){
        int row = action[0];
        int col = action[1];

        Board modifiedBoard = Result(brdNode.getBoard(), row, col); // new Board after the action is applied
        modifiedBoard.switchTurn(); // reason why I put here and not at top is because of the first calling of 'maxValue' if we switch turn immediately when alg started it wouldve made player 2 MAX.
        Node modifiedBoardNode = new Node(modifiedBoard, brdNode, brdNode.getPly()+1);
        String[] maxValueMoveList = maxValue(modifiedBoardNode);
        double maxLowValue = Double.parseDouble(maxValueMoveList[0]); // Utility Function
        if(maxLowValue < highValue){
            highValue = maxLowValue;
            minValueMoveList[0] = ""+highValue;
            minValueMoveList[1] = row+""+col;
        }
    } 
    return minValueMoveList;
}

public int alphaBetaSearch(){
    Node gameBoardNode = new Node(gameBoard, null);
    String [] valueMoveList = alphaBetaMaxValue(gameBoardNode, Integer.MIN_VALUE, Integer.MAX_VALUE);
    if(valueMoveList[1]!=null){
        int moveValue = Integer.parseInt(valueMoveList[1]);
        return moveValue;}

    return -1; // no find
}

private String[] alphaBetaMaxValue(Node brdNode, int atLeast, int atMost){
    String [] maxValueMoveList = new String[2];

    if(isCutOff(brdNode, 1)){
        maxValueMoveList[0] = ""+evaluationFunction(brdNode);
        maxValueMoveList[1] = null;
        return maxValueMoveList;
    }
    int maxValueLowValue = atLeast;
    for(int[] action:Actions(brdNode.getBoard())){
        int row = action[0];
        int col = action[1];

        Board modifiedBoard = Result(gameBoard, row, col);
        modifiedBoard.switchTurn();
        Node modifiedBoardNode = new Node(modifiedBoard, brdNode, brdNode.getPly()+1);
        String [] minValueMoveList = alphaBetaMinValue(modifiedBoardNode, atLeast, atMost);
        if(Integer.parseInt(minValueMoveList[0]) > maxValueLowValue){
            maxValueLowValue = Integer.parseInt(maxValueMoveList[0]);
            maxValueMoveList[1] = row+""+col;
            atLeast = Math.max(atLeast, maxValueLowValue);
        }
        if(maxValueLowValue >= atLeast){
            maxValueMoveList[0] = ""+maxValueLowValue;
            return maxValueMoveList;
        }
    }

    return maxValueMoveList;
}

private String[] alphaBetaMinValue(Node brdNode, int atLeast, int atMost){
    String [] minValueMoveList = new String[2];

    if(isCutOff(brdNode, 1)){
        minValueMoveList[0] = ""+evaluationFunction(brdNode);
        minValueMoveList[1] = null;
        return minValueMoveList;
    }
    int minValueHighValue = atMost;
    for(int[] action:Actions(brdNode.getBoard())){
        int row = action[0];
        int col = action[1];

        Board modifiedBoard = Result(gameBoard, row, col);
        modifiedBoard.switchTurn();
        Node modifiedBoardNode = new Node(modifiedBoard, brdNode, brdNode.getPly()+1);
        String[] maxValueMoveList = alphaBetaMaxValue(modifiedBoardNode, atLeast, atMost);
        if(Integer.parseInt(maxValueMoveList[0]) < minValueHighValue){
            minValueHighValue = Integer.parseInt(minValueMoveList[0]);
            maxValueMoveList[1] = row+""+col;
            atLeast = Math.min(atLeast, minValueHighValue);
        }
        if(minValueHighValue <= atLeast){
            minValueMoveList[0] = ""+minValueHighValue;
            return minValueMoveList;
        }
    }

    return minValueMoveList;
}


private Board Result(Board brd, int valueRow, int valueCol) {
    Board copyBoard = brd.Copy(); // Getters and Setters
    Cell[][] copyBoardCell = copyBoard.getCells();

    copyBoardCell[valueRow][valueCol].select(); // Flag indicator to know its been selected
    int chosenVal = copyBoardCell[valueRow][valueCol].getValue(); // retrive chosen Value
    copyBoard.setPreviousChosenPosition(valueRow, valueCol); // Set the previous move/position
    if(brd.getTurn()){ // if it is row's turn
        copyBoard.getRowPlayer().setScore(copyBoard.getRowPlayer().getScore()+chosenVal); // add to row player score.
    }
    else{
        copyBoard.getColPlayer().setScore(copyBoard.getColPlayer().getScore()+chosenVal);
    }
    
    return copyBoard; // new modified board
}

private int utilityFunction(Node boardNode) {
    Board brd = boardNode.getBoard();
    if(brd.getRowPlayer().getScore() > brd.getColPlayer().getScore()){
        if(this.gameBoard.getTurn()) // if the original actual move, not hypothetical moves, is rows turn
            return 1; // fill in util body here
        else{
            return -1;
        }
    }
    else {
        if(this.gameBoard.getTurn()){
            return -1;
        }
        else{
            return 1;
        }
    }
}


private double evaluationFunction(Node brdNode){
    // Features and weights
    // Feature: We could choose a value at a row/col then search through the row/col the value was selected at to dictate how well this move will be compared to the next player's move
    // Feature: How well this value we are selecting compared to the rest of the values on the row/col
    // Feature: We could compare the value the current player chosen to the previous players value
    Board brd = brdNode.getBoard();
    double weight = 0;
    double evalFunction = 0;

    if(isTerminal(brd)){
        utilityFunction(brdNode);
    }

    evalFunction += nextPlayersMove(brd); // another weight/feature
    weight = (1.0 - Math.abs(evalFunction))/brd.getCells().length;
    if(valuesGreaterThan(brd) != brd.getCells().length-1){
        evalFunction += valuesGreaterThan(brd)*weight; // arbitrary weight
        evalFunction -= valuesSmallerThan(brd)*weight;} // same feature just the disadvantage part
    evalFunction += scoreAdvantage(brd)*weight;

    
    return evalFunction; // fill in eval body here
}

private int valuesGreaterThan(Board brd){
    Cell[][] cells = brd.getCells();
    int currRow = brd.getPreviousRow();
    int currCol = brd.getPreviousCol();
    int cnt = 0;


    if(brd.getTurn()){ // if previous player was col
        for(int row=0; row<cells[currCol].length; row++){
            // if this value is greater than any value on the column
            if(cells[currRow][currCol].getValue()>cells[row][currCol].getValue()){ 
                cnt++; // count the total values this move is greater than
            }
        }
    }
    else{ // previous player was row
        for(int col=0; col<cells.length; col++){
            if(cells[currRow][currCol].getValue()>cells[currRow][col].getValue()){ 
                cnt++;
            }
    }

}
    return cnt;
}

private int valuesSmallerThan(Board brd){
    Cell[][] cells = brd.getCells();
    int currRow = brd.getPreviousRow();
    int currCol = brd.getPreviousCol();
    int cnt = 0;


    if(brd.getTurn()){ // if previous player was col
        for(int row=0; row<cells[currCol].length; row++){
            // if this value is greater than any value on the column
            if(cells[currRow][currCol].getValue()<cells[row][currCol].getValue()){ 
                cnt++; // count the total values this move is greater than
            }
        }
    }
    else{ // previous player was row
        for(int col=0; col<cells.length; col++){
            if(cells[currRow][currCol].getValue()<cells[currRow][col].getValue()){ 
                cnt++;
            }
    }

}
    return cnt;
}

private double nextPlayersMove(Board brd){
    Cell[][] cells = brd.getCells();
    int currRow = brd.getPreviousRow();
    int currCol = brd.getPreviousCol();
    int futureScore = 0;
    int maxScore = Integer.MIN_VALUE;

    if(brd.getTurn()){ // if previous turn was col
    // Predict row player's move
        for(int col = 0; col<cells.length; col++){
            if(!cells[currRow][col].isSelected()){
                int futureValue = cells[currRow][col].getValue();
                futureScore = brd.getRowPlayer().getScore() + futureValue;
                if(futureScore > maxScore){
                    maxScore = futureScore;
                }
            }
        }
    }
    else{ // predict col player's move
        for(int row = 0; row<cells[currCol].length; row++){
            if(!cells[row][currCol].isSelected()){
                int futureValue = cells[row][currCol].getValue();
                futureScore = brd.getColPlayer().getScore() + futureValue;
                if(futureScore > maxScore){
                    maxScore = futureScore;
                }
            }
        }
    }

    int previousScore = brd.getRowPlayer().getScore();
    if(brd.getTurn()){ // previous turn was col
        previousScore = brd.getColPlayer().getScore();
    } // Compare the next turn's score with the previous turn's score
    return (double)(previousScore-maxScore)/(previousScore+maxScore);
}



private double scoreAdvantage(Board brd){
    int rowScore = brd.getRowPlayer().getScore();
    int colScore = brd.getColPlayer().getScore();
    double scoreAdvantage;

    if(brd.getTurn()){ // prev turn was col
        scoreAdvantage = (double)(rowScore-colScore)/(colScore+rowScore);
    }
    else{
        scoreAdvantage = (double)(colScore-rowScore)/(rowScore+colScore);
    }

    if(Math.abs(scoreAdvantage) == 1.0){
        return 0;
    }

    return scoreAdvantage;
    
}

private ArrayList<int[]> Actions(Board brd){
    ArrayList<int[]> actions = new ArrayList<>();
    
    Cell[][] cells = brd.getCells();
    int currRow = 0;
    int currCol = 0;

    if(brd.getTurn()){ // if row Player turn
        if(brd.getPreviousRow()==-1)
            currRow = brd.getCurrentRow();
        else{
            currRow = brd.getPreviousRow();
        }
        for(int col=0; col<cells[currRow].length; col++){
            if(!(cells[currRow][col].isSelected())){
                int[] rowColList = new int[2];
                rowColList[0] = currRow;
                rowColList[1] = col;
                actions.add(rowColList);}
        }
    }
    else{ 
        if(brd.getPreviousCol()==-1)
            currCol = brd.getCurrentCol();
        else{
            currCol = brd.getPreviousCol();
        }
        for(int row=0; row<cells.length; row++){
            if(!cells[row][currCol].isSelected()){
                int[] rowColList = new int[2];
                rowColList[0] = row;
                rowColList[1] = currCol;
                actions.add(rowColList);}
        }
    }
    
    return actions;
}

public boolean isTerminal(Board brd){

    if(isBoardFull(brd)){
        return true;
    }
    return !nextAvailableMove(brd);
    
}

private boolean isBoardFull(Board brd){
    Cell[][] brdCells = brd.getCells();
    for(int row=0; row<brdCells.length; row++){
        for(int col=0; col<brdCells[row].length; col++){
            if(!brdCells[row][col].isSelected()){ // if there are a few values left
                return false;
            }
    }
}
    return true;

}

private boolean nextAvailableMove(Board brd){
    int prevRow = brd.getPreviousRow();
    int prevCol = brd.getPreviousCol();
    int selectionRange = brd.getBoardSize()-1;

    if(prevRow == -1 && prevCol == -1){ // Inital movement
      return true;  
    }
    // We could make this faster by creating an if case based on who's move it is
    else{
        if(brd.getTurn()){ // if current turn is rows
            // Reason why we're going up until the boardSize-1 is because we're thinking in sense of the corner of the grid. Sure we could make another if statement to descrease traversal range
            for(int nextCol=-selectionRange; nextCol<=selectionRange; nextCol++){
                int adjacentCol = prevCol+nextCol;
                if(validCell(prevRow, adjacentCol)){
                    if(!brd.getCells()[prevRow][adjacentCol].isSelected())
                        return true;
                }
            }
        }
        for(int nextRow=-selectionRange; nextRow<=selectionRange; nextRow++){
            int adjacentRow = prevRow+nextRow;
            if(validCell(adjacentRow, prevCol)){
                if(!brd.getCells()[adjacentRow][prevCol].isSelected()){
                    return true;
                }
            }
        }
    return false;
    }
   
}

private boolean validCell(int row, int col){
    return (row >= 0 && row < gameBoard.getBoardSize()) && (col >= 0 && col < gameBoard.getBoardSize());
}

private boolean isCutOff(Node brdNode, int depth){
    Board brd = brdNode.getBoard();
    if(isTerminal(brd)){
        return true;
    }
    if(brdNode.getPly() >= depth){
        return true;
    }
    return false;
}


}