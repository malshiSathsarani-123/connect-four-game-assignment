package lk.ijse.dep.service;

public class HumanPlayer extends Player {
    public HumanPlayer(BoardImpl newBoard) {
        super(newBoard);
    }

    @Override
    public void movePiece(int col){
        if (board.isLegalMove(col)){
            board.updateMove(col,Piece.BLUE);
            board.getBoardUI().update(col,true);
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
    }

}
