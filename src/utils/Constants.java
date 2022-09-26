package utils;

public class Constants {

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants{

        public static final int RUNNING = 1;
        public static final int IDLE = 0;
        public static final int ATTACK = 2;

        public static int GetSpriteAmounts(int player_action) {

            switch(player_action) {

                case RUNNING:
                    return 4;
                case IDLE:
                    return 6;
                case ATTACK:
                    return 3;
                default:
                    return 1;
            }
        }
    }
}
