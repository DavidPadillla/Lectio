package com.bibli.bia.service;

import com.bibli.bia.Model.ReservaModel;
import com.bibli.bia.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Instances;

import java.io.*;
import java.util.*;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // --- Métodos existentes ---
    public ReservaModel crearReserva(ReservaModel reserva) {
        return reservaRepository.save(reserva);
    }

    public ReservaModel obtenerReservaPorId(String id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public List<ReservaModel> obtenerTodasReservas() {
        return reservaRepository.findAll();
    }

    public void eliminarReserva(String id) {
        Optional<ReservaModel> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            reservaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Reserva no encontrada");
        }
    }

    // --- CORREGIDO: Obtener la categoría más reservada ---
    public String obtenerCategoriaMasReservada() throws Exception {
        // Leer desde el classpath (funciona en local y en Render)
        InputStream inputStream = getClass().getResourceAsStream("/reservas.arff");

        if (inputStream == null) {
            throw new RuntimeException("No se encontró reservas.arff en resources");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Instances data = new Instances(reader);
        reader.close();

        // Contar frecuencias de cada categoría
        HashMap<String, Integer> categorias = new HashMap<>();
        for (int i = 0; i < data.numInstances(); i++) {
            String categoria = data.instance(i).stringValue(0);
            categorias.put(categoria, categorias.getOrDefault(categoria, 0) + 1);
        }

        // Encontrar la categoría con mayor frecuencia (moda)
        String categoriaMasReservada = Collections.max(categorias.entrySet(),
                Map.Entry.comparingByValue()).getKey();

        return categoriaMasReservada;
    }

    // --- CORREGIDO: Obtener estadísticas completas ---
    public Map<String, Integer> obtenerEstadisticasCategorias() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/reservas.arff");

        if (inputStream == null) {
            throw new RuntimeException("No se encontró reservas.arff en resources");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Instances data = new Instances(reader);
        reader.close();

        HashMap<String, Integer> categorias = new HashMap<>();
        for (int i = 0; i < data.numInstances(); i++) {
            String categoria = data.instance(i).stringValue(0);
            categorias.put(categoria, categorias.getOrDefault(categoria, 0) + 1);
        }

        return categorias;
    }
}