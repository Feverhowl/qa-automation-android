// Тест №003
// End to End тест - проверка работы фиксированного промокода.

package main;

import org.openqa.selenium.By;

public class Test003 extends Main{

    public static final String NAME = "Тест №003 - End to End - Проверка работы фиксированного промокода";

    public static void test003(String deviceName) {

        // Выбираем случайного клиента с привязанной тестовой картой 5100 0000 0000 0008
        Client client = STsql.pickRandomClientWithTestCard();
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
        int random = rnd(2, 3);
        clickBrand(random);

        // Кликаем на случайную категорию
        clickRandomCategory();

        // Кликаем на случайную подкатегорию
        clickRandomSubCategory();

        // Набираем в корзину товаров на сумму менее 500 рублей
        addGoodsOnCartLessThan500();

        // Кликаем по значку Корзины
        clickCartButton();

        // Кликаем по кнопке Оформить заказ
        clickCheckoutButton();

        // Заполняем поля Подъезд, Этаж, Кв./Офис, Код домофона
        fillPorchFloorFlatIntercom("3", "1", "2", "312");

        // Кликаем по кнопке Далее на экране Доставка
        clickNextButtonOnDeliveryScreen();

        // Кликаем по кнопке Далее на экране Время доставки
        clickNextButtonOnDeliveryTimeScreen();

        // Вводим комментарий клиента
        fillCommentField("Farewell, and if forever - still forever farewell.");

        // Добавляем тестовую карту оплаты, если она ещё не привязана
        addCard(deviceName);

        // Кликаем по кнопке "Активировать промокод"
        clickPromocodeButton();

        // Создаем в БД промокод
        String discountType = "fix";
        float discount = 100;
        String promocode = STsql.createAndReturnPromocode(discountType, discount);
        System.out.println(promocode);

        // Вводим промокод и нажимаем ОК
        enterPromocode(promocode, deviceName);

        // Проверяем наличие алерта "Промокод не может быть применен..." и кликаем ОК
        assertLowCartPromocodeAlertAndClickOK();

        // Возвращаемся в список категорий из чекаута
        backToCategoryListFromCheckout();

        // Кликаем на бренд, который мы выбирали вначале
        clickBrand(random);

        // Кликаем на случайную категорию
        clickRandomCategory();

        // Кликаем на случайную подкатегорию
        clickRandomSubCategory();

        // Набираем в корзину товаров на сумму более 500 рублей
        addGoodsOnCartMoreThan500();

        // Кликаем по значку Корзины
        clickCartButton();

        // Кликаем по кнопке Оформить заказ
        clickCheckoutButton();

        // Запоминаем значение суммы к оплате
        double before = paymentAmount();

        // Кликаем по кнопке "Активировать промокод"
        clickPromocodeButton();

        // Вводим промокод и нажимаем ОК
        enterPromocode(promocode, deviceName);

        // Проверяем наличие алерта "Промокод активирован" и кликаем ОК
        assertValidCartPromocodeAlertAndClickOK();

        // Свайпаем экран вниз
        verticalSwipe();

        // Проверяем корректность отображения введенного промокода
        assertValidPromocodeDisplay(promocode);

        // Проверяем корректность отображения скидки по промокоду
        assertValidPromocodeDiscountDisplay(discount, discountType);

        // Запоминаем значение суммы к оплате после применения промокода
        double after = paymentAmount();

        // Проверяем, что сумма "Всего к оплате" уменьшилась на сумму скидки
        assertPaymentAmountIsReduced(before, after, discount, discountType);

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

        // Кликаем по Бургер-меню
        clickBurgerMenu();

        // Переходим на экран "Заказы"
        clickOrdersButton();

        // Ищем заказ в списке заказов и кликаем на него
        clickLastOrder();

        // Проверяем, что статус заказа изменился на "Заказ доставлен"
        assertOrderStatusDone();
    }
}
