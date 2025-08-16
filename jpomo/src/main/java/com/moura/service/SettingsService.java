package com.moura.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moura.model.Settings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Serviço responsável por salvar e carregar as configurações da aplicação.
 */
public class SettingsService {

    private final Gson gson;
    private final Path settingsPath;

    public SettingsService() {
        // Usa o GsonBuilder para criar um JSON "bonito" e formatado
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        
        // Define o caminho para o ficheiro de configurações de forma segura
        // Ex: C:\Utilizadores\SeuNome\.jpomo\settings.json
        String userHome = System.getProperty("user.home");
        this.settingsPath = Paths.get(userHome, ".jpomo", "settings.json");
    }

    /**
     * Carrega as configurações do ficheiro JSON.
     * Se o ficheiro não existir, retorna um objeto de configurações padrão.
     * @return O objeto Settings carregado ou um novo.
     */
    public Settings loadSettings() {
        // Verifica se o ficheiro existe antes de tentar lê-lo
        if (Files.exists(settingsPath)) {
            try (Reader reader = new FileReader(settingsPath.toFile())) {
                // Converte o JSON do ficheiro de volta para um objeto Settings
                Settings settings = gson.fromJson(reader, Settings.class);
                System.out.println("Configurações carregadas de: " + settingsPath);
                return settings;
            } catch (IOException e) {
                System.err.println("Erro ao ler o ficheiro de configurações.");
                e.printStackTrace();
            }
        }
        // Se o ficheiro não existe ou deu erro, retorna configurações padrão
        System.out.println("Nenhum ficheiro de configurações encontrado. Usando valores padrão.");
        return new Settings();
    }

    /**
     * Salva o objeto de configurações fornecido num ficheiro JSON.
     * @param settings O objeto Settings a ser salvo.
     */
    public void saveSettings(Settings settings) {
        try {
            // Garante que o diretório (.jpomo) existe antes de salvar
            Files.createDirectories(settingsPath.getParent());

            try (Writer writer = new FileWriter(settingsPath.toFile())) {
                // Converte o objeto Settings para JSON e escreve no ficheiro
                gson.toJson(settings, writer);
                System.out.println("Configurações salvas em: " + settingsPath);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o ficheiro de configurações.");
            e.printStackTrace();
        }
    }
}
