package com.websystique.springmvc.dto;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.Prenotazione;
import org.springframework.security.access.method.P;

import java.util.Date;

public class PrenotazioneDto {
    private int id;
    private String targa;
    private String username;
    private Date dataDiInizio;
    private Date dataDiFine;
    private Boolean approvata;

    public PrenotazioneDto(Prenotazione prenotazione) {
        this.id = prenotazione.getId();
        this.targa = prenotazione.getAuto().getTarga();
        this.username = prenotazione.getUser().getUsername();
        this.dataDiInizio = prenotazione.getDataDiInizio();
        this.dataDiFine = prenotazione.getDataDiFine();
        this.approvata=prenotazione.getApprovata();
    }

    public Boolean getApprovata() {
        return approvata;
    }

    public void setApprovata(Boolean approvata) {
        this.approvata = approvata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDataDiInizio() {
        return dataDiInizio;
    }

    public void setDataDiInizio(Date dataDiInizio) {
        this.dataDiInizio = dataDiInizio;
    }

    public Date getDataDiFine() {
        return dataDiFine;
    }

    public void setDataDiFine(Date dataDiFine) {
        this.dataDiFine = dataDiFine;
    }

    public PrenotazioneDto() {
    }
}
