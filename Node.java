import java.util.ArrayList;
import java.util.List;

public class Node {
    private Board gameBoard;
    private Node parent;
    private int evalFunction;
    private int utilFunction;


public Node(Board brd, Node parent) {
    this.gameBoard = brd; // intialize intial state
    this.parent = parent; // initialize state parent
}

public int minimaxSearch(Board brd){
    String[] valueMoveList = maxValue(brd);
    int utilValue = Integer.parseInt(valueMoveList[0]);
    if(valueMoveList[1]!=null){
        int moveValue = Integer.parseInt(valueMoveList[1]);
        return moveValue;}
    return null; // Gotta find a number to act as our null flag or maybe change the return type
}

public Integer[] maxValue(Board brd){
    Integer [] maxValueMoveList = new Integer [2];
    if(isTerminal(brd)){
        maxValueMoveList[0] = utilityFunction(brd);
        maxValueMoveList[1] = null;
        return maxValueMoveList;
    }
    int lowValue = Integer.MIN_VALUE;
    for(Integer action:Actions(brd)){
        Board newBoard = Result(brd, action);
        Integer [] minValueMoveList = minValue(newBoard);
        Integer minLowValue = minValueMoveList[0]; // Utility Function
        if (minLowValue > lowValue){ // if the node's util function is greater than the low value
            lowValue = minLowValue;
            maxValueMoveList[0] = lowValue;
            maxValueMoveList[1] = action; 
        }
    }
    return maxValueMoveList;
}

public Integer[] minValue(Board brd){
    Integer[] minValueMoveList = new Integer[2];
    if(isTerminal(brd)){
        minValueMoveList[0] = utilityFunction(brd);
        minValueMoveList[1] = null;
        return minValueMoveList;
    }
    int highValue = Integer.MAX_VALUE;
    for(Integer action:Actions(brd)){
        Board newBoard = Result(brd, action); // new Board after the action is applied
        Integer[] maxValueMoveList = maxValue(newBoard);
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
    Board newBoard = new Board(); // Getters and Setters
    return newBoard;
}

private Integer utilityFunction(Board brd) {
    return 0; 
}

private ArrayList<Integer> Actions(Board brd){
    ArrayList<Integer> actions = new ArrayList<>();
    Cell[][] cells = brd.getCells();

    if(brd.getTurn()){ // if rows turn
        int currRow = brd.getCurrentRow();
        for(int i=0; i<cells[currRow].length; i++){
            actions.add(i);
        }
    }
    else{ 
        int currCol = brd.getCurrentCol();
        for(int i=0; i<cells[currCol].length; i++){
            actions.add(i);
        }
    }
    
    return actions;
}

private boolean isTerminal(Board brd){
    return brd.gameOver() || brd.getRowPlayer().isWinner(brd.getColPlayer()) 
    || brd.getColPlayer().isWinner(brd.getRowPlayer());
}

}