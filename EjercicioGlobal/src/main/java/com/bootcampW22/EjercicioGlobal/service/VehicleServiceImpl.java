package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.repository.IVehicleRepository;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements IVehicleService{

    IVehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepositoryImpl vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }
    @Override
    public List<VehicleDto> searchAllVehicles() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        if(vehicleList.isEmpty()){
            throw new NotFoundException("No se encontró ningun auto en el sistema.");
        }
        return vehicleList.stream()
                .map(this::convertVehicleToDto)
                .collect(Collectors.toList());
    }


    @Override
    public double getByBrandSpeed(String brand) {

        List<Vehicle> vehicles = vehicleRepository.getByBrand(brand);

        double totalSpeed = 0;

        if (vehicles.isEmpty()) {
            throw new NotFoundException("nenhum carro encontrado");
        }

        for (Vehicle vehicle : vehicles) {
            try {
                double maxSpeed = Double.parseDouble(vehicle.getMax_speed());
                totalSpeed += maxSpeed;
            } catch (NumberFormatException e) {
                continue;
            }
        }



        return totalSpeed / vehicles.size();
    }

    private VehicleDto convertVehicleToDto(Vehicle v){
        return new VehicleDto(
                v.getId(),
                v.getBrand(),
                v.getModel(),
                v.getRegistration(),
                v.getColor(),
                v.getYear(),
                v.getMax_speed(),
                v.getPassengers(),
                v.getFuel_type(),
                v.getTransmission(),
                v.getLength(),
                v.getWidth(),
                v.getWeight());
    }
}
