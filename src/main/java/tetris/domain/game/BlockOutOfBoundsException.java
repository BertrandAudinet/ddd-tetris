package tetris.domain.game;

public class BlockOutOfBoundsException extends RuntimeException {
    public BlockOutOfBoundsException(Block block) {
        super("Block out of range : " + block.toString());
    }
}
