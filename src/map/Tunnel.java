package map;

public class Tunnel extends Rail {
    private int tunnelN; //staticnak kell lennie, hogy mindegyik tunnel ugyan azt lássa
    private boolean selected;
    private int tunnelLength;
    private Tunnel otherTunnel;


    public int getTunnelLength() {
        return tunnelLength;
    }
    public boolean isSelected() {
        return selected;
    }

    public void creating(){
        if (tunnelN < 2){
            selected = true;
            tunnelN++;
        }
    }

    public void deleting(){
        if (tunnelN > 0) {
            selected = false;
            tunnelN--;
        }
    }

    @Override
    public Rail getDirection(Rail rail) {
        if (tunnelN < 2){
            if(this.getPossibleRail1() == rail) return this.getPossibleRail2();
            else return this.getPossibleRail1();
        }
        /*Ha van másik alagútszáj, akkor 3 esetünk van, hogy merre mehet a mozdony:
            - ha az előző sín a másik tunnel volt, akkor mehet: -> possibleRail1 fele
                                                                -> possibleRail2 fele
            - ha sima sín felöl jön, akkor -> másik Tunnel

            Az első lehetőségnél vagy úgy döntsük el az irányt, hogy megadunk egy fix irányt az alagútnak,
            vagy véletlenszerűen vagy az egyik vagy a másik irányba fog kijönni. Én az utóbbira szavazok,
            sokkal viccesebb és egyszerűbb megcsinálni.
        */
        else{
            if (rail == otherTunnel){
                if ((Math.random()%2 == 0)) return this.getPossibleRail1();
                else return getPossibleRail2();
            }
            else return otherTunnel; //ha van másik alagútszáj, akkor mindenképpen oda küldi tovább
        }
    }
}
