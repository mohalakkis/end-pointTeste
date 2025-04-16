package com.bootcampW22.EjercicioGlobal.controller;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.BadRequest;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.service.IVehicleService;
import com.bootcampW22.EjercicioGlobal.service.VehicleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {

    IVehicleService vehicleService;

    public VehicleController(VehicleServiceImpl vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehicles(){
        return new ResponseEntity<>(vehicleService.searchAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/vehicles/average_speed/brand/{brand}")
    public ResponseEntity<Double> getSpeedBybrand( @PathVariable String brand) {
        Double getByBrand = vehicleService.getByBrandSpeed(brand);

        return new ResponseEntity<>(getByBrand, HttpStatus.OK);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) {
        List<?> addCars = vehicleService.addnewVehicle(vehicle);

        if (addCars.isEmpty()){
            throw new BadRequest("Dados do veículo mal formatados ou incompletos.");

        }

        if (vehicle == null || vehicle.getId() == null || vehicle.getId() <= 0 ) {
            throw new BadRequest("Dados do veículo mal formatados ou incompletos.");
        }




        return new ResponseEntity<>(addCars, HttpStatus.CREATED);

    }

}
