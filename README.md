# JPomo - Aplicação Pomodoro em JavaFX

Um aplicativo de desktop simples e funcional para gerenciamento de tempo usando a Técnica Pomodoro, construído com Java e JavaFX.

## Visão Geral

JPomo ajuda você a manter o foco e a produtividade, dividindo o trabalho em intervalos de tempo focados, tradicionalmente de 25 minutos, separados por pequenas pausas.

## Funcionalidades

*   **Ciclo Pomodoro Completo:** Suporte para sessões de Foco, Pausa Curta e Pausa Longa.
*   **Temporizador Interativo:** Controles para Iniciar, Pausar, Continuar e Resetar o cronômetro.
*   **Configurações Personalizáveis:** Ajuste facilmente a duração das sessões de foco e das pausas. As configurações são salvas e carregadas automaticamente.
*   **Notificações Sonoras:** Um alerta sonoro notifica o final de cada sessão.
*   **Rastreamento de Sessões:** Acompanhe seu progresso com um histórico de sessões de foco concluídas.
*   **Visualização de Estatísticas:** Uma janela dedicada exibe suas estatísticas de produtividade.

## Estrutura do Projeto

O projeto segue uma estrutura baseada no padrão MVC para organizar o código:

*   `src/main/java/com/moura/`
    *   `App.java`: Classe principal que inicia a aplicação JavaFX.
    *   `controller/`: Controladores que gerenciam a interação entre a view e o model.
        *   `PomodoroController.java`: Lógica da tela principal.
        *   `StatsController.java`: Lógica da tela de estatísticas.
    *   `model/`: Classes que representam os dados da aplicação.
        *   `Settings.java`: Armazena as configurações do temporizador.
        *   `FocusSession.java`: Representa uma sessão de foco concluída.
        *   `TimerState.java`: Enum para os estados do temporizador (Foco, Pausa, etc.).
    *   `service/`: Classes de serviço que encapsulam a lógica de negócio.
        *   `TimerService.java`: Gerencia toda a lógica do cronômetro.
        *   `SettingsService.java`: Lida com o salvamento e carregamento das configurações.
        *   `TrackingService.java`: Gerencia o histórico de sessões.
        *   `NotificationService.java`: Responsável por emitir notificações.
*   `src/main/resources/com/moura/jpomo/view/`
    *   `PomodoroView.fxml`: Arquivo FXML para a interface principal.
    *   `StatsView.fxml`: Arquivo FXML para a tela de estatísticas.
    *   `styles.css`: Folha de estilo para a interface.
    *   `alert.mp3`: Som de notificação.

## Tecnologias Utilizadas

*   **Java 11+**
*   **JavaFX 17+**
*   **Maven**

## Como Executar o Projeto

### Pré-requisitos

*   JDK 11 ou superior instalado.
*   Apache Maven instalado.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone <url-do-seu-repositorio>
    cd jpomo
    ```

2.  **Compile o projeto com o Maven:**
    ```bash
    mvn clean install
    ```

3.  **Execute a aplicação:**
    ```bash
    mvn javafx:run
    ```

---
*Este README foi gerado pelo
