package ac.bootcamp.row1game;

public class Randomizer {

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
