package main;

import java.sql.Timestamp;

class Promo {
    private int id;
    private String name;
    private String code;
    private int promo_count;
    private int use_count;
    private String type;
    private int unique;
    private String discount_type;
    private float discount;
    private int status;
    private Timestamp date_start;
    private Timestamp date_end;
    private int min_sum;
    private int max_sum;
    private int only_first;
    private String order_type;
    private int brand;
    private int client;
    private int city;

    Promo(int id, String name, String code, int promo_count, int use_count, String type, int unique,
          String discount_type, float discount, int status, Timestamp date_start, Timestamp date_end, int min_sum,
          int max_sum, int only_first, String order_type, int brand, int client, int city) {
        setId(id);
        setName(name);
        setCode(code);
        setPromo_count(promo_count);
        setUse_count(use_count);
        setType(type);
        setUnique(unique);
        setDiscount_type(discount_type);
        setDiscount(discount);
        setStatus(status);
        setDate_start(date_start);
        setDate_end(date_end);
        setMin_sum(min_sum);
        setMax_sum(max_sum);
        setOnly_first(only_first);
        setOrder_type(order_type);
        setBrand(brand);
        setClient(client);
        setCity(city);
    }

    int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    int getPromo_count() {
        return promo_count;
    }

    private void setPromo_count(int promo_count) {
        this.promo_count = promo_count;
    }

    int getUse_count() {
        return use_count;
    }

    private void setUse_count(int use_count) {
        this.use_count = use_count;
    }

    String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    int getUnique() {
        return unique;
    }

    private void setUnique(int unique) {
        this.unique = unique;
    }

    String getDiscount_type() {
        return discount_type;
    }

    private void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    float getDiscount() {
        return discount;
    }

    private void setDiscount(float discount) {
        this.discount = discount;
    }

    int getStatus() {
        return status;
    }

    private void setStatus(int status) {
        this.status = status;
    }

    Timestamp getDate_start() {
        return date_start;
    }

    private void setDate_start(Timestamp date_start) {
        this.date_start = date_start;
    }

    Timestamp getDate_end() {
        return date_end;
    }

    private void setDate_end(Timestamp date_end) {
        this.date_end = date_end;
    }

    int getMin_sum() {
        return min_sum;
    }

    private void setMin_sum(int min_sum) {
        this.min_sum = min_sum;
    }

    int getMax_sum() {
        return max_sum;
    }

    private void setMax_sum(int max_sum) {
        this.max_sum = max_sum;
    }

    int getOnly_first() {
        return only_first;
    }

    private void setOnly_first(int only_first) {
        this.only_first = only_first;
    }

    String getOrder_type() {
        return order_type;
    }

    private void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    int getBrand() {
        return brand;
    }

    private void setBrand(int brand) {
        this.brand = brand;
    }

    int getClient() {
        return client;
    }

    private void setClient(int client) {
        this.client = client;
    }

    int getCity() {
        return city;
    }

    private void setCity(int city) {
        this.city = city;
    }
}
