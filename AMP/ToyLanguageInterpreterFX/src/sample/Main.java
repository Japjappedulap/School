package sample;

import Model.Expression.ArithmeticExpression;
import Model.Expression.ConstantExpression;
import Model.Statement.ConditionalAssignmentStatement;
import Model.Statement.IStatement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
//
//        IStatement x = new ConditionalAssignmentStatement("c",
//                new ArithmeticExpression(new ConstantExpression(5), "-", new ConstantExpression(3)),
//                new ConstantExpression(1), new ConstantExpression(2));
//        System.out.println(x);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
