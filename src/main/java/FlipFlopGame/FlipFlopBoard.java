package FlipFlopGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class is used to store all information about current game
 * 1. Images: a list of images used for Flip Flop game
 * ToDo: request image from online db
 * 2. Player list: identified by player unique id
 * ToDo: use web socket to identify unique id
 * 3. Board Size (size of flip flop board)
 * 4. Coordinate, image id pair
 * 5. Coordinate, flip condition
 * 6. Coordinate, flip method
 * 7. Clean board
 * 8. Generate Un-flip board
 * */
public class FlipFlopBoard {
    private List<String> images;
    private List<String> playerIds;
    private final int boardWidth;
    private final int boardHeight;
    private final List<List<FlipFlopCell>> board;

    //this is the number of same looking images have to be flipped at once
    private int flipCount;
    private int onHoldImageId;

    private class coor {
        public final int w;
        public final int h;
        public coor(int w, int h) {
            this.w = w;
            this.h = h;
        }
    }

    private List<coor> onSelect;

    public int getBoardWidth(){
        return this.boardWidth;
    }

    public int getBoardHeight(){
        return this.boardHeight;
    }

    public FlipFlopBoard(int w, int h, int flipCount){

        this.boardWidth = w;
        this.boardHeight = h;
        this.board = new ArrayList<List<FlipFlopCell>>();
        this.playerIds = new ArrayList<>();
        this.images = new ArrayList<>();
        this.onSelect = new ArrayList<>();
        this.flipCount = flipCount;
        this.onHoldImageId = -1;
    }

    public boolean addNewPlayer(String userName) {
        if (this.playerIds.contains(userName)) {
            return false;
        } else {
            this.playerIds.add(userName);
            return true;
        }
    }

    public void removeNewPlayer(String userName) {
        if (this.playerIds.contains(userName)) {
            this.playerIds.remove(userName);
        }
    }

    /**
     * True means the click successfully update, false means failed to update
     * */
    public synchronized void click(int w, int h){
        if (this.board.size() <= w || this.board.get(w).size() <=h ) {
            //this not supposed to happen
            return;
        }

        FlipFlopCell flipFlopCell = this.board.get(w).get(h);
        if (flipFlopCell.getCellState().equals(FlipFlopCell.CellState.UNFLIPPEDE)){
            if (this.onHoldImageId == -1 || this.onHoldImageId == flipFlopCell.getImage()) {
                flipFlopCell.flip();
                this.onHoldImageId = flipFlopCell.getImage();
                this.onSelect.add(new coor(w,h));


                //if reached sufficient on one trial, make them permanent flipped
                if(this.onSelect.size() == this.flipCount) {
                    while(!this.onSelect.isEmpty()) {
                        coor c = this.onSelect.remove(0);
                        this.board.get(c.w).get(c.h).flipDone();
                    }

                    this.onHoldImageId = -1;
                }
            } else {
                //flip back anything else
                while(!this.onSelect.isEmpty()) {
                    coor c = this.onSelect.remove(0);
                    this.board.get(c.w).get(c.h).flipBack();
                }

                this.onHoldImageId = -1;
            }

        }

    }

    /**
     * Generate a new game board
     *
     */
    public void generateBoard(){
        if( this.boardWidth*this.boardHeight % this.flipCount != 0) {
            System.out.println("Failed to generate");
        }
        List<FlipFlopCell> list = new ArrayList<>();
        Random random = new Random();
        int imageChoice;
        while(list.size()<(this.boardHeight*this.boardWidth)) {
            imageChoice = random.nextInt(this.images.size());

            for(int i = 0; i< this.flipCount ; i++) {
                list.add(new FlipFlopCell(imageChoice, true));
            }

        }
        Collections.shuffle(list);

        for (int w = 0; w < this.boardWidth; w++) {
            this.board.add(new ArrayList<>());
            for (int h = 0; h < this.boardHeight; h++) {
                this.board.get(w).add(list.remove(0));
            }
        }


    }

    public void addImage(String image){
        this.images.add(image);
    }

    public void removeImage(String image) {
        this.images.remove(image);
    }

    public void testPrint(){
        for (int i = 0; i< this.boardWidth; i++) {
            for (int j = 0; j < this.boardHeight; j++){
                System.out.print(this.board.get(i).get(j).getImage());
                System.out.print(this.board.get(i).get(j).getCellState());
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) {
        test2();
    }

    //ToDo factor these out soon
    public static void test1() {
        FlipFlopBoard flipFlopBoard = new FlipFlopBoard(5,10,2);
        flipFlopBoard.addImage("a");
        flipFlopBoard.addImage("b");
        flipFlopBoard.addImage("c");
        flipFlopBoard.generateBoard();
        flipFlopBoard.testPrint();
    }

    public static void test2() {
        FlipFlopBoard flipFlopBoard = new FlipFlopBoard(5,10,2);
        flipFlopBoard.addImage("a");
        flipFlopBoard.addImage("b");
        flipFlopBoard.addImage("c");
        flipFlopBoard.generateBoard();
        flipFlopBoard.testPrint();
        System.out.println("--------------------------------------------------------");
        flipFlopBoard.click(1,1);
        flipFlopBoard.testPrint();
        System.out.println("--------------------------------------------------------");
        flipFlopBoard.click(1,2);
        flipFlopBoard.testPrint();
        System.out.println("--------------------------------------------------------");
        flipFlopBoard.click(1,3);
        flipFlopBoard.testPrint();
        System.out.println("--------------------------------------------------------");
        flipFlopBoard.click(1,4);
        flipFlopBoard.testPrint();
        System.out.println("--------------------------------------------------------");
        flipFlopBoard.click(1,5);
        flipFlopBoard.testPrint();
    }
}
