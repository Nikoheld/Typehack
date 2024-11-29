package com.nikoheld;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {

        //Starten und Anmelden
        System.setProperty("webdriver.chrome.driver","src/main/chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.get("https://at4.typewriter.at/");
        WebElement inputfield = driver.findElement(By.id("LoginForm_username"));
        inputfield.sendKeys("Test");
    }
}