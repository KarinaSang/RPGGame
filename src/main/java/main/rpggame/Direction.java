package main.rpggame;

public enum Direction {
    LEFT, RIGHT, UP, DOWN;

    public static Direction getNewDirection() {
        int result = (int) (Math.random()*4);
        return Direction.values()[result];
    }
}
