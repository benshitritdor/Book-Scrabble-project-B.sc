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
    public boolean consistInBoard(Word word){
        if(word.getRow()<0 || word.getRow()>14 || word.getCol()<0 || word.getCol()>14)
            return false;
        int length = lengthInWord(word.tiles);
        if(word.getVertical()) {
            return word.getRow() + length - 1 <= 14;
        }
        else {
            return word.getCol() + length - 1 <= 14;
        }
    }//assistant function
    public boolean wellFirstPlay(Word word){
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
    }//assistant function
    public boolean identicalLetters(Word word){
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
    }//assistant function
    public boolean overRide(Word word){
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
        }//assistant function
    /*public boolean unnecessarySpace(Word word){
            int index ;
            for (int i = 0; i < word.getTiles().length; i++) {
                if(word.getVertical())
                {
                    index = word.getRow()+i;
                    if(word.tiles[i]==null && board[index][word.getCol()]==null)
                        return true;
                }
                else{
                    index = word.getCol()+i;
                    if(word.tiles[i]==null && board[word.getRow()][index]==null)
                        return true;
                }
            }
            return false;
        }*///assistant function
    public boolean adjustLetters(Word word){
        int length = lengthInWord(word.tiles);
        for (int i = 0; i < word.getTiles().length; i++) {
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
    }//assistant function
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
    public int lengthInWord(Tile[] tiles){
        int length = 0;
        for(Tile tile : tiles){
            length++;
        }
    return length;
    }
    public Word setInWord(Word word){
        int length = lengthInWord(word.getTiles());
        Tile[] tiles = new Tile[length];
        for (int i = 0; i < length; i++) {
            if(word.tiles[i]!=null) {
                tiles[i] = word.tiles[i];
            }
            else{
                if(word.getVertical()){
                    tiles[i] = board[word.getRow()+i][word.getCol()];
                }
                else
                    tiles[i]=board[word.getRow()][word.getCol()+i];
            }
        }
        return new Word(tiles,word.getRow(),word.getCol(),word.getVertical());
    }//assistant function
    public boolean preImplementation(Word word,int i,int j,int delta,boolean vertical){
        int length = lengthInWord(word.tiles);
        if(word.getVertical() == vertical && vertical==true) {
            if (i+delta >= word.getRow() && i+delta <= word.getRow() + length - 1)
                return true;
        }
        if((word.getVertical() == vertical && vertical==false)){
            if(j+delta >= word.getCol() && i+delta <= word.getCol() + length - 1)
                return true;
        }
        if(vertical) {
            if (word.getRow() == i + delta)
                return true;
        }
        if(!(vertical)){
            if(word.getCol()==j+delta)
                return true;
        }
        return false;
    }
    public Tile currentTile(Word word,int row,int col,boolean vertical) {
        int length = lengthInWord(word.tiles);
        if(word.getVertical() == vertical && vertical==true) {
            for (int i = 0; i < length; i++) {
                if(word.getRow()+i==row)
                    return word.tiles[i];
            }
        }
        if((word.getVertical() == vertical && vertical==false)) {
            for (int i = 0; i < length; i++) {
                if (word.getCol() + i == col)
                    return word.tiles[i];
            }
        }
        if(vertical) {
            for (int i = 0; i < length; i++) {
                if(word.getCol()+i==col)
                    return word.tiles[i];
            }
        }
        if(!(vertical)){
            for (int i = 0; i < length; i++) {
                if(word.getRow()+i==row)
                    return word.tiles[i];
            }
        }
        return null;
    }
    public Word setNewColWord(Word word,int delta){
        int i = word.getRow() - 1;
        int j = word.getCol()+delta;
        while(board[i][j]!=null){
            i--;
        }
        i++;
        int length = 0;
        int k = i;
        while (k<=14 && (board[k][j]!=null || preImplementation(word,i,j,length,true))){
            length++;
            k++;
        }
        Tile[] tiles = new Tile[length];
        for (int t = 0; t < length; t++) {
            if(board[i+t][j]!=null)
                tiles[t] = board[i+t][j];
            else
                tiles[t] = currentTile(word,i+t,j,true);
        }
        return new Word(tiles,i,j,true);
        }//assistant function
    public Word setNewRowWord(Word word,int delta){
        int i = word.getRow() +delta;
        int j = word.getCol()-1;
        while(board[i][j]!=null){
            j--;
        }
        j++;
        int length = 0;
        int k = j;
        while (k<=14 && (board[i][k]!=null || preImplementation(word,i,j,length,false))){
            length++;
            k++;
        }
        Tile[] tiles = new Tile[length];
        for (int t = 0; t < length; t++) {
            if(board[i][j+t]!=null)
                tiles[t] = board[i][j+t];
            else
                tiles[t] = currentTile(word,i,j+t,false);
        }
        return new Word(tiles,i,j,false);
    }//assistant function
    public ArrayList<Word> getWords(Word word){
        ArrayList<Word> words = new ArrayList<>();
        int length = lengthInWord(word.tiles);
        words.add(setInWord(word));
        Word tempWord ;
        if(word.getVertical()) {
            if (word.getRow() > 0 && board[word.getRow() - 1][word.getCol()] != null){
                words.clear();
                tempWord = (setNewColWord(word, 0));
                words.add(tempWord);
            }
            else if (word.getRow() < 14 && board[word.getRow() + length][word.getCol()] != null){
                words.clear();
                tempWord = (setNewColWord(word, 0));
                words.add(tempWord);
            }

            for (int i = 0; i < length; i++) {
                if(word.tiles[i]!=null){
                    if (word.getCol() > 0 && board[word.getRow() + i][word.getCol() - 1] != null) {
                        tempWord = (setNewRowWord(word, i));
                        if(!(words.contains(tempWord)))
                            words.add(tempWord);
                    }
                    else if (word.getCol() < 14 && board[word.getRow() + i][word.getCol() + 1] != null) {
                        tempWord = (setNewRowWord(word, i));
                        if(!(words.contains(tempWord)))
                            words.add(tempWord);
                    }
                }

            }
        }
        else {
            if (word.getCol() > 0 && board[word.getRow()][word.getCol() - 1] != null) {
                words.clear();
                tempWord = (setNewRowWord(word, 0));
                words.add(tempWord);
            }
            else if (word.getCol() < 14 && board[word.getRow()][word.getCol() + length] != null) {
                words.clear();
                tempWord = (setNewRowWord(word, 0));
                words.add(tempWord);
            }

            for (int i = 0; i < length; i++) {
                if(word.tiles[i]!=null){
                    if (word.getRow() > 0 && board[word.getRow() - 1][word.getCol() + i] != null) {
                        tempWord = (setNewColWord(word, i));
                        if (!(words.contains(tempWord)))
                            words.add(tempWord);
                    }
                    else if (word.getRow() < 14 && board[word.getRow() + 1][word.getCol() + i] != null) {
                        tempWord = (setNewColWord(word, i));
                        if (!(words.contains(tempWord)))
                            words.add(tempWord);
                    }
                }
            }
        }
    return words;
    }
    public int lettersBonus(Word word) {
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
    }//assistant function
    public int wordsBonus(Word word,int currentScore){
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
                if (bonusBoard[word.getRow()][word.getCol()+i] == 'S')
                    counterBonus *= 2;
                if(bonusBoard[word.getRow()][word.getCol()+i] == 'Y')
                    counterBonus *= 2;
                if(bonusBoard[word.getRow()][word.getCol()+i] == 'R')
                    counterBonus *= 3;
            }
        }
    return counterBonus;
    }//assistant function
    public int getScore(Word word){
        int scoreBonus = lettersBonus(word);
        scoreBonus = wordsBonus(word,scoreBonus);
        return scoreBonus;
    }
    public void implementWordOnBoard(Word word){
        if(word.getVertical()){
            for (int i = 0; i < word.getTiles().length; i++) {
                if(board[word.getRow()+i][word.getCol()]==null)
                    board[word.getRow()+i][word.getCol()]=word.tiles[i];
            }
        }
        else {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (board[word.getRow()][word.getCol() + i] == null)
                    board[word.getRow()][word.getCol() + i] = word.tiles[i];
            }
        }
    }
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


