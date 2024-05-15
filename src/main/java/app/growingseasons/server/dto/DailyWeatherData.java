package app.growingseasons.server.dto;

public class DailyWeatherData {
    private String date;
    private Double tempMax;
    private Double tempMin;
    private double sunshine;
    private double uv;
    private double rain;
    private double shower;
    private double snow;
    private double wind;
    private double gust;

    public DailyWeatherData() {
    }

    public DailyWeatherData(String date, Double tempMax, Double tempMin, double sunshine, double uv, double rain, double shower, double snow, double wind, double gust) {
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.sunshine = sunshine;
        this.uv = uv;
        this.rain = rain;
        this.shower = shower;
        this.snow = snow;
        this.wind = wind;
        this.gust = gust;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public double getSunshine() {
        return sunshine;
    }

    public void setSunshine(double sunshine) {
        this.sunshine = sunshine;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getShower() {
        return shower;
    }

    public void setShower(double shower) {
        this.shower = shower;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }
}
