package main_data;

import database_utils.Db_connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.PixelFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main_appController implements Initializable {
    @FXML
    private Label home_link;
    @FXML
    private Label guest_link;
    @FXML
    private Label room_link;
    @FXML
    private Button close_btn;
    @FXML
    private Label add_roomlbl;

    @FXML
    private Button add_roomBtn;
    @FXML
    private Button del_roomBtn;
    @FXML
    private Button backup_btn;
    @FXML
    private Button restore_btn;

    @FXML
    private Label room_nolbl;
    @FXML
    private Label guest_nolbl;
    @FXML
    private Label vacc_roomlbl;
    @FXML
    private Pane home_page;
    @FXML
    private Pane room_pane;
    @FXML
    private Pane guest_pane;


    @FXML
    private TableView<RoomData> rooms_table;
    @FXML
    private TableColumn<RoomData,String>room_id;
    @FXML
    private TableColumn<RoomData,String>roomType;
    @FXML
    private TableColumn<RoomData,String>rates;
    @FXML
    private TableColumn<RoomData,String>room_loc;
    @FXML
    private TableColumn<RoomData,String>room_cap;
    @FXML
    private TableColumn<RoomData,String>status;
    @FXML
    private ComboBox<Options>room_filterCombo;

    @FXML
    private TableView<GuestData> guest_table;
    @FXML
    private TableColumn<GuestData,String> guest_id;
    @FXML
    private  TableColumn<GuestData,String> guest_name;
    @FXML
    private  TableColumn<GuestData,String> guest_email;
    @FXML
    private  TableColumn<GuestData,String> guest_phone;
    @FXML
    private  TableColumn<GuestData,String> guest_city;
    @FXML
    private  TableColumn<GuestData,String> guest_state;
    @FXML
    private Button load_guestBtn;
    @FXML
    private TextField search_guests;
    @FXML
    private  TextField filterRtxtfield;

    private Db_connect dc;
    private ResultSetMetaData metaData = null;
    private ObservableList<RoomData>data;
    private ObservableList<GuestData>guest_info;
    private FilteredList<GuestData> filteredData;
    private FilteredList<RoomData>dataFilteredList;



    @Override
    public void initialize(URL location, ResourceBundle resources){
        //this.add_roomBtn.addEventHandler(null,(this)->{})
        Db_connect dc = new Db_connect();
        try{
            Connection conn = Db_connect.get_connection();
            CallableStatement callableStatement = conn.prepareCall("{call get_DataCount(?,?)}");
            callableStatement.setString(1,"Rooms");
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();
            if(callableStatement.getInt(2)>=1){
                this.room_nolbl.setText(String.format("    %d%n Room(s)",callableStatement.getInt(2)));
            }
            callableStatement.close();
            CallableStatement callableStatement1 =conn.prepareCall("{call get_BookedRooms(?,?)}");
            callableStatement1.setString(1,"reservation");
            callableStatement1.registerOutParameter(2,Types.INTEGER);
            callableStatement1.execute();
            if(callableStatement1.getInt(2)>=1){
                this.vacc_roomlbl.setText(String.format("    %s%n Booked",callableStatement1.getInt(2)));
            }

            callableStatement1.close();
            CallableStatement callableStatement2 =conn.prepareCall("{call get_guestCount(?,?)}");
            callableStatement2.setString(1,"Guests");
            callableStatement2.registerOutParameter(2,Types.INTEGER);
            callableStatement2.execute();
            if(callableStatement2.getInt(2)>=1){
                this.guest_nolbl.setText(String.format("    %s%n Guests",callableStatement2.getInt(2)));
            }

            callableStatement2.close();
            conn.close();
        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();

        }

        this.guest_pane.setVisible(false);
        this.room_pane.setVisible(false);
        this.home_page.setVisible(true);
        this.room_filterCombo.setItems(FXCollections.observableArrayList(Options.values()));

    }
    @FXML
    public void close_application(ActionEvent actionEvent){
         Stage stage = (Stage)this.close_btn.getScene().getWindow();
         stage.close();

    }
    public void loadRoomsData(){
        try{
            Connection conn = Db_connect.get_connection();
            this.data = FXCollections.observableArrayList();
            String sql = "{CALL get_roomsData()}";
            //ResultSet rs = conn.prepareStatement("SELECT*FROM Rooms").executeQuery();
            CallableStatement stmt = conn.prepareCall(sql);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                this.data.add(new RoomData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
           // this.room_nolbl.setText(String.valueOf(metaData.getColumnCount()));
           // JOptionPane.showMessageDialog(null,String.format("%s%n",String.valueOf(metaData.getColumnCount())));

            this.room_id.setCellValueFactory(new PropertyValueFactory<RoomData,String>("room_id"));
            this.roomType.setCellValueFactory(new PropertyValueFactory<RoomData,String>("room_type"));
            this.rates.setCellValueFactory(new PropertyValueFactory<RoomData,String>("rates"));
            this.room_loc.setCellValueFactory(new PropertyValueFactory<RoomData,String>("room_loc"));
            this.room_cap.setCellValueFactory(new PropertyValueFactory<RoomData,String>("room_capacity"));
            this.status.setCellValueFactory(new PropertyValueFactory<RoomData,String>("status"));


            this.rooms_table.setItems(null);
            this.rooms_table.setItems(this.data);
            rs.close();
            conn.close();

        }catch (SQLException | ClassNotFoundException e){
             e.printStackTrace();
        }

    }
 @FXML
    private void loadGuestInfo(){
        try{
            Connection conn = Db_connect.get_connection();
            this.guest_info = FXCollections.observableArrayList();
            ResultSet rs = conn.prepareStatement("SELECT*FROM Guests").executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            //this.guest_nolbl.setText(String.format("%d Guests",resultSetMetaData.getColumnCount()));

            while(rs.next()){
                this.guest_info.add(new GuestData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));

            }
            this.guest_id.setCellValueFactory(new PropertyValueFactory<GuestData,String>("guest_id"));
            this.guest_name.setCellValueFactory(new PropertyValueFactory<GuestData,String>("guest_name"));
            this.guest_email.setCellValueFactory(new PropertyValueFactory<GuestData,String>("guest_email"));
            this.guest_phone.setCellValueFactory(new PropertyValueFactory<GuestData,String>("guest_phone"));
            this.guest_city.setCellValueFactory(new PropertyValueFactory<GuestData,String>("guest_city"));
            this.guest_state.setCellValueFactory(new PropertyValueFactory<GuestData,String>("guest_state"));
            this.guest_table.setItems(null);
            this.guest_table.setItems(this.guest_info);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void transitScene(MouseEvent event){
            event.getSource().toString();
            Label label = (Label)event.getSource();
            System.out.print("Event source"+label.getText().toString());
            switch (label.getText()){
                case "Home":
                    home_page.setVisible(true);
                    room_pane.setVisible(false);
                    guest_pane.setVisible(false);
                    break;
                case "Rooms":
                    room_pane.setVisible(true);
                    guest_pane.setVisible(false);
                    home_page.setVisible(false);

                    break;
                case "Guests":
                    guest_pane.setVisible(true);
                    room_pane.setVisible(false);
                    home_page.setVisible(false);
                    break;
                default:
                    home_page.setVisible(true);

            }
    }
    @FXML
    public void deleteRoom() throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM Rooms WHERE room_id=?";
        int selectedIndex = rooms_table.getSelectionModel().getSelectedIndex();
        RoomData room = (RoomData) rooms_table.getSelectionModel().getSelectedItem();
        //data.remove(ix);
        Connection conn = Db_connect.get_connection();
        PreparedStatement pst = conn.prepareStatement(sql);
        System.out.println("Selected index = : "+ selectedIndex);
        if(selectedIndex >= 0){
            pst.setString(1, String.valueOf(selectedIndex +1));
            pst.execute();
            this.rooms_table.getItems().remove(selectedIndex);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR:");
            //alert.setHeaderText("No selection was made.");
            alert.setContentText("No item selected for deletion. Please try again!!!.");
            alert.showAndWait();
        }
        pst.close();
        conn.close();

        rooms_table.requestFocus();
        rooms_table.getSelectionModel().select(selectedIndex);
        rooms_table.getFocusModel().focus(selectedIndex);

    }
    @FXML
    public void  deleteGuest() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Guests WHERE guest_id=?";
        int selectedIndex = this.guest_table.getSelectionModel().getSelectedIndex();
        GuestData guest = (GuestData) guest_table.getSelectionModel().getSelectedItem();
        //data.remove(ix);
        Connection conn = Db_connect.get_connection();
        PreparedStatement pst = conn.prepareStatement(sql);
        System.out.println("Selected index = : "+ selectedIndex);
        if(selectedIndex >= 0){
            pst.setString(1, String.valueOf(selectedIndex + 1));
            pst.execute();
            this.guest_table.getItems().remove(selectedIndex);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR:");
            //alert.setHeaderText("No selection was made.");
            alert.setContentText("No item selected for deletion. Please try again!!!.");
            alert.showAndWait();
        }
        pst.close();
        conn.close();
        guest_table.requestFocus();
        guest_table.getSelectionModel().select(selectedIndex);
        guest_table.getFocusModel().focus(selectedIndex);
    }
    @FXML
    public void insertRoom(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("add_room.fxml"));
            Dialog insert_dialog = new Dialog();
            insert_dialog.getDialogPane().setContent(root);
            insert_dialog.initStyle(StageStyle.TRANSPARENT);
            insert_dialog.initModality(Modality.APPLICATION_MODAL);
            insert_dialog.show();

            Connection conn = Db_connect.get_connection();
            //PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Rooms VALUES(?,?,?,?,?,?)");

        }catch (SQLException | ClassNotFoundException | IOException e){

        }

    }
  @FXML
    public void insertGuest() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_guest.fxml"));
        Dialog insert_dialog = new Dialog();
        insert_dialog.getDialogPane().setContent(root);
        insert_dialog.initStyle(StageStyle.TRANSPARENT);
        insert_dialog.initModality(Modality.APPLICATION_MODAL);
        insert_dialog.setWidth(400);
        insert_dialog.setHeight(600);
        insert_dialog.show();

    }

    public void updateRoomInfo(){

    }
    public void updateGuestInfo(){

    }
    @FXML
    public void backup_Db() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("ru"));
        java.util.Date currentDate = new java.util.Date();

        try {
            Connection conn = Db_connect.get_connection();
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            Process p = null;
            Runtime runtime = Runtime.getRuntime();
            String command = " --host=localhost  --user=" + databaseMetaData.getUserName() + " --password=@livingstone7 --result-file= D:/   --database hotel_bookingSystem";
            //p=runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe"+command);
            //String cmd = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe -e -uroot -p@livingstone7 -hlocalhost hotel_bookingSystem > D:\\hotel_backup.sql";
            //p = runtime.exec(cmd);
            p = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe  --user= "+databaseMetaData.getUserName()+" --password= @livingstone7  --add-drop-database -B "+databaseMetaData.getURL()+ " -r " +"D:/" + dateFormat.format(currentDate) + "_backup" + ".sql");

            int processComplete = p.waitFor();
            if (processComplete == 0) {

                System.out.println("Backup created successfully!");

            } else {
                System.out.println("Could not create the backup");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void restore_Db() throws IOException{
        Process p = null;
        try{
            Connection conn = Db_connect.get_connection();
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            Runtime runtime = Runtime.getRuntime();
            String[] restoreCmd = new String[]{"mysql ", "--user=" + databaseMetaData.getUserName(), "--password= @livingstone7" , "-e", "source " + databaseMetaData.getURL()};
            p = runtime.exec(restoreCmd);
            int processComplete = p.waitFor();
            if (processComplete == 0) {

                System.out.println("Database restored successfully!");

            } else {
                System.out.println("Failure could not restore");
            }

        }catch (SQLException | ClassNotFoundException | InterruptedException e){
            e.printStackTrace();

        }

    }
    @FXML
    public void filterGuestTable(ActionEvent actionEvent) {

        filteredData = new FilteredList<>(guest_info,g->true);
        filterRtxtfield.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(GuestData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (GuestData.getGuest_id().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (GuestData.getGuest_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<GuestData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(guest_table.comparatorProperty());
        guest_table.setItems(sortedData);
    }
    @FXML
    public void filterRoomTable(ActionEvent actionEvent) {
        dataFilteredList= new FilteredList<>(data,d->true);
        filterRtxtfield.textProperty().addListener((observable, oldValue, newValue) -> {
            dataFilteredList.setPredicate(GuestData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (GuestData.getRoom_id().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (GuestData.getRoom_type().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<RoomData> sortedData = new SortedList<>(dataFilteredList);
        sortedData.comparatorProperty().bind(rooms_table.comparatorProperty());
        rooms_table.setItems(sortedData);
    }

}
