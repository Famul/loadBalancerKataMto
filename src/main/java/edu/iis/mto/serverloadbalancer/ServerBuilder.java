package edu.iis.mto.serverloadbalancer;


public class ServerBuilder {

    private int capacity;

    public ServerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Serwer build() {
        // TODO Auto-generated method stub
        return new Serwer();
    }

}
