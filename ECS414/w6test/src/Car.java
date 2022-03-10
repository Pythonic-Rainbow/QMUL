public class Car {
    private int horsepower;
    private String carmaker;

    public Car(String carmaker) {
        this.carmaker = carmaker;
    }

    public Car(int horsepower, String carmaker) {
        this.horsepower = horsepower;
        this.carmaker = carmaker;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public String getCarmaker() {
        return carmaker;
    }

}