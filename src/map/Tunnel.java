package map;

public class Tunnel extends Rail {
    //private int tunnelNumber;
    private boolean selected;
    private int tunnelLength;
    private Tunnel otherTunnel;

    public Tunnel() {
        this.setType("Tunnel");
    }

    public int getTunnelLength() {
        return tunnelLength;
    }

    public boolean isSelected() {
        return selected;
    }

    //ezzel a setterrel be tudja állítani a mapcreator a másik tunnelt ide, hogy tudjanak egymásról
    public void setOtherTunnel(Tunnel otherTunnel) {
        this.otherTunnel = otherTunnel;
    }

    public void creating(Tunnel t){
        this.selected = true;
    }

    public void deleting(Tunnel t){
        this.selected = false;
    }

    //Itt lett egy kis csavar benne, mert arra nem gondoltunk, hogy mi van, ha a másik alagútszájból jön a vonat
    //és így ugye két lehetséges útvonala van újfent. Ilyenkor vagy meghatározunk egy fix irányt minden alagútnak,
    //vagy a szerintem viccesebb, random vagy az egyik vagy a másik irányba fogja továbbküldeni a vonatot.
    @Override
    public Rail getDirection(Rail r){
        if (otherTunnel.isSelected()){
            if (r == otherTunnel){
                if ((Math.random()%2 == 0)) return this.getPossibleRail1();
                else return getPossibleRail2();
            }
            else return otherTunnel; //ha van másik alagútszáj, akkor mindenképpen oda küldi tovább
        }
        else {
            if (this.getPossibleRail1() == r)
                return this.getPossibleRail2();
            else
                return this.getPossibleRail1();
        }
    }
}
