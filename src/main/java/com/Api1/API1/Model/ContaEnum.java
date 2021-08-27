package com.Api1.API1.Model;


public enum ContaEnum {
    FISICA(10, 5, "FISICA"),
    JURIDICA(10, 50, "JURIDICA"),
    GOVERNAMENTAL(20, 250, "GOVERNAMENTAL");


    private double taxa;
    private Integer qtSaque;
    private String desc;

    ContaEnum(double taxa, Integer qtSaque, String desc) {
        this.taxa = taxa;
        this.qtSaque = qtSaque;
        this.desc = desc;
    }

    ContaEnum(String desc) {
        this.desc = desc;
    }

    ContaEnum() {
    }

    public double getTaxa() {
        return taxa;
    }

    public Integer getQtSaque() {
        return qtSaque;
    }


    public String getDesc() {
        return desc;
    }

}

