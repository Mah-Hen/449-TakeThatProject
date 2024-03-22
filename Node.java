import java.util.ArrayList;
import java.util.List;

public class Node {
    private Board gameBoard;
    private Node parent;
    private int evalFunction;


public Node(Board brd, Node parent) {
    this.gameBoard = brd; // intialize intial state
    this.parent = parent; // initialize state parent
}

private Board getBoard(){
    return gameBoard;
}

private int getEvalFunction(){
    return evalFunction;
}

public int minimaxSearch(){
    Node gameBoardNode = new Node(gameBoard, null);

    Integer[] valueMoveList = maxValue(gameBoardNode);
    int utilValue = valueMoveList[0];
    if(valueMoveList[1]!=null){
        int moveValue = valueMoveList[1];
        return moveValue;}
    return 0; // Gotta find a number to act as our null flag or maybe change the return type
}

public Integer[] maxValue(Node brdNode){
    Integer [] maxValueMoveList = new Integer [2];
    if(isTerminal(brdNode.getBoard())){
        maxValueMoveList[0] = utilityFunction(brdNode);
        maxValueMoveList[1] = null;
        return maxValueMoveList;
    }
    int lowValue = Integer.MIN_VALUE;
    for(Integer action:Actions(brdNode.getBoard())){
        Board modifiedBoard = Result(brdNode.getBoard(), action);
        Node modifiedBoardNode = new Node(modifiedBoard, parent);
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
        minValueMoveList[0] = utilityFunction(brdNode);
        minValueMoveList[1] = null;
        return minValueMoveList;
    }
    int highValue = Integer.MAX_VALUE;
    for(Integer action:Actions(brdNode.getBoard())){
        Board modifiedBoard = Result(brdNode.getBoard(), action); // new Board after the action is applied
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

    if(brd.getTurn()){ // if row Player turn
        int currRow = brd.getCurrentRow();
        for(int col=0; col<copyBoardCell[currRow].length; col++){
            if(copyBoardCell[currRow][col].getValue() == chosenValue){
                copyBoardCell[currRow][col].setValue(-100);; // Flag indicator to know its been selected
                break;
            }
        }
}
else{
    int currCol = brd.getCurrentCol();
    for(int row=0; row<copyBoardCell.length; row++){
        if(copyBoardCell[row][currCol].getValue() == chosenValue){
            copyBoardCell[row][currCol].setValue(-100);; // Flag indicator to know its been selected
            break;
        }
    }
}
    
    return copyBoard; // new modified board
}

private Integer utilityFunction(Node brdNode) {
    //if(isTerminal(brdNode)){

    
    return 0; 
}

private ArrayList<Integer> Actions(Board brd){
    ArrayList<Integer> actions = new ArrayList<>();
    Cell[][] cells = brd.getCells();

    if(brd.getTurn()){ // if row Player turn
        int currRow = brd.getCurrentRow();
        for(int col=0; col<cells[currRow].length; col++){
            if(!cells[currRow][col].isSelected())
                actions.add(cells[currRow][col].getValue());
        }
    }
    else{ 
        int currCol = brd.getCurrentCol();
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
    return nextAvailableMove(brd);
    
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

    if(prevRow == -1 && prevCol == -1){
      return true;  
    }
    else{
        for(int nextRow=-1; nextRow<=1; nextRow++){
            for(int nextCol=-1; nextCol<=-1; nextCol++){
                int adjacentRow = prevRow+nextRow;
                int adjacentCol = prevCol+nextCol;
                if(validCell(adjacentRow, adjacentCol)){
                    if(!brd.getCells()[adjacentRow][adjacentCol].isSelected())
                        return true;
                }
            }
        }
    }
    return false;
}

private boolean validCell(int row, int col){
    return row >= 0 && row < gameBoard.getBoardSize() || col >= 0 && col < gameBoard.getBoardSize();
}

}