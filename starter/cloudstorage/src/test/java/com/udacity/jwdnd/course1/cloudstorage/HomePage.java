package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "userTable")
    private WebElement notesTable;
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;
    @FindBy(id = "add-note")
    private WebElement addNoteButton;
   @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
   private WebElement noteDescription;
    @FindBy(id = "submit")
    private WebElement submitNoteButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;
    @FindBy(id = "add-credential")
    private WebElement addCredentialButton;
    @FindBy(id = "credential-url")
    private WebElement credentialURL;
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;
    @FindBy(id = "credentialPassword")
    private WebElement CredentialPasswordFromTable;
    @FindBy(id = "submit-credential")
    private WebElement submitCredentialButton;

    private WebDriverWait webDriverWait;
    private WebDriver webDriver;


    public HomePage(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void createNote(String noteTitle, String noteDescription)  {
        this.noteTab.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-note")));
        this.addNoteButton.click();
        this.webDriverWait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteTitle.sendKeys(noteTitle);;
        this.noteDescription.sendKeys(noteDescription);

        this.submitNoteButton.click();
    }

    public void editNote(String noteTitle, String noteDescription) {
        this.noteTab.click();
        WebElement editButton = this.webDriver.findElement(By.xpath("//table[@id='userTable']/tbody/tr/td[1]/button"));
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
        editButton.click();
        this.webDriverWait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);

        this.submitNoteButton.click();
    }

    public void deleteNote() {
        this.noteTab.click();
        WebElement deleteButton = this.webDriver.findElement(By.xpath("//table[@id='userTable']/tbody/tr/td[1]/a"));
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        deleteButton.click();
    }

    public void createCredential(String url, String username, String password) {
        this.credentialTab.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential")));
        this.addCredentialButton.click();
        this.webDriverWait.until(ExpectedConditions.visibilityOf(this.credentialURL));
        this.credentialURL.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);

        this.submitCredentialButton.click();
    }

    public String getFirstCredentialPassword() {
        return  CredentialPasswordFromTable.getText();
    }

    public void editCredential(String url, String username, String password) {
        this.credentialTab.click();
        WebElement editButton = this.webDriver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr/td[1]/button"));
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
        editButton.click();

        this.webDriverWait.until(ExpectedConditions.visibilityOf(this.credentialURL));
        credentialURL.sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);

        this.submitCredentialButton.click();
    }

    public void deleteCredential() {
        this.credentialTab.click();
        WebElement deleteButton = this.webDriver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr/td[1]/a"));
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        deleteButton.click();
    }
}