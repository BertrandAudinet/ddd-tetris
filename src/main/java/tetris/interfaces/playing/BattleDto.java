package tetris.interfaces.playing;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "battle")
public class BattleDto {

    private String battleId;

    private List<String> opponents;

    private String status;

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public void setOpponents(List<String> opponents) {
        this.opponents = opponents;

    }

    public String getBattleId() {
        return battleId;
    }

    public List<String> getOpponents() {
        return opponents;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
