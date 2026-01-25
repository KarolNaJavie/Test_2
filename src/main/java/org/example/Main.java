package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main {
    static void main() {
//        Polecenie:
//        Pliki noworodki.txt oraz mamy.txt zawierają dane o dzieciach i ich matkach.
//        W pliku noworodki.txt każdy wiersz zawiera następujące informacje o jednym dziecku,
//        rozdzielone znakami odstępu: identyfikator, płeć (c – córka, s – syn), imię, data urodzenia, waga [g],
//        wzrost [cm] oraz identyfikator matki.
//                Przykład:
//        1 c Agnieszka 20-lis-1999 2450 48 33
//        W pliku mamy.txt każdy wiersz zawiera informacje o jednej kobiecie, rozdzielone znakami odstępu: identyfikator matki, imię, wiek.
//        Przykład:
//        1 Agata 25
//        Identyfikator matki z pliku noworodki.txt odpowiada identyfikatorowi w pliku mamy.txt.
//                Wykorzystując dane zawarte w plikach mamy.txt i noworodki.txt oraz dostępne narzędzia informatyczne,
//                wykonaj poniższe polecenia.
//        Podaj imię i wzrost najwyższego chłopca oraz imię i wzrost najwyższej dziewczynki.                             x
//        Uwaga: Jest tylko jeden taki chłopiec i tylko jedna taka dziewczynka.                                          x
//        W którym dniu urodziło się najwięcej dzieci? Podaj datę i liczbę dzieci.                                       x
//        Uwaga: Jest tylko jeden taki dzień.                                                                            x
//                Podaj imiona kobiet w wieku poniżej 25 lat, które urodziły dzieci o wadze powyżej 4000 g.              x
//                Podaj imiona i daty urodzenia dziewczynek, które odziedziczyły imię po matce.                          x
//        W pliku noworodki.txt zapisane są informacje o narodzinach bliźniąt.
//        Bliźnięta można rozpoznać po tej samej dacie urodzenia i tym samym identyfikatorze matki.
//        Pamiętaj, że przykładowo Jacek i Agatka oraz Agatka i Jacek to ta sama para.
//        Możesz założyć, że w danych nie ma żadnych trojaczków, czworaczków, itd. Podaj daty, w których urodziły się bliźnięta.
//        uwaga: nalezy zaimplementowac 2 kierunkowe relacje i uzywac tych relacji do podpunktow,
//                pamietaj tez ze Matka ma liste dzieci a dziecko ma MATKE nie id Matki tylko matke.


        List<String> tekstowySpisMam = new ArrayList<>();
        List<String> tekstowySpisDzieci = new ArrayList<>();

        try (BufferedReader readerMatek = new BufferedReader(new FileReader("mamy.txt"));) {
            tekstowySpisMam = readerMatek.readAllLines();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader readerDziatek = new BufferedReader(new FileReader("noworodki.txt"));) {
            tekstowySpisDzieci = readerDziatek.readAllLines();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Mama> matki = new ArrayList<>();
        List<Noworodek> noworodki = new ArrayList<>();

        for (String tekstowaMama : tekstowySpisMam) {
            Pattern pattern = Pattern.compile("(\\d+) ([A-Z][a-z]+) (\\d+)");
            Matcher matcher = pattern.matcher(tekstowaMama);

            if (matcher.find()) {
                int identyfikator = Integer.parseInt(matcher.group(1).trim());
                String imie = matcher.group(2).trim();
                int wiek = Integer.parseInt(matcher.group(3).trim());
                Mama mama = new Mama(imie, wiek, identyfikator);
                matki.add(mama);
            }
        }

        for (String dziecko : tekstowySpisDzieci) {
            Pattern pattern = Pattern.compile("(\\d+) ([a-z]) ([A-Z][a-z]+) (\\d+-\\d+-\\d+) (\\d+) (\\d+) (\\d+)");
            Matcher matcher = pattern.matcher(dziecko);

            if (matcher.find()) {
                int identyfikator = Integer.parseInt(matcher.group(1).trim());
                String plec = matcher.group(2).trim();
                String imie = matcher.group(3).trim();
                String dataUrodzenia = matcher.group(4).trim();
                int waga = Integer.parseInt(matcher.group(5).trim());
                int wzrost = Integer.parseInt(matcher.group(6).trim());
                int identyfikatorMatki = Integer.parseInt(matcher.group(7).trim());
                Noworodek noworodek = new Noworodek(imie, identyfikator, plec, dataUrodzenia, waga, wzrost, identyfikatorMatki);
                noworodki.add(noworodek);
            }
        }

        Map<Integer, Mama> matkiID = matki.stream().collect(Collectors.toMap(Mama::getIdentyfikator, n -> n));
        for (Noworodek noworodek : noworodki) {
            Mama matka = matkiID.get(noworodek.getIdentyfikatorMatki());
            if (matka != null) {
                matka.dodajDziecko(noworodek);
                noworodek.setMama(matka);
            }
        }

//        Podaj imię i wzrost najwyższego chłopca oraz imię i wzrost najwyższej dziewczynki.
        Optional<Noworodek> najwyzszy = noworodki.stream()
                .filter(n -> Objects.equals(n.getPlec(), "s"))
                .max(Comparator.comparing(Noworodek::getWzrost));
        najwyzszy.ifPresent(n -> System.out.println("Najwyzszy chlopiec: " + n.getImie() + " " + n.getWzrost() + " cm"));

        Optional<Noworodek> najwyzsza = noworodki.stream()
                .filter(n -> Objects.equals(n.getPlec(), "c"))
                .max(Comparator.comparing(Noworodek::getWzrost));
        najwyzsza.ifPresent(n -> System.out.println("Najwyzsza dziewczynka: " + n.getImie() + " " + n.getWzrost() + " cm"));

//        W którym dniu urodziło się najwięcej dzieci? Podaj datę i liczbę dzieci.
        noworodki.stream()
                .collect(Collectors.groupingBy(Noworodek::getDataUrodzenia, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(n -> System.out.println("\nNajwiecej dzieci urodzilo sie w dniu: " + n.getKey() + " (" + n.getValue() + " dzieci)\n"));

//        Podaj imiona kobiet w wieku poniżej 25 lat, które urodziły dzieci o wadze powyżej 4000 g.

        List<Mama> mlodeMatki = matki
                .stream()
                .filter(n -> n.getWiek() < 25)
                .toList();

        List<Noworodek> grubeNoworodki = mlodeMatki
                .stream()
                .flatMap(n -> n.getDzieci().stream())
                .filter(n -> n.getWaga() > 4000)
                .toList();

        System.out.println("Kobiety ponizej 25 lat ktore urodzily dzieci powyzej 4000g: ");
        grubeNoworodki.forEach(n -> System.out.println(n.getMama().getImie() + " urodzila: " + n.getImie() + " o wadze: " + n.getWaga() + "g!"));

//       Podaj imiona i daty urodzenia dziewczynek, które odziedziczyły imię po matce.
        List<Noworodek> dziewczynkiImiePoMatce = noworodki
                .stream()
                .filter(n -> n.getPlec().equals("c"))
                .filter(n -> n.getImie().equals(n.getMama().getImie()))
                .toList();

        System.out.println("\nDziewczynki z imieniem po matce: ");
        dziewczynkiImiePoMatce.forEach(n -> System.out.println(n.getImie() + " urodzona: " + n.getDataUrodzenia()));

//      Daty Blizniat

//        Set<String> datyBlizniat = noworodki.stream().filter(n1 -> noworodki.stream()
//                        .anyMatch(n2 -> Objects.equals(n1.getDataUrodzenia(), n2.getDataUrodzenia())
//                                && Objects.equals(n1.getIdentyfikatorMatki(), n2.getIdentyfikatorMatki())
//                                && !Objects.equals(n1.getImie(), n2.getImie())))
//                .map(Noworodek::getDataUrodzenia)
//                .collect(Collectors.toSet());
        List<String> daty = new ArrayList<>();
        Map<String, List<Noworodek>> datyBlizniaki = noworodki.stream()
                .collect(Collectors.groupingBy(n -> n.getDataUrodzenia() + "x" + n.getIdentyfikatorMatki()))
                .entrySet()
                .stream()
                .filter(n -> n.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for (Map.Entry<String, List<Noworodek>> data : datyBlizniaki.entrySet()) {
            daty.add(data.getKey());
        }
        System.out.println("\nDaty urodzenia blizniat: ");
        for (String string : daty) {
            Pattern pattern = Pattern.compile("(.+)(x)(\\d)");
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                System.out.println(matcher.group(1).trim());
            }

        }
    }
}
