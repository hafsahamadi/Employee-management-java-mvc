package View;

public class HolidayViewEmployee extends HolidayView{
    protected static final HolidayViewEmployee INSTANCE = new HolidayViewEmployee();
    public HolidayViewEmployee() {
        super();
        ajouterButton.setVisible(false);
        modifierButton.setVisible(false);
        supprimerButton.setVisible(false);
        afficherButton.setVisible(false);
        deselectButton.setVisible(false);
        inputPanel.setVisible(false);
    }
    public static HolidayViewEmployee getInstance() {
        return INSTANCE;
    }
}