package com.example.erashop.Model;

public class RecentOrdersModel {
    String invoice_no,total_amount,date,payment_type;

    public RecentOrdersModel(String invoice_no, String total_amount, String date,String payment_type) {
        this.invoice_no = invoice_no;
        this.total_amount = total_amount;
        this.date = date;
        this.payment_type = payment_type;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
