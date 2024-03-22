package demographic_data;

public class HierarchicalInheritance {
    public static void main(String[] args) {
         Car car =  new Car();
         car.showVehicle();
         car.showCar();
         
         Bike bike = new Bike();
         bike.showBike();
    }
}

