package sample;

public class Country {
    private String countryName;
    private String capital;
    private String flagPicturePath;
    // continent

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

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Country(String countryName, String capital, String flagPicturePath) {
        this.countryName = countryName;
        this.capital = capital;
        this.flagPicturePath = flagPicturePath;
    }

    public String getFlagPicturePath() {
        return flagPicturePath;
    }

    public void setFlagPicturePath(String flagPicturePath) {
        this.flagPicturePath = flagPicturePath;
    }
}
