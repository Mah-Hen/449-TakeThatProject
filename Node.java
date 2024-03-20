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

public String minimaxSearch(Board brd){
    String valueMove = maxValue(brd);
    
}

private ArrayList action(Board brd){
    ArrayList<Integer> actions = new ArrayList<>();

    return actions;
}

private boolean isTerminal(Board brd){
    return brd.gameOver() || brd.getRowPlayer().isWinner(brd.getColPlayer()) 
    || brd.getColPlayer().isWinner(brd.getRowPlayer());
}

}