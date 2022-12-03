package test;

import java.util.Objects;
import java.util.Random;

public class Tile {
    public final int score;
    public final char letter;

    private Tile(int score, char letter) {
        this.score = score;
        this.letter = letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return score == tile.score && letter == tile.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, letter);
    }

    public static class Bag {
        int[] lettersInBagOrigin = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        int[] lettersInBag = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        int amountOfTilesInBag = 98;
        Tile[] tiles = {new Tile(1, 'A'), new Tile(3, 'B'), new Tile(3, 'C'), new Tile(2, 'D'),
                new Tile(1, 'E'), new Tile(4, 'F'), new Tile(2, 'G'), new Tile(4, 'H'),
                new Tile(1, 'I'), new Tile(8, 'J'), new Tile(5, 'K'), new Tile(1, 'L'),
                new Tile(3, 'M'), new Tile(1, 'N'), new Tile(1, 'O'), new Tile(3, 'P'),
                new Tile(10, 'Q'), new Tile(1, 'R'), new Tile(1, 'S'), new Tile(1, 'T'),
                new Tile(1, 'U'), new Tile(4, 'V'), new Tile(4, 'W'), new Tile(8, 'X'),
                new Tile(4, 'Y'), new Tile(10, 'Z')};

        public Tile getRand() {
            if (amountOfTilesInBag == 0)
                return null;
            Random random = new Random();//Creating random object
            int rnd = random.nextInt(26);//generate integer between 0-25.
            while (lettersInBag[rnd] == 0)
                rnd = (rnd + 1) % 26;
            lettersInBag[rnd]--;
            amountOfTilesInBag--;
            return tiles[rnd];
        }

        public Tile getTile(char letter) {
            if (amountOfTilesInBag == 0)
                return null;
            if(Character.isUpperCase(letter)){
                int index = letter-'A';
                if (lettersInBag[index] == 0)
                    return null;
                else {
                    lettersInBag[index]--;
                    amountOfTilesInBag--;
                    return tiles[index];
                }
            }
            return null;
        }

        public void put(Tile test) {
            int index = test.letter - 'A';
            int maxLetters = lettersInBagOrigin[index];
            if (lettersInBag[index] < maxLetters) {
                lettersInBag[index]++;
                amountOfTilesInBag++;
            }
        }

        public int size() {
            return amountOfTilesInBag;
        }

        public int[] getQuantities() {
            return lettersInBag.clone();
        }

        private Bag() {}//private constructor for Singleton design.
        private static Bag singleInstance=null;
        public static Bag getBag(){
            if(singleInstance==null)
                singleInstance=new Bag();
            return singleInstance;
        }

    }
}
