package com.tutorial.racksemanalservice.service;

import com.tutorial.racksemanalservice.entity.RackSemanalEntity;
import com.tutorial.racksemanalservice.modelo.Booking;
import com.tutorial.racksemanalservice.modelo.Client;
import com.tutorial.racksemanalservice.repository.RackSemanalRepository;
//import edu.mtisw.payrollbackend.repositories.EmployeeRepository;
//import edu.mtisw.payrollbackend.repositories.ExtraHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RackSemanalService {
    @Autowired
    private RestTemplate restTemplate;

    public ArrayList<RackSemanalEntity> getRack() {
        ArrayList<RackSemanalEntity> rackSemanal = new ArrayList<>();

        try {
            // Obtener bookings con la clase correcta
            ResponseEntity<List<Booking>> bookingsResponse = restTemplate.exchange(
                    "http://bookingVoucherMS/api/v1/booking/",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Booking>>() {}
            );
            List<Booking> bookings = bookingsResponse.getBody();

            // Obtener RUTs
            ResponseEntity<List<String>> rutsResponse = restTemplate.exchange(
                    "http://bookingVoucherMS/api/v1/booking/rut/",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<String>>() {}
            );
            List<String> ruts = rutsResponse.getBody();

            if (ruts != null && !ruts.isEmpty()) {
                // Obtener clientes
                String rutsString = String.join(",", ruts);
                ResponseEntity<List<Client>> clientesResponse = restTemplate.exchange(
                        "http://clientMS/api/v1/clients/ruts/" + rutsString,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Client>>() {}
                );
                List<Client> clientes = clientesResponse.getBody();

                // Crear mapa de clientes por RUT
                Map<String, Client> clientePorRut = new HashMap<>();
                if (clientes != null) {
                    for (Client client : clientes) {
                        clientePorRut.put(client.getRut(), client);
                    }
                }

                // Crear entradas del rack semanal
                for (Booking booking : bookings) {
                    RackSemanalEntity entrada = new RackSemanalEntity();
                    Client cliente = clientePorRut.get(booking.getPersonRUT());

                    entrada.setRutCliente(booking.getPersonRUT());
                    entrada.setNombreCliente(cliente != null ? cliente.getName() : booking.getMainPerson());

                    // Convertir Date a LocalDateTime
                    entrada.setFechaHoraInicio(convertToLocalDateTime(booking.getInitialTime()));
                    entrada.setFechaHoraFin(convertToLocalDateTime(booking.getFinalTime()));
                    entrada.setFechaCreacion(convertToLocalDateTime(booking.getDateBooking()));

                    entrada.setActiva(true);
                    entrada.setEstado(determinarEstado(booking));

                    // Crear notas
                    StringBuilder notas = new StringBuilder();
                    notas.append("Personas: ").append(booking.getNumberOfPerson());
                    if (booking.getEspecialDay()) {
                        notas.append(" - Día especial");
                    }
                    entrada.setNotas(notas.toString());

                    rackSemanal.add(entrada);
                }
            }

        } catch (Exception e) {
            // Log del error
            e.printStackTrace();
            // Podrías lanzar una excepción personalizada aquí
        }

        // Ordenar el rack por fecha y RUT
        rackSemanal.sort(Comparator
                .comparing(RackSemanalEntity::getFechaHoraInicio)
                .thenComparing(RackSemanalEntity::getRutCliente));

        return rackSemanal;
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        if (date == null) {
            return LocalDateTime.now(); // O manejar de otra forma
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private String determinarEstado(Booking booking) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicio = convertToLocalDateTime(booking.getInitialTime());
        LocalDateTime fin = convertToLocalDateTime(booking.getFinalTime());

        if (ahora.isBefore(inicio)) {
            return "PENDIENTE";
        } else if (ahora.isAfter(fin)) {
            return "COMPLETADA";
        } else {
            return "EN_CURSO";
        }
    }
}
