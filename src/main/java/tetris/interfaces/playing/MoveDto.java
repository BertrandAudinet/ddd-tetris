package tetris.interfaces.playing;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "move")
public class MoveDto {
    String direction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
