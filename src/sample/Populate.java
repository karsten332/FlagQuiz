package sample;

import java.util.HashMap;
import java.util.Map;

public class Populate {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    private Map<Integer, Country> countries;
    public Populate(){
        countries = new HashMap<>();


    }

    public int size(){
        return countries.size();
    }
    public void add(Country country){

        countries.put(counter++,country);
    }
    public void clear(){
        countries.clear();
    }
    public Country getCountry(int number){
        return countries.get(number);
    }

    public void populateMapStandardQuiz() {
        // Countries
        Country no = new Country("Norway", "oslo", "sample/pictures/no.png");
        Country swe = new Country("Sweden", "stockholm", "sample/pictures/se.png");
        Country dk = new Country("Denmark", "copenhagen", "sample/pictures/dk.png");
        Country md = new Country("Moldova", "Chisinau", "sample/pictures/md.png");
        Country fr = new Country("France", "Paris", "sample/pictures/fr.png");
        Country be = new Country("Belgium", "Brussels", "sample/pictures/be.png");
        Country us = new Country("USA", "washington", "sample/pictures/us.png");
        Country ca = new Country("Canada", "Ottawa", "sample/pictures/ca.png");

        add(no);
        add(swe);
        add(dk);
        add(md);
        add(be);
        add(fr);
        add(us);
        add(ca);

    }

    public void populateMapEuropeCapitalQuiz(){
        Country de = new Country("Germany", "Berlin", "sample/pictures/de.png");
        Country fi = new Country("Finland", "Helsinki", "sample/pictures/fi.png");
        Country al = new Country("Albania", "Tirana", "sample/pictures/al.png");
        Country ru = new Country("Russia", "Moscow", "sample/pictures/ru.png");
        Country lu = new Country("Luxembourg", "Luxembourg", "sample/pictures/lu.png");

        add(de);
        add(fi);
        add(al);
        add(ru);
        add(lu);
    }
    public void populateMapQuizScandinaviaCapitalQuiz(){
        Country fi = new Country("Finland", "Helsinki", "sample/pictures/fi.png");
        Country no = new Country("Norway", "oslo", "sample/pictures/no.png");
        Country swe = new Country("Sweden", "stockholm", "sample/pictures/se.png");
        Country dk = new Country("Denmark", "copenhagen", "sample/pictures/dk.png");
        Country is = new Country("Iceland", "Reykjavik", "sample/pictures/is.png");

        add(fi);
        add(no);
        add(swe);
        add(dk);
        add(is);
    }
}
