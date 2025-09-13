package org.example;

import org.example.zoo.animals.Animal;
import org.example.zoo.animals.Elephant;
import org.example.zoo.animals.Giraffe;
import org.example.zoo.animals.Lion;
import org.example.zoo.feeding.FeedingService;

public class ZooApp {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Animal lion = new Lion();
        Animal elephant = new Elephant();
        Animal Giraffe = new Giraffe();

        System.out.println("Animal sounds like this: " + animal.sound());
        System.out.println("Lion sounds like this: " + lion.sound());
        System.out.println("Elephant sounds like this: " + elephant.sound());
        System.out.println("Giraffe sounds like this: " + Giraffe.sound());

        FeedingService zooService = new FeedingService();
        zooService.feed(animal);
        zooService.feed(lion);
        zooService.feed(elephant);
        zooService.feed(Giraffe);
    }
}
