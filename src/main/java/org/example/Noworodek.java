package org.example;

public class Noworodek extends Czlowiek {
    private int identyfikator;
    private String plec;
    private String dataUrodzenia;
    private int waga;
    private int wzrost;
    private int identyfikatorMatki;
    private Mama mama;

    public Noworodek(String imie, int identyfikator, String plec, String dataUrodzenia, int waga, int wzrost, int identyfikatorMatki) {
        super(imie);
        this.identyfikator = identyfikator;
        this.plec = plec;
        this.dataUrodzenia = dataUrodzenia;
        this.waga = waga;
        this.wzrost = wzrost;
        this.identyfikatorMatki = identyfikatorMatki;
    }

    public int getIdentyfikator() {
        return identyfikator;
    }

    public void setIdentyfikator(int identyfikator) {
        this.identyfikator = identyfikator;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public int getWaga() {
        return waga;
    }

    public int getWzrost() {
        return wzrost;
    }

    public void setWzrost(int wzrost) {
        this.wzrost = wzrost;
    }

    public int getIdentyfikatorMatki() {
        return identyfikatorMatki;
    }

    public Mama getMama() {
        return mama;
    }

    public void setMama(Mama mama) {
        this.mama = mama;
    }

}
//identyfikator, płeć (c – córka, s – syn), imię, data urodzenia, waga [g], wzrost [cm] oraz identyfikator matki.