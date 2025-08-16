package com.moura.model;

import java.time.LocalDateTime;


public class FocusSession {

    // Usamos String para a data para facilitar a serialização com Gson.
    private final String endTime;
    private final long durationInMinutes;

    public FocusSession(long durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        // Armazena a data e hora atuais no formato ISO_LOCAL_DATE_TIME (ex: "2025-08-16T15:30:00")
        this.endTime = LocalDateTime.now().toString();
    }

    // Getters são necessários para que o Gson possa ler as propriedades ao salvar.
    public String getEndTime() {
        return endTime;
    }

    public long getDurationInMinutes() {
        return durationInMinutes;
    }
}
