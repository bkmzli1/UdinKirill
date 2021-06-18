package ru.example.udinkirill.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pricing_plans")
public class PricingPlans {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String teg;
    private String href;
    private double start;
    private double stop;
    private double bet;
    private double deadlineStart;
    private double deadlineStop;
    @OneToOne(fetch = FetchType.EAGER)
    private Img img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeg() {
        return teg;
    }
    public String getTegUUI() {
        return teg + UUID.randomUUID().toString();
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

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }


}
