package com.moura.service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

/**
 * Serviço responsável por lidar com todas as formas de notificação ao usuário.
 */
public class NotificationService {

    private MediaPlayer mediaPlayer;

    /**
     * Toca um som de notificação.
     * O arquivo de som deve estar na pasta de recursos.
     */
    public void playSoundNotification() {
        try {
           
            URL resource = getClass().getResource("/com/moura/jpomo/view/alert.mp3");

            if (resource == null) {
                System.err.println("Aviso: Arquivo de som de notificação não encontrado. Verifique o caminho.");
                return;
            }

            Media sound = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao tentar tocar o som de notificação.");
            e.printStackTrace();
        }
    }
}
