package controllers;

import models.Month;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */
public class Details {
    private Integer sno;
    private Integer id;
    private String time;
    private String date;
    private Integer amount;
    private Integer remaining;
    private Month month  = Month.april;

    public Details() {
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    
    
    
    
    

    public Integer getSno() {
        return sno;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }
    
    
}
