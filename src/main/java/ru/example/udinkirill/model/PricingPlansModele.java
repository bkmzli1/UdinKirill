package ru.example.udinkirill.model;

import org.hibernate.annotations.GenericGenerator;
import ru.example.udinkirill.domain.Img;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class PricingPlansModele {

    @NotEmpty(message = "Имя пустое")
    private String teg;
    private String href;
    @NotNull
    private double start;
    @NotNull
    private double stop;
    @NotNull
    private double bet;
    @NotNull
    private double deadlineStart;
    @NotNull
    private double deadlineStop;

    private String[] img;


    public String getTeg() {
        return teg;
    }

    public void setTeg(String teg) {
        this.teg = teg;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getStop() {
        return stop;
    }

    public void setStop(double stop) {
        this.stop = stop;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public double getDeadlineStart() {
        return deadlineStart;
    }

    public void setDeadlineStart(double deadlineStart) {
        this.deadlineStart = deadlineStart;
    }

    public double getDeadlineStop() {
        return deadlineStop;
    }

    public void setDeadlineStop(double deadlineStop) {
        this.deadlineStop = deadlineStop;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
