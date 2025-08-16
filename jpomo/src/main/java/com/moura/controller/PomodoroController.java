package com.moura.controller;

import com.moura.model.Settings;
import com.moura.service.NotificationService;
import com.moura.service.TimerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PomodoroController {

    @FXML
    private Label statusLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    private TimerService timerService;
    private Settings currentSettings;
    private NotificationService notificationService;

    @FXML
    void handleStartButton(ActionEvent event) {
        // Esta linha é a que estava faltando. Ela inicia o timer.
        timerService.startTimer();
        updateButtonStates(true); // Atualiza os botões para o estado "rodando"
    }

    @FXML
    void handlePauseButton(ActionEvent event) {
        timerService.pauseTimer();
        updateButtonStates(false); // Atualiza os botões para o estado "pausado"
        startButton.setText("Continuar");
    }

    @FXML
    void handleResetButton(ActionEvent event) {
        timerService.resetTimer();
        updateButtonStates(false); // Atualiza os botões para o estado "pronto"
        startButton.setText("Iniciar");
    }

    @FXML
    public void initialize() {
        // 1. Instancia os serviços
        this.currentSettings = new Settings(); 
        this.timerService = new TimerService(currentSettings);
        this.notificationService = new NotificationService();

        // 2. Conecta (bind) os labels da UI com as propriedades do serviço
        timerLabel.textProperty().bind(timerService.formattedTimeProperty());
        statusLabel.textProperty().bind(timerService.statusTextProperty());

        // 3. Adiciona um "ouvinte" para saber quando uma sessão termina
        timerService.sessionFinishedProperty().addListener((obs, oldVal, sessionJustFinished) -> {
            if (sessionJustFinished) {
                notificationService.playSoundNotification();
                updateButtonStates(false);
                startButton.setText("Iniciar");
            }
        });

        // 4. Configura o estado inicial dos botões
        updateButtonStates(false);
    }

    /**
     * Método auxiliar para centralizar a lógica de habilitar/desabilitar botões.
     * @param isRunning true se o timer estiver rodando, false caso contrário.
     */
    private void updateButtonStates(boolean isRunning) {
        startButton.setDisable(isRunning);
        pauseButton.setDisable(!isRunning);
    }
}
