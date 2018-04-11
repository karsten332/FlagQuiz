package sample;

import java.util.HashMap;
import java.util.Map;

public class Populate {
    private int counter = 0;
    private Map<Integer, Country> countries;
    public Populate(){
        countries = new HashMap<>();
        populateMap();

    }

    public int size(){
        return countries.size();
    }
    public void add(Country country){

        countries.put(counter++,country);
    }

    public Country getCountry(int number){
        return countries.get(number);
    }

    private void populateMap() {
        // Countries
        Country no = new Country("Norway", "oslo", "sample/pictures/no.png");
        Country swe = new Country("Sweden", "stockholm", "sample/pictures/se.png");
        Country dk = new Country("Denmark", "copenhagen", "sample/pictures/dk.png");
        Country md = new Country("Moldova", "Chisinau", "sample/pictures/md.png");
        Country fr = new Country("France", "Paris", "sample/pictures/fr.png");
        Country be = new Country("Belgium", "Brussels", "sample/pictures/be.png");
        Country us = new Country("USA", "washington", "sample/pictures/us.png");
        Country ca = new Country("Canada", "Ottawa", "sample/pictures/ca.png");


        // Europe
        add(no);
        add(swe);
        add(dk);
        add(md);
        add(be);
        add(fr);

        // NA
        add(us);
        add(ca);

    }
}
