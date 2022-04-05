package FlipFlop.flip.flop.models.flipFlopGameObjects;

import FlipFlop.flip.flop.models.communicationObjects.BoardObject;

import java.util.*;

public class Board {
    private List<Image> images;
    private Image backgroundImage;
    private int boardWidth = -1;
    private int boardHeight = -1;
    private List<List<BoardCell>> board;
    private int version = 0;

    //this is the number of same looking images have to be flipped at once
    private int flipCount;

    //this is the current image on hold, -1 means no image on hold
    private int onHoldImageId;

    //count to already flipped, to indicate if win
    private int flipped;

    public static class Coor {
        public final int w;
        public final int h;
        public Coor(int w, int h) {
            this.w = w;
            this.h = h;
        }
    }

    //A bunch of cell being temporarily flipped
    private List<Coor> onSelect;

    /**
     * Getters
     */

    public BoardCell getCell(int x, int y) {
        if (this.board.size() <= x || this.board.get(x).size() <= y) {
            return null;
        }
        return this.board.get(x).get(y);
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public Image getImage(int imageId){
        if (this.images.size()>imageId){
            return this.images.get(imageId);
        }
        return null;
    }

    public int getOnHoldImageId() {
        return onHoldImageId;
    }

    public List<Coor> getOnSelect() {
        return onSelect;
    }

    public int getFlipCount() {
        return flipCount;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public int getVersion() {
        return version;
    }

    public boolean gameOver() {
        return (this.flipped == this.boardWidth*this.boardHeight);
    }

    /**
     * Getter ends
     * */

    /**
     * Setters
     * */
    public void uploadImage(String imgPath){
        Image img = new Image(imgPath);
        uploadImage(img);
    }

    public void uploadImage(Image img){
        if(!this.images.contains(img)) {
            this.images.add(img);
        }
    }

    public void deleteImage(String imgPath){
        Image img = new Image(imgPath);
        deleteImage(img);
    }

    public void deleteImage(Image img){
        if(this.images.contains(img)) {
            this.images.remove(img);
        }
    }

    public void clearImage(){
        this.images.clear();
        this.backgroundImage = null;
    }

    public void uploadBackgroundImage(String img){
        uploadBackgroundImage(new Image(img));
    }
    public void uploadBackgroundImage(Image img){
        this.backgroundImage = img;
    }

    public void generateNewBoard(int newW, int newH, int flipCount){
        this.version = this.version + 1;

        if(this.images.isEmpty() || Objects.isNull(this.backgroundImage)) {
            return;
        }

        this.boardWidth = newW;
        this.boardHeight = newH;
        this.flipCount = flipCount;
        this.flipped = 0;
        this.board.clear();

        List<BoardCell> cells = new ArrayList<>();
        Random random = new Random();
        int imageChoice;

        while(cells.size() < this.boardWidth*this.boardHeight){
            imageChoice = random.nextInt(this.images.size());
            for (int i = 0; i< flipCount; i++) {

                cells.add(new BoardCell(imageChoice, true));
            }
        }
        Collections.shuffle(cells);
        for (int x = 0; x< this.boardWidth; x++){
            this.board.add(new ArrayList<>());
            for(int y = 0; y< this.boardHeight; y++){
                this.board.get(x).add(cells.remove(0));
            }
        }
    }

    public void setOnHoldImageId(int onHoldImageId) {
        this.onHoldImageId = onHoldImageId;
    }

    public void addOnSelect(Coor coor) {
        this.onSelect.add(coor);
    }

    public void flipOneMore() { this.flipped = this.flipped + 1; }

    /**
     * Setters end
     * */

    public Board(){
        this.images = new ArrayList<>();
        this.onSelect = new ArrayList<>();
        this.board = new ArrayList<>();
        this.onHoldImageId = -1;
    }













    public class Action {
        public Image image;
        public int w;
        public int h;
        public Action(Image image, int w, int h){
            this.image = image.copy();
            this.w = w;
            this.h = h;
        }
    }

    public List<Action> click(int w, int h) {
        if (this.board.size() <= w || this.board.get(w).size() <=h || w<0 || h<0) {
            //this will happen if click outside table raneg in FE
            return null;
        }

        List<Action> actions = new ArrayList<>();
        BoardCell boardCell = this.board.get(w).get(h);
        if (boardCell.getCellState().equals(BoardCell.CellState.UNFLIPPEDE)){
            //only this case will do some update
            if(this.onHoldImageId == -1 || this.onHoldImageId ==boardCell.getImage()){
                //successfully click
                boardCell.flip();
                this.onHoldImageId = boardCell.getImage();
                this.onSelect.add(new Coor(w, h));

                actions.add(new Action(this.images.get(boardCell.getImage()),w,h));
                if (this.onSelect.size() == this.flipCount) {
                    //successfully flip, mark those cells as flip done
                    while(!this.onSelect.isEmpty()){
                        Coor c = this.onSelect.remove(0);
                        this.board.get(c.w).get(c.h).flipDone();
                    }

                    this.onHoldImageId = -1;
                }

            }else{
                //failed click
                while(!this.onSelect.isEmpty()) {
                    Coor c = this.onSelect.remove(0);
                    this.board.get(c.w).get(c.h).flipBack();
                    actions.add(new Action(this.backgroundImage, c.w, c.h));
                }
                this.onHoldImageId = -1;
            }
        } else {
            return null;
        }

        return null;
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

    //ToDo add test to that

}
