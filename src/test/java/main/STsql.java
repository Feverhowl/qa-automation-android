package main;

import io.qameta.allure.Step;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

class STsql {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://sym-01.svt/savetime_net";
    private static String user = "tester";
    private static String password = "S88S88Tj0k0C1qUbNJhX43624";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    // Метод получения всех данных о заказе по его id (номеру)
    @Step("Подтаскиваем всю инфу о созданном заказе")
    static Order getOrderData(int id) {
        String status = null;
        float sum = 0;
        float sum_total = 0;
        float sum_delivery = 0;
        float sum_coin = 0;
        Timestamp time_delivery = null;
        Timestamp done = null;
        String address_from = null;
        String address_to = null;
        String floor = null;
        String porch = null;
        String flat = null;
        String code = null;
        String comment = null;

        String query = "SELECT * FROM `savetime_net`.`order` WHERE `id` = " + id;

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                status = rs.getString("status");
                sum = rs.getFloat("sum");
                sum_delivery = rs.getFloat("sum_delivery");
                sum_total = rs.getFloat("sum_total");
                sum_coin = rs.getFloat("sum_coin");
                time_delivery = rs.getTimestamp("time_delivery");
                try {
                    done = rs.getTimestamp("done");
                } catch (SQLException e) {
                    done = null;
                }
                address_from = rs.getString("address_from");
                address_to = rs.getString("address_to");
                floor = rs.getString("floor");
                porch = rs.getString("porch");
                flat = rs.getString("flat");
                code = rs.getString("code");
                comment = rs.getString("comment");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return new Order(id, status, sum, sum_total, sum_delivery, sum_coin, time_delivery, done, address_from,
                address_to, floor, porch, flat, code, comment);
    }

    @Step("Выбираем случайного клиента с привязанной тестовой картой 5100 0000 0000 0008")
    static Client pickRandomClientWithTestCard() {
        int id = 0;
        double savecoin = 0.0;
        String name = null;
        String phone = null;
        String referral_code = null;
        String status = null;

        String query = "SELECT `client` FROM `savetime_net`.client_card WHERE `pan` = '510000**0008' ORDER BY RAND() LIMIT 1";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt("client");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query2 = "SELECT `client`, `balance`, `date` FROM `savetime_net`.`coin` WHERE `client` = " + id +
                " ORDER BY date DESC LIMIT 1";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query2);

            while (rs.next()) {
                savecoin = rs.getDouble("balance");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query3 = "SELECT `id`, `name`, `phone`, `referral_code`, `status` FROM `savetime_net`.`client` " +
                "WHERE `id` = " + id;

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query3);

            while (rs.next()) {
                name = rs.getString("name");
                phone = rs.getString("phone");
                referral_code = rs.getString("referral_code");
                status = rs.getString("status");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return new Client(id, name, phone, referral_code, status, savecoin);
    }

    @Step("Выбираем случайного клиента с активными заказами")
    static Client pickRandomClientWithActiveOrders() {
        int id = 0;
        double savecoin = 0.0;
        String name = null;
        String phone = null;
        String referral_code = null;
        String status = null;

        String query = "SELECT `client` FROM `savetime_net`.`order` WHERE `status` != 'done' AND `status` != " +
                "'cancel' AND `status` != 'unpaid' AND `status` != 'delete' ORDER BY RAND() LIMIT 1";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt("client");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query2 = "SELECT `client`, `balance`, `date` FROM `savetime_net`.`coin` WHERE `client` = " + id +
                " ORDER BY date DESC LIMIT 1";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query2);

            while (rs.next()) {
                savecoin = rs.getDouble("balance");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query3 = "SELECT `id`, `name`, `phone`, `referral_code`, `status` FROM `savetime_net`.`client` " +
                "WHERE `id` = " + id;

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query3);

            while (rs.next()) {
                name = rs.getString("name");
                phone = rs.getString("phone");
                referral_code = rs.getString("referral_code");
                status = rs.getString("status");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return new Client(id, name, phone, referral_code, status, savecoin);
    }

    @Step("Выбираем случайного клиента с сейвкоинами")
    static Client pickRandomClientWithSavecoins() {
        int id = 0;
        double savecoin = 0.0;
        String name = null;
        String phone = null;
        String referral_code = null;
        String status = null;

        String query = "SELECT `client` FROM `savetime_net`.`coin` ORDER BY RAND() LIMIT 1";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt("client");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query2 = "SELECT `client`, `balance`, `date` FROM `savetime_net`.`coin` WHERE `client` = " + id +
                " ORDER BY date DESC LIMIT 1";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query2);

            while (rs.next()) {
                savecoin = rs.getDouble("balance");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query3 = "SELECT `id`, `name`, `phone`, `referral_code`, `status` FROM `savetime_net`.`client` " +
                "WHERE `id` = " + id;

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query3);

            while (rs.next()) {
                name = rs.getString("name");
                phone = rs.getString("phone");
                referral_code = rs.getString("referral_code");
                status = rs.getString("status");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return new Client(id, name, phone, referral_code, status, savecoin);
    }

