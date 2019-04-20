package main;

class Client {
    private int id;
    private String name;
    private String phone;
    private String referral_code;
    private String status;
    private double savecoin;

    Client(int id, String name, String phone, String referral_code, String status, double savecoin) {
        setId(id);
        setName(name);
        setPhone(phone);
        setReferral_code(referral_code);
        setStatus(status);
        setSavecoin(savecoin);
    }

    static void printAllInfo(Client client) {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("║ Информация о клиенте: " + client.getName());
        System.out.println("╟╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌");
        System.out.println("║ ID клиента: " + client.getId());
        System.out.println("║ Имя клиента: " + client.getName());
        System.out.println("║ Телефон клиента: " + client.getPhone());
        System.out.println("║ Реферальный код клиента: " + client.getReferral_code());
        System.out.println("║ Статус клиента: " + client.getStatus());
        System.out.println("║ Сейвкоины клиента: " + client.getSavecoin());
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════");
    }

    int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    String getPhone() {
        return phone;
    }

    private void setPhone(String phone) {
        this.phone = phone;
    }

    private String getReferral_code() {
        return referral_code;
    }

    private void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    private String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    double getSavecoin() {
        return savecoin;
    }

    void setSavecoin(double savecoin) {
        this.savecoin = savecoin;
    }
}
