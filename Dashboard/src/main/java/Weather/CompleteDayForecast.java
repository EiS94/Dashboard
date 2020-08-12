package Weather;

import java.time.LocalDate;

public class CompleteDayForecast {

    private int tempHigh;
    private int tempLow;
    private double precipitationProbabilityInPercent;
    private String kindOfPrecipitation;
    private String shortDescription;
    private LocalDate localDate;

    public CompleteDayForecast(int tempHigh, int tempLow, double precipitationProbabilityInPercent, String kindOfPrecipitation, String shortDescription, LocalDate localDate) {
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
        this.precipitationProbabilityInPercent = precipitationProbabilityInPercent;
        this.kindOfPrecipitation = kindOfPrecipitation;
        this.shortDescription = shortDescription;
        this.localDate = localDate;
    }

    public String toString(){
        return "Datum: " + localDate.toString() + ",\tWetterbeschreibung: " + shortDescription + ",\tMaxTemp: " + tempHigh + ",\tMinTemp: " +
                tempLow + ",\tNiederschlagswkt.: " + precipitationProbabilityInPercent + ",\tArt d. Niederschlags: " + kindOfPrecipitation;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public void setTempHigh(int tempHigh) {
        this.tempHigh = tempHigh;
    }

    public int getTempLow() {
        return tempLow;
    }

    public void setTempLow(int tempLow) {
        this.tempLow = tempLow;
    }

    public double getPrecipitationProbabilityInPercent() {
        return precipitationProbabilityInPercent;
    }

    public void setPrecipitationProbabilityInPercent(double precipitationProbabilityInPercent) {
        this.precipitationProbabilityInPercent = precipitationProbabilityInPercent;
    }

    public String getKindOfPrecipitation() {
        return kindOfPrecipitation;
    }

    public void setKindOfPrecipitation(String kindOfPrecipitation) {
        this.kindOfPrecipitation = kindOfPrecipitation;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDateTime(LocalDate localDate) {
        this.localDate = localDate;
    }
}
