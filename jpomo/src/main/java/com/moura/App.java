package com.moura;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Classe principal que inicia a aplicação JavaFX.
 * Herda de Application, que é o ponto de entrada para qualquer app JavaFX.
 */
public class App extends Application {

    /**
     * O método start é o principal ponto de entrada para todas as aplicações JavaFX.
     * A plataforma JavaFX chama o método start depois de chamar o construtor e o método init().
     *
     * @param primaryStage O "palco" principal da aplicação, onde a cena é colocada.
     * A plataforma constrói o palco principal para nós.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Encontra e carrega o arquivo FXML que define a nossa interface.
            // O getResource procura o arquivo dentro da pasta 'resources'.
            // Usamos Objects.requireNonNull para garantir que o recurso não seja nulo,
            // o que nos dará uma mensagem de erro mais clara (NullPointerException) se o arquivo não for encontrado.
            URL fxmlUrl = getClass().getResource("/com/moura/jpomo/PomodoroView.fxml");
            Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlUrl));

            // 2. Cria uma "cena" com o conteúdo carregado do FXML.
            Scene scene = new Scene(root);

            // 3. (Opcional) Carrega a folha de estilos CSS.
            URL cssUrl = getClass().getResource("/com/moura/jpomo/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                 System.out.println("Aviso: Arquivo styles.css não encontrado. A aplicação funcionará sem estilização customizada.");
            }

            // 4. Configura o palco (a janela principal).
            primaryStage.setTitle("JPomo - Seu Timer Pomodoro");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Impede que o usuário redimensione a janela.
            primaryStage.show(); // Exibe a janela para o usuário.

        } catch (IOException e) {
            // Em caso de erro ao carregar o FXML, imprime o erro.
            System.err.println("Ocorreu um erro de I/O ao carregar o FXML.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            // Este erro será lançado se o getResource retornar null.
            System.err.println("Erro Crítico: Não foi possível encontrar o arquivo FXML. Verifique se o caminho '/com/moura/jpomo/PomodoroView.fxml' está correto e se a pasta 'resources' está configurada como uma pasta de recursos do Maven.");
            e.printStackTrace();
        }
    }

    /**
     * O método main é usado para lançar a aplicação.
     * Ele não é estritamente necessário em alguns cenários, mas é a forma padrão
     * de iniciar uma aplicação JavaFX a partir de um JAR executável.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
