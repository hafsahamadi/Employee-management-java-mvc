package View;

public class EmployeeViewManager extends EmployeeView{
    protected static final EmployeeViewManager INSTANCE = new EmployeeViewManager();
    public EmployeeViewManager() {
        super();
        getCreerCompteButton().setVisible(false);
    }
    public static EmployeeViewManager getInstance() {
        return INSTANCE;
    }
}