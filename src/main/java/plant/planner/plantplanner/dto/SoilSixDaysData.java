package plant.planner.plantplanner.dto;

import java.util.Collections;
import java.util.List;

public class SoilSixDaysData {

    private double tempAt0cmMin;
    private double tempAt6cmMin;
    private double tempAt0cmMax;
    private double tempAt6cmMax;

    private double moistureTo1cm;
    private double moisture3To9cm;


    public SoilSixDaysData(List<Double> tempsAt0, List<Double> tempsAt6, List<Double> moistureTo1, List<Double> moisture3To9) {
        tempAt0cmMin = Collections.min(tempsAt0);
        tempAt0cmMax = Collections.max(tempsAt0);
        tempAt6cmMin = Collections.min(tempsAt6);
        tempAt6cmMax = Collections.max(tempsAt6);

        var m1 = (moistureTo1.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0)) * 100;

        moistureTo1cm = Math.round(m1 * 10) / 10.0;

        var m2 = (moisture3To9.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0)) * 100;

        moisture3To9cm = Math.round(m2 * 10) / 10.0;


    }


    public double getMoistureTo1cm() {
        return moistureTo1cm;
    }

    public void setMoistureTo1cm(double moistureTo1cm) {
        this.moistureTo1cm = moistureTo1cm;
    }

    public double getMoisture3To9cm() {
        return moisture3To9cm;
    }

    public void setMoisture3To9cm(double moisture3To9cm) {
        this.moisture3To9cm = moisture3To9cm;
    }

    public double getTempAt0cmMin() {
        return tempAt0cmMin;
    }

    public void setTempAt0cmMin(double tempAt0cmMin) {
        this.tempAt0cmMin = tempAt0cmMin;
    }

    public double getTempAt6cmMin() {
        return tempAt6cmMin;
    }

    public void setTempAt6cmMin(double tempAt6cmMin) {
        this.tempAt6cmMin = tempAt6cmMin;
    }

    public double getTempAt0cmMax() {
        return tempAt0cmMax;
    }

    public void setTempAt0cmMax(double tempAt0cmMax) {
        this.tempAt0cmMax = tempAt0cmMax;
    }

    public double getTempAt6cmMax() {
        return tempAt6cmMax;
    }

    public void setTempAt6cmMax(double tempAt6cmMax) {
        this.tempAt6cmMax = tempAt6cmMax;
    }
}
