package main_data;

public enum Options {
    Delux, Executive,Kalyet_annex;
    private Options(){}
    public String value(){
        return name();
    }
    public static Options fromvalue(String v){
        return valueOf(v);

    }
}
//this.combo.setItems(FXColllections.observableArrayList(Options.values());