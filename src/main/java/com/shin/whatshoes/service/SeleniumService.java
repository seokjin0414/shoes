package com.shin.whatshoes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeleniumService {
    private WebDriver driver;
    final Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver");
    final String user_agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";

    public List<List<String>> getResale(Long shoesId, String shoesUrl) {
        System.setProperty("webdriver.chrome.driver", path.toString());
        // /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --remote-debugging-port=9222 --user-data-dir="~/ChromeProfile"
        log.info("@@@@@@ set options");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=" + user_agent);
        // 자동화 숨김
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--start-maximized");            // 전체화면으로 실행
        options.addArguments("--disable-popup-blocking");
//        options.addArguments("headless");
//        options.addArguments("--disable-infobars");
//        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--blink-settings=imagesEnabled=false");
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        log.info("@@@@@@ set driver");
        driver = new ChromeDriver(options);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(shoesUrl);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
//        try {
//            log.info("@@@@@@ set cookie");
//            driver.findElement(By.id("onetrust-accept-btn-handler")).click();
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            driver.findElement(By.tagName("body")).sendKeys(Keys.CANCEL);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("@@@@@@ not found cookie");
//        }
//      광고 방지
//      /html/body/div[4]/div/div/div/div[3]/div
//*[@id="px-captcha-wrapper"]/div/div[1]
//        try {
//            driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/p")).click();
//        }

        // select region, language, currency
        webDriverWait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"site-footer\"]/div/div[2]/div/div[1]/button"))
        );
        WebElement la = driver.findElement(By.xpath("//*[@id=\"site-footer\"]/div/div[2]/div/div[1]/button/span[3]"));
        WebElement cu = driver.findElement(By.xpath("//*[@id=\"site-footer\"]/div/div[2]/div/div[1]/button/span[5]"));
        if (!String.valueOf(la.getText()).equals("한국어") && !String.valueOf(cu.getText()).equals("$ USD")) {
            log.info("@@@@@@ set Region, Language, Currency");
            driver.findElement(By.xpath("//*[@id=\"site-footer\"]/div/div[2]/div/div[1]/button")).click();
            webDriverWait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[4]/div/div[4]/div/section/footer/div/button[2]"))
            );
            Select selectRegion = new Select(driver.findElement(By.id("region-select")));
            selectRegion.selectByValue("KR");
            Select selectLanguage = new Select(driver.findElement(By.id("language-select")));
            selectLanguage.selectByValue("ko");
            Select selectCurrency = new Select(driver.findElement(By.id("currency-select")));
            selectCurrency.selectByValue("USD");
            driver.findElement(By.xpath("/html/body/div[4]/div/div[4]/div/section/footer/div/button[2]")).click();
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
            webDriverWait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]/div/section[6]/div/div/div/div/div[1]/div[2]/button"))
            );
        }
        log.info("@@@@@@ find & get resale history");
        webDriverWait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"main-content\"]/div/section[6]/div/div/div/div/div[1]/div[2]/button"))
        );
        WebElement bu = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div/section[6]/div/div/div/div/div[1]/div[2]/button"));
        bu.sendKeys(Keys.ENTER);
        webDriverWait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("tbody"))
        );
        log.info("@@@@@@ get p value");
        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> tr = tbody.findElements(By.tagName("p"));
        log.info("@@@@@@ get resale list");
        List<List<String>> re = new ArrayList<List<String>>();
        for (int i = 0; i < tr.size(); i += 5) {
            List<String> a = new ArrayList<>();
            a.add(String.valueOf(tr.get(i).getText()));
            a.add(String.valueOf(tr.get(i + 1).getText()));
            a.add(String.valueOf(tr.get(i + 2).getText()));
            a.add(String.valueOf(tr.get(i + 3).getText()));
            re.add(a);
        }
        log.info("success get resale list");
        for (List<String> e : re) {
            System.out.println(e);
        }
        log.info("save history");
        log.info("@@@@@@@@@@@  success  @@@@@@@@@");
        // shoes 검색
//        driver.findElement(By.name("q")).sendKeys(shoesStyle);
//        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
//        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return re;
    }

}
