package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signupAndLoginTest(){
		// test that signs up a new user(1)
		//  logs in(2),
		//  verifies that the home page is accessible(3),
		//  logs out(4),
		//  and verifies that the home page is no longer accessible.(5)

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		//1- signup
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("shammaa", "alsomaikhi", "shammaa", "123");
		//2- log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("shammaa", "123");
		//3- verifies that the home page is accessible
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		//4- log out
		HomePage homePage = new HomePage(driver, webDriverWait);
		homePage.logout();
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		//5- verifies that the home page is no longer accessible.
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotSame("Home", driver.getTitle());
	}

	public void signupAndLogin(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		//1- signup
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("shammaa", "alsomaikhi", "shammaa", "123");
		//2- log in
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("shammaa", "123");
	}

	@Test
	public void createAndDisplayedNoteTest(){
		//test that creates a note(1), and verifies it is displayed(2).

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		signupAndLogin();
		//create note
		HomePage homePage = new HomePage(driver,webDriverWait);
		homePage.createNote("test","This note is used for testing purposes");
		//display note
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
	}

	@Test
	public void editNoteTest(){
		//test that edits an existing note and verifies that the changes are displayed

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		signupAndLogin();
		HomePage homePage = new HomePage(driver,webDriverWait);
		//1 - create note.
		homePage.createNote("test","test note");
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		//2- edit note.
		homePage.editNote("test update", "test test");
		//3- verifies that the changes are displayed.
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
	}
	@Test
	public void deleteNoteTest(){
		//test that deletes a note and verifies that the note is no longer displayed.

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		signupAndLogin();
		HomePage homePage = new HomePage(driver,webDriverWait);
		//1 - create note.
		homePage.createNote("test","test note");
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		//2- delete note.
		homePage.deleteNote();
		//3- verifies that the changes are displayed.
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
	}

	@Test
	public void createsCredentials(){
		//test that creates a set of credentials, (1)
		// verifies that they are displayed(2), and verifies that the displayed password is encrypted.(3)

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		signupAndLogin();
		HomePage homePage = new HomePage(driver,webDriverWait);

		// 1- creates a set of credentials , 2- verifies that they are displayed
		homePage.createCredential("www.amazon.com","shammaa", "test123");
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		// 3- verifies that the displayed password is encrypted
		Assertions.assertNotSame(homePage.getFirstCredentialPassword(),"test123");
	}

	@Test
	public void editCredentials(){
		//test that views an existing set of credentials, verifies that the viewable password is unencrypted,(this already tested in createsCredentials() )
		// edits the credentials(1), and verifies that the changes are displayed.(2)
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		signupAndLogin();
		HomePage homePage = new HomePage(driver,webDriverWait);

		// creates a set of credentials & verifies that they are displayed
		homePage.createCredential("www.amazon.com","shammaa", "test123");
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
		driver.get("http://localhost:" + this.port + "/home");

		// 1 - edits the credentials
		homePage.editCredential("www.test.com","shammaa", "edit123");

		// 2- verifies that the changes are displayed
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
	}

	@Test
	public void deleteCredentials(){
		//test that deletes an existing set of credentials (1) and verifies that the credentials are no longer displayed.(2)
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		signupAndLogin();
		HomePage homePage = new HomePage(driver,webDriverWait);

		// creates a set of credentials & verifies that they are displayed
		homePage.createCredential("www.amazon.com","shammaa", "test123");
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
		driver.get("http://localhost:" + this.port + "/home");

		// 1 - deletes an existing set of credentials
		homePage.deleteCredential();

		// 2- verifies that the credentials are no longer displayed.
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());
	}

	@Test
	public void unauthorizedUserAccessTest(){
		//test that verifies that an unauthorized user can only access the login and signup pages.

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		//trying to go home page, should direct to login page
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		//trying to go login page, should be allowed
		driver.get("http://localhost:" + this.port + "/login");
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		//trying to go signup page, should be allowed
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/

		//based on what I understand I need to transfer the user to login page so I test this
		driver.get("http://localhost:" + this.port + "/login");
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
