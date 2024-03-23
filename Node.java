import java.util.ArrayList;
import java.util.List;

public class Node {
    private Board gameBoard;
    private Node parent;
    //private int evalFunction;


public Node(Board brd, Node parent) {
    this.gameBoard = brd; // intialize intial state
    this.parent = parent; // initialize state parent
}

private Board getBoard(){
    return gameBoard;
}



public int minimaxSearch(){
    Node gameBoardNode = new Node(gameBoard, null);

    Integer[] valueMoveList = maxValue(gameBoardNode);
    int utilValue = valueMoveList[0];

    if(valueMoveList[1]!=null){
        int moveValue = valueMoveList[1];
        return moveValue;}

    return -1; // Since we're returning a row/column position number 
}

public Integer[] maxValue(Node brdNode){

    Integer [] maxValueMoveList = new Integer [2];
    if(isTerminal(brdNode.getBoard())){
        maxValueMoveList[0] = utilityFunction(brdNode.getBoard());
        maxValueMoveList[1] = null;
        return maxValueMoveList;
    }
    int lowValue = Integer.MIN_VALUE;
    for(Integer action:Actions(brdNode.getBoard())){
        Board modifiedBoard = Result(brdNode.getBoard(), action);
        modifiedBoard.switchTurn();
        Node modifiedBoardNode = new Node(modifiedBoard, brdNode); // Parent is the brdNode
        Integer [] minValueMoveList = minValue(modifiedBoardNode);
        Integer minLowValue = minValueMoveList[0]; // Utility Function
        if (minLowValue > lowValue){ // if the node's util function is greater than the low value
            lowValue = minLowValue;
            maxValueMoveList[0] = lowValue;
            maxValueMoveList[1] = action; 
        }
    }
    return maxValueMoveList;
}

public Integer[] minValue(Node brdNode){

    Integer[] minValueMoveList = new Integer[2];
    if(isTerminal(brdNode.getBoard())){
        minValueMoveList[0] = utilityFunction(brdNode.getBoard());
        minValueMoveList[1] = null;
        return minValueMoveList;
    }
    int highValue = Integer.MAX_VALUE;
    for(Integer action:Actions(brdNode.getBoard())){
        Board modifiedBoard = Result(brdNode.getBoard(), action); // new Board after the action is applied
        modifiedBoard.switchTurn(); // reason why I put here and not at top is because of the first calling of 'maxValue' if we switch turn immediately when alg started it wouldve made player 2 MAX.
        Node modifiedBoardNode = new Node(modifiedBoard, brdNode);
        Integer[] maxValueMoveList = maxValue(modifiedBoardNode);
        Integer maxLowValue = maxValueMoveList[0]; // Utility Function
        if(maxLowValue < highValue){
            highValue = maxLowValue;
            minValueMoveList[0] = highValue;
            minValueMoveList[1] = action;
        }
    } 
    return minValueMoveList;
}

private Board Result(Board brd, Integer action) {
    Board copyBoard = brd.copy(); // Getters and Setters
    Cell[][] copyBoardCell = copyBoard.getCells();
    int chosenValue = action;
    int currRow = 0;
    int currCol = 0;


    if(brd.getTurn()){ // if row Player turn
        if(brd.getPreviousRow()==-1)
            currRow = brd.getCurrentRow();
        else{
            currRow = brd.getPreviousRow();
        }
        for(int col=0; col<copyBoardCell[currRow].length; col++){
            if(copyBoardCell[currRow][col].getValue() == chosenValue){
                copyBoardCell[currRow][col].select(); // Flag indicator to know its been selected
                copyBoard.setPreviousChosenPosition(currRow, col);
                break;
            }
        }
}
else{
    if(brd.getPreviousRow()==-1)
            currCol = brd.getCurrentCol();
        else{
            currCol = brd.getPreviousCol();
        }
    for(int row=0; row<copyBoardCell.length; row++){
        if(copyBoardCell[row][currCol].getValue() == chosenValue){
            copyBoardCell[row][currCol].select();; // Flag indicator to know its been selected
            copyBoard.setPreviousChosenPosition(row, currCol);
            break;
        }
    }
}
    
    return copyBoard; // new modified board
}

private Integer utilityFunction(Board brd) {
    if(isTerminal(brd))
        return 0; // fill in util body here
    return evaluationFunction(brd);
}

private Integer evaluationFunction(Board brd){
    // Features and weights
    // Feature: We could choose a value at a row/col then search through the row/col the value was selected at to dictate how well this move will be compared to the next player's move
    // Feature: How well this value we are selecting compared to the rest of the values on the row/col
    // Feature: We could compare the value the current player chosen to the previous players value
    int prevRow = brd.getPreviousRow();
    int prevCol = brd.getPreviousCol();
    Cell[][] cells = brd.getCells();
    int evalFunction = 0;
    if(brd.getTurn()){ // if rows turn
        
    }

    if(prevRow != -1 && prevCol !=-1){ // 1st Feature
        int prevChosenVal = cells[prevRow][prevCol].getValue();
    }

    return evalFunction; // fill in eval body here
}

private ArrayList<Integer> Actions(Board brd){
    ArrayList<Integer> actions = new ArrayList<>();
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
            if(!cells[currRow][col].isSelected())
                actions.add(cells[currRow][col].getValue());
        }
    }
    else{ 
        if(brd.getPreviousCol()==-1)
            currCol = brd.getCurrentCol();
        else{
            currCol = brd.getPreviousCol();
        }
        for(int row=0; row<cells.length; row++){
            if(!cells[row][currCol].isSelected())
                actions.add(cells[row][currCol].getValue());
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

    if(prevRow == -1 && prevCol == -1){
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



}