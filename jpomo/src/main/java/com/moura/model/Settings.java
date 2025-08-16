package com.moura.model;

/**
 * Uma classe simples (POJO) para armazenar as configurações do usuário.
 */
public class Settings {

    // Valores padrão
    private int focusDurationMinutes = 1;
    private int shortBreakDurationMinutes = 5;
    private int longBreakDurationMinutes = 15;

    // Getters e Setters são necessários para que outras classes possam
    // ler e modificar estes valores.

    public int getFocusDurationMinutes() {
        return focusDurationMinutes;
    }

    public void setFocusDurationMinutes(int focusDurationMinutes) {
        this.focusDurationMinutes = focusDurationMinutes;
    }

    public int getShortBreakDurationMinutes() {
        return shortBreakDurationMinutes;
    }

    public void setShortBreakDurationMinutes(int shortBreakDurationMinutes) {
        this.shortBreakDurationMinutes = shortBreakDurationMinutes;
    }

    public int getLongBreakDurationMinutes() {
        return longBreakDurationMinutes;
    }

    public void setLongBreakDurationMinutes(int longBreakDurationMinutes) {
        this.longBreakDurationMinutes = longBreakDurationMinutes;
    }
}
