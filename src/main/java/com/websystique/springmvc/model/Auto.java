package com.websystique.springmvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "auto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotEmpty
    @Column(name="casa_costruttrice",nullable = false)
    String casaCostruttrice;

    @NotEmpty
    @Column(name="modello",nullable = false)
    String modello;

    @NotNull
    @Column(name="anno_immatricolazione",nullable = false)
    int annoImmatricolazione;

    @NotEmpty
    @Column(name="targa",nullable = false)
    String targa;

    @NotEmpty
    @Column(name="tipologia",nullable = false)
    String tipologia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCasaCostruttrice() {
        return casaCostruttrice;
    }

    public void setCasaCostruttrice(String casaCostruttrice) {
        this.casaCostruttrice = casaCostruttrice;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public int getAnnoImmatricolazione() {
        return annoImmatricolazione;
    }

    public void setAnnoImmatricolazione(int annoImmatricolazione) {
        this.annoImmatricolazione = annoImmatricolazione;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Auto() {
    }

    public Auto(String casaCostruttrice, String modello, int annoImmatricolazione, String targa, String tipologia) {
        this.casaCostruttrice = casaCostruttrice;
        this.modello = modello;
        this.annoImmatricolazione = annoImmatricolazione;
        this.targa = targa;
        this.tipologia = tipologia;
    }
}