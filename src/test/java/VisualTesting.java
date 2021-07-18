
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class VisualTesting {

    public static WebDriver driver;

    @Test
    public void visualTestingGoogleHomePage() throws Exception {
        //Initiate and launch the driver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //launch the browser and webpage
        driver.manage().window().maximize();
        driver.get("https://www.google.co.uk/");
        driver.findElement(By.id("L2AGLb")).click();

        // Move the cursor to (0,0) point using Robot class
        Robot robot = new Robot();
        robot.mouseMove(0,0);

        // Take screenshot of the page
        Screenshot takesScreenshot = new AShot().takeScreenshot(driver);
        File actualScreenshot =new File("src/main/resources/actual_images/"+"google_home_base.png");
        ImageIO.write(takesScreenshot.getImage(),"png",actualScreenshot);

        //Compare Screenshot now
        Screenshot expectedScreenshot = new Screenshot(ImageIO.read(new File("src/main/resources/expected_images/" + "google_home_base.png")));
        ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, takesScreenshot);
        int size = diff.getDiffSize();

        //Compare the file size
        if(size!=0){
            File diffFile = new File("src/main/resources/compared_images/" + "google_home_diff.png");
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
            throw new Exception("There are some bugs on the page");
        }

        //close the driver
        driver.quit();

    }
}
