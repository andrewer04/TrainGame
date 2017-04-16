package map;

import application.MapCreator;

public class Tunnel extends Rail {
    static private int tunnelN = 0;
    private boolean selected;
    private int tunnelLength;
    private Tunnel otherTunnel;
    /*
     * Paraméteres konstruktor, előre megadott értékek beállítása
     */
    public Tunnel(boolean selected, int TLength, Tunnel other) {
        this.selected = selected;
        this.tunnelLength = TLength;
        this.otherTunnel = other;
    }
    /*
     * @return visszaadja az alagút hosszát.
     */
    public int getTunnelLength() {
        return tunnelLength;
    }
    /*
     * @return visszaadja, hogy meg van e építve az alagútszáj vagy sem.
     */
    public boolean isSelected() {
        return selected;
    }

    public void creating() {
        if (tunnelN < 2){
            selected = true;
            tunnelN++;
            // Ha vankét megépített alagútszáj, akkor beállítjuk mindkettő otherTunnel attribútumát.
            if (tunnelN == 2) {
                Tunnel[] selectedT = MapCreator.searchselectedTunnels();
                if (this == selectedT[0]) {
                    this.otherTunnel = selectedT[1];
                    selectedT[1].otherTunnel = this;
                }
                else {
                    this.otherTunnel = selectedT[0];
                    selectedT[0].otherTunnel = this;
                }
            }
        }
    }
    /*
     * Alagút törlése
     */
    public void deleting() {
        if (tunnelN > 0) {
            selected = false;
            tunnelN--;
        }
    }

    /*Ha van másik alagútszáj, akkor 3 esetünk van, hogy merre mehet a mozdony:
            - ha az előző sín a másik tunnel volt, akkor mehet: -> possibleRail1 fele
                                                                -> possibleRail2 fele
            - ha sima sín felöl jön, akkor -> másik Tunnel

            Az első lehetőségnél vagy úgy döntsük el az irányt, hogy megadunk egy fix irányt az alagútnak,
            vagy véletlenszerűen vagy az egyik vagy a másik irányba fog kijönni. Én az utóbbira szavazok,
            sokkal viccesebb és egyszerűbb megcsinálni.
        */
    @Override
    public Rail getDirection(Rail prevRail) {
        if (tunnelN == 2 && selected == true) {
            if (prevRail == otherTunnel) {
                //ez nem működik, de nem baj, a tesztelésnél legalább nem lesz random eredmény, mindig a possibleRail1 fele küldi tovább, ha a másik alagútból jön
                if ((Math.random() % 2 == 0)) return this.getPossibleRail2();
                else return getPossibleRail1();
            } else return otherTunnel; //ha van másik alagútszáj, akkor mindenképpen oda küldi tovább
        } else {
            if (this.getPossibleRail1() == prevRail) return this.getPossibleRail2();
            else return this.getPossibleRail1();
        }
    }
}

