package View;

import Controller.Controller;

public class RunCommand extends Command{
    private Controller controller;

    public RunCommand(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.executeAllSteps();
    }
}