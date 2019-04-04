/**
 * @author Gino Tarraga & Kaival Patel
 */

package com.cs213.chess.chess.board;


import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cs213.chess.chess.R;
import com.cs213.chess.chess.pieces.*;
import com.cs213.chess.chess.warnings.warning;


/**
 * This class prints out the initial board and moves the pieces on the board
 */
public class board extends AppCompatActivity{

    //created a new board size 9*9 where in file goes in row 9 and rank goes in col 9 and the game is played in 8*8
    String[][] board = new String[9][9];
    private pieces[][] pieces; //created pieces array object
    warning warn = new warning(); //warning object
    int[][] white_king_location = new int[1][2]; //stores the rank in [0][0] and file in [0][1] of the white king
    int[][] black_king_location = new int[1][2];//stores the rank in [0][0] and file in [0][1] of the black king
    char kingInTrouble = 0; //determines which king is in trouble
    boolean checkmate = false; //is checkmate
    public boolean enpassant = false; //is en passant
    boolean stalemate = false; // is stalemate
    boolean check = false; //is check
    boolean draw = false; //is draw
    boolean enpassants = false;
    boolean promotion = false;
    public pieces tempPiece, tempPiece2 = null, pass = null;

    public com.cs213.chess.chess.pieces.pieces getPass() {
        return pass;
    }

    public void setPass(com.cs213.chess.chess.pieces.pieces pass) {
        this.pass = pass;
    }

    int checker = 0;

    /**
     * default constructor
     * call setBoard method which sets up the initial board
     */
    public board(){
        setBoard();
    }

