package demographic_data;

public class HybridInheritance {
    public static void main(String[] args) {
         AltoCar car =  new AltoCar();
         car.showVehicle();
         car.showCar();
         car.showAltoCar();
         
         Bike bike = new Bike();
         bike.showBike();
    }
}



