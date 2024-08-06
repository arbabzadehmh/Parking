package parking.model.bl;

import parking.controller.exception.DuplicateException;
import parking.controller.exception.NoContentException;
import parking.model.da.CarDa;
import parking.model.entity.Car;

import java.util.List;

public class CarBl {

    public static Car save(Car car) throws Exception{
        try(CarDa carDa = new CarDa()) {
            if (carDa.findByNumber(car.getCarNumber()) == null){
                return carDa.save(car);
            }
            throw new DuplicateException("Car is exist !");
        }
    }

    public static Car edit(Car car) throws Exception{
        try(CarDa carDa = new CarDa()) {
            if (carDa.findByID(car.getId()) != null){
                return carDa.edit(car);
            }
            throw new NoContentException("Car is not in the list !");
        }
    }

    public static Car remove(Long id) throws Exception{
        try(CarDa carDa = new CarDa()) {
            Car car = carDa.findByID(id);
            if (car != null){
                carDa.remove(id);
                return car;
            }
            throw new NoContentException("Car is not in the list !");
        }
    }

    public static List<Car> findAll() throws Exception{
        try(CarDa carDa = new CarDa()) {
            List<Car> carList = carDa.findAll();
            if (carList.size()>0){
                return carList;
            }
            throw new NoContentException("The list is empty !");
        }
    }

    public static Car findById(Long id) throws Exception{
        try(CarDa carDa = new CarDa()) {
            Car car = carDa.findByID(id);
            if (car != null){
                return car;
            }
            throw new NoContentException("No car was found !");
        }
    }

    public static Car findByNumber(String carNumber) throws Exception{
        try(CarDa carDa = new CarDa()) {
            Car car = carDa.findByNumber(carNumber);
            if (car != null){
                return car;
            }
            throw new NoContentException("No car was found !");
        }
    }

    public static List<Car> findByVipStatus(boolean status) throws Exception{
        try(CarDa carDa = new CarDa()) {
            List<Car> carList = carDa.findByVipStatus(status);
            if (carList.size()>0){
                return carList;
            }
            throw new NoContentException("No car was found !");
        }
    }

    public static Car findOrSaveByNumber(String carNumber) throws Exception{
        try(CarDa carDa = new CarDa()) {
            if (carDa.findByNumber(carNumber) == null){
                return carDa.findOrSaveByNumber(carNumber);
            }
            return carDa.findByNumber(carNumber);
        }
    }

}
