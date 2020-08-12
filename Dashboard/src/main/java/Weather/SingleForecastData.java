package Weather;

import java.time.LocalDateTime;

public class SingleForecastData {
    private int temp;
    private double precipitationProbabilityInPercent;
    private String kindOfPrecipitation;
    private String shortDescription;
    private LocalDateTime localDateTime;


    public SingleForecastData(int temp, double precipitationProbabilityInPercent, String kindOfPrecipitation, String shortDescription, LocalDateTime localDateTime) {
        this.temp = temp;
        this.precipitationProbabilityInPercent = precipitationProbabilityInPercent;
        this.kindOfPrecipitation = kindOfPrecipitation;
        this.shortDescription = shortDescription;
        this.localDateTime = localDateTime;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public double getPrecipitationProbabilityInPercent() {
        return precipitationProbabilityInPercent;
    }

    public void setPrecipitationProbabilityInPercent(double rainProbabilityInPercent) {
        this.precipitationProbabilityInPercent = rainProbabilityInPercent;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        if (shortDescription.equals("Overcast")){
            return "Datum: " + localDateTime+  ",  Temperatur: " + temp + ",\tWetterbeschreibung: " + shortDescription + ",\t\t\tNiederschlagswkt: "
                    + precipitationProbabilityInPercent + ",\tArt d. Niederschlags: " + kindOfPrecipitation;
        }else return "Datum: " + localDateTime+  ",  Temperatur: " + temp + ",\tWetterbeschreibung: " + shortDescription + ",\t\tNiederschlagswkt: "
                + precipitationProbabilityInPercent + ",\tArt d. Niederschlags: " + kindOfPrecipitation;
    }
}
