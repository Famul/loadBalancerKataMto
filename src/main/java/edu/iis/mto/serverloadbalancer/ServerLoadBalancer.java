package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

    public void balance(Server[] servers, Vm[] vms) {
        for (Vm vm : vms) {
            List<Server> capableServer = findCapableServers(servers, vm);
            addToLessLoadedServer(capableServer, vm);
        }
    }

    private List<Server> findCapableServers(Server[] servers, Vm vm) {
        List<Server> capableServer = new ArrayList<Server>();
        for (Server server : servers) {
            if (server.canFit(vm)) {
                capableServer.add(server);
            }
        }
        return capableServer;
    }

    private void addToLessLoadedServer(List<Server> capableServer, Vm vm) {
        Server lessLoadedServer = findLessLoadedServer(capableServer);
        if (lessLoadedServer != null) {
            lessLoadedServer.addVm(vm);
        }
    }

    private Server findLessLoadedServer(List<Server> capableServer) {
        Server lessLoadedServer = null;
        for (Server server : capableServer) {
            if (lessLoadedServer == null || lessLoadedServer.currentLoadPercentage > server.currentLoadPercentage) {
                lessLoadedServer = server;
            }
        }
        return lessLoadedServer;
    }

}
