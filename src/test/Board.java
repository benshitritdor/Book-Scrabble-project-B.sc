package test;

import java.util.ArrayList;

public class Board {
    Tile[][] board;
    char[][] bonusBoard;
    private Board(){
    board=new Tile[15][15];
    //R-Red Tile(TRIPLE WORD)
    //A-Azure Tile(DOUBLE LETTER)
    //Y-Yellow Tile(DOUBLE WORD)
    //B-Blue Tile(TRIPLE LETTER)
    //S-Star Tile(DOUBLE WORD FOR FIRST PLAYER)
    bonusBoard= new char[][]{{'R',' ',' ','A',' ',' ',' ','R',' ',' ',' ','A',' ',' ','R'},
                             {' ','Y',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','Y',' '},
                             {' ',' ','Y',' ',' ',' ','A',' ','A',' ',' ',' ','Y',' ',' '},
                             {'A',' ',' ','Y',' ',' ',' ','A',' ',' ',' ','Y',' ',' ','A'},
                             {' ',' ',' ',' ','Y',' ',' ',' ',' ',' ','Y',' ',' ',' ',' '},
                             {' ','B',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','B',' '},
                             {' ',' ','A',' ',' ',' ','A',' ','A',' ',' ',' ','A',' ',' '},
                             {'R',' ',' ','A',' ',' ',' ','S',' ',' ',' ','A',' ',' ','R'},
                             {' ',' ','A',' ',' ',' ','A',' ','A',' ',' ',' ','A',' ',' '},
                             {' ','B',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','B',' '},
                             {' ',' ',' ',' ','Y',' ',' ',' ',' ',' ','Y',' ',' ',' ',' '},
                             {'A',' ',' ','Y',' ',' ',' ','A',' ',' ',' ','Y',' ',' ','A'},
                             {' ',' ','Y',' ',' ',' ','A',' ','A',' ',' ',' ','Y',' ',' '},
                             {' ','Y',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','Y',' '},
                             {'R',' ',' ','A',' ',' ',' ','R',' ',' ',' ','A',' ',' ','R'}};
    }
    private static Board singleInstance=null;
    public static Board getBoard(){
        if(singleInstance==null)
            singleInstance=new Board();
        return singleInstance;
    }
    public Tile[][] getTiles(){
        Tile[][] currentBoard = new Tile[15][15];
        for (int i = 0; i < 15; i++) {
            System.arraycopy(board[i],0,currentBoard[i],0,15);
        }
        return currentBoard;
    }

    //****************** assistant functions for boardLegal ******************//
    private boolean consistInBoard(Word word){
        if(word.getRow()<0 || word.getRow()>14 || word.getCol()<0 || word.getCol()>14)
            return false;
        int length = word.getTiles().length;
        if(word.getVertical()) {
            return word.getRow() + length - 1 <= 14;
        }
        else {
            return word.getCol() + length - 1 <= 14;
        }
    }
    private boolean wellFirstPlay(Word word){
        if(word.getVertical()){
            if(word.getCol()!=7)
                return false;
            else
                return word.getRow() <= 7 && word.getRow() + word.getTiles().length - 1 >= 7;
        }
        else{
            if(word.getRow()!=7)
                return false;
            else
                return word.getCol() <= 7 && word.getCol() + word.getTiles().length - 1 >= 7;
        }
    }
    private boolean identicalLetters(Word word){
    int index;
    for (int i = 0; i < word.tiles.length; i++) {
        if(word.tiles[i]==null) {
            if (word.getVertical()) {
                index = word.getRow() + i;
                if(board[index][word.col] != null)
                    return true;
            }
            else {
                index = word.getCol() + i;
                if(board[word.getRow()][index] != null)
                    return true;
            }
        }
    }
    //if there isn't override enough to check if tile exists on the word path's.
    return false;
    }
    private boolean overRide(Word word){
        int index ;
        for (int i = 0; i < word.getTiles().length; i++) {
            if(word.getVertical())
            {
                index = word.getRow()+i;
                if(board[index][word.getCol()]!=null && word.tiles[i]!=null)
                    return true;
            }
            else{
                index = word.getCol()+i;
                if(board[word.getRow()][index]!=null && word.tiles[i]!=null)
                    return true;
            }
        }
        return false;
        }
    private boolean adjustLetters(Word word){
        int length = word.getTiles().length;
        for (int i = 0; i < length; i++) {
            if(word.getVertical()){
                if(word.getRow()>0 && board[word.getRow()-1][word.getCol()]!=null)
                    return true;
                if(word.getRow()+length-1<14 && board[word.getRow()+length][word.getCol()]!=null)
                    return true;
                if(word.getCol()>0 && board[word.getRow()+i][word.getCol()-1]!=null)
                    return true;
                if(word.getCol()<14 && board[word.getRow()+i][word.getCol()+1]!=null)
                    return true;
            }
            else{
                if(word.getCol()>0 && board[word.getRow()][word.getCol()-1]!=null)
                    return true;
                if(word.getCol()+length-1<14 && board[word.getRow()][word.getCol()+length]!=null)
                    return true;
                if(word.getRow()>0 && board[word.getRow()-1][word.getCol()+i]!=null)
                    return true;
                if(word.getRow()<14 && board[word.getRow()+1][word.getCol()+i]!=null)
                    return true;
            }
        }
        return false;
    }
    //****************** until here ******************//

    public boolean boardLegal(Word word){

        if(!(consistInBoard(word)))//check if the word fit in
            return false;

        if(overRide(word))//check if the word override letter on board.
            return false;

        if(board[7][7]==null) {
            if(wellFirstPlay(word))
                return true;
        }

        if (identicalLetters(word))
            return true;

        return adjustLetters(word);
    }

    public boolean dictionaryLegal(Word word){
        return true;
    }

    //****************** assistant functions for getWords ******************//
    private Tile tileInPlace(Word word, int i , int j) {
        //return tile on board or going to be in board(from input word),if there isn't return null
        //check if there is a tile in board[i][j]
        if(board[i][j]!=null)
            return board[i][j];
        //check if the input word are going to sit on place [i][j] in board
        int row = word.getRow();
        int col = word.getCol();
        int length = word.getTiles().length;
        if (word.getVertical()) {
            if (col != j)
                return null;
            for (int k = 0; k < length; k++) {
                if (row + k == i) {
                    if (word.getTiles()[k] != null)
                        return word.getTiles()[k];
                    else
                        return null;
                }
            }
        }
        if (!word.getVertical()) {
            if (row != i)
                return null;
            for (int k = 0; k < length; k++) {
                if (col + k == j) {
                    if (word.getTiles()[k] != null)
                        return word.getTiles()[k];
                    else
                        return null;
                }
            }
        }
        return null;
    }//return the specific Tile in the Input word while given place [i,j] in board
    private int lengthNewWord(Word word,int row,int col,boolean vertical){//[row,col] it the index to check consider the vertical direction.
        int i = row;
        int j = col;
        int k;
        int length = 0;
        if(vertical){
            while(tileInPlace(word,i,j)!=null){//check if Tile in board or Tile in Input word
                i--;
            }
            i++;
            k = i;
            while (k<=14 && (tileInPlace(word,k,j)!=null)){
                length++;
                k++;
            }
        }
        else {
            while(tileInPlace(word,i,j)!=null){//check if Tile in board or Tile in Input word
                j--;
            }
            j++;
            k = j;
            while (k<=14 && (tileInPlace(word,i,k)!=null)){
                length++;
                k++;
            }
        }
        return length;
    }
    private int setNewIndex(Word word,int row,int col,boolean vertical){
        int i = row;
        int j = col;
        if(vertical) {
            while (tileInPlace(word, i, j) != null) i--;//check if Tile in board or Tile in Input word
            return ++i;
        }
        else {
            while (tileInPlace(word, i, j) != null) j--;//check if Tile in board or Tile in Input word
            return ++j;
        }
    }//return the row/col index that are going to change
    private Tile[] setNewTileArray(Word word,int row,int col,int length,boolean vertical){
        int i = row;
        int j = col;
        Tile[] tiles = new Tile[length];
        if(vertical)
            for (int t = 0; t < length; t++) tiles[t] = tileInPlace(word,i+t,j);
        if(!vertical)
            for (int t = 0; t < length; t++) tiles[t] = tileInPlace(word,i,j+t);
        return tiles;
    }
    private Word setNewColWord(Word word,int delta){
        int i = word.getRow() ;
        int j = word.getCol()+delta;
        int length = lengthNewWord(word,i,j,true);
        i = setNewIndex(word,i,j,true);
        Tile[] tiles = setNewTileArray(word,i,j,length,true);
        return new Word(tiles,i,j,true);
    }
    private Word setNewRowWord(Word word,int delta){
        int i = word.getRow() +delta;
        int j = word.getCol();
        int length = lengthNewWord(word,i,j,false);
        j = setNewIndex(word,i,j,false);
        Tile[] tiles = setNewTileArray(word,i,j,length,false);
        return new Word(tiles,i,j,false);
    }
    //****************** until here ******************//

    public ArrayList<Word> getWords(Word word){
        ArrayList<Word> words = new ArrayList<>();
        int row = word.getRow();
        int col = word.getCol();
        int length = word.getTiles().length;
        boolean vertical = word.getVertical();
        if(vertical) {
            words.add(setNewColWord(word,0));
            for (int i = 0; i < length; i++) {//search for adjust letter along the word that create new word.
                if(word.tiles[i]!=null)//if there isn't tile there isn't new word
                    if (col > 0 && board[row + i][col - 1] != null || col < 14 && board[row + i][col + 1] != null)
                        words.add(setNewRowWord(word,i));
            }
        }
        else {
            words.add(setNewRowWord(word,0));
            for (int i = 0; i < length; i++) {
                if(word.tiles[i]!=null)
                    if(row > 0 && board[row - 1][col + i] != null || row < 14 && board[row + 1][col + i] != null)
                        words.add(setNewColWord(word,i));
            }
        }
    return words;
    }

    //****************** assistant functions for getScore ******************//
    private int lettersBonus(Word word) {
        int counterBonus = 0;
        if (word.getVertical()) {
            for (int i = 0; i < word.getTiles().length; i++) {
                if ((bonusBoard[word.getRow() + i][word.getCol()] != 'A') && (bonusBoard[word.getRow() + i][word.getCol()] != 'B'))
                    counterBonus += word.tiles[i].score;
                else if (bonusBoard[word.getRow() + i][word.getCol()] == 'A')
                    counterBonus += (word.tiles[i].score * 2);
                else
                    counterBonus += (word.tiles[i].score * 3);
            }
        }
        else{
            for (int i = 0; i < word.getTiles().length; i++) {
                if ((bonusBoard[word.getRow()][word.getCol() + i] != 'A') && (bonusBoard[word.getRow()][word.getCol() + i] != 'B'))
                    counterBonus += word.tiles[i].score;
                else if (bonusBoard[word.getRow()][word.getCol() + i] == 'A')
                    counterBonus += (word.tiles[i].score * 2);
                else
                    counterBonus += (word.tiles[i].score * 3);
            }
        }
        return counterBonus;
    }
    private int wordsBonus(Word word,int currentScore){
        int counterBonus = currentScore;
        if (word.getVertical()) {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (bonusBoard[word.getRow() + i][word.getCol()] == 'S' && board[7][7]==null)
                    counterBonus *= 2;
                if(bonusBoard[word.getRow() + i][word.getCol()] == 'Y')
                    counterBonus *= 2;
                if(bonusBoard[word.getRow() + i][word.getCol()] == 'R')
                    counterBonus *= 3;
            }
        }
        else{
            for (int i = 0; i < word.getTiles().length; i++) {
                if (bonusBoard[word.getRow()][word.getCol()+i] == 'S' && board[7][7]==null)
                    counterBonus *= 2;
                if(bonusBoard[word.getRow()][word.getCol()+i] == 'Y')
                    counterBonus *= 2;
                if(bonusBoard[word.getRow()][word.getCol()+i] == 'R')
                    counterBonus *= 3;
            }
        }
    return counterBonus;
    }
    //****************** until here ******************//

    public int getScore(Word word){
        int scoreBonus = lettersBonus(word);
        scoreBonus = wordsBonus(word,scoreBonus);
        return scoreBonus;
    }

    //****************** assistant functions for tryPlaceWord ******************//
    private void implementWordOnBoard(Word word){
        int length = word.getTiles().length;
        int row = word.getRow();
        int col = word.getCol();
        if(word.getVertical()){
            for (int i = 0; i < length; i++) {
                if(board[row+i][col]==null)
                    board[row+i][col]=word.tiles[i];
            }
        }
        else {
            for (int i = 0; i < length; i++) {
                if (board[row][col + i] == null)
                    board[row][col + i] = word.tiles[i];
            }
        }
    }
    //****************** until here ******************//
    public int tryPlaceWord(Word word){
        int totalScore=0;
        if(boardLegal(word)) {
            if (dictionaryLegal(word)){
                ArrayList<Word> words = getWords(word);
                for (Word i : words) {
                    if(dictionaryLegal(i)){
                        totalScore+=getScore(i);
                        implementWordOnBoard(i);
                    }
                }
            }
        }
        return totalScore;
    }
}


