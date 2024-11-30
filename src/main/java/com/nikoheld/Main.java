package com.nikoheld;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

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

            // Start
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[5]/div[2]/div[3]/div[2]/div[2]/span[1]")));

            // Tastatur
            Robot robot = new Robot();
            robot.delay(10);
            boolean elementFound = false;

            while (!elementFound) {
                try {
                    // Suche nach dem Element mit der Klasse "view gradientBox"
                    WebElement element = driver.findElement(By.xpath("//*[contains(@class, 'view') and contains(@class, 'gradientBox')]"));
                    elementFound = true;
                    System.out.println("Element gefunden: " + element.getText());
                } catch (Exception e) {
                    // Buchstaben aus dem XPath "/html/body/div[5]/div[2]/div[3]/div[2]/div[2]/span[1]" auslesen und schreiben
                    try {
                        WebElement charElement = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[3]/div[2]/div[2]/span[1]"));
                        String character = charElement.getText();

                        // Zeichen verarbeiten und schreiben
                        for (char c : character.toCharArray()) {
                            writeCharacter(robot, c);
                        }
                    } catch (Exception innerEx) {
                        System.err.println("Fehler beim Abrufen des Zeichens: " + innerEx.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der JSON-Datei: " + e.getMessage());
        } catch (AWTException e) {
            System.err.println("Fehler beim Initialisieren des Robot: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    private static void writeCharacter(Robot robot, char c) {
        try {
            // Großbuchstaben mit Shift
            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(Character.toLowerCase(c)));
                robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(Character.toLowerCase(c)));
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            // Standardzeichen schreiben
            else {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                if (KeyEvent.CHAR_UNDEFINED != keyCode) {
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Schreiben des Zeichens: " + c);
        }
    }
}
