package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;

import java.util.List;

public interface IVehicleService {
    List<VehicleDto> searchAllVehicles();

    double getByBrandSpeed(String brand);

    double getCapacity(String brand);


    List<VehicleDto> addVehicle(VehicleDto vehicle);

    List<VehicleDto> filterByTransmission(String transmission);


}
