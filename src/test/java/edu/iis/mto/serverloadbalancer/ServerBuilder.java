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
            Vm initialVm = VmBuilder.vm().ofSize(initialVmSize).build();
            server.addVm(initialVm);
        }
        return server;

    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public Builder<Server> withCurrentLoadPercentageOf(double initialLoad) {
        this.initialLoad = initialLoad;
        return this;
    }

}
