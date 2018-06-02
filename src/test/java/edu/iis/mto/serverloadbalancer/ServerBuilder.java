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
        addInitialLoad(server);
        return server;
    }

    private void addInitialLoad(Server server) {
        if (initialLoad > 0) {
            int initialVmSize = (int) (initialLoad * (double) capacity / 100.0d);
            Vm vm = VmBuilder.vm().ofSize(initialVmSize).build();
            server.addVm(vm);
        }
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public Builder<Server> withCurrentLoadOf(double initialLoad) {
        this.initialLoad = initialLoad;
        return this;
    }

}
