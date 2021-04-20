package presentation.controller;

public interface ScreenController {

    enum Screen{
        SPLASH_SCREEN,
        LOGIN_SCREEN,
        REGISTER_SCREEN,
        MAIN_MENU,
        BATTLE_SCREEN
    }

    void start();
    void goToScreen(Screen s);
}
