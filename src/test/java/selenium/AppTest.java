package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;
import org.junit.Before;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    
    private WebDriver driver;

    @Before
    public void setUp(){
        System.out.println("Iniciando configuración...");
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
        //driver.navigate().to("https://www.google.com");
    }

    @Test
    public void googleSearch()
    {
        System.out.println("Iniciando Pruebas...");
        WebElement searchbox = driver.findElement(By.name("q"));
        searchbox.sendKeys("HandBook Devops");
        searchbox.submit();
        //assertEquals("HandBook Devops", driver.getTitle());
        
        //driver.findElement(By.xpath("//*[@id='hdtb-msb']/div[1]/div/div[2]/a")).click();
        WebElement imageButton = driver.findElement(By.xpath("//a[contains(@href,'tbm=isch')]"));
        imageButton.click();
        
        
        WebElement shoppingButton = driver.findElement(By.xpath("//a[contains(@href,'tbm=shop')]"));
        shoppingButton.click();
        assertTrue(driver.getCurrentUrl().contains("tbm=shop"));
        
    }

    @Test
    public void amazonSearch()
    {
        driver.navigate().to("https://www.amazon.com");
        WebElement searchbox = driver.findElement(By.id("twotabsearchtextbox"));
        searchbox.sendKeys("The Phoenix Project");
        searchbox.submit();
        assertEquals("Amazon.com : The Phoenix Project", driver.getTitle());
    }
}
