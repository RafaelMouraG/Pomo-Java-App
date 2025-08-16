package com.moura.controller;

import com.moura.model.FocusSession;
import com.moura.model.Settings;
import com.moura.model.TimerState;
import com.moura.service.NotificationService;
import com.moura.service.SettingsService;
import com.moura.service.TimerService;
import com.moura.service.TrackingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


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
    @FXML
    private TextField focusDurationField;
    @FXML
    private TextField shortBreakDurationField;
    @FXML
    private TextField longBreakDurationField;
    @FXML
    private Button saveSettingsButton;
    
    
    @FXML
    private Button showStatsButton;

    // Todos os nossos serviços
    private TimerService timerService;
    private SettingsService settingsService;
    private TrackingService trackingService;
    private NotificationService notificationService;
    
    private Settings currentSettings;

    @FXML
    void handleStartButton(ActionEvent event) {
        timerService.startTimer();
        updateButtonStates(true);
    }

    @FXML
    void handlePauseButton(ActionEvent event) {
        timerService.pauseTimer();
        updateButtonStates(false);
        startButton.setText("Continuar");
    }

    @FXML
    void handleResetButton(ActionEvent event) {
        timerService.resetTimer();
        updateButtonStates(false);
        startButton.setText("Iniciar");
    }

    @FXML
    void handleSaveSettingsButton(ActionEvent event) {
        try {
            int focusDuration = Integer.parseInt(focusDurationField.getText());
            int shortBreakDuration = Integer.parseInt(shortBreakDurationField.getText());
            int longBreakDuration = Integer.parseInt(longBreakDurationField.getText());

            currentSettings.setFocusDurationMinutes(focusDuration);
            currentSettings.setShortBreakDurationMinutes(shortBreakDuration);
            currentSettings.setLongBreakDurationMinutes(longBreakDuration);

            settingsService.saveSettings(currentSettings);

            timerService.resetTimer();
            System.out.println("Configurações guardadas e aplicadas!");

        } catch (NumberFormatException e) {
            System.err.println("Erro: Por favor, insira apenas números nos campos de duração.");
        }
    }

    
    @FXML
    void handleShowStatsButton(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/moura/jpomo/view/StatsView.fxml"));
            Parent root = loader.load();

            
            StatsController statsController = loader.getController();
            
            statsController.setTrackingService(trackingService);

            
            Stage statsStage = new Stage();
            statsStage.setTitle("Estatísticas de Foco");
            
            statsStage.initModality(Modality.APPLICATION_MODAL);
            statsStage.initStyle(StageStyle.UTILITY);

            Scene scene = new Scene(root);
            statsStage.setScene(scene);
            statsStage.showAndWait(); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        this.settingsService = new SettingsService();
        this.trackingService = new TrackingService();
        this.notificationService = new NotificationService();

        this.currentSettings = settingsService.loadSettings();
        
        populateSettingsFields();

        this.timerService = new TimerService(currentSettings);

        timerLabel.textProperty().bind(timerService.formattedTimeProperty());
        statusLabel.textProperty().bind(timerService.statusTextProperty());

        timerService.sessionFinishedProperty().addListener((obs, oldVal, sessionJustFinished) -> {
            if (sessionJustFinished) {
                if (timerService.getCurrentState() == TimerState.FOCUS) {
                    FocusSession session = new FocusSession(currentSettings.getFocusDurationMinutes());
                    trackingService.addFocusSession(session);
                }
                
                notificationService.playSoundNotification();
                updateButtonStates(false);
                startButton.setText("Iniciar");
            }
        });

        updateButtonStates(false);
    }

    private void updateButtonStates(boolean isRunning) {
        startButton.setDisable(isRunning);
        pauseButton.setDisable(!isRunning);
    }

    private void populateSettingsFields() {
        focusDurationField.setText(String.valueOf(currentSettings.getFocusDurationMinutes()));
        shortBreakDurationField.setText(String.valueOf(currentSettings.getShortBreakDurationMinutes()));
        longBreakDurationField.setText(String.valueOf(currentSettings.getLongBreakDurationMinutes()));
    }
}
