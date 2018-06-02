package edu.iis.mto.serverloadbalancer;

public class Server {

    public double currentLoadPercentage;
    private int capacity;

    public Server(int capacity) {
        super();
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return true;
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = (double) vm.size / (double) capacity * 100.0d;
    }

}
