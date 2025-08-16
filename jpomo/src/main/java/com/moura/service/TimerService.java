package com.moura.service;

import com.moura.model.Settings;
import com.moura.model.TimerState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class TimerService {

    private final Timeline timeline;
    private int remainingSeconds;
    private final Settings settings;
    private TimerState currentState;
    private int focusCyclesCompleted = 0;

    // Propriedades observáveis para a UI
    private final StringProperty formattedTime = new SimpleStringProperty();
    private final StringProperty statusText = new SimpleStringProperty();
    private final BooleanProperty sessionFinished = new SimpleBooleanProperty(false);

    public TimerService(Settings settings) {
        this.settings = settings;
        this.timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        // Inicia o estado e prepara o primeiro timer
        setupTimerForState(TimerState.FOCUS);
    }

    public void startTimer() {
        timeline.stop();

        sessionFinished.set(false); // Reseta o gatilho de notificação
        timeline.getKeyFrames().clear();
        
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            remainingSeconds--;
            updateFormattedTime();
            if (remainingSeconds <= 0) {
                timeline.stop();
                if (currentState == TimerState.FOCUS) {
                    focusCyclesCompleted++; // Incrementa apenas ao final do foco
                }
                sessionFinished.set(true); // Notifica o Controller
                advanceToNextState();
            }
        });
        
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void pauseTimer() {
        timeline.pause();
    }

    public void resetTimer() {
        timeline.stop();
        // Reseta para o estado atual (ex: se estava em pausa, reseta o tempo de pausa)
        setupTimerForState(currentState);
    }

    private void advanceToNextState() {
        if (currentState == TimerState.FOCUS) {
            // Após 4 ciclos de foco, inicia uma pausa longa, senão, uma curta
            if (focusCyclesCompleted % 4 == 0) {
                setupTimerForState(TimerState.LONG_BREAK);
            } else {
                setupTimerForState(TimerState.SHORT_BREAK);
            }
        } else {
            // Após qualquer pausa, volta para o foco
            setupTimerForState(TimerState.FOCUS);
        }
    }

    private void setupTimerForState(TimerState newState) {
        this.currentState = newState;
        switch (newState) {
            case FOCUS:
                remainingSeconds = settings.getFocusDurationMinutes() * 60;
                statusText.set("Foco");
                break;
            case SHORT_BREAK:
                remainingSeconds = settings.getShortBreakDurationMinutes() * 60;
                statusText.set("Pausa Curta");
                break;
            case LONG_BREAK:
                remainingSeconds = settings.getLongBreakDurationMinutes() * 60;
                statusText.set("Pausa Longa");
                break;
        }
        updateFormattedTime();
    }

    private void updateFormattedTime() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        formattedTime.set(String.format("%02d:%02d", minutes, seconds));
    }

    // Getters para as propriedades que a UI vai observar
    public StringProperty formattedTimeProperty() { return formattedTime; }
    public StringProperty statusTextProperty() { return statusText; }
    public BooleanProperty sessionFinishedProperty() { return sessionFinished; }
    
    //  Permite que o Controller saiba qual era o estado quando a sessão terminou
    public TimerState getCurrentState() {
        return currentState;
    }
}
