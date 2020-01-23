package main_data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GuestData {
       private StringProperty guest_id;
       private StringProperty guest_name;
       private StringProperty guest_email;
       private StringProperty guest_phone;
       private StringProperty guest_city;
       private StringProperty guest_state;

       public GuestData(String guestID,String guestName,String guestEmail,String guestPhone,String guestCity,String guestState){

           this.guest_id = new SimpleStringProperty(guestID);
           this.guest_name = new SimpleStringProperty(guestName);
           this.guest_email = new SimpleStringProperty(guestEmail);
           this.guest_phone = new SimpleStringProperty(guestPhone);
           this.guest_city = new SimpleStringProperty(guestCity);
           this.guest_state = new SimpleStringProperty(guestState);
       }

    public String getGuest_id() {
        return guest_id.get();
    }

    public StringProperty guest_idProperty() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id.set(guest_id);
    }

    public String getGuest_name() {
        return guest_name.get();
    }

    public StringProperty guest_nameProperty() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name.set(guest_name);
    }

    public String getGuest_email() {
        return guest_email.get();
    }

    public StringProperty guest_emailProperty() {
        return guest_email;
    }

    public void setGuest_email(String guest_email) {
        this.guest_email.set(guest_email);
    }

    public String getGuest_phone() {
        return guest_phone.get();
    }

    public StringProperty guest_phoneProperty() {
        return guest_phone;
    }

    public void setGuest_phone(String guest_phone) {
        this.guest_phone.set(guest_phone);
    }

    public String getGuest_city() {
        return guest_city.get();
    }

    public StringProperty guest_cityProperty() {
        return guest_city;
    }

    public void setGuest_city(String guest_city) {
        this.guest_city.set(guest_city);
    }

    public String getGuest_state() {
        return guest_state.get();
    }

    public StringProperty guest_stateProperty() {
        return guest_state;
    }

    public void setGuest_state(String guest_state) {
        this.guest_state.set(guest_state);
    }
}
