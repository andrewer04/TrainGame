package map;

public class Tunnel extends Rail {
    private int tunnelNumber;
    private boolean selected;
    private int tunnelLength;

    public Tunnel() {
        this.setType(Tunnel);
    }

    public int getTunnelLength() {
        return tunnelLength;
    }

    public void creating(Tunnel t){

    }

    public void deleting(Tunnel t){

    }
}
