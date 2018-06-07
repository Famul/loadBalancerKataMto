package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final double MAX_LOAD = 100.0d;
    public double currentLoadPercentage;
    public int capacity;
    List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        super();
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return vms.contains(theVm);
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = (double) vm.size / (double) capacity * MAX_LOAD;
        vms.add(vm);
    }

    public int hasVmsCount() {
        return vms.size();
    }

}
