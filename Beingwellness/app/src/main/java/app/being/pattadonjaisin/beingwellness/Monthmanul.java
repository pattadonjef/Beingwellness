package app.being.pattadonjaisin.beingwellness;

public class Monthmanul {
    //    String Foodid;
    String totalcal;
    String totalfat;
    String totalsod;
    String totalsug;

    String key2;

    public Monthmanul() {
    }

    public Monthmanul(String totalcal, String totalfat, String totalsod, String totalsug, String key2) {
        this.totalcal = totalcal;
        this.totalfat = totalfat;
        this.totalsod = totalsod;
        this.totalsug = totalsug;
        this.key2 = key2;
    }

    public String getTotalcal() {
        return totalcal;
    }

    public void setTotalcal(String totalcal) {
        this.totalcal = totalcal;
    }

    public String getTotalfat() {
        return totalfat;
    }

    public void setTotalfat(String totalfat) {

        this.totalfat = totalfat;
    }

    public String getTotalsod() {
        return totalsod;
    }

    public void setTotalsod(String totalsod) {
        this.totalsod = totalsod;
    }

    public String getTotalsug() {
        return totalsug;
    }

    public void setTotalsug(String totalsug) {
        this.totalsug = totalsug;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
}