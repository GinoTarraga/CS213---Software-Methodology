/**
 * @author Gino Tarraga & Kaival Patel
 */

package com.cs213.chess.chess.pieces;

import com.cs213.chess.chess.board.board;

/**
 * The king is the most important piece, but is one of the weakest.
 * The king can only move one square in any direction - up, down, to the sides, and diagonally.
 */

public class king extends pieces{
    private boolean inCheck;

    /**
     * default constructor
     * @param color color of the piece
     * @param board current board
     * @param rank rank of the piece
     * @param file file of the piece
     */

    public king(char color, board board,int rank, int file){
        super(color, board);
        this.type = 'K';
        this.rank = rank;
        this.file = file;
        this.rankss = rank;
        this.filess = file;
    }

    /**
     * checks whether if you can move the king or not
     * @param color color
     * @param file1 file1
     * @param rank1 rank1
     * @param file2 file2
     * @param rank2 rank2
     * @param piece piece
     * @return if the piece can move from file1,rank1 to file2 rank2
     */
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
                //if(this.board.is_spot_open(7,7)) break label;
                //castling on the right
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
                    //if(this.board.is_spot_open(7,0)) break label;
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
                //if(this.board.is_spot_open(0,7)) break label;
                //castling on the right
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
                    //if (this.board.is_spot_open(0, 0)) break label;
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

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece, int xu){

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
                //if(this.board.is_spot_open(7,7)) break label;
                //castling on the right
                if (rank1 == rank2 && file1 == (file2 - 2)) {
                    pieces p1 = this.board.get_piece_at(7, 7);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("wR")) {
                        if (this.board.is_spot_open(7, 5) && this.board.is_spot_open(7, 6)) {
                            //this.board.set_piece_at(7, 7, 7, 5, p1);
                            //System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
                //castling on the left
                else if (rank1 == rank2 && file1 == (file2 + 2)) {
                    //if(this.board.is_spot_open(7,0)) break label;
                    pieces p1 = this.board.get_piece_at(7, 0);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("wR")) {
                        if (this.board.is_spot_open(7, 1) && this.board.is_spot_open(7, 2) && this.board.is_spot_open(7, 3)) {
                            //this.board.set_piece_at(7, 0, 7, 3, p1);
                            //System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
            }

            //checking for castling the black king
            else if (piece.getTimesmoved() == 0 && file1 == 4 && rank1 == 0) {
                //if(this.board.is_spot_open(0,7)) break label;
                //castling on the right
                if (rank1 == rank2 && file1 == (file2 - 2)) {
                    pieces p1 = this.board.get_piece_at(0, 7);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("bR")) {
                        if (this.board.is_spot_open(0, 5) && this.board.is_spot_open(0, 6)) {
                            //this.board.set_piece_at(0, 7, 0, 5, p1);
                            //System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
                //castling on the left
                else if (rank1 == rank2 && file1 == (file2 + 2)) {
                    //if (this.board.is_spot_open(0, 0)) break label;
                    pieces p1 = this.board.get_piece_at(0, 0);
                    if(p1 == null) return false;
                    if (p1.getTimesmoved() == 0 && p1.getPieceType().equals("bR")) {
                        if (this.board.is_spot_open(0, 1) && this.board.is_spot_open(0, 2) && this.board.is_spot_open(0, 3)) {
                            //this.board.set_piece_at(0, 0, 0, 3, p1);
                            //System.out.println("\ncastling\n");
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    /**
     * move the piece
     */
    public void move(){
        return;
    }
}
