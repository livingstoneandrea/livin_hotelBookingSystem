package main_data;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private  TextField login_userName;
    @FXML
    private  PasswordField login_userPass;
    @FXML
    private  Button login_btn;
    @FXML
    private  Button signUp_btn;
    @FXML
    public void AuthenticateUsers(ActionEvent actionEvent) {

        try{
            String login_name  = this.login_userName.getText();
            String login_pass = this.login_userPass.getText();
            System.out.println(String.format("Name = %s and password = %s%n",login_name,login_pass));
            JOptionPane.showMessageDialog(null,String.format("name entered is : %s and password is:%s",login_name,login_pass ));
            Connection conn = get_connection();
            PreparedStatement stmt = conn.prepareStatement("SELECT*FROM users WHERE login_name =? and login_pass =?");
            stmt.setString(1,this.login_userName.getText());
            stmt.setString(2,this.login_userPass.getText());

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null,String.format(" name: %s and password %s ",rs.getString(1),rs.getString(2)));
                //transit the stage
                Stage stage = (Stage)this.login_btn.getScene().getWindow();
                stage.close();
                System.out.print("Loading main app......");
                load_mainApp();
            }else{
                JOptionPane.showMessageDialog(null,"Invalid user name or password","connection status",JOptionPane.ERROR_MESSAGE);
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void createAccount(ActionEvent actionEvent) {
        System.out.println(String.format(" name is %s%n",this.login_userName.getText().toString()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public Connection get_connection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/hotel_bookingSystem" , "root", "@livingstone7");
        System.out.println("succesfully connected");
        return  connection;
    }

    public void load_mainApp(){
        try{
            Stage userstage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("main_app.fxml").openStream());
            Main_appController maincontroller = (Main_appController) loader.getController();
            root.getStylesheets().add(getClass().getResource("main_app.css").toExternalForm());
            Scene scene = new Scene(root);
            userstage.setScene(scene);
            userstage.setTitle("Noble Hotel and conference center");
            userstage.setResizable(false);
            userstage.initStyle(StageStyle.TRANSPARENT);
            userstage.show();

        }catch (IOException e){
            //System.out.printf("%s %n",e.getMessage());
            e.getStackTrace();

        }

    }

}
