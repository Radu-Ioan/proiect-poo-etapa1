package factory;

import business.Consumer;
import business.Distributor;

import java.util.List;

/**
 * Obiectul json scris in fisierele de out
 */
public final class Result {
    private List<Consumer> consumers;
    private List<Distributor> distributors;

    public Result(final List<Consumer> consumers,
                  final List<Distributor> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Distributor> distributors) {
        this.distributors = distributors;
    }
}
