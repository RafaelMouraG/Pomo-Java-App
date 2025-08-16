package com.moura.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.moura.model.FocusSession;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por salvar, carregar e analisar o histórico de sessões de foco.
 */
public class TrackingService {

    private final Gson gson;
    private final Path historyPath;
    private List<FocusSession> sessionHistory;

    public TrackingService() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        
        String userHome = System.getProperty("user.home");
        this.historyPath = Paths.get(userHome, ".jpomo", "tracking_history.json");

        this.sessionHistory = loadHistory();
    }

    private List<FocusSession> loadHistory() {
        if (Files.exists(historyPath)) {
            try (Reader reader = new FileReader(historyPath.toFile())) {
                Type listType = new TypeToken<ArrayList<FocusSession>>() {}.getType();
                List<FocusSession> history = gson.fromJson(reader, listType);
                System.out.println("Histórico de foco carregado de: " + historyPath);
                return history != null ? history : new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Nenhum ficheiro de histórico encontrado. Criando um novo.");
        return new ArrayList<>();
    }

    private void saveHistory() {
        try {
            Files.createDirectories(historyPath.getParent());
            try (Writer writer = new FileWriter(historyPath.toFile())) {
                gson.toJson(sessionHistory, writer);
                System.out.println("Histórico de foco salvo em: " + historyPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFocusSession(FocusSession session) {
        this.sessionHistory.add(session);
        saveHistory();
    }

    // --- NOVOS MÉTODOS DE CÁLCULO ---

    /**
     * Calcula o total de minutos focados no dia de hoje.
     * @return Total de minutos.
     */
    public long getFocusMinutesToday() {
        LocalDate today = LocalDate.now();
        return sessionHistory.stream()
                .filter(session -> LocalDateTime.parse(session.getEndTime()).toLocalDate().isEqual(today))
                .mapToLong(FocusSession::getDurationInMinutes)
                .sum();
    }

    /**
     * Calcula o total de minutos focados na semana atual (de Domingo a Sábado).
     * @return Total de minutos.
     */
    public long getFocusMinutesThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        return sessionHistory.stream()
                .filter(session -> {
                    LocalDate sessionDate = LocalDateTime.parse(session.getEndTime()).toLocalDate();
                    return !sessionDate.isBefore(startOfWeek) && !sessionDate.isAfter(endOfWeek);
                })
                .mapToLong(FocusSession::getDurationInMinutes)
                .sum();
    }

    /**
     * Calcula o total de minutos focados no mês atual.
     * @return Total de minutos.
     */
    public long getFocusMinutesThisMonth() {
        LocalDate today = LocalDate.now();
        return sessionHistory.stream()
                .filter(session -> {
                    LocalDate sessionDate = LocalDateTime.parse(session.getEndTime()).toLocalDate();
                    return sessionDate.getYear() == today.getYear() && sessionDate.getMonth() == today.getMonth();
                })
                .mapToLong(FocusSession::getDurationInMinutes)
                .sum();
    }
}
