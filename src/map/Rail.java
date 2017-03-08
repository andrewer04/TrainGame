package map;

public class Rail extends Field{
    private boolean byTheStation;
    private int availability;

    public Rail() {
        this.setType(Rail);
    }

    public Rail[] getDirection(){

    }

    public boolean isByTheStation() {
        return byTheStation;
    }

    public int getAvailability() {
        return availability;
    }
}
