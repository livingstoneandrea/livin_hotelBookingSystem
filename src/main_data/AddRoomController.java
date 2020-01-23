package main_data;

import database_utils.Db_connect;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddRoomController implements Initializable {
   // @FXML
   // private TextField RoomNo;
    @FXML
    private ComboBox<String> room_type;
    @FXML
    private ComboBox<String>rates;
    @FXML
    private ComboBox<String> room_loc;
    @FXML
    private ComboBox<String>room_capacity;
    @FXML
    private Label close_dialog;

    ObservableList<String> roomType;
    ObservableList<String>Rates;
    ObservableList<String>capacity;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomType = FXCollections.observableArrayList("Delux","Executive","Kalyet annex","noble_rest0");
        Rates = FXCollections.observableArrayList("4500","5000","6000","8000","12000");
        capacity =FXCollections.observableArrayList("1","2","3","4");
        room_type.setItems(roomType);
        rates.setItems(Rates);
        room_capacity.setItems(capacity);

    }
    @FXML
    public void addRoom(){
        try{
            Connection conn = Db_connect.get_connection();
            String sql = "INSERT INTO Rooms(room_type,rates,room_location,room_capacity) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //preparedStatement.setString(1,this.addRoom.getText());
            preparedStatement.setString(1, String.valueOf(this.room_type.getSelectionModel().getSelectedItem()));
            preparedStatement.setString(2,String.valueOf(this.rates.getSelectionModel().getSelectedItem()));
            preparedStatement.setString(3,this.room_loc.getValue());
            preparedStatement.setString(4,String.valueOf(this.room_capacity.getSelectionModel().getSelectedItem()));
            //preparedStatement.setString(6,this.Gid.getText());

            preparedStatement.execute();
            JOptionPane.showMessageDialog(null,"Room added succesfully");
            System.out.println(String.format("%s %n",String.valueOf(this.room_type.getSelectionModel().getSelectedItem())));

        }catch(SQLException | ClassNotFoundException e){

            e.printStackTrace();
        }

    }
    @FXML
    public void closeDialog(MouseEvent event){
        Stage stage = (Stage)this.close_dialog.getScene().getWindow();
        stage.close();

    }
}
