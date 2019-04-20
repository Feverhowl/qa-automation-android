package main;

import java.sql.Timestamp;

class Order {
    private int id;
    private String status;
    private float sum;
    private float sum_total;
    private float sum_delivery;
    private float sum_coin;
    private Timestamp time_delivery;
    private Timestamp done;
    private String address_from;
    private String address_to;
    private String floor;
    private String porch;
    private String flat;
    private String code;
    private String comment;

    Order(int id, String status, float sum, float sum_total, float sum_delivery, float sum_coin, Timestamp time_delivery,
          Timestamp done, String address_from, String address_to, String floor, String porch, String flat,
          String code, String comment) {
        setId(id);
        setStatus(status);
        setSum(sum);
        setSum_delivery(sum_delivery);
        setSum_total(sum_total);
        setSum_coin(sum_coin);
        setTime_delivery(time_delivery);
        setDone(done);
        setAddress_from(address_from);
        setAddress_to(address_to);
        setFloor(floor);
        setPorch(porch);
        setFlat(flat);
        setCode(code);
        setComment(comment);
    }

    static void printAllInfo(Order order) {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("║ Информация о заказе № " + order.getId());
        System.out.println("╟╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌");
        System.out.println("║ ID заказа: " + order.getId());
        System.out.println("║ Статус заказа: " + order.getStatus());
        System.out.println("║ Сумма корзины: " + order.getSum());
        System.out.println("║ Сумма доставки: " + order.getSum_delivery());
        System.out.println("║ Сумма итого: " + order.getSum_total());
        System.out.println("║ Сумма потраченных сейвкоинов: " + order.getSum_coin());
        System.out.println("║ Дата создания заказа: " + order.getTime_delivery());
        System.out.println("║ Дата завершения доставки: " + order.getDone());
        System.out.println("║ Адрес магазина: " + order.getAddress_from());
        System.out.println("║ Адрес клиента: " + order.getAddress_to());
        System.out.println("║ Подъезд клиента: " + order.getPorch());
        System.out.println("║ Этаж клиента: " + order.getFloor());
        System.out.println("║ Квартира клиента: " + order.getFlat());
        System.out.println("║ Домофон клиента: " + order.getCode());
        System.out.println("║ Комментарий клиента: " + order.getComment());
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════");
    }

    int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    float getSum() {
        return sum;
    }

    private void setSum(float sum) {
        this.sum = sum;
    }

    float getSum_total() {
        return sum_total;
    }

    void setSum_total(float sum_total) {
        this.sum_total = sum_total;
    }

    float getSum_delivery() {
        return sum_delivery;
    }

    private void setSum_delivery(float sum_delivery) {
        this.sum_delivery = sum_delivery;
    }

    float getSum_coin() {
        return sum_coin;
    }

    private void setSum_coin(float sum_coin) {
        this.sum_coin = sum_coin;
    }

    private Timestamp getTime_delivery() {
        return time_delivery;
    }

    private void setTime_delivery(Timestamp time_delivery) {
        this.time_delivery = time_delivery;
    }

    Timestamp getDone() {
        return done;
    }

    void setDone(Timestamp done) {
        this.done = done;
    }

    private String getAddress_from() {
        return address_from;
    }

    private void setAddress_from(String address_from) {
        this.address_from = address_from;
    }

    private String getAddress_to() {
        return address_to;
    }

    private void setAddress_to(String address_to) {
        this.address_to = address_to;
    }

    private String getFloor() {
        return floor;
    }

    private void setFloor(String floor) {
        this.floor = floor;
    }

    private String getPorch() {
        return porch;
    }

    private void setPorch(String porch) {
        this.porch = porch;
    }

    private String getFlat() {
        return flat;
    }

    private void setFlat(String flat) {
        this.flat = flat;
    }

    private String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    private String getComment() {
        return comment;
    }

    private void setComment(String comment) {
        this.comment = comment;
    }
}