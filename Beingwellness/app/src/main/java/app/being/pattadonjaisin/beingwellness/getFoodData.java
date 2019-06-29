package app.being.pattadonjaisin.beingwellness;

public class getFoodData {
    String Foodname;
    int Calories;
    int Fat;
    int Sodium;
    int Sugar;

    getFoodData() {
    }

    public getFoodData(String Foodname, int Calories, int Fat, int Sodium, int Sugar) {
        this.Foodname = Foodname;
        this.Calories = Calories;
        this.Fat = Fat;
        this.Sodium = Sodium;
        this.Sugar = Sugar;

    }

    public String getFoodname() {
        return Foodname;
    }

    public int getCalories() {
        return Calories;
    }

    public int getFat() {
        return Fat;
    }

    public int getSodium() {
        return Sodium;
    }

    public int getSugar() {
        return Sugar;
    }
}




