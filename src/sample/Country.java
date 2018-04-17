package sample;

import java.util.Objects;

public class Country {
    private String countryName;
    private String capital;
    private String flagPicturePath;

    public Country(String countryName, String capital, String flagPicturePath) {

        this.countryName = countryName;
        this.capital = capital;
        this.flagPicturePath = flagPicturePath;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapital() {
        return capital;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", capital='" + capital + '\'' +
                ", flagPicturePath='" + flagPicturePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(countryName, country.countryName) &&
                Objects.equals(capital, country.capital) &&
                Objects.equals(flagPicturePath, country.flagPicturePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(countryName, capital, flagPicturePath);
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlagPicturePath() {
        return flagPicturePath;
    }

    public void setFlagPicturePath(String flagPicturePath) {
        this.flagPicturePath = flagPicturePath;
    }


}
