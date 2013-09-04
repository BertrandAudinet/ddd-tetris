package tetris.domain.game;

public class Tetrominoes {

    public static final Tetrominoes I_Tetrominoes = new Tetrominoes(new int[][]{{0, 0, 0, 0 }, {1, 1, 1, 1 },
                    {0, 0, 0, 0 }, {0, 0, 0, 0 } }, new int[][]{{0, 1, 0, 0 }, {0, 1, 0, 0 }, {0, 1, 0, 0 },
                    {0, 1, 0, 0 } });

    public static final Tetrominoes T_Tetrominoes = new Tetrominoes(new int[][]{{0, 0, 0, 0 }, {1, 1, 1, 0 },
                    {0, 1, 0, 0 }, {0, 0, 0, 0 } }, new int[][]{{0, 1, 0, 0 }, {1, 1, 0, 0 }, {0, 1, 0, 0 },
                    {0, 0, 0, 0 } }, new int[][]{{0, 1, 0, 0 }, {1, 1, 1, 0 }, {0, 0, 0, 0 }, {0, 0, 0, 0 } },
                    new int[][]{{0, 1, 0, 0 }, {0, 1, 1, 0 }, {0, 1, 0, 0 }, {0, 0, 0, 0 } });

    public static final Tetrominoes L_Tetrominoes = new Tetrominoes(new int[][]{{0, 0, 0, 0 }, {1, 1, 1, 0 },
                    {0, 0, 0, 0 }, {0, 0, 0, 0 } }, new int[][]{{1, 1, 0, 0 }, {0, 1, 0, 0 }, {0, 1, 0, 0 },
                    {0, 0, 0, 0 } }, new int[][]{{0, 0, 1, 0 }, {1, 1, 1, 0 }, {0, 0, 0, 0 }, {0, 0, 0, 0 } },
                    new int[][]{{0, 1, 0, 0 }, {0, 1, 0, 0 }, {0, 1, 1, 0 }, {0, 0, 0, 0 } });

    public static final Tetrominoes J_Tetrominoes = new Tetrominoes(new int[][]{{1, 0, 0, 0 }, {1, 1, 1, 0 },
                    {0, 0, 0, 0 }, {0, 0, 0, 0 } }, new int[][]{{0, 1, 1, 0 }, {0, 1, 0, 0 }, {0, 1, 0, 0 },
                    {0, 1, 0, 0 } }, new int[][]{{0, 0, 0, 0 }, {1, 1, 1, 0 }, {0, 0, 1, 0 }, {0, 0, 0, 0 } },
                    new int[][]{{0, 1, 0, 0 }, {0, 1, 0, 0 }, {1, 1, 0, 0 }, {0, 0, 0, 0 } });

    public static final Tetrominoes Z_Tetrominoes = new Tetrominoes(new int[][]{{0, 0, 0, 0 }, {1, 1, 0, 0 },
                    {0, 1, 1, 0 }, {0, 0, 0, 0 } }, new int[][]{{0, 0, 1, 0 }, {0, 1, 1, 0 }, {0, 1, 0, 0 },
                    {0, 0, 0, 0 } });

    public static final Tetrominoes S_Tetrominoes = new Tetrominoes(new int[][]{{0, 0, 0, 0 }, {0, 1, 1, 0 },
                    {1, 1, 0, 0 }, {0, 0, 0, 0 } }, new int[][]{{0, 1, 0, 0 }, {0, 1, 1, 0 }, {0, 0, 1, 0 },
                    {0, 0, 0, 0 } });

    public static final Tetrominoes O_Tetrominoes = new Tetrominoes(new int[][]{{0, 1, 1, 0 }, {0, 1, 1, 0 },
                    {0, 0, 0, 0 }, {0, 0, 0, 0 } });

    private final int[][][] positions;

    private Tetrominoes(int[][]... positions) {
        this.positions = positions;
    }

    public int getRotationNumber() {
        return positions.length;
    }

    public static Tetrominoes getTetrominoes(Tetromino tetromino) {
        switch (tetromino) {
            case I:
                return I_Tetrominoes;
            case T:
                return T_Tetrominoes;
            case L:
                return L_Tetrominoes;
            case J:
                return J_Tetrominoes;
            case Z:
                return Z_Tetrominoes;
            case S:
                return S_Tetrominoes;
            case O:
                return O_Tetrominoes;
            default:
                throw new IllegalArgumentException("Can't create a tetrominoes of " + tetromino);
        }
    }

    public static Tetrominoes getI() {
        return new Tetrominoes(new int[][]{{0, 0, 0, 0 }, {1, 1, 1, 1 }, {0, 0, 0, 0 }, {0, 0, 0, 0 } }, new int[][]{
                        {0, 1, 0, 0 }, {0, 1, 0, 0 }, {0, 1, 0, 0 }, {0, 1, 0, 0 } });
    }

    public int[][] getPicture(int rotation) {
        int position = 0;
        if (rotation < 0) {
            position = (getRotationNumber() - ((-rotation) % getRotationNumber())) % getRotationNumber();
        } else {
            position = rotation % getRotationNumber();
        }
        return positions[position];
    }

}
