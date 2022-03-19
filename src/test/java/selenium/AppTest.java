package selenium;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.utility.RandomString;

/**
 * Unit test for simple App.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

	private static final String URL = "http://automationpractice.com/index.php";
	private WebDriver driver;
	String randomEmail;

	@Before
	public void setUp() {
		System.out.println("Iniciando configuración...");
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get(URL);
		driver.manage().window().maximize();
		randomEmail = String.format("%s@mail.com", RandomString.make(15)).toLowerCase();
		System.out.println("Random email : " + randomEmail);
	}

	@Test
	public void test_01_buy_product_creating_a_new_user() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		WebElement productContainer = driver.findElement(By.cssSelector(".ajax_block_product"));
		Actions actions = new Actions(driver);
		actions.moveToElement(productContainer).perform();
		WebElement btnAddToCart = productContainer.findElement(By.xpath(".//span[text()='Add to cart']"));
		btnAddToCart.click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Proceed to checkout']")))
				.click();
		driver.findElement(By.xpath("//a[contains(@href, 'step=1')]")).click();
		driver.findElement(By.id("email_create")).sendKeys(randomEmail);
		driver.findElement(By.id("SubmitCreate")).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("id_gender1"))).click();
		driver.findElement(By.id("customer_firstname")).sendKeys("Alejandro");
		driver.findElement(By.id("customer_lastname")).sendKeys("Arévalo");
		String email = driver.findElement(By.id("email")).getAttribute("value");
		assertEquals(randomEmail, email);
		driver.findElement(By.id("passwd")).sendKeys("password");
		new Select(driver.findElement(By.id("days"))).selectByValue("22");
		new Select(driver.findElement(By.id("months"))).selectByValue("5");
		new Select(driver.findElement(By.id("years"))).selectByValue("1980");
		driver.findElement(By.id("firstname")).clear();
		driver.findElement(By.id("firstname")).sendKeys("Alejandro");
		driver.findElement(By.id("lastname")).clear();
		driver.findElement(By.id("lastname")).sendKeys("Arévalo");
		driver.findElement(By.id("company")).sendKeys("none");
		driver.findElement(By.id("address1")).sendKeys("Alameda 123");
		driver.findElement(By.id("address2")).sendKeys("Depto 321");
		driver.findElement(By.id("city")).sendKeys("Santiago");
		Select cboState = new Select(driver.findElement(By.id("id_state")));
		cboState.selectByValue("1");
		driver.findElement(By.id("postcode")).sendKeys("88888");
		driver.findElement(By.id("other")).sendKeys("Additional information");
		driver.findElement(By.id("phone")).sendKeys("222222222");
		driver.findElement(By.id("phone_mobile")).sendKeys("999999999");
		WebElement txtAlias = driver.findElement(By.id("alias"));
		txtAlias.clear();
		txtAlias.sendKeys("Mi dirección");
		driver.findElement(By.id("submitAccount")).click();
		String name = driver.findElement(By.xpath("//li[@class='address_firstname address_lastname']")).getText();
		String company = driver.findElement(By.xpath("//li[@class='address_company']")).getText();
		String address = driver.findElement(By.xpath("//li[@class='address_address1 address_address2']")).getText();
		String address2 = driver
				.findElement(By.xpath("//li[@class='address_city address_state_name address_postcode']")).getText();
		String country = driver.findElement(By.xpath("//li[@class='address_country_name']")).getText();
		String phone = driver.findElement(By.xpath("//li[@class='address_phone']")).getText();
		String phone_mobile = driver.findElement(By.xpath("//li[@class='address_phone_mobile']")).getText();
		assertEquals("Alejandro Arévalo", name);
		assertEquals("none", company);
		assertEquals("Alameda 123 Depto 321", address);
		assertEquals("Santiago, Alabama 88888", address2);
		assertEquals("United States", country);
		assertEquals("222222222", phone);
		assertEquals("999999999", phone_mobile);
		driver.findElement(By.name("processAddress")).click();
		driver.findElement(By.id("cgv")).click();
		driver.findElement(By.name("processCarrier")).click();
		driver.findElement(By.cssSelector(".bankwire")).click();
		driver.findElement(By.xpath("//span[contains(text(), 'I confirm my order')]")).click();
	}

	@Test
	public void test_02_buy_product_with_existing_user() {
		WebElement productContainer = driver.findElement(By.cssSelector(".ajax_block_product"));
		Actions actions = new Actions(driver);
		actions.moveToElement(productContainer).perform();
		WebElement btnAddToCart = productContainer.findElement(By.xpath(".//span[text()='Add to cart']"));
		btnAddToCart.click();
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Proceed to checkout']"))).click();
		driver.findElement(By.xpath("//a[contains(@href, 'step=1')]")).click();
		driver.findElement(By.id("email")).sendKeys(randomEmail.toLowerCase());
		driver.findElement(By.id("passwd")).sendKeys("password");
		driver.findElement(By.id("SubmitLogin")).click();
		driver.findElement(By.name("processAddress")).click();
		driver.findElement(By.id("cgv")).click();
		driver.findElement(By.name("processCarrier")).click();
		driver.findElement(By.cssSelector(".bankwire")).click();
		driver.findElement(By.xpath("//span[contains(text(), 'I confirm my order')]")).click();
	}

	@After
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}
}