    @Step("Добавляем клиенту указанное количество сейвкоинов")
    static void addSavecoinsToClient(Client client, double amount) {
        String query = "UPDATE `savetime_net`.`coin` SET `balance` = " + (Math.round((client.getSavecoin() +
                amount) * 100.0) / 100.0) + " WHERE `client` = " + client.getId() +
                " AND `date` = (SELECT MAX(`date`) FROM `savetime_net`.`coin` WHERE `client` = " + client.getId() + ")";
        client.setSavecoin(client.getSavecoin() + amount);
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    @Step("Создаем в БД промокод")
    static String createAndReturnPromocode(String discount_type, float discount) {
        String query = "SELECT `id` FROM `savetime_net`.promo ORDER BY `id` DESC LIMIT 1";
        int lastPromoId = 0;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                lastPromoId = rs.getInt("id");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Timestamp date_start = new Timestamp(cal.getTimeInMillis());
        cal.add(Calendar.DAY_OF_MONTH, +2);
        Timestamp date_end = new Timestamp(cal.getTimeInMillis());
        int random = Main.rnd(1000, 9999);

        Promo promo = new Promo(lastPromoId + 1, "Test" + random, "Test" + random, 1,
                50, "common", 0, discount_type, discount, 1, date_start, date_end,
                500, 0, 0, "", 0, 0, 1);

        String query2 = "INSERT INTO `savetime_net`.`promo` (`id`, `name`, `code`, `promo_count`, `use_count`, " +
                "`type`, `unique`, `discount_type`, `discount`, `status`, `date_start`, `date_end`, `min_sum`, " +
                "`max_sum`, `only_first`, `order_type`, `brand`, `client`, `city`) VALUES (" + promo.getId() +
                ", '" + promo.getName() + "', '" + promo.getCode() + "', " + promo.getPromo_count() +
                ", " + promo.getUse_count() + ", '" + promo.getType() + "', " + promo.getUnique() +
                ", '" + promo.getDiscount_type() + "', " + promo.getDiscount() + ", " + promo.getStatus() +
                ", '" + promo.getDate_start() + "', '" + promo.getDate_end() + "', " + promo.getMin_sum() +
                ", " + promo.getMax_sum() + ", " + promo.getOnly_first() + ", '" + promo.getOrder_type() +
                "', " + promo.getBrand() + ", " + promo.getClient() + ", " + promo.getCity() + ")";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            int i = stmt.executeUpdate(query2);
            System.out.println("Изменено строк в базе: " + i);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query3 = "SELECT `id` FROM `savetime_net`.promo_code ORDER BY `id` DESC LIMIT 1";
        int lastPromoCodeId = 0;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query3);

            while (rs.next()) {
                lastPromoCodeId = rs.getInt("id");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        String query4 = "INSERT INTO `savetime_net`.`promo_code` (`id`, `promo`, `code`, `status`, `city`) VALUES (" +
                (lastPromoCodeId + 1) + ", " + promo.getId() + ", '" + promo.getCode() +
                "', " + promo.getStatus() + ", " + promo.getCity() + ")";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            int i = stmt.executeUpdate(query4);
            System.out.println("Изменено строк в базе: " + i);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }


        return promo.getCode();
    }

    static String getRandomAddress() {
        String address = "ул. Наметкина, 12А";
        String query = "SELECT `address_to` FROM `savetime_net`.`order` ORDER BY RAND() LIMIT 1";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                address = rs.getString("address_to");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        return address;
    }

    @Step("Меняем статус заказа")
    static void setOrderStatus (String status, Order order, Client client) {
        String query = "UPDATE `savetime_net`.`order` SET `status` = \"" + status + "\" WHERE `id` = " + order.getId();
        String query2 = "SELECT `status` FROM `savetime_net`.`order` WHERE `id` = " + order.getId();

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            int i = stmt.executeUpdate(query);
            rs = stmt.executeQuery(query2);

            while (rs.next()) {
                order.setStatus(rs.getString("status"));
                System.out.println("Изменено строк в базе: " + i + ". Статус заказа №" + order.getId() +
                        " изменён на: " + order.getStatus());
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        if (status.equals("ship")) {
            order.setSum_total(order.getSum() + order.getSum_delivery());
            String query3 = "UPDATE `savetime_net`.`order` SET `sum_total` = \"" + order.getSum_total() +
                    "\" WHERE `id` = " + order.getId();
            try {
                con = DriverManager.getConnection(url, user, password);
                stmt = con.createStatement();
                stmt.executeUpdate(query3);
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            }
        }

        if (status.equals("done")) {
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.HOUR, -3);
            order.setDone(new Timestamp(cal.getTimeInMillis()));
            String query4 = "UPDATE `savetime_net`.`order` SET `done` = \"" + order.getDone() + "\" WHERE `id` = " +
                    order.getId();
            String query5 = "SELECT `id` FROM `savetime_net`.`coin` ORDER BY `id` DESC LIMIT 1";
            int lastCoinId = 0;
            try {
                con = DriverManager.getConnection(url, user, password);
                stmt = con.createStatement();
                stmt.executeUpdate(query4);
                rs = stmt.executeQuery(query5);
                while (rs.next()) {
                    lastCoinId = rs.getInt("id");
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            }

            if (order.getSum_coin() == 0) {
                double savecoinsAccured = Math.round(order.getSum()) / 100.0;
                String query6 = "INSERT INTO `savetime_net`.`coin` (`id`, `client`, `sum`, `type`, `item`, `item_id`, " +
                        "`comment`, `date`, `balance`) VALUES (" + (lastCoinId + 1) + ", " + client.getId() + ", " +
                        savecoinsAccured + ", 101, 'order', " + order.getId() + ", '', '" + order.getDone() + "', " +
                        (Math.round((client.getSavecoin() + savecoinsAccured) * 100.0) / 100.0) + ")";
                try {
                    con = DriverManager.getConnection(url, user, password);
                    stmt = con.createStatement();
                    stmt.executeUpdate(query6);
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                    try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                    try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
                }
            }
        }
    }
}
