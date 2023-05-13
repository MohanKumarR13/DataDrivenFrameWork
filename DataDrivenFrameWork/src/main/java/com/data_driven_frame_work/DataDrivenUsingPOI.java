package com.data_driven_frame_work;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DataDrivenUsingPOI {
	static WebDriver driver;
	static List<String> userNameList = new ArrayList<String>();
	static List<String> passwordList = new ArrayList<String>();

	public static void readExcel() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(".\\Login.xlsx");
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row rowValue = rowIterator.next();
			Iterator<Cell> columnIterator = rowValue.iterator();
			int i = 2;
			while (columnIterator.hasNext()) {
				if (i % 2 == 0) {
					userNameList.add(columnIterator.next().getStringCellValue());
				} else {
					passwordList.add(columnIterator.next().getStringCellValue());
				}
				i++;
			}

		}
	}

	public static void login(String userName, String passowrd) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		// Open the browser and go to url
		driver.get("https://www.facebook.com/login/");
		// Set the current url as a string
		String url = driver.getCurrentUrl();
		// Verify that current url
		Assert.assertTrue(url.contains("facebook"));
		WebElement userNames = driver.findElement(By.name("email"));
		userNames.sendKeys(userName);
		WebElement passwords = driver.findElement(By.name("pass"));
		passwords.sendKeys(passowrd);
		WebElement loginBtn = driver.findElement(By.name("login"));
		loginBtn.click();
		driver.quit();
	}

	public static void excecuteTest() {
		for (int i = 0; i < userNameList.size(); i++) {
			login(userNameList.get(i), passwordList.get(i));
		}
	}

	public static void main(String[] args) throws IOException {
		readExcel();
		System.out.println("User Name List " + userNameList);
		System.out.println("Password List " + passwordList);
		excecuteTest();

	}
}
