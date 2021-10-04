package uz.ziraatbank.pinger.telnet;

public enum ErrorMessages {
    LOW_INET("Low network connection"),
    INVALID_PORT("Check port correctness"),
    INVALID_DATA("Check the correctness of data in DB"),
    NET_CONNECT("Check the network connection");    

    private String name;

    ErrorMessages(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
