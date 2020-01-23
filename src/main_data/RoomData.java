package main_data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RoomData {
    private StringProperty room_id;
    private StringProperty room_type;
    private StringProperty rates;
    private StringProperty room_loc;
    private StringProperty room_capacity;
    private StringProperty status;

    public RoomData(String roomId,String roomType,String rates,String roomLoc,String roomCapacity,String state){
        this.room_id = new SimpleStringProperty(roomId);
        this.room_type = new SimpleStringProperty(roomType);
        this.rates = new SimpleStringProperty(rates);
        this.room_loc= new SimpleStringProperty(roomLoc);
        this.room_capacity =new SimpleStringProperty(roomCapacity);
        this.status = new SimpleStringProperty(state);
    }

    public String getRoom_id() {
        return room_id.get();
    }

    public StringProperty room_idProperty() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id.set(room_id);
    }

    public String getRoom_type() {
        return room_type.get();
    }

    public StringProperty room_typeProperty() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type.set(room_type);
    }

    public String getRates() {
        return rates.get();
    }

    public StringProperty ratesProperty() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates.set(rates);
    }

    public String getRoom_loc() {
        return room_loc.get();
    }

    public StringProperty room_locProperty() {
        return room_loc;
    }

    public void setRoom_loc(String room_loc) {
        this.room_loc.set(room_loc);
    }

    public String getRoom_capacity() {
        return room_capacity.get();
    }

    public StringProperty room_capacityProperty() {
        return room_capacity;
    }

    public void setRoom_capacity(String room_capacity) {
        this.room_capacity.set(room_capacity);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
