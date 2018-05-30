package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final double MAX_LOAD = 100.0d;
    public double currentLoadPercentage;
    public int capacity;
    public List<Vm> vms = new ArrayList<Vm>();

    public boolean contains(Vm theVm) {
        return vms.contains(theVm);
    }

    public Server(int capacity) {
        super();
        this.capacity = capacity;
    }

    public void addVm(Vm vm) {

        currentLoadPercentage = (double) vm.size / (double) capacity * MAX_LOAD;
        vms.add(vm);
    }

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        return currentLoadPercentage + ((double) vm.size / (double) capacity * MAX_LOAD) <= MAX_LOAD;
    }

}
