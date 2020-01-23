package main_data;

import database_utils.Db_connect;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddGuestController implements Initializable {
    @FXML
    private TextField guest_name;
    @FXML
    private TextField Guest_id;
    @FXML
    private TextField guest_email;
    @FXML
    private TextField guest_city;
    @FXML
    private TextField guest_state;
    @FXML
    private Button add_guestBtn;
    @FXML
    private TextField guest_id;
    @FXML
    private TextField room_id;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private DatePicker checkOutDate;
    @FXML
    private Button create_resBtn;
    @FXML
    private Button close_dialog;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ObservableList<String>

    }
    @FXML
    public void addGuest(){
        try{
            Connection conn = Db_connect.get_connection();
            String sql = "INSERT INTO Guests(guest_name,guest_email_addr,guest_phoneNo,guest_city,guest_state) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //preparedStatement.setInt(1, Integer.parseInt(this.guest_id.getText()));
            preparedStatement.setString(1,this.guest_name.getText());
            preparedStatement.setString(2,this.guest_email.getText());
            preparedStatement.setString(3,"(+254.567.486.800)");
            preparedStatement.setString(4,this.guest_city.getText());
            preparedStatement.setString(5,this.guest_state.getText());

            preparedStatement.execute();
            JOptionPane.showMessageDialog(null,"Guest added succesfully");

        }catch(SQLException | ClassNotFoundException e){

            e.printStackTrace();
        }

    }
    @FXML
    public void closeDialog(ActionEvent actionEvent){
        Stage stage = (Stage)this.close_dialog.getScene().getWindow();
        stage.close();

    }
    @FXML
    public void create_reservation() throws SQLException, ClassNotFoundException {
          String sql = "INSERT INTO  reservation(guest_id,check_in,check_out,room_id,reservation_date)VALUES(?,?,?,?,?)";
          Connection conn = Db_connect.get_connection();
          PreparedStatement preparedStatement = conn.prepareStatement(sql);
          preparedStatement.setString(Integer.parseInt("1"),this.guest_id.getText());
          preparedStatement.setDate(2, Date.valueOf(this.checkInDate.getValue()));
          preparedStatement.setDate(3, Date.valueOf(this.checkOutDate.getValue()));
          preparedStatement.setString(4,this.room_id.getText());
          preparedStatement.setDate(5,Date.valueOf(LocalDate.now()));

          preparedStatement.execute();
          JOptionPane.showMessageDialog(null,"Reservetion created succesfully");

          preparedStatement.close();
          conn.close();

    }
}
