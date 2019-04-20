// Тест №001
// Проверка текущего количества сейвкоинов клиента

package main;

public class Test001 extends Main {

    public static final String NAME = "Тест №001 - Проверка текущего количества сейвкоинов клиента";

    public static void test001(String deviceName) {

        // Выбираем случайного клиента с сейвкоинами
        Client client = STsql.pickRandomClientWithSavecoins();
        Client.printAllInfo(client);

        // Ждем пока прогрузится приложение
        waitForAppIsLoaded(deviceName);

        // Кликаем на кнопку "ПРИСТУПИТЬ К ПОКУПКАМ"
        clickStartShoppingButton();

        // Кликаем по Бургер-меню
        clickBurgerMenu();

        // Вводим номер телефона
        enterPhone(deviceName, client);

        // Жмем кнопку "ПРОДОЛЖИТЬ"
        clickContinueButton();

        // Вводим код из смс
        enterSmsCode();

        // Переходим на экран Сейвкоины
        clickSavecoinButton();

        // Сверяем количество сейвкоинов клиента со значением в БД
        assertSavecoins(client);
    }
}