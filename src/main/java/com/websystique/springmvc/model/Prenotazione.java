package com.websystique.springmvc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    Boolean approvata;
    @Column(name = "data_di_inizio")
    @Temporal(TemporalType.DATE)
    Date dataDiInizio;
    @Column(name = "data_di_fine")
    @Temporal(TemporalType.DATE)
    Date dataDiFine;
    @ManyToOne(targetEntity = Auto.class)
    Auto auto;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    User user;


    public Prenotazione(Date dataDiInizio, Date dataDiFine, Auto auto, User user) {
        this.dataDiInizio = dataDiInizio;
        this.dataDiFine = dataDiFine;
        this.auto = auto;
        this.user = user;
    }

    public Prenotazione() {

    }


    public Boolean getApprovata() {

        return approvata;
    }

    public void setApprovata(Boolean approvata) {
        this.approvata = approvata;
    }

    public Date getDataDiInizio() {
        return dataDiInizio;
    }

    public void setDataDiInizio(Date data_di_inizio) {
        this.dataDiInizio = data_di_inizio;
    }

    public Date getDataDiFine() {
        return dataDiFine;
    }

    public void setDataDiFine(Date data_di_fine) {
        this.dataDiFine = data_di_fine;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}