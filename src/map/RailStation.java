package map;

public class RailStation extends Field{
    private String colour;

    public RailStation() {
        this.setType(RailStation);
    }

    public String getColour() {
        return colour;
    }
}
