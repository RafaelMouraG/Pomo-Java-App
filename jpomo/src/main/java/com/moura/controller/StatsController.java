package com.moura.controller;

import com.moura.service.TrackingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StatsController {

    @FXML
    private Label todayMinutesLabel;

    @FXML
    private Label weekMinutesLabel;

    @FXML
    private Label monthMinutesLabel;

    @FXML
    private Button closeButton;

    private TrackingService trackingService;

    /**
     * Este método será chamado pelo PomodoroController para passar o serviço de tracking.
     * @param trackingService A instância do serviço de tracking.
     */
    public void setTrackingService(TrackingService trackingService) {
        this.trackingService = trackingService;
        updateStats(); // Atualiza as estatísticas assim que o serviço é recebido
    }

    /**
     * Usa o serviço de tracking para calcular e mostrar as estatísticas.
     */
    private void updateStats() {
        if (trackingService != null) {
            long todayMinutes = trackingService.getFocusMinutesToday();
            long weekMinutes = trackingService.getFocusMinutesThisWeek();
            long monthMinutes = trackingService.getFocusMinutesThisMonth();

            todayMinutesLabel.setText(formatMinutes(todayMinutes));
            weekMinutesLabel.setText(formatMinutes(weekMinutes));
            monthMinutesLabel.setText(formatMinutes(monthMinutes));
        }
    }

    /**
     * Formata os minutos para uma string mais legível (ex: "125 min").
     */
    private String formatMinutes(long minutes) {
        return minutes + " min";
    }

    /**
     * Fecha a janela de estatísticas.
     */
    @FXML
    void handleCloseButton(ActionEvent event) {
        // Obtém a janela (Stage) a partir do botão e fecha-a
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
