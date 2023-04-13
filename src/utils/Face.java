package utils;

public enum Face {
    UP_RIGHT(0),
    RIGHT(1),
    DOWN_RIGHT(2),
    DOWN_LEFT(3),
    LEFT(4),
    UP_LEFT(5);

    public int index;

    Face(int index) {
        this.index = index;
    }

    public static Face GetFace(int index){
        for (Face value : values()) {
            if (value.index == index){
                return value;
            }
        }
        return null;
    }

    public static boolean isStraight(Face face1, Face face2){
        return ((face1 == Face.RIGHT && face2 == Face.LEFT) || (face1 == Face.LEFT && face2 == Face.RIGHT))
                || ((face1 == Face.UP_RIGHT && face2 == Face.DOWN_LEFT) || (face1 == Face.DOWN_LEFT && face2 == Face.UP_RIGHT))
                || ((face1 == Face.UP_LEFT && face2 == Face.DOWN_RIGHT) || (face1 == Face.DOWN_RIGHT && face2 == Face.UP_LEFT));
    }

    public Face getOppositeSide(){
        return switch (this) {
            case UP_RIGHT -> Face.DOWN_LEFT;
            case RIGHT -> Face.LEFT;
            case DOWN_RIGHT -> Face.UP_LEFT;
            case DOWN_LEFT -> Face.UP_RIGHT;
            case LEFT -> Face.RIGHT;
            case UP_LEFT -> Face.DOWN_RIGHT;
        };
    }
}
