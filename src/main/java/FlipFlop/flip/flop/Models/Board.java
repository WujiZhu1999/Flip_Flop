package FlipFlop.flip.flop.Models;

import java.util.*;

public class Board {
    private List<Image> images;
    private Image backgroundImage;
    private int boardWidth;
    private int boardHeight;
    private List<List<BoardCell>> board;

    //this is the number of same looking images have to be flipped at once
    private int flipCount;

    //this is the current image on hold, -1 means no image on hold
    private int onHoldImageId;

    private class coor {
        public final int w;
        public final int h;
        public coor(int w, int h) {
            this.w = w;
            this.h = h;
        }
    }

    //A bunch of cell being temporarily flipped
    private List<coor> onSelect;

    public int getBoardWidth(){
        return this.boardWidth;
    }

    public int getBoardHeight(){
        return this.boardHeight;
    }

    public Board(){
        this.images = new ArrayList<>();
        this.onSelect = new ArrayList<>();
        this.board = new ArrayList<>();
        this.onHoldImageId = -1;
    }

    public void uploadImage(String imgPath){
        Image img = new Image(imgPath);
        uploadImage(img);
    }

    public void deleteImage(String imgPath){
        Image img = new Image(imgPath);
        deleteImage(img);
    }

    public void uploadImage(Image img){
        if(!this.images.contains(img)) {
            this.images.add(img);
        }
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
                this.onSelect.add(new coor(w, h));

                actions.add(new Action(this.images.get(boardCell.getImage()),w,h));
                if (this.onSelect.size() == this.flipCount) {
                    //successfully flip, mark those cells as flip done
                    while(!this.onSelect.isEmpty()){
                        coor c = this.onSelect.remove(0);
                        this.board.get(c.w).get(c.h).flipDone();
                    }

                    this.onHoldImageId = -1;
                }

            }else{
                //failed click
                while(!this.onSelect.isEmpty()) {
                    coor c = this.onSelect.remove(0);
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

    public void generateNewBoard(int newW, int newH, int flipCount){
        if(this.images.isEmpty() || Objects.isNull(this.backgroundImage)) {
            return;
        }

        this.boardWidth = newW;
        this.boardHeight = newH;
        this.flipCount = flipCount;
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