    /**
     * Sets up the initial board
     */
    public void setBoard(){
        this.pieces = new pieces[9][9]; //creating 2d array (i.e. chess board)
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                this.pieces[i][j] = null;
            }
        }

        //placing pawns into right places
        for(int i =0; i <this.pieces.length-1; i++){
            this.pieces[1][i] = new pawn('b',this,1,i);
            this.pieces[6][i] = new pawn('w',this,6,i);
        }

        //placing rooks into right places
        this.pieces[0][0] = new rook('b',this,0,0);
        this.pieces[0][7] = new rook('b',this,0,7);
        this.pieces[7][0] = new rook('w',this,7,0);
        this.pieces[7][7] = new rook('w',this,7,7);

        //placing knights into right places
        this.pieces[0][1] = new knight('b',this,0,1);
        this.pieces[0][6] = new knight('b',this,0,6);
        this.pieces[7][1] = new knight('w',this,7,1);
        this.pieces[7][6] = new knight('w',this,7,6);

        //placing bishops into right places
        this.pieces[0][2] = new bishop('b',this,0,2);
        this.pieces[0][5] = new bishop('b',this,0,5);
        this.pieces[7][2] = new bishop('w',this,7,2);
        this.pieces[7][5] = new bishop('w',this,7,5);

        //placing kings and queens into right pieces
        this.pieces[0][3] = new queen('b',this,0,3);
        this.pieces[0][4] = new king('b',this,0,4);
        this.pieces[7][3] = new queen('w',this,7,3);
        this.pieces[7][4] = new king('w',this,7,4);

        this.white_king_location[0][0] = 7;
        this.white_king_location[0][1] = 4;
        this.black_king_location[0][0] = 0;
        this.black_king_location[0][1] = 4;
    }

    /**
     *
     * @return returns all the pieces on the board
     */
    public pieces[][] getPieces(){
        return this.pieces;
    }

    /**
     * checks if the given spot in a board is open or not given rank and file
     * @param rank rank of the piece
     * @param file file of the piece
     * @return if the spot on the board is open
     */
    public boolean is_spot_open(int rank, int file){
        if(rank < 0) return false;
        if(rank > 7) return false;
        if(file < 0) return false;
        if(file > 7) return false;
        pieces pieces = this.pieces[rank][file];
        if(pieces == null)
            return true;
        return false;
    }

    /**
     * gets the color from the given spot on a board, takes in rank and file
     * @param rank rank of the piece
     * @param file file of the piece
     * @return the color of the piece from given spot
     */
    public char get_color_from_spot(int rank, int file){
        pieces pieces = this.pieces[rank][file];
        return pieces.get_color();
    }

    /**
     * gets the piece type from the given spot (i.e. pawn, king, knight, etc.)
     * @param rank rank of the piece
     * @param file file of the piece
     * @return the piece type (i.e. king, pawn, queen, etc)
     */
    public char get_piece_type(int rank,int file){
        pieces pieces = this.pieces[rank][file];
        return pieces.pieceType();
    }

    /**
     * moves the pieces on a board if applicable.  It takes in a color, initial spot, and destination (i.e 'w', a2, a4)
     * @param color color of the piece
     * @param src source that piece is on
     * @param dest destination the pieces is being moved to
     * @return if a piece can move or not
     */
    public boolean move(char color, String src, String dest, String promotion){
        int file1 = convert_file(src.charAt(0));
        int file2 = convert_file(dest.charAt(0));
        int rank1 = convert_rank(Character.getNumericValue(src.charAt(1)));
        int rank2 = convert_rank(Character.getNumericValue(dest.charAt(1)));

        pieces[][] brd = this.pieces;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                brd[i][j] = this.pieces[i][j];
            }
        }

        pieces current_piece = get_piece_at(rank1,file1);
        pieces backup = get_piece_at(rank2,file2);

        //if tyring to move nothing
        if(current_piece == null){
            warn.no_piece_there();
            return false;
        }

        //if trying to move oppenent's piece print error
        if(current_piece.get_color() != color){
            warn.not_ur_piece();
            return false;
        }

        if(current_piece.can_it_move(color, file1, rank1, file2, rank2 , current_piece)){

            set_piece_at(rank1, file1, rank2, file2, current_piece);

            //if the pawn can promote, promote it
            if(current_piece.isPromote()){
                current_piece.setPromotionOff();
                setPromotion();
                promote(color,rank2,file2,promotion);
            }

            if(current_piece.pieceType() == 'K'){
                if(color == 'w'){
                    white_king_location[0][0] = rank2;
                    white_king_location[0][1] = file2;
                }else{
                    black_king_location[0][0] = rank2;
                    black_king_location[0][1] = file2;
                }
            }
            current_piece.rank = rank2;
            current_piece.file = file2;

            if(isCheck()){
                if(this.kingInTrouble == 'b' &&  color == 'b'){

                    warn.kingInTrouble();
                    this.kingInTrouble = 0;
                    set_piece_back(rank1,file1,rank2,file2,current_piece,null);
                    current_piece.rank = rank1;
                    current_piece.file = file1;
                    if(isStalemate(color)){
                        this.stalemate = true;
                        return false;
                    }
                    return false;
                }else if(this.kingInTrouble == 'w' && color == 'w'){
                    isStalemate(color);
                    warn.kingInTrouble();
                    this.kingInTrouble = 0;
                    set_piece_back(rank1,file1,rank2,file2,current_piece,null);
                    current_piece.rank = rank1;
                    current_piece.file = file1;
                    if(isStalemate(color)){
                        this.stalemate = true;
                        return false;
                    }
                    return false;
                }
                this.kingInTrouble = 0;
                if(isCheckmate(color,rank2,file2,current_piece)){
                    checkmate = true;
                    //warn.check_mate();
                }
                else this.check = true;
            }
             else if(isStalemate(color,1)){
                this.stalemate = true;
            }

            if(current_piece.enpassant){
                if(isEnPassant(file2,rank2,color)){
                    this.enpassant = true;
                    this.enpassants = true;
                }
                current_piece.enpassant = false;
            }

            if(isDraw(brd)){
                this.draw = true;
            }
            tempPiece = current_piece;
            tempPiece2 = backup;
            current_piece.rankss = rank2;
            current_piece.filess = file2;
            return true;
        }
        warn.Illegal_move();
        return false;
    }

    public boolean isEnpassants() {
        return enpassants;
    }

    public void setEnpassants() {
        this.enpassants = false;
    }

    public com.cs213.chess.chess.pieces.pieces getTempPiece2() {
        return tempPiece2;
    }

    public void setTempPiece2(com.cs213.chess.chess.pieces.pieces tempPiece2) {
        this.tempPiece2 = tempPiece2;
    }

    public com.cs213.chess.chess.pieces.pieces getTempPiece() {
        return tempPiece;
    }

    public void setTempPiece(com.cs213.chess.chess.pieces.pieces tempPiece) {
        this.tempPiece = tempPiece;
    }

    public boolean getPromotion(){
        return promotion;
    }
    public void setPromotion(){
        promotion = true;
    }
    public void setPromotionOff(){
        promotion = false;
    }

    /**
     *
     * @param brd takes in the all the pieces
     * @return if the game is a draw or not
     */
    public boolean isDraw(pieces[][] brd){
        int count = 0;
        int pieces = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(brd[i][j] != null && get_piece_type(i,j) == 'K' )
                    count++;
                if(brd[i][j] != null) {
                    pieces++;
                }
            }
        }
        if(count  == pieces)
            return true;
        return false;
    }
    public boolean isDraw(){
        return this.draw;
    }

    /**
     * checks for check
     * @param color color of current piece
     * @param current_piece current piece
     * @return if there is check
     */
    public boolean isCheck(char color, pieces current_piece){
        int[][] location = new int[16][2];
        int[][] location2 = new int[16][2];
        for(int i = 0; i < 16; i++){
            location[i][0] = 99;
            location[i][1] = 99;
            location2[i][0] = 99;
            location2[i][1] = 99;
        }
        int krank, kfile, krank2, kfile2;
        if(color == 'w'){
            krank = black_king_location[0][0];
            kfile = black_king_location[0][1];
            krank2 = white_king_location[0][0];
            kfile2 = white_king_location[0][1];
        }else{
            krank = white_king_location[0][0];
            kfile = white_king_location[0][1];
            krank2 = black_king_location[0][0];
            kfile2 = black_king_location[0][1];
        }
        int x  =0,y=0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                pieces p1 = get_piece_at(i,j);
                if(p1 != null){
                    if(p1.get_color() == color){
                        location[x][0] = p1.rank;
                        location[x][1] = p1.file;
                        x++;
                    }else if(p1.get_color() != color){
                        location2[y][0] = p1.rank;
                        location2[y][1] = p1.file;
                        y++;
                    }
                }
            }
        }

        for(int i = 0; i < 16; i++){
            if(location[i][0] == 99) return false;
            pieces p1 = get_piece_at(location[i][0],location[i][1]);
            if(p1.can_it_move(p1.get_color(),location[i][1],location[i][0],kfile,krank,p1)){
                if(color == 'w')
                    this.kingInTrouble = 'b';
                else this.kingInTrouble = 'w';
                return true;
            }
        }


        for(int i = 0; i < 16; i++){
            if(location2[i][0] == 99) return false;
            pieces p1 = get_piece_at(location2[i][0],location2[i][1]);
            if(p1.can_it_move(p1.get_color(),location2[i][1],location2[i][0],kfile2,krank2,p1)){
                if(color == 'w'){
                    this.kingInTrouble = 'w';
                }else this.kingInTrouble = 'b';
                return true;
            }
        }


        return false;
    }

    /**
     * checks if there is a checkmate
     * @param color color of the piece
     * @param rank rank of the piece that is giving a check
     * @param file file of the piece that is giving check
     * @param piece piece that is giving check
     * @return
     */
    public boolean isCheckmate(char color, int rank, int file, pieces piece){
        char ch;
        int krank;
        int kfile;
        if(color == 'w'){
            ch = 'b';
            krank = black_king_location[0][0];
            kfile = black_king_location[0][1];
        }
        else{
            ch = 'w';
            krank = white_king_location[0][0];
            kfile = white_king_location[0][1];
        }


        //check if you can take opponents piece out that is causing the check
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                pieces p1 = get_piece_at(i, j);
                if (p1 == null) continue;
                if (p1.get_color() == ch && p1.can_it_move(ch, j, i, file, rank, p1,1))
                    return false;
            }
        }


        //check if it can be blocked
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                pieces p1 = get_piece_at(i,j);
                if(p1 == null) continue;
                if(p1.get_color() == color) continue;
                for(int k = 0; k<8;k++){
                    for(int l = 0; l<8; l++){
                        if(p1.get_color() == ch && p1.can_it_move(ch,j,i,l,k,p1,1)){
                            pieces p2 = get_piece_at(k,l);
                            set_piece_at(i,j,k,l,p1);
                            if(!isCheck()){
                                if(p2 == null)
                                    set_piece_back(i,j,k,l,p1,null);
                                else set_piece_back(i,j,k,l,p1,p2);
                                return false;
                            }
                            if(p2 == null)
                                set_piece_back(i,j,k,l,p1,null);
                            else set_piece_back(i,j,k,l,p1,p2);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @return if there is a check
     */
    public boolean isCheckChecker(){
        return this.check;
    }

    /**
     * sets the check id off
     */
    public void checkOff(){
        this.check = false;
    }

    /**
     *
     * @return is there is a checkmate
     */
    public boolean isCheckmate(){
        return this.checkmate;
    }

    /**
     * if there is checkmate
     * @param color of the piece thats not in check
     * @return if there is checkmate
     */
    public boolean isStalemate(char color){
        int[][] location = new int[16][2];
        for(int i = 0; i < 16; i++){
            location[i][0] = 99;
            location[i][1] = 99;
        }
        int x = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                pieces p1 = get_piece_at(i,j);
                if(p1 != null && p1.get_color() == color){
                    location[x][0] = p1.rank;
                    location[x][1] = p1.file;
                    x++;
                }
            }
        }

        int moves = 0;
        int check = 0;

        for(int i = 0; i < 16; i++){
            if(location[i][0] == 99) break;
            pieces p1 = get_piece_at(location[i][0],location[i][1]);
            for(int z = 0; z< 8; z++){
                for(int y = 0; y < 8; y++ ){
                    if(p1 != null) {
                        if(p1.can_it_move(p1.get_color(),p1.file,p1.rank,y,z,p1,1)){
                            int a = p1.rank;
                            int b = p1.file;
                            moves++;
                            pieces p2 = get_piece_at(z,y);
                            set_piece_at(p1.rank,p1.file,z,y,p1);
                            if(isCheck())
                                check++;
                            if(p2 == null)
                                set_piece_back(a,b,z,y,p1,null);
                            else set_piece_back(a,b,z,y,p1,p2);

                        }
                    }
                }
            }
        }
        if(moves == check)
            return true;
        return false;
    }

    /**
     * checks for stalemate
     * @param color color of the current piece
     * @param ff useless not needed
     * @return checks for stalemate
     */
    public boolean isStalemate(char color, int ff){
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                pieces p1 = get_piece_at(i,j);
                if(p1 == null) continue;
                if(p1.get_color() == color) continue;
                for(int k = 0; k<8;k++){
                    for(int l = 0; l<8; l++){
                        if(p1.get_color() != color && p1.can_it_move(p1.get_color(),p1.file,p1.rank,l,k,p1,1)){
                            pieces p2 = get_piece_at(k,l);
                            set_piece_at(i,j,k,l,p1);
                            if(!isCheck()){
                                if(p2 == null)
                                    set_piece_back(i,j,k,l,p1,null);
                                else set_piece_back(i,j,k,l,p1,p2);
                                return false;
                            }
                            if(p2 == null)
                                set_piece_back(i,j,k,l,p1,null);
                            else set_piece_back(i,j,k,l,p1,p2);
                        }
                    }
                }
            }
        }
        return true;
    }



    /**
     *
     * @return if there is stalemate
     */
    public boolean isStalemate(){
        return this.stalemate;
    }

    /**
     *
     * @param file2 file of the moving pawn
     * @param rank2 rank of the moving pawn
     * @param color color of the moving pawn
     * @return if En passant is valid or not
     */
    public boolean isEnPassant(int file2, int rank2, char color){
        if(file2 == 0){
            pieces p1 = get_piece_at(rank2, (file2+1));
            if(p1 != null && p1.get_color() != color && p1.pieceType() == 'p')
                return true;
        }else if(file2 == 7){
            pieces p1 = get_piece_at(rank2,(file2-1));
            if(p1 != null && p1.get_color() != color && p1.pieceType() == 'p')
                return true;
        }else{
            pieces p1 = get_piece_at(rank2, (file2 - 1));
            pieces p2 = get_piece_at(rank2, (file2 + 1));
            if(p1 == null && p2 == null) return false;
            if(p1 != null && p1.get_color() != color && p1.pieceType() == 'p') return true;
            else if(p2 != null && p2.get_color() != color && p2.pieceType() == 'p') return true;
        }

        return false;
    }

    /**
     *
     * @return if a player can do en passant or not
     */
    public boolean isEnpassant(){
        return this.enpassant;
    }

    /**
     * sets en passant id off
     */
    public void SetEnpassantOff(){
        this.enpassant = false;
    }





    /**
     * promote the pawn to queen, bishop, knight, or rook
     * @param color color of the promoting pawn
     * @param rank2 rank of the promoting pawn
     * @param file2 file of the promoting pawn
     */
    public void promote(char color, int rank2, int file2, String promotion){

        if(promotion == null){
            this.pieces[rank2][file2] = new queen(color, this,rank2,file2);
        }
        else if(promotion.equals("N")){
            this.pieces[rank2][file2] = new knight(color, this,rank2,file2);
        }else if(promotion.equals("Q")){
            this.pieces[rank2][file2] = new queen(color,this,rank2,file2);
        }
        else if(promotion.equals("B")){
            this.pieces[rank2][file2] = new bishop(color,this,rank2,file2);
        }else if(promotion.equals("R")){
            this.pieces[rank2][file2] = new rook(color, this,rank2,file2);
        }

    }

    /**
     * gives the piece at a certain location
     * @param rank rank
     * @param file file
     * @return a piece from a spot
     */
    public pieces get_piece_at(int rank, int file){
        if(rank < 0) return null;
        if(rank > 7) return null;
        if(file < 0) return null;
        if(file > 7) return null;
        if(this.pieces[rank][file] == null) return null;
        return this.pieces[rank][file];
    }

    /**
     * sets a piece at given destination
     * @param rank1 rank1
     * @param file1 file1
     * @param rank2 rank2
     * @param file2 file2
     * @param piece piece
     * @return sets a piece from rank1, file1 to rank2,file2
     */
    public pieces set_piece_at(int rank1, int file1, int rank2, int file2, pieces piece){
        if(piece == null) return null;
        if(!is_spot_open(rank2,file2)){
            char ch = get_piece_type(rank2,file2);
            //warn.nice_move(ch);
            if(ch == 'K'){
                checker = 1;
            }
            //this.pieces[rank2][file2] = null;
        }
        if(piece.pieceType() == 'K'){
            if(piece.get_color() == 'w'){
                white_king_location[0][0] = rank2;
                white_king_location[0][1] = file2;
            }else{
                black_king_location[0][0] = rank2;
                black_king_location[0][1] = file2;
            }
        }
        this.pieces[rank1][file1] = null;
        piece.setTimesmoved(piece);
        piece.rank = rank2;
        piece.file = file2;
        return this.pieces[rank2][file2] = piece;
    }

    public pieces set_piece_at(int rank, int file, pieces pieces){
        return this.pieces[rank][file] = pieces;
    }

    /**
     * sets the pieces back if moving a piece puts the player's own king at risk
     * @param rank1 rank1
     * @param file1 rank2
     * @param rank2 rank2
     * @param file2 file2
     * @param pieces1 piece 1
     * @param pieces2 piece 1
     * @return puts the piece back if some cases occur such as if the player is in check and is moving a piece that keeps the king in check
     */
    public pieces set_piece_back(int rank1, int file1, int rank2, int file2, pieces pieces1, pieces pieces2){

        if(pieces2 != null)
            this.pieces[rank2][file2] = pieces2;
        pieces1.resetTimesmoved(pieces1);
        if(pieces1.pieceType() == 'K'){
            if(pieces1.get_color() == 'w'){
                white_king_location[0][1] = file1;
                white_king_location[0][0] = rank1;
            }else{
                black_king_location[0][0] = rank1;
                black_king_location[0][1] = file1;
            }
        }
        pieces1.rank = rank1;
        pieces1.file = file1;
        if(pieces2 != null){
            pieces2.rank = rank2;
            pieces2.file = file2;
        }
        if(pieces2 == null)
            this.pieces[rank2][file2] = null;
        else
            this.pieces[rank2][file2] = pieces2;
        return this.pieces[rank1][file1] = pieces1;
    }

    public void set_piece_null(int rank,int file){
        this.pieces[rank][file] = null;
    }

    /**
     * sets the game status to false, hence it will end the game
     */
    public boolean game_status(){
        if(checker == 1)
            return true;
        return false;
    }


    /**
     * converts the file to make it easy to read for the coader
     * @param ch file character
     * @return converts the file into interger fro easy reading
     */
    public int convert_file(char ch){
        switch (ch){
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
        }
        return  -1;
    }

    /**
     * converts rank for easy manipulation
     * @param letter rank
     * @return converts the rank to int for easy reading
     */
    public int convert_rank(int letter){
        switch(letter){
            case 1:
                return 7;
            case 2:
                return 6;
            case 3:
                return 5;
            case 4:
                return 4;
            case 5:
                return 3;
            case 6:
                return 2;
            case 7:
                return 1;
            case 8:
                return 0;
            default:
                return -1;
        }
    }

    /**
     * prints out the board class (the current board)
     * @return prints the class board
     */
    public String toString(){

        //create a string for to be returned
        String board = "";

        //loop through the board and add the pieces to board
        for(int i = 0; i < this.pieces.length; i++){
            for(int j = 0; j < this.pieces.length; j++){
                pieces piece = pieces[i][j];

                //if piece is null then it has to be replaced with "##" or "  "
                //also number the column 8 form 1 to 8 and number row 8 from a - h
                if(piece == null){
                    if(i >= 0 && i < 8 && j < 8) {
                        if (i % 2 == 0) {
                            if (j % 2 != 0)
                                board += "##";
                            else board += "  ";
                        } else {
                            if (j % 2 == 0)
                                board += "##";
                            else board += "  ";
                        }
                    }
                    else if(j == 8 && i != 8){
                        board += (8-i);
                    }
                    //place the letter on the last row
                    else if(i == 8 && j != 8){
                        switch (j){
                            case 0:
                                board += "a "; break;
                            case 1:
                                board += "b "; break;
                            case 2:
                                board += "c "; break;
                            case 3:
                                board += "d "; break;
                            case 4:
                                board += "e "; break;
                            case 5:
                                board += "f "; break;
                            case 6:
                                board += "g "; break;
                            case 7:
                                board += "h "; break;
                        }
                    }
                    board += " ";
                    continue;
                }
                board += piece.getPieceType();
                board += " ";
            }
            board += "\n";
        }
        return board;
    }













    /**
     * checks whether moving a piece puts a king into a danger
     * @return if there is a check or not
     */
    public boolean isCheck(){
        int wrank = white_king_location[0][0];
        int wfile = white_king_location[0][1];
        int brank = black_king_location[0][0];
        int bfile = black_king_location[0][1];

        if(is_King_in_danger_from_bottom(wrank,wfile) || is_king_in_danger_from_down_left(wrank,wfile) || is_king_in_danger_from_down_right(wrank,wfile)
                || is_king_in_danger_from_knight(wrank,wfile) || is_king_in_danger_from_left(wrank,wfile) || is_king_in_danger_from_right(wrank,wfile)
                || is_king_in_danger_from_top(wrank,wfile) || is_king_in_danger_from_up_left(wrank,wfile) || is_king_in_danger_from_up_right(wrank,wfile)){
            this.kingInTrouble = 'w';
        }
        if(is_King_in_danger_from_bottom(brank,bfile) || is_king_in_danger_from_down_left(brank,bfile) || is_king_in_danger_from_down_right(brank,bfile)
                || is_king_in_danger_from_knight(brank,bfile) || is_king_in_danger_from_left(brank,bfile) || is_king_in_danger_from_right(brank,bfile)
                || is_king_in_danger_from_top(brank,bfile) || is_king_in_danger_from_up_left(brank,bfile) || is_king_in_danger_from_up_right(brank,bfile)){
            this.kingInTrouble = 'b';
        }

        if(   (is_King_in_danger_from_bottom(wrank,wfile) || is_king_in_danger_from_down_left(wrank,wfile) || is_king_in_danger_from_down_right(wrank,wfile)
                || is_king_in_danger_from_knight(wrank,wfile) || is_king_in_danger_from_left(wrank,wfile) || is_king_in_danger_from_right(wrank,wfile)
                || is_king_in_danger_from_top(wrank,wfile) || is_king_in_danger_from_up_left(wrank,wfile) || is_king_in_danger_from_up_right(wrank,wfile))
                ||  (is_King_in_danger_from_bottom(brank,bfile) || is_king_in_danger_from_down_left(brank,bfile) || is_king_in_danger_from_down_right(brank,bfile)
                || is_king_in_danger_from_knight(brank,bfile) || is_king_in_danger_from_left(brank,bfile) || is_king_in_danger_from_right(brank,bfile)
                || is_king_in_danger_from_top(brank,bfile) || is_king_in_danger_from_up_left(brank,bfile) || is_king_in_danger_from_up_right(brank,bfile))     )

            return true;

        //if(this.kingInTrouble == 'w' || this.kingInTrouble == 'b') return true;

        return false;
    }



    /**
     * checks if the king is in danger from the left
     * @param rank rank
     * @param file file
     * @return if the king is in danger from left
     */
    public boolean is_king_in_danger_from_left(int rank, int file){

        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();
        if(file-1 < 0) return false;

        for(int i = (file - 1); i >= 0; i--){
            p = get_piece_at(rank,i);
            if(p == null)continue;
            if(is_spot_open(rank,i)){
                if(i == 0) return false;
            }
            else if(i == (file-1) && !is_spot_open(rank,i) && get_color_from_spot(rank,i) != color && (p.pieceType() == 'K')) return true;
            else if(!is_spot_open(rank,i) && get_color_from_spot(rank,i) == color) return false;
            else if(!is_spot_open(rank,i) && get_color_from_spot(rank,i) != color && (p.pieceType() == 'R' || p.pieceType() == 'Q')) return true;
        }
        return false;
    }

    /**
     * checks if the king is in danger from the right
     * @param rank rank
     * @param file file
     * @return if the king is in danger from right
     */
    public boolean is_king_in_danger_from_right(int rank, int file){
        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();

        if(file + 1 > 7) return false;
        for(int i = (file + 1); i <= 7; i++){
            p = get_piece_at(rank,i);
            if(p == null)continue;
            if(is_spot_open(rank,i)){
                if(i == 7) return false;
            }
            else if(i == (file+1) && !is_spot_open(rank,i) && get_color_from_spot(rank,i) != color && p.pieceType() == 'K') return true;
            else if(!is_spot_open(rank,i) && get_color_from_spot(rank,i) == color) return false;
            else if(!is_spot_open(rank,i) && get_color_from_spot(rank,i) != color && (p.pieceType() == 'R' || p.pieceType() == 'Q')) return true;
        }
        return false;
    }

    /**
     * checks if the king is in danger from the top
     * @param rank rank
     * @param file file
     * @return if the king is in danger from top
     */
    public boolean is_king_in_danger_from_top(int rank,int file){
        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();

        if(rank - 1 < 0) return false;
        for(int i = (rank - 1); i >= 0; i--){
            p = get_piece_at(i,file);
            if(p == null)continue;
            if(is_spot_open(i,file)){
                if(i == 0) return false;
            }
            else if(i == (rank - 1) && !is_spot_open(i,file) && get_color_from_spot(i,file) != color && p.pieceType() == 'K') return true;
            else if(!is_spot_open(i,file) && get_color_from_spot(i,file) == color) return false;
            else if(!is_spot_open(i,file) && get_color_from_spot(i,file) != color && (p.pieceType() == 'R' || p.pieceType() == 'Q')) return true;
        }
        return false;
    }

    /**
     * checks if the king is in danger from the bottom
     * @param rank rank
     * @param file file
     * @return if the king is in danger from bottom
     */
    public boolean is_King_in_danger_from_bottom(int rank,int file){
        pieces p = get_piece_at(rank,file);
        if(p == null)
            return false;
        char color = p.get_color();

        if(rank + 1 > 7) return false;

        for(int i = (rank + 1); i <= 7; i++){
            p =get_piece_at(i,file);
            if(p == null)continue;
            if(is_spot_open(i,file)){
                if(i == 7) return false;
            }
            else if(i == (rank + 1) && !is_spot_open(i,file) && get_color_from_spot(i,file) != color && p.pieceType() == 'K') return true;
            else if(!is_spot_open(i,file) && get_color_from_spot(i,file) == color) return false;
            else if(!is_spot_open(i,file) && get_color_from_spot(i,file) != color && (p.pieceType() == 'R' || p.pieceType() == 'Q')) return true;
        }
        return false;
    }

    /**
     * checks if the king is in danger from the up right
     * @param rank rank
     * @param file file
     * @return if the king is in danger from up right
     */

    public boolean is_king_in_danger_from_up_right(int rank, int file){
        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();

        for(int i  = rank -1, j = file + 1; j < 8; i--, j++){
            if(i < 0) return false;
            if(is_spot_open(i,j)){
                if(j == 7) return false;
                continue;
            }

            p = get_piece_at(i,j);
            if((i == rank - 1 && j == file + 1) && !is_spot_open(i,j) && color == 'b' && get_color_from_spot(i,j) != color && p.pieceType() == 'p') return false;
            else if((i == rank -1 && j == file + 1) && !is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'K' || p.pieceType() == 'p'))
                return true;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) == color) return false;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'Q' || p.pieceType() == 'B')) return true;
            else return false;
        }

        return false;
    }

    /**
     * checks if the king is in danger from the down right
     * @param rank rank
     * @param file file
     * @return if the king is in danger from down right
     */
    public boolean is_king_in_danger_from_down_right(int rank, int file){
        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();


        for(int i = rank + 1, j = file + 1; j < 8;i++, j++){
            if(i > 7) return false;
            if(is_spot_open(i,j)){
                if(j == 7) return false;
                continue;
            }
            p = get_piece_at(i,j);

            if((i == rank + 1 && j == file + 1) && !is_spot_open(i,j) && color == 'w' && get_color_from_spot(i,j) != color && p.pieceType() == 'p') return false;
            else if((i == rank +1 && j == file + 1) && !is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'K' || p.pieceType() == 'p'))
                return true;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) == color) return false;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'Q' || p.pieceType() == 'B')) return true;
            else return false;
        }
        return false;
    }

    /**
     * checks if the king is in danger from up left
     * @param rank rank
     * @param file file
     * @return if the king is in danger from up left
     */
    public boolean is_king_in_danger_from_up_left(int rank, int file){
        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();

        for(int i = rank - 1, j = file - 1; j >=0; i--, j--){
            if(i < 0) return false;
            if(j < 0) return false;

            if(is_spot_open(i,j)){
                if(j == 0) return false;
                continue;
            }

            p = get_piece_at(i,j);

            if((i == rank - 1 && j == file - 1) && !is_spot_open(i,j) && color == 'n' && get_color_from_spot(i,j) != color && p.pieceType() == 'p') return false;
            else if((i == rank -1 && j == file - 1) && !is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'K' || p.pieceType() == 'p'))
                return true;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) == color) return false;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'Q' || p.pieceType() == 'B')) return true;
            else return false;
        }
        return false;
    }

    /**
     * checks if the king is in danger from down left
     * @param rank rank
     * @param file file
     * @return if the king is in danger from down left
     */
    public boolean is_king_in_danger_from_down_left(int rank, int file){
        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();

        for(int i = rank + 1, j = file-1; j>= 0; i++, j--){
            if(i > 7) return false;
            if(j < 0) return false;

            if(is_spot_open(i,j)){
                if(j == 0) return false;
                continue;
            }
            p = get_piece_at(i,j);

            if((i == rank + 1 && j == file - 1) && !is_spot_open(i,j) && color == 'n' && get_color_from_spot(i,j) != color && p.pieceType() == 'p') return false;
            else if((i == rank +1 && j == file - 1) && !is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'K' || p.pieceType() == 'p'))
                return true;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) == color) return false;
            else if(!is_spot_open(i,j) && get_color_from_spot(i,j) != color && (p.pieceType() == 'Q' || p.pieceType() == 'B')) return true;
            else return false;
        }
        return false;
    }

    /**
     * checks if the king is in danger from the knight
     * @param rank rank
     * @param file file
     * @return if the king is in danger from knight
     */
    public boolean is_king_in_danger_from_knight(int rank, int file){
        pieces p = get_piece_at(rank,file);
        if(p == null) return false;
        char color = p.get_color();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j<8; j++){
                p = get_piece_at(j,i);
                if(p == null) continue;
                if(p.pieceType() == 'N'){
                    if(p.get_color() != color && p.can_it_move(p.get_color(),i,j,file,rank,p,1))
                        return true;
                }
            }
        }

        return false;
    }
}
