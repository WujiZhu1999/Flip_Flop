package FlipFlop.flip.flop.models.flipFlopGameObjects;

public class BoardFactory {
    public static void setupBoard(Board board, String difficulty){
        if (difficulty.equals("easy")){
            easy(board);
        }

        if (difficulty.equals("normal")){
            normal(board);
        }

        if (difficulty.equals("hard")){
            hard(board);
        }

        if (difficulty.equals("insane")){
            insane(board);
        }

        if (difficulty.equals("ha")){
            ha(board);
        }
    }
    private static void easy(Board board){
        board.clearImage();
        board.uploadBackgroundImage("image\\01.png");
        board.uploadImage("image\\02.png");
        board.uploadImage("image\\03.png");
        board.uploadImage("image\\04.png");
        board.uploadImage("image\\05.png");
        board.generateNewBoard(4,4,2);
    }
    private static void normal(Board board){
        board.clearImage();
        board.uploadBackgroundImage("image\\01.png");
        board.uploadImage("image\\02.png");
        board.uploadImage("image\\03.png");
        board.uploadImage("image\\04.png");
        board.uploadImage("image\\05.png");
        board.uploadImage("image\\06.png");
        board.uploadImage("image\\07.png");
        board.uploadImage("image\\08.png");
        board.uploadImage("image\\09.png");
        board.generateNewBoard(6,6,2);
    }
    private static void hard(Board board){
        board.clearImage();
        board.uploadBackgroundImage("image\\01.png");
        board.uploadImage("image\\02.png");
        board.uploadImage("image\\03.png");
        board.uploadImage("image\\04.png");
        board.uploadImage("image\\05.png");
        board.uploadImage("image\\06.png");
        board.uploadImage("image\\07.png");
        board.uploadImage("image\\08.png");
        board.uploadImage("image\\09.png");
        board.uploadImage("image\\10.png");
        board.uploadImage("image\\11.png");
        board.uploadImage("image\\12.png");
        board.uploadImage("image\\13.png");
        board.generateNewBoard(8,8,2);
    }
    private static void insane(Board board){
        board.clearImage();
        board.uploadBackgroundImage("image\\01.png");
        board.uploadImage("image\\02.png");
        board.uploadImage("image\\03.png");
        board.uploadImage("image\\04.png");
        board.uploadImage("image\\05.png");
        board.uploadImage("image\\06.png");
        board.uploadImage("image\\07.png");
        board.uploadImage("image\\08.png");
        board.uploadImage("image\\09.png");
        board.uploadImage("image\\10.png");
        board.uploadImage("image\\11.png");
        board.uploadImage("image\\12.png");
        board.uploadImage("image\\13.png");
        board.uploadImage("image\\14.png");
        board.uploadImage("image\\15.png");
        board.uploadImage("image\\16.png");
        board.generateNewBoard(10,10,5);
    }
    private static void ha(Board board){
        board.clearImage();
        board.uploadBackgroundImage("image\\01.png");
        board.uploadImage("image\\02.png");
        board.uploadImage("image\\03.png");
        board.uploadImage("image\\04.png");
        board.uploadImage("image\\05.png");
        board.uploadImage("image\\06.png");
        board.uploadImage("image\\07.png");
        board.uploadImage("image\\08.png");
        board.uploadImage("image\\09.png");
        board.uploadImage("image\\10.png");
        board.uploadImage("image\\11.png");
        board.uploadImage("image\\12.png");
        board.uploadImage("image\\13.png");
        board.uploadImage("image\\14.png");
        board.uploadImage("image\\15.png");
        board.uploadImage("image\\16.png");
        board.generateNewBoard(12,12,8);
    }
}
