package app.being.pattadonjaisin.beingwellness;

public class Foodmanul {
    //    String Foodid;
    String foodname;
    String calad;
    String sugad;
    String sodad;
    String fatad;
    String key;
    public Foodmanul() {
    }

    public Foodmanul(String foodname, String calad, String sugad, String sodad, String fatad ) {
        this.foodname = foodname;
        this.calad = calad;
        this.sugad = sugad;
        this.sodad = sodad;
        this.fatad = fatad;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setCalad(String calad) {
        this.calad = calad;
    }

    public void setSugad(String sugad) {
        this.sugad = sugad;
    }

    public void setSodad(String sodad) {
        this.sodad = sodad;
    }

    public void setFatad(String fatad) {
        this.fatad = fatad;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {

        return key;
    }

    public String getFoodname() {
        return foodname;
    }

    public String getCalad() {
        return calad;
    }

    public String getSugad() {
        return sugad;
    }

    public String getSodad() {
        return sodad;
    }

    public String getFatad() {
        return fatad;
    }
}