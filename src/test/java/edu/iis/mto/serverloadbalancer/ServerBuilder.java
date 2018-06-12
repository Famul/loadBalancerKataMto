package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {

    private int capacity;
    private double initialLoad;

    public ServerBuilder withCapacityOf(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        Server server = new Server(capacity);
        if (initialLoad > 0) {
            int initialVmSize = (int) (initialLoad * (double) capacity / 100.0d);
            Vm initialLoadVm = VmBuilder.vm().ofSize(initialVmSize).build();
            server.addVm(initialLoadVm);
        }
        return server;
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public Builder<Server> withCurrentLoadPercentage(double initialLoad) {
        this.initialLoad = initialLoad;
        return this;
    }

}
