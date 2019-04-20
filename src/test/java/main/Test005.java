// Тест №005
// End to End тест - проверка корректного списания сейвкоинов.

package main;

import org.openqa.selenium.By;

public class Test005 extends Main {

    public static final String NAME = "Тест №005 - End to End - Проверка корректного списания сейвкоинов";

    public static void test005(String deviceName) {

        // Выбираем случайного клиента с привязанной тестовой картой 5100 0000 0000 0008
        Client client = STsql.pickRandomClientWithTestCard();

        // Если количество сейвкоинов выбранного клиента меньше (количество к списанию + 1), то прибавляем
        // ему в БД (количество к списанию + 5)
        int savecoinsToSubstract = 10; // Здесь указываем количество к списанию
        if (client.getSavecoin() < savecoinsToSubstract + 1) STsql.addSavecoinsToClient(client,
                savecoinsToSubstract + 5);
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

        // Кликаем на поле с адресом доставки
        clickDeliveryAddressField();

        // Кликаем по кнопке "Изменить адрес"
        clickChangeDeliveryAddressButton();

        // Смена адреса клиента на случайный
        chooseRandomAddress();

        // Кликаем на случайный бренд
        clickRandomBrand();

        // Кликаем на случайную категорию
        clickRandomCategory();

        // Кликаем на случайную подкатегорию
        clickRandomSubCategory();

        // Накидываем товары в корзину
        addGoodsOnCart(4);

        // Кликаем по значку Корзины
        clickCartButton();

        // Кликаем по кнопке Оформить заказ
        clickCheckoutButton();

        // Заполняем поля Подъезд, Этаж, Кв./Офис, Код домофона
        fillPorchFloorFlatIntercom("6", "7", "8", "876");

        // Кликаем по кнопке Далее на экране Доставка
        clickNextButtonOnDeliveryScreen();

        // Кликаем по кнопке Далее на экране Время доставки
        clickNextButtonOnDeliveryTimeScreen();

        // Запоминаем значение суммы к оплате
        double before = paymentAmount();

        // Перетаскиваем ползунок с сейвкоинами в нужную точку, чтобы списывалось указанное количество
        dragAndDropSavecoinSlider(savecoinsToSubstract, deviceName);

        // Добавляем тестовую карту оплаты, если она ещё не привязана
        addCard(deviceName);

        // Свайпаем экран вниз
        verticalSwipe();

        // Запоминаем значение суммы к оплате после выставления сейвкоинов к списанию
        double after = paymentAmount();

        // Проверяем, что сумма "Всего к оплате" уменьшилась на количество сейвкоинов к списанию
        assertPaymentAmountIsReducedBySavecoins(before, after, savecoinsToSubstract);

        // Нажимаем кнопку ОПЛАТИТЬ
        clickPayButton();

        // Нажимаем кнопку ОК на алерте "Новый заказ"
        clickOkOnAlertNewOrder(deviceName);

        // Подтаскиваем всю инфу о созданном заказе
        Order order = STsql.getOrderData(Integer.parseInt(driver.findElement(By.xpath(getElementLocator("Окно заказа" +
                " - поле с номером заказа"))).getText().substring(7)));
        Order.printAllInfo(order);

        // Проверяем, что статус заказа "Ваш заказ принят"
        assertOrderStatusHold();

        // Меняем статус на "Собирается" и рефрешим страницу заказа
        STsql.setOrderStatus("packing", order, client);
        driver.navigate().refresh();
        wait10sec();
        if (deviceName.equals("32010f78a810444a")) wait5sec();

        // Проверяем, что статус заказа изменился на "Ваш заказ собирается"
        assertOrderStatusPacking();

        // Меняем статус на "Собран" и рефрешим страницу заказа
        STsql.setOrderStatus("compiled", order, client);
        driver.navigate().refresh();
        wait10sec();
        if (deviceName.equals("32010f78a810444a")) wait5sec();

        // Проверяем, что статус заказа изменился на "Ваш заказ собран"
        assertOrderStatusCompiled();

        // Меняем статус на "В пути" и рефрешим страницу заказа
        STsql.setOrderStatus("ship", order, client);
        driver.navigate().refresh();
        wait10sec();
        if (deviceName.equals("32010f78a810444a")) wait5sec();

        // Проверяем, что статус заказа изменился на "Ваш заказ в пути"
        assertOrderStatusShip();

        // Меняем статус на "Доставлен" и рефрешим страницу
        STsql.setOrderStatus("done", order, client);
        driver.navigate().refresh();
        wait5sec();

        // Проставляем заказу оценку 3 звезды
        selectRating("3");

        // Жмем кнопку "ОТПРАВИТЬ", чтобы отправить оценку
        clickSendRatingButton();
        if (deviceName.equals("32010f78a810444a")) wait5sec();

        // Кликаем по Бургер-меню
        clickBurgerMenu();

        // Переходим на экран "Заказы"
        clickOrdersButton();

        // Ищем заказ в списке заказов и кликаем на него
        clickLastOrder();

        // Проверяем, что статус заказа изменился на "Заказ доставлен"
        assertOrderStatusDone();

        // Рефрешим страницу и жмем на Бургер-меню
        driver.navigate().refresh();
        wait5sec();
        clickBurgerMenu();

        // Переходим на экран Сейвкоины
        clickSavecoinButton();

        // Проверяем, что количество сейвкоинов уменьшилось на количество сейвкоинов к списанию
        assertSavecoinsIsReduced(savecoinsToSubstract, client);

    }
}
