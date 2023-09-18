package lk.ijse.dep.service;

public class BoardImpl implements Board{
    private static Piece[][] pieces;
    private BoardUI boardUI;


    public BoardImpl(BoardUI boardUI) {
        pieces=new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        this.setBoardUI(boardUI);

        for (int col = 0; col < 6; col++) {
            for (int row = 0; row < 5; row++) {
                getPieces()[col][row] = Piece.EMPTY;
            }
        }
    }

    public static Piece[][] getPieces() {
        return pieces;
    }

    public static void setPieces(Piece[][] pieces) {
        BoardImpl.pieces = pieces;
    }

    public BoardUI getBoardUI() {
        return boardUI;
    }


    @Override
    public int findNextAvailableSpot(int col) {
        for (int i=0;i<5;i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        if (findNextAvailableSpot(col) != -1){
            return true;
        }
        return false;
    }

    @Override
    public boolean exitLegalMoves() {
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++) {
                if(pieces[i][j] == Piece.EMPTY){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        pieces[col][findNextAvailableSpot(col)] = move;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row] = move;
    }


    @Override
    public Winner findWinner() {
       for (int i=0; i<6; i++) {
           if (pieces[i][1] == pieces[i][2] && pieces[i][2] == pieces[i][3] &&pieces[i][3]!=Piece.EMPTY) {

               if (pieces[i][0] == pieces[i][2]) {
                   return new Winner(pieces[i][0], 0, i, 3, i);
               }
               if (pieces[i][4] == pieces[i][2]) {
                   return new Winner(pieces[i][0], 1, i, 4, i);
               }
           }
       }
        for (int i=0; i<5; i++) {
            if (pieces[2][i] == pieces[3][i] &&pieces[3][i]!=Piece.EMPTY) {
                if (pieces[2][i] == pieces[4][i] && pieces[5][i] == pieces[2][i]) {
                    return new Winner(pieces[4][i], i, 2, i, 5);
                }
                if (pieces[2][i] == pieces[0][i] && pieces[1][i] == pieces[2][i]) {
                    return new Winner(pieces[2][i], i, 0, i, 3);

                }
                if (pieces[2][i] == pieces[1][i] && pieces[4][i] == pieces[2][i]) {
                    return new Winner(pieces[2][i], i, 1, i, 4);

                }
            }
        }
        return null;
    }

    public void setBoardUI(BoardUI boardUI) {
        this.boardUI = boardUI;
    }
}