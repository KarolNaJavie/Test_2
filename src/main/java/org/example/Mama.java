package org.example;

import java.util.ArrayList;
import java.util.List;

public class Mama extends Czlowiek {
    private int wiek;
    private int identyfikator;
    private List<Noworodek> dzieci = new ArrayList<>();

    public Mama(String imie, int wiek, int identyfikator) {
        super(imie);
        this.wiek = wiek;
        this.identyfikator = identyfikator;
    }

    public int getWiek() {
        return wiek;
    }

    public void dodajDziecko(Noworodek noworodek) {
        dzieci.add(noworodek);
    }

    public int getIdentyfikator() {
        return identyfikator;
    }

    public List<Noworodek> getDzieci() {
        return dzieci;
    }

}
