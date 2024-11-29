package com.nikoheld;

import org.json.JSONObject; // Für JSON-Verarbeitung
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Passwort und E-Mail aus der JSON-Datei laden
            String filePath = "src/main/java/com/nikoheld/pass.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject json = new JSONObject(content);
            String password = json.getString("password");
            String email = json.getString("username");

            // WebDriver konfigurieren
            System.setProperty("webdriver.chrome.driver", "src/main/chromedriver.exe");
            WebDriver driver = new ChromeDriver();

            // Website öffnen
            driver.get("https://at4.typewriter.at/");

            // E-Mail eingeben
            WebElement emailField = driver.findElement(By.id("LoginForm_username"));
            emailField.sendKeys(email);

            // Passwort eingeben
            WebElement passField = driver.findElement(By.id("LoginForm_pw"));
            passField.sendKeys(password);

            // Login absenden
            WebElement submit = driver.findElement(By.name("yt0"));
            submit.click();

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der JSON-Datei: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
}
