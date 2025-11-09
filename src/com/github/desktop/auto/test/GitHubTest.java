package com.github.desktop.auto.test;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class GitHubTest {
	WebDriver driver = null;

	@BeforeTest
	public void openURL() {
		System.setProperty(Constants.DRIVER, Constants.PATH);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(Constants.URL);

		
	}

	/*
	 * get total open issues on all repositories
	 */
	@Test(priority = 0)
	public void getTotalOpenIssues() {
		int totalIssues = 0;
		WebElement allRepositories = driver.findElement(By.xpath("//div[@id='org-repositories']//ul"));
		List<WebElement> issuesList = allRepositories.findElements(By.cssSelector("a[href*='/issues']"));

		for (WebElement issue : issuesList) {
			System.out.println("---- the number of open issues is ----" + issue.getText());
			totalIssues += Utils.getIssuesNumber(issue.getText());
		}
		System.out.println("===========[The Anwser for the first question]============= \n"
				+ "The total open issues on all repositories is : " + totalIssues);
	}

	/*
	 * find out the repository which its watcher is max.
	 */

	@Test(priority = 1)
	public void getRepoHasMaxWatchers() throws InterruptedException {
		String maxWatcherReposity = Utils.getKeyHasMaxValue(getNumberWatchersOfEachRepos());
		System.out.println("===========[The Anwser for the third question]============= \n"
				+ "The repository that has the most watchers is " + maxWatcherReposity
				+ " with the number of watchers is " + Utils.maxWatchers);

	}

	/*
	 * get the number of watchers of each repository
	 */
	public HashMap<String, Float> getNumberWatchersOfEachRepos() throws InterruptedException {
		HashMap<String, Float> hmap = new HashMap<String, Float>();
		String[] links, repoNames = null;
		WebElement allRepositories = driver.findElement(By.xpath("//div[@id='org-repositories']//ul"));
		List<WebElement> linkReposList = allRepositories.findElements(By.xpath("//li//a[@class='d-inline-block']"));

		int linksCount = linkReposList.size();
		System.out.println("Total number of links Available: " + linksCount);
		links = new String[linksCount];
		repoNames = new String[linksCount];

		for (int i = 0; i < linksCount; i++) {
			links[i] = linkReposList.get(i).getAttribute("href");
			repoNames[i] = linkReposList.get(i).getText();
		}
		// navigate to each Link on the web page
		for (int i = 0; i < linksCount; i++) {
			driver.navigate().to(links[i]);
			WebElement element = driver.findElement(By.cssSelector("a[href*='/watcher']"));
			float watchersNumber = Utils.gettWatchersNumber(element.getText());
			hmap.put(repoNames[i], watchersNumber);
			Thread.sleep(1000);
		}
		return hmap;
	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();

	}

}
