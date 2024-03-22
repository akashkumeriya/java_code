package demographic_data;

public class PolymorphismExample {
    public static void main(String[] args) {
        Animal animal1 = new Dog();
        Animal animal2 = new Cat();

        animal1.makeSound(); // Output will be "Woof!"
        animal2.makeSound(); // Output will be "Meow!"
    }
}



