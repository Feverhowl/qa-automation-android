package main;

public class Test010 extends Main {

    public static final String NAME = "Тест №010 - Проверка BLA BLA BLA";

    public static void test010(String deviceName) {

        // Работа над проблемой Активные заказы

        // Выбираем случайного клиента с активными заказами
        Client client = STsql.pickRandomClientWithActiveOrders();
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

        // Если у пользователя есть активные заказы - переводим их в DONE
        setAllActiveOrdersDone(client, deviceName);

        // Переходим назад к списку брендов
        clickBackButton();

        // Избавляемся от поп-апа 'Новая функция "Повторить заказ"'
        closePopupRepeatOrder(deviceName);

        // Кликаем на случайный бренд
        clickBrand(rnd(4, 7));

        // Кликаем на случайную категорию
        clickRandomCategory();

        // Кликаем на случайную подкатегорию
        clickRandomSubCategory();

        // Накидываем товары в корзину
        addGoodsOnCart(15);

        // Кликаем по значку Корзины
        clickCartButton();

        // Кликаем по кнопке Оформить заказ
        clickCheckoutButton();

        // Заполняем поля Подъезд, Этаж, Кв./Офис, Код домофона
        fillPorchFloorFlatIntercom("1", "2", "3", "123");

        // Кликаем по кнопке Далее на экране Доставка
        clickNextButtonOnDeliveryScreen();

        // Кликаем по кнопке Далее на экране Время доставки
        clickNextButtonOnDeliveryTimeScreen();

        // Вводим комментарий клиента
        fillCommentField("Yo, yo yo, 148-3 to the 3 to the 6 to the 9, representing the ABQ, what up biatch?!");

        // Добавляем тестовую карту оплаты, если она ещё не привязана
        addCard(deviceName);
    }
}
