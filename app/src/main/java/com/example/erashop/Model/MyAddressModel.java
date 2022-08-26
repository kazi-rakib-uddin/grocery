package com.example.erashop.Model;

public class MyAddressModel {
    String name,number,mailId,pin,flatNO,area,landmark,state,type;
    Boolean def_add;

    public MyAddressModel(String name, String number, String mailId, String pin, String flatNO, String area, String landmark, String state, String type, Boolean def_add) {
        this.name = name;
        this.number = number;
        this.mailId = mailId;
        this.pin = pin;
        this.flatNO = flatNO;
        this.area = area;
        this.landmark = landmark;
        this.state = state;
        this.type = type;
        this.def_add = def_add;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getFlatNO() {
        return flatNO;
    }

    public void setFlatNO(String flatNO) {
        this.flatNO = flatNO;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDef_add() {
        return def_add;
    }

    public void setDef_add(Boolean def_add) {
        this.def_add = def_add;
    }
}
