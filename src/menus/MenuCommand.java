package menus;

public class MenuCommand implements Command{
    private final MenuScreen screen;

    public MenuCommand(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public void execute() {
        screen.runMenu();
    }
}
