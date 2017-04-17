package map;

public class Switch extends Rail {
    private Rail selectedRail;
    private Rail notselectedRail;
    private Rail fixRail;

    //eRTeKEK BEaLLiTaSA/VISSZAADaSA

    public void setSelectedRail(Rail selectedRail) {
        this.selectedRail = selectedRail;
    }

    public void setNotselectedRail(Rail notselectedRail) {
        this.notselectedRail = notselectedRail;
    }

    public void setFixRail(Rail fixRail) {
        this.fixRail = fixRail;
    }

    public Rail getSelectedRail() {
        return selectedRail;
    }

    public Rail getNotselectedRail() {
        return notselectedRail;
    }

    public Rail getFixRail() {
        return fixRail;
    }

    /*
     * Valto allitasa masik helyzetbe
     */
    public void switching(){
        Rail temp;
        temp = selectedRail;
        selectedRail = notselectedRail;
        notselectedRail = temp;
    }

    /* A tovabbhaladasi iranyt adja meg a vonatelem elozo poziciojanak ismereteben.
     * Ha a fix sin felol erkezik, akkor a valto kivalasztott iranyaba halad tovabb a vonat,
     * ha a selectedRail vagy a notselectedRail felol erkezik, akkor mindenkepp a fix sin
     * iranyaba halad tovabb a vonat.
     */
    @Override
    public Rail getDirection(Rail r) {
        if (selectedRail == r || notselectedRail == r)
            return fixRail;
        else
            return selectedRail;
    }
}
