package lk.ijse.dep.service;

import java.util.Random;

public class AiPlayer extends Player {
    public AiPlayer(BoardImpl newBoard) {
        super(newBoard);
    }
    @Override
    public void movePiece(int col){

//        Random r = new Random();
//        int i;
//        do{
//            i = r.nextInt(6);
//        }while (!board.isLegalMove(i));
//        col=i;
//
//        if (board.isLegalMove(col)){
        int row=0;
            int maxEvl= (int) Double.NEGATIVE_INFINITY;
            for (int colom=0; colom<6; colom++){
                if (board.isLegalMove(colom)){
                   int tempRow = board.findNextAvailableSpot(colom);
                    board.updateMove(colom,tempRow, Piece.GREEN);
                    int heuristicVal= minimax(0, false);
                    board.updateMove(colom,tempRow, Piece.EMPTY);
                    if (maxEvl<heuristicVal){
                        maxEvl=heuristicVal;
                        col=colom;
                        row=tempRow;
                    }
                }
            }

            board.updateMove(col,row,Piece.GREEN);

            board.getBoardUI().update(col,false);
            Winner winner = board.findWinner();

            if (winner==null){
                boolean b = board.exitLegalMoves();
                if(!b){
                    board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }else{
                board.getBoardUI().notifyWinner(winner);
            }
        }

   private int minimax(int depth,boolean maximizingPlayer){

        Winner winner= board.findWinner();
       //check if a player as alrady won or if the maximum depth has been reached
        if(depth == 4 || winner != null){
            if(winner == null) {
                return 0;
            }
            if (winner.getWinningPiece()== Piece.GREEN){
                return  1;
            }
            if (winner.getWinningPiece()== Piece.BLUE){
                return  -1;
            }
        }

        if (maximizingPlayer){
            //itarate through each colomn and try to make a move
            int maxEval= (int) Double.NEGATIVE_INFINITY;
            for (int i=0; i<board.NUM_OF_COLS; i++){
                //make the move and call minimax recurisivly with the other player
                if(board.isLegalMove(i)){
                    int row=board.findNextAvailableSpot(i);
                    board.updateMove(i, row, Piece.GREEN);
                    int heuristicVal = minimax(depth + 1, false);
                    board.updateMove(i, row, Piece.EMPTY);
                    maxEval = Math.max(heuristicVal, maxEval);
                }
            }
            return maxEval;
        }else{
            int minEval=(int)Double.POSITIVE_INFINITY;
            for (int i=0; i<board.NUM_OF_COLS; i++){
                if (board.isLegalMove(i)){
                    int row=board.findNextAvailableSpot(i);
                    board.updateMove(i, row, Piece.BLUE);
                    int heuristicVal = minimax(depth + 1, true);
                    board.updateMove(i, row, Piece.EMPTY);
                    minEval =  Math.min(heuristicVal , minEval);
                }
            }
            return minEval;
        }
    }
}