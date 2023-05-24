package com.ddf.testexcecution;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ddf.readexcel.ReadExcelFile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestExcecution {
	ChromeDriver driver;

	@Test(dataProvider = "testdata")
	public void DemoProject(String username, String password) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.get("https://www.facebook.com/");

		driver.findElement(By.name("email")).sendKeys(username);
		driver.findElement(By.name("pass")).sendKeys(password);
		driver.findElement(By.name("login")).click();

		Thread.sleep(5000);
	}

	@AfterMethod
	void ProgramTermination() {
		driver.quit();
	}

	@DataProvider(name = "testdata")
	public Object[][] TestDataFeed() {

		ReadExcelFile config = new ReadExcelFile("D:\\Java Questions\\DataDrivenFramework_POI\\TestData.xlsx");

		int rows = config.getRowCount(0);

		Object[][] credentials = new Object[rows][2];

		for (int i = 0; i < rows; i++) {
			credentials[i][0] = config.getData(0, i, 0);
			credentials[i][1] = config.getData(0, i, 1);
		}

		return credentials;
	}
}