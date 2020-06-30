package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.UserRepositoryImpl;
import service.UserService;

import java.lang.reflect.Constructor;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("expense/expense.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics/expense_overview.fxml"));

//        loader.setControllerFactory((Class<?> type) -> {
//            try {
//                // look for constructor taking MyService as a parameter
//                for (Constructor<?> c : type.getConstructors()) {
//                    if (c.getParameterCount() == 1) {
//                        if (c.getParameterTypes()[0] == UserService.class) {
//                            return c.newInstance(userService);
//                        }
//                    }
//                }
//                // didn't find appropriate constructor, just use default constructor:
//                return type.newInstance();
//            } catch (Exception exc) {
//                throw new RuntimeException(exc);
//            }
//        });


        Parent root = loader.load();
        primaryStage.setTitle("PEM");
        primaryStage.setScene(new Scene(root, 700, 455));
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
