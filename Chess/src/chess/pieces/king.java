// author Gino Tarraga, Kaival Patel
package chess.pieces;

import chess.board.board;

//document somethings plz, having trouble knowing whats what
public class king extends pieces{
    private boolean inCheck;

    public king(char color, board board,int rank, int file){
        super(color, board);
        this.type = 'K';
        this.rank = rank;
        this.file = file;
    }

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){

        if(file1 == file2 && rank1 == rank2) return false;

        //moving up
        if ((file1 == file2 && rank2 == (rank1 - 1) && this.board.is_spot_open(rank2, file2))
                || (file1 == file2 && rank2 == (rank1 - 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color)) { //move up
            return true;
        }

        //moving down
        if ((file1 == file2 && rank2 == (rank1 + 1) && this.board.is_spot_open(rank2, file2))
                || (file1 == file2 && rank2 == (rank1 + 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color) ) { //move down
            return true;
        }

        //moving right
        if((rank1 == rank2 && file2 == (file1 + 1) && this.board.is_spot_open(rank2,file2))
                || (rank1 == rank2 && file2 == (file1 + 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color) ){// move right
            return true;
        }

        //moving left
        if((rank1 == rank2 && file2 == (file1 - 1) && this.board.is_spot_open(rank2,file2))
                || (rank1 == rank2 && file2 == (file1-1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color)){//move left
            return true;
        }

        //moving right up
        if((file1 == (file2 - 1) && rank1 == (rank2 + 1) && this.board.is_spot_open(rank2,file2))
                || (file1 == (file2 - 1) && rank1 == (rank2 + 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color)){//move right up diagnol
            return true;
        }

        //moving left up
        if((file1 == (file2 + 1) && rank1 == (rank2 + 1) && this.board.is_spot_open(rank2,file2))
                || (file1 == (file2 + 1) && rank1 == (rank2 + 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color)){//move left up diagnol
            return true;
        }

        //moving right down
        if((file1 == (file2 - 1) && rank1 == (rank2 - 1) && this.board.is_spot_open(rank2,file2))
                || (file1 == (file2 - 1) && rank1 == (rank2 - 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color)){//move right down diagnol
            return true;
        }

        //moving left down
        if((file1 == (file2 + 1) && rank1 == (rank2 - 1) && this.board.is_spot_open(rank2,file2))
                || (file1 == (file2 + 1) && rank1 == (rank2-1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color )){//move left down diagnol
            return true;
        }

        label:
        {
            //checking for castling the white king
            if (piece.getTimesmoved() == 0 && file1 == 4 && rank1 == 7) {
                
                if (rank1 == rank2 && file1 == (file2 - 2)) {
                    pieces p1 = this.board.get_piece_at(7, 7);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("wR")) {
                        if (this.board.is_spot_open(7, 5) && this.board.is_spot_open(7, 6)) {
                            this.board.set_piece_at(7, 7, 7, 5, p1);
                            System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
                //castling on the left
                else if (rank1 == rank2 && file1 == (file2 + 2)) {
                    
                    pieces p1 = this.board.get_piece_at(7, 0);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("wR")) {
                        if (this.board.is_spot_open(7, 1) && this.board.is_spot_open(7, 2) && this.board.is_spot_open(7, 3)) {
                            this.board.set_piece_at(7, 0, 7, 3, p1);
                            System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
            }

            //checking for castling the black king
            else if (piece.getTimesmoved() == 0 && file1 == 4 && rank1 == 0) {
               
                if (rank1 == rank2 && file1 == (file2 - 2)) {
                    pieces p1 = this.board.get_piece_at(0, 7);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("bR")) {
                        if (this.board.is_spot_open(0, 5) && this.board.is_spot_open(0, 6)) {
                            this.board.set_piece_at(0, 7, 0, 5, p1);
                            System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
                //castling on the left
                else if (rank1 == rank2 && file1 == (file2 + 2)) {
                    
                    pieces p1 = this.board.get_piece_at(0, 0);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("bR")) {
                        if (this.board.is_spot_open(0, 1) && this.board.is_spot_open(0, 2) && this.board.is_spot_open(0, 3)) {
                            this.board.set_piece_at(0, 0, 0, 3, p1);
                            System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    public void move(){
        return;
    }
}
