package nl.utwente.di.dao;

import nl.utwente.di.model.Car;

import java.util.HashMap;
import java.util.Map;

public enum CarDao {

    instance;
    private Map<String, Car> contentProvider = new HashMap<>();

    private CarDao() {
        Car car1 = new Car("1", "red", "1000", "BMW");
        contentProvider.put("this is car 1", car1);
    }

    public Map<String, Car> getModel() {
        return contentProvider;
    }

}
