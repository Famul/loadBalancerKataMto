package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPercentage;
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
        currentLoadPercentage = (double) vm.size / (double) capacity * MAXIMUM_LOAD;
    }

    public int countOfVms() {
        return vms.size();
    }

}
