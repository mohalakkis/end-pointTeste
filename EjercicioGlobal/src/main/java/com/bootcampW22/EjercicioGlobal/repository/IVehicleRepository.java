package com.bootcampW22.EjercicioGlobal.repository;

import com.bootcampW22.EjercicioGlobal.entity.Vehicle;

import java.util.List;

public interface IVehicleRepository {
    List<Vehicle> findAll();
    List<Vehicle> getByBrand(String brand);

    List<Vehicle> filterbytrasmissontype(String transmission);

    void addNewVehicle(Vehicle vehicle);






}
