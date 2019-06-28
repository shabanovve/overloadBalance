package overload.balance.client.client;

public enum Server {
    ONE("172.21.0.2"), TWO("172.21.0.3");

    private String name;
    Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
