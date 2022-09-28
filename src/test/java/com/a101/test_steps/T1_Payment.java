package com.a101.test_steps;

import com.github.javafaker.Faker;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class T1_Payment {

    AppiumDriver<MobileElement> driver;
    Faker faker = new Faker();

    @BeforeEach
    public void setUp() throws MalformedURLException {

        //************ 1 - User taps to A101 application
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        desiredCapabilities.setCapability(MobileCapabilityType.VERSION, "10.0");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Google_Pixel_3");
        desiredCapabilities.setCapability("appPackage", "org.studionord.a101");
        desiredCapabilities.setCapability("appActivity", "org.studionord.a101.MainActivity");
        driver = new AppiumDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }

    @Test
    public void paymentScreenVerification() throws InterruptedException {

        //************ 2 - User clicks to "YUKLE" for updating
        driver.findElement(MobileBy.id("android:id/button1")).click();

        //************ 3 - User taps hamburger button
        try {
            List<MobileElement> hamburgerButton = driver.findElements(MobileBy.className("android.widget.TextView"));
            hamburgerButton.get(0).click();
        } catch (Exception e) {
            List<MobileElement> mainPicture = driver.findElements(MobileBy.className("android.widget.ImageView"));
            mainPicture.get(0).click();
            driver.findElement(MobileBy.className("android.widget.TextView")).click();

        }

        //************ 4 - User taps <GİYİM & AKSESUAR> category from opened submenu
        driver.findElement(MobileBy.xpath("//*[@text='GİYİM & AKSESUAR']")).click();

        //************ 5 - User taps "Kadın İç Giyim"
        driver.findElement(MobileBy.xpath("//*[@text='Kadın İç Giyim']")).click();

        //************ 6 - User taps "Dizaltı Çorap"
        driver.findElement(MobileBy.xpath("//*[@text='Dizaltı Çorap']")).click();

        Thread.sleep(1000);
        //************ 7 - User taps first product
        List<MobileElement> images = driver.findElements(MobileBy.className("android.widget.ImageView"));
        images.get(0).click();

        //************ 8 - android.widget.TextView 11.
        Thread.sleep(1000);

        //************ 9 - User taps <Sepete Ekle> button
        driver.findElement(MobileBy.xpath("//*[@text='SEPETE EKLE']")).click();
        Thread.sleep(1000);

        //************ 10 - User taps <Sepeti Git> button
        driver.findElement(MobileBy.xpath("//*[@text='SEPETE GİT']")).click();

        //************ 11 - Verify that user can see the product in the basket and color is black
        Thread.sleep(1000);
        Assertions.assertTrue(driver.findElement(MobileBy.xpath("//*[@text='Penti Kadın 50 Denye Pantolon Çorabı Siyah']")).isDisplayed());
        Assertions.assertEquals("SİYAH", driver.findElement(MobileBy.xpath("//*[@text='SİYAH']")).getAttribute("text"));

        //************ 12 - User taps <Sepeti Onayla> button
        driver.findElement(MobileBy.xpath("//*[@text='SEPETİ ONAYLA']")).click();

        //************ 13 - User taps <ÜYE OLMADAN DEVAM ET> button
        driver.findElement(MobileBy.xpath("//*[@text='ÜYE OLMADAN DEVAM ET']")).click();

        //************ 14 - User enters email address
        String emailAddress = faker.internet().emailAddress();
        driver.findElement(MobileBy.xpath("//*[@text='E-posta Adresiniz']")).sendKeys(emailAddress);

        //************ 15 - user select check box
        List<MobileElement> checkBox = driver.findElements(MobileBy.className("android.view.ViewGroup"));
        checkBox.get(3).click();

        //************ 16 - User taps <GÖNDER> button
        Thread.sleep(1000);
        driver.findElement(MobileBy.xpath("//*[@text='GÖNDER']")).click();

        //************ 17 - User accepts the cookies
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(MobileBy.xpath("//*[@text='Kabul Et']"))));
        } catch (Exception e) {
        }

        driver.findElement(MobileBy.xpath("//*[@text='Kabul Et']")).click();

        //************ 18 - User taps <Yeni adres oluştur>
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(MobileBy.className("android.view.View"))));
        } catch (Exception e) {
        }

        List<MobileElement> yeniAdresOlustur = driver.findElements(MobileBy.className("android.view.View"));
        for (MobileElement mobileElement : yeniAdresOlustur) {
            if (mobileElement.getAttribute("text").contains("Yeni adres oluştur")) {
                mobileElement.click();
            }
        }
        Thread.sleep(1000);

        //************ 19 - User enters required information
        List<MobileElement> informationBoxes = driver.findElements(MobileBy.className("android.widget.EditText"));
        informationBoxes.get(0).sendKeys("Home");
        informationBoxes.get(1).sendKeys(faker.name().firstName());
        informationBoxes.get(2).sendKeys(faker.name().lastName());
        informationBoxes.get(3).sendKeys("3126477178");
        driver.findElement(MobileBy.xpath("//*[@text='Seçiniz']")).click();
        driver.findElement(MobileBy.xpath("//*[@text='İSTANBUL']")).click();
        driver.findElement(MobileBy.xpath("//*[@text='Seçiniz']")).click();
        driver.findElement(MobileBy.xpath("//*[@text='ADALAR']")).click();
        driver.findElement(MobileBy.xpath("//*[@text='Seçiniz']")).click();
        driver.findElement(MobileBy.xpath("//*[@text='BURGAZADA MAH']")).click();

        String fulLAddres = faker.address().fullAddress();
        informationBoxes.get(4).sendKeys(" ");
        informationBoxes.get(4).sendKeys("397 Hettinger Prairie Alexandria Connecticut");

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(945, 900))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(600)))
                .moveTo(PointOption.point(945, 440))
                .release()
                .perform();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(MobileBy.xpath("//*[@text='KAYDET']"))));
        List<MobileElement> kaydetButton = driver.findElements(MobileBy.className("android.widget.Button"));
        kaydetButton.get(1).click();

        //************ 20 - User selects any radio button
        Thread.sleep(2000);
        List<MobileElement> radioButton = driver.findElements(MobileBy.className("android.widget.RadioButton"));
        radioButton.get(radioButton.size() - 1).click();

        //************ 21 - User taps <Kaydet ve Devam Et> button
        driver.findElement(MobileBy.xpath("//*[@text='Kaydet ve Devam Et']")).click();

        //************ 22 - User enters payment information and selects checkbox
        Thread.sleep(1000);
        List<MobileElement> paymentBoxes = driver.findElements(MobileBy.className("android.widget.EditText"));
        paymentBoxes.get(0).sendKeys("isbankasi");
        paymentBoxes.get(1).click();
        Thread.sleep(1000);
        String cardNumber = "9792042022562362";
        String[] card = cardNumber.split("");
        TouchAction touchAction = new TouchAction<>(driver);
        for (String eachNumber : card) {
            switch (eachNumber) {
                case "0":

                    touchAction.tap(PointOption.point(570, 2750)).perform();
                    break;
                case "1":

                    touchAction.tap(PointOption.point(180, 2090)).perform();
                    break;
                case "2":

                    touchAction.tap(PointOption.point(570, 2090)).perform();
                    break;
                case "3":

                    touchAction.tap(PointOption.point(940, 2090)).perform();
                    break;
                case "4":

                    touchAction.tap(PointOption.point(180, 2300)).perform();
                    break;
                case "5":

                    touchAction.tap(PointOption.point(570, 2300)).perform();
                    break;
                case "6":

                    touchAction.tap(PointOption.point(940, 2300)).perform();
                    break;
                case "7":

                    touchAction.tap(PointOption.point(180, 2500)).perform();
                    break;
                case "8":

                    touchAction.tap(PointOption.point(570, 2500)).perform();
                    break;
                case "9":

                    touchAction.tap(PointOption.point(940, 2500)).perform();
                    break;
            }
        }
        Thread.sleep(1000);
        touchAction.tap(PointOption.point(1300, 2750)).perform();
        Thread.sleep(1000);
        List<MobileElement> spinners = driver.findElements(MobileBy.className("android.widget.Spinner"));
        spinners.get(0).click();
        driver.findElement(MobileBy.xpath("//*[@text='10']")).click();
        spinners.get(1).click();
        driver.findElement(MobileBy.xpath("//*[@text='2025']")).click();
        paymentBoxes.get(2).sendKeys("000");

        //************ 23 - User taps <Siparişi Tamamla> button
        driver.findElement(MobileBy.className("android.widget.CheckBox")).click();
        Thread.sleep(1000);
        driver.findElement(MobileBy.xpath("//*[@text='Siparişi Tamamla']")).click();

        //************ 24 - Verify that user navigated to page of bank
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.visibilityOf(driver.findElement(MobileBy.xpath("//*[@text='Özür dileriz, teknik nedenlerden dolayı şu anda işleminizi gerçekleştiremiyoruz.\n" +
                "Lütfen bir süre sonra tekrar deneyiniz.']"))));

        Assertions.assertTrue(driver.findElement(MobileBy.xpath("//*[@text='Özür dileriz, teknik nedenlerden dolayı şu anda işleminizi gerçekleştiremiyoruz.\n" +
                "Lütfen bir süre sonra tekrar deneyiniz.']")).isDisplayed());
    }

    @AfterEach
    public void cleanUp() {
        driver.closeApp();
    }

}
