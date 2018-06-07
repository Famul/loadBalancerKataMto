package edu.iis.mto.serverloadbalancer;

public class ServerBuilder {

    private int capacity;

    public ServerBuilder withCapacityOf(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        return new Server();
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

}
