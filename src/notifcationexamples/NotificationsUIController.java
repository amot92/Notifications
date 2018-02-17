/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private Button btn1;
    
    @FXML
    private Button btn2;
    
    @FXML
    private Button btn3;
    
    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void Task1(ActionEvent event) {
        if (task1 == null) {
            task1 = new Task1(214748360, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        } else {
            task1.end();
        }
    }
    
    @Override
    public void notify(String message) {
        System.out.println(message);
        textArea.appendText(message + "\n");

        if (message.equals("Task1 done.")) {
            task1 = null;
            btn1.setText("Start Task 1");
        }else if(message.equalsIgnoreCase("Task1 start.")){
            System.out.println("start Task 1");
            btn1.setText("End task 1");
        }
    }
    
    @FXML
    public void Task2(ActionEvent event) {
        if (task2 == null) {
            task2 = new Task2(214748364, 1000000);
            task2.setOnNotification(new Notification() {
                @Override
                public void handle(String message) {
                    System.out.println(message);
                    textArea.appendText(message + "\n");
                    if(message.equalsIgnoreCase("Started Task2!")){
                        btn2.setText("End Task 2");
                    }else if(message.equalsIgnoreCase("Task2 done.")){
                        btn2.setText("Start Task 2");
                        task2 = null;
                    }
                }
            });
            
            task2.start();
        } else {
            task2.end();
        }       
    }
    
    @FXML
    public void Task3(ActionEvent event) {
        if (task3 == null) {
            task3 = new Task3(214748364, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    String message = (String)evt.getNewValue() + "\n";
                    textArea.appendText(message);
                    System.out.println(message);
                    if(message.equalsIgnoreCase("Task3 start.\n")){
                        btn3.setText("End Task 3");
                    }else if(message.equalsIgnoreCase("Task3 done.\n")){
                        btn3.setText("Start Task 3");
                        task3 = null;
                    }
                }
            });
            
            task3.start();
        } else {
            task3.end();
        }
    } 
}
