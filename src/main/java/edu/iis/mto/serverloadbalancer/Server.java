package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final double MAXIMUM_LOAD = 100.0d;
    private double currentLoadPercentage;
    private int capacity;
    private List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        super();
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return vms.contains(theVm);
    }

    public void addVm(Vm vm) {
        vms.add(vm);
        currentLoadPercentage += addLoad(vm);
    }

    private double addLoad(Vm vm) {
        return (double) vm.getSize() / (double) capacity * MAXIMUM_LOAD;
    }

    public int countOfVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        return currentLoadPercentage + addLoad(vm) <= MAXIMUM_LOAD;
    }

    private int getCapacity() {
        return capacity;
    }

    public double getCurrentLoadPercentage() {
        return currentLoadPercentage;
    }

    private List<Vm> getVms() {
        return vms;
    }

}
