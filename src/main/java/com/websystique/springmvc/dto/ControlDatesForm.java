package com.websystique.springmvc.dto;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

public class ControlDatesForm {
    private ModelMap modelMap;
    private BindingResult bindingResult;
    private PrenotazioneDto prenotazioneDto;
    private String errorString;


    public ControlDatesForm(ModelMap modelMap, BindingResult bindingResult, PrenotazioneDto prenotazioneDto, String errorString) {
        this.modelMap = modelMap;
        this.bindingResult = bindingResult;
        this.prenotazioneDto = prenotazioneDto;
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public ModelMap getModelMap() {
        return modelMap;
    }

    public void setModelMap(ModelMap modelMap) {
        this.modelMap = modelMap;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public PrenotazioneDto getPrenotazioneDto() {
        return prenotazioneDto;
    }

    public void setPrenotazioneDto(PrenotazioneDto prenotazioneDto) {
        this.prenotazioneDto = prenotazioneDto;
    }

}
