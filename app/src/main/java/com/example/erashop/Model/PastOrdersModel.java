package com.example.erashop.Model;

public class PastOrdersModel {
    String invoice_no,total_amount,date,payment_method;

    public PastOrdersModel(String invoice_no, String total_amount, String date, String payment_method) {
        this.invoice_no = invoice_no;
        this.total_amount = total_amount;
        this.date = date;
        this.payment_method = payment_method;
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

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
