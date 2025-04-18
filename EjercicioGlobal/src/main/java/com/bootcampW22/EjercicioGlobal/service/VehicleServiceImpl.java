package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.BadRequest;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.repository.IVehicleRepository;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
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

        if (vehicles.isEmpty()) {
            throw new NotFoundException("nenhum carro encontrado");
        }
        return vehicles.stream().mapToDouble(v -> Double.parseDouble(v.getMax_speed())).average().orElse(0.0);

    }

    @Override
    public double getCapacity(String brand) {
        List<Vehicle> vehicle = vehicleRepository.getByBrand(brand);

        if (vehicle.isEmpty()) {
            throw new NotFoundException("Nao foi encontrado nenhuma marca");
        }

        return vehicle.stream().mapToDouble(v -> v.getPassengers()).average().orElse(0.0);
    }


    @Override
    public List<VehicleDto> addVehicle(VehicleDto vehicleDto) {
        if (vehicleDto == null) {
            throw new BadRequest("Nenhum veiculo.");
        }

        Vehicle vehicle = new Vehicle(
                vehicleDto.id(),
                vehicleDto.brand(),
                vehicleDto.model(),
                vehicleDto.registration(),
                vehicleDto.color(),
                vehicleDto.year(),
                vehicleDto.max_speed(),
                vehicleDto.passengers(),
                vehicleDto.fuel_type(),
                vehicleDto.transmission(),
                vehicleDto.length(),
                vehicleDto.width(),
                vehicleDto.weight()
        );

        vehicleRepository.addNewVehicle(vehicle);

        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
                .map(this::convertVehicleToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> filterByTransmission(String transmission) {
        List<Vehicle> VehicleByTransmission = vehicleRepository.filterbytrasmissontype(transmission);

        if (VehicleByTransmission.isEmpty()){
            throw new NotFoundException("Nenhum carro com esse cambio encontrado");
        }
        return VehicleByTransmission.stream().map(this::convertVehicleToDto).collect(Collectors.toList());
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
