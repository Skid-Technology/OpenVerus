package me.levansj01.verus.data.client;

public class ClientData {
    private String brand;
    private ClientType type;

    public void setType(ClientType type) {
        this.type = type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return this.brand;
    }

    public ClientData(String brand, ClientType type) {
        this.brand = brand;
        this.type = type;
    }

    public ClientType getType() {
        return this.type;
    }
}
