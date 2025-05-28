package com.tutorial.racksemanalservice.service;

import com.tutorial.racksemanalservice.entity.RackSemanalEntity;
import com.tutorial.racksemanalservice.modelo.Booking;
import com.tutorial.racksemanalservice.repository.RackSemanalRepository;
//import edu.mtisw.payrollbackend.repositories.EmployeeRepository;
//import edu.mtisw.payrollbackend.repositories.ExtraHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RackSemanalService {

    @Autowired
    RackSemanalRepository rackSemanalRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<RackSemanalEntity> getRack(){
        //Conseguimos las reservas realizas
        List<Booking> bookings = restTemplate.getForObject("http://BookingVoucherMS/api/v1/booking/", List.class);

        //Ahora conseguimos los ruts que tiene reserva
        List<String> ruts = restTemplate.getForObject("http://BookingVoucherMS/api/v1/booking/rut/", List.class);

        // Convertir la lista de ruts a un string separado por comas
        String rutsString = String.join(",", ruts);

        //Ahora con los ruts podemos conseguir a los clientes
        List<Clientes> clientes = restTemplate.getForObject("http://BookingVoucherMS/api/v1/booking/ruts/" + rutsString,  List.class);
        //Con los ruts y reservamos podemos armar el rack semanal


    }

    }
