package model;

import java.util.List;
import java.util.Objects;

public class GameConfiguration extends Entity<Long>{
    private Integer pos1;
    private Integer pos2;
    private Integer pos3;
    private Integer pos4;
    private Integer pos5;

    public GameConfiguration() {
    }

    public Integer getPos1() {
        return pos1;
    }

    public void setPos1(Integer pos1) {
        this.pos1 = pos1;
    }

    public Integer getPos2() {
        return pos2;
    }

    public void setPos2(Integer pos2) {
        this.pos2 = pos2;
    }

    public Integer getPos3() {
        return pos3;
    }

    public void setPos3(Integer pos3) {
        this.pos3 = pos3;
    }

    public Integer getPos4() {
        return pos4;
    }

    public void setPos4(Integer pos4) {
        this.pos4 = pos4;
    }

    public Integer getPos5() {
        return pos5;
    }

    public void setPos5(Integer pos5) {
        this.pos5 = pos5;
    }

    public void setValues(List<Integer> values){
        pos1 = values.get(0);
        pos2 = values.get(1);
        pos3 = values.get(2);
        pos4 = values.get(3);
        pos5 = values.get(4);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameConfiguration)) return false;
        if (!super.equals(o)) return false;
        GameConfiguration that = (GameConfiguration) o;
        return Objects.equals(pos1, that.pos1) && Objects.equals(pos2, that.pos2) && Objects.equals(pos3, that.pos3) && Objects.equals(pos4, that.pos4) && Objects.equals(pos5, that.pos5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pos1, pos2, pos3, pos4, pos5);
    }

    @Override
    public String toString() {
        return "GameConfiguration{" +
                "pos1=" + pos1 +
                ", pos2=" + pos2 +
                ", pos3=" + pos3 +
                ", pos4=" + pos4 +
                ", pos5=" + pos5 +
                '}';
    }
}
