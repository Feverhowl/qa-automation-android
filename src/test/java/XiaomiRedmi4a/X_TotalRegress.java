package XiaomiRedmi4a;

import io.qameta.allure.junit4.DisplayName;
import main.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class X_TotalRegress {

    // Имя устройства: 2fcaae347d74
    // Версия Android: 7.1.2
    // Appium Server Port: 4723
    // Appium Bootstrap Port: 4733

    private String deviceName = "2fcaae347d74";

    @Before
    public void setUp() {
        Main.setUpAndroid(deviceName, "7.1.2", "4723");
    }

    @Test
    @DisplayName(Test001.NAME)
    public void Test001() {
        Test001.test001(deviceName);
    }

    @Test
    @DisplayName(Test002.NAME)
    public void Test002() {
        Test002.test002(deviceName);
    }

    @Test
    @DisplayName(Test003.NAME)
    public void Test003() {
        Test003.test003(deviceName);
    }

    @Test
    @DisplayName(Test004.NAME)
    public void Test004() {
        Test004.test004(deviceName);
    }

    @Test
    @DisplayName(Test005.NAME)
    public void Test005() {
        Test005.test005(deviceName);
    }

    @Test
    @DisplayName(Test006.NAME)
    public void Test006() {
        Test006.test006(deviceName);
    }

    @Test
    @DisplayName(Test007.NAME)
    public void Test007() {
        Test007.test007(deviceName);
    }

    @Test
    @DisplayName(Test008.NAME)
    public void Test008() {
        Test008.test008(deviceName);
    }

    @Test
    @DisplayName(Test009.NAME)
    public void Test009() {
        Test009.test009(deviceName);
    }
/*
    @Test
    @DisplayName(Test010.NAME)
    public void Test010() {
        Test010.test010(deviceName);
    }
*/



    @After
    public void tearDown() {
        byte[] screenshot = Main.saveAllureScreenshot();
        System.out.println(screenshot.length);
        Main.driver.quit();
    }
}
