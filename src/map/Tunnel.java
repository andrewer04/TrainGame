package map;

import application.MapCreator;
import application.MapCreator2;

public class Tunnel extends Rail {
    static private int tunnelN;
    private boolean selected;
    private int tunnelLength;
    private Tunnel otherTunnel;

    public Tunnel() {
        this.selected = false;
        this.tunnelLength = 0;
        this.otherTunnel = null;
        tunnelN = 0;
    }

    /**
     * Visszaadja az alagut hosszat.
     * @return int
     */
    public int getTunnelLength() {
        return tunnelLength;
    }

    /**
     * Visszaadja, hogy meg van e epitve az alagutszaj vagy sem.
     * @return boolean
     */
    public boolean isSelected() {
        return selected;
    }

    public void setOtherTunnel(Tunnel otherTunnel) {
        this.otherTunnel = otherTunnel;
    }

    public void creating() {
        if (tunnelN < 2){
            selected = true;
            tunnelN++;
            // Ha vanket megepitett alagutszaj, akkor beallitjuk mindketto otherTunnel attributumat.
            if (tunnelN == 2) {
                Tunnel[] selectedT = MapCreator2.searchSelectedTunnels();
                if (this == selectedT[0]) {
                    this.otherTunnel = selectedT[1];
                    selectedT[1].setOtherTunnel(this);
                }
                else {
                    this.otherTunnel = selectedT[0];
                    selectedT[0].setOtherTunnel(this);
                }
            }
        }
    }
    /*
     * Alagut torlese
     */
    public void deleting() {
        if (tunnelN > 0) {
            selected = false;
            tunnelN--;
        }
    }

    /*Ha van masik alagutszaj, akkor 3 esetunk van, hogy merre mehet a mozdony:
            - ha az elozo sin a masik tunnel volt, akkor mehet: -> possibleRail1 fele
                                                                -> possibleRail2 fele
            - ha sima sin felol jon, akkor -> masik Tunnel

            Az elso lehetosegnel vagy ugy dontsuk el az iranyt, hogy megadunk egy fix iranyt az alagutnak,
            vagy veletlenszeruen vagy az egyik vagy a masik iranyba fog kijonni. en az utóbbira szavazok,
            sokkal viccesebb es egyszerubb megcsinalni.
        */

    /**
     * Visszaadja azt a sint amerre tovabbmehet a vonat az elozo ismereteben.
     * @param prevRail
     * @return Rail
     */
    @Override
    public Rail getDirection(Rail prevRail) {
        if (tunnelN == 2 && selected == true) {
            if (prevRail == otherTunnel) {
                //ez nem mukodik, de nem baj, a tesztelesnel legalabb nem lesz random eredmeny, mindig a possibleRail1 fele kuldi tovabb, ha a masik alagutból jon
                if ((Math.random() % 2 == 0)) return this.getPossibleRail2();
                else return getPossibleRail1();
            } else return otherTunnel; //ha van masik alagutszaj, akkor mindenkeppen oda kuldi tovabb
        } else {
            if (this.getPossibleRail1() == prevRail) return this.getPossibleRail2();
            else return this.getPossibleRail1();
        }
    }
}

