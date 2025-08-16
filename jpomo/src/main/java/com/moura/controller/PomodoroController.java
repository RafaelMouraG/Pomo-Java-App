package com.moura.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PomodoroController {

    // Injeção dos componentes da interface (View)
    // A anotação @FXML conecta estas variáveis com os componentes
    // que têm o mesmo fx:id no arquivo PomodoroView.fxml.

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

    // Métodos para tratar os eventos dos botões
    // A anotação @FXML conecta estes métodos com os atributos onAction
    // dos botões no arquivo FXML.

    @FXML
    void handleStartButton(ActionEvent event) {
        System.out.println("Botão Iniciar clicado!");
        // Aqui virá a lógica para iniciar o TimerService
    }

    @FXML
    void handlePauseButton(ActionEvent event) {
        System.out.println("Botão Pausar clicado!");
        // Aqui virá a lógica para pausar o TimerService
    }

    @FXML
    void handleResetButton(ActionEvent event) {
        System.out.println("Botão Resetar clicado!");
        // Aqui virá a lógica para resetar o TimerService
    }

    /**
     * O método initialize() é chamado automaticamente pelo JavaFX
     * depois que o arquivo FXML é carregado e os componentes são injetados.
     * É um ótimo lugar para configurar o estado inicial da sua interface.
     */
    @FXML
    public void initialize() {
        System.out.println("PomodoroController inicializado.");
        // Exemplo: desabilitar o botão de pausa inicialmente
        pauseButton.setDisable(true);
    }
}
