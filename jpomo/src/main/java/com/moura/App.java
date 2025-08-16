package com.moura;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
            // 1. Encontra e carrega o arquivo FXML.
            // O caminho foi CORRIGIDO para incluir a pasta 'view'.
            URL fxmlUrl = App.class.getClassLoader().getResource("com/moura/jpomo/view/PomodoroView.fxml");
            Parent root = FXMLLoader.load(Objects.requireNonNull(fxmlUrl));

            // 2. Cria uma "cena" com o conteúdo carregado do FXML.
            Scene scene = new Scene(root);

            // 3. (Opcional) Carrega a folha de estilos CSS.
            // O caminho também foi corrigido aqui para consistência, assumindo que o CSS também estará na pasta 'view'.
            URL cssUrl = App.class.getClassLoader().getResource("com/moura/jpomo/view/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                 System.out.println("Aviso: Arquivo styles.css não encontrado. A aplicação funcionará sem estilização customizada.");
            }


             try {
                Image appIcon = new Image(Objects.requireNonNull(App.class.getClassLoader().getResourceAsStream("com/moura/jpomo/view/icon.png")));
                primaryStage.getIcons().add(appIcon);
            } catch (Exception e) {
                System.err.println("Aviso: Ficheiro icon.png não encontrado. A aplicação irá usar o ícone padrão.");
            }

            
            primaryStage.setTitle("JPomo - Seu Timer Pomodoro");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); 
            primaryStage.show(); 

        } catch (IOException e) {
            // Em caso de erro ao carregar o FXML, imprime o erro.
            System.err.println("Ocorreu um erro de I/O ao carregar o FXML.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            // Este erro será lançado se o getResource retornar null.
            System.err.println("Erro Crítico: Não foi possível encontrar o arquivo FXML ou CSS.");
            System.err.println("Verifique se os caminhos 'com/moura/jpomo/view/PomodoroView.fxml' e 'com/moura/jpomo/view/styles.css' existem dentro da pasta 'src/main/resources'.");
            System.err.println("Tente rodar 'mvn clean install' e depois 'mvn javafx:run' novamente.");
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
