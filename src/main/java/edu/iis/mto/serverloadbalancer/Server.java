package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPercentage;
    public int capacity;
    private List<Vm> Vms = new ArrayList<Vm>();

    public boolean contains(Vm theVm) {
        return true;
    }

    public Server(int capacity) {
        super();
        this.capacity = capacity;
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = (double) vm.size / (double) capacity * MAXIMUM_LOAD;
        this.Vms.add(vm);
    }

    public int countVms() {
        return Vms.size();
    }

}
