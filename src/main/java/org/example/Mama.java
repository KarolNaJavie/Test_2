package org.example;

import java.util.ArrayList;
import java.util.List;

public class Mama extends Czlowiek {
private int wiek;
private List<Noworodek> dzieci = new ArrayList<>();
    public Mama(String imie, int wiek) {
        super(imie);
        this.wiek = wiek;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }
    public void dodajDziecko(Noworodek noworodek){
        dzieci.add(noworodek);
    }
}
