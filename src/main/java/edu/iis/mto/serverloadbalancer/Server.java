package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final double MAX_LOAD = 100.0d;
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
        currentLoadPercentage += addLoad(vm);
        vms.add(vm);
    }

    public int vmsCount() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        return currentLoadPercentage + addLoad(vm) <= MAX_LOAD;
    }

    private double addLoad(Vm vm) {
        return (double) vm.getSize() / (double) capacity * MAX_LOAD;
    }

    public double getCurrentLoadPercentage() {
        return currentLoadPercentage;
    }

    public int getCapacity() {
        return capacity;
    }

    List<Vm> getVms() {
        return vms;
    }

}
