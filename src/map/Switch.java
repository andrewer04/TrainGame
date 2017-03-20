package map;

public class Switch extends Rail {
    private Rail selectedRail;
    private Rail notselectedRail;
    private Rail fixRail;

    public void switching(){
        Rail temp;
        temp = selectedRail;
        selectedRail = notselectedRail;
        notselectedRail = temp;
    }

    /* A továbbhaladási irányt adja meg a vonatelem előző pozicíójának ismeretében.
     * Ha a fix sín felől érkezik, akkor a váltó kiválasztott irányába halad tovább a vonat,
     * ha a selectedRail vagy a notselectedRail felől érkezik, akkor mindenképp a fix sín
     * irányába halad tovább a vonat.
     */
    @Override
    public Rail getDirection(Rail r) {
        if (selectedRail == r || notselectedRail == r)
            return fixRail;
        else
            return selectedRail;
    }
}
