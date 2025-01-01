package View;

public class HolidayViewManager extends HolidayView{
    protected static final HolidayViewManager INSTANCE = new HolidayViewManager();
    public HolidayViewManager() {
        super();
        ajouterButton.setVisible(false);
        modifierButton.setVisible(false);
        supprimerButton.setVisible(false);
        afficherButton.setVisible(false);
        deselectButton.setVisible(false);
        inputPanel.setVisible(false);
    }
    public static HolidayViewManager getInstance() {
        return INSTANCE;
    }
}