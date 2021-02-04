package factory;

import business.Consumer;
import business.Distributor;
import input.ConsumerInput;
import input.DistributorInput;

public final class Factory {

    private Factory() { }

    private static Factory instance;

    /**
     * @return instanta de factory care creeaza entitati folosind datele din
     * input
     */
    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    /**
     * @param data contine informatiile despre un distribuitor primite din
     * input
     * @return un obiect pentru un distribuitor folosind data
     */
    public Distributor createDistributor(final DistributorInput data) {
        Distributor distributor = new Distributor();
        distributor.setId(data.getId());
        distributor.setBudget(data.getInitialBudget());
        distributor.setInfrastructureCost(data.getInitialInfrastructureCost());
        distributor.setProductionCost(data.getInitialProductionCost());
        distributor.setContractLength(data.getContractLength());
        distributor.setBankrupt(false);
        return distributor;
    }

    /**
     * @param data contine informatiile despre un consumer din input
     * @return un obiect consumer folosind data
     */
    public Consumer createConsumer(final ConsumerInput data) {
        Consumer consumer = new Consumer();
        consumer.setId(data.getId());
        consumer.setBankrupt(false);
        consumer.setBudget(data.getInitialBudget());
        consumer.setMonthlyIncome(data.getMonthlyIncome());
        consumer.setContract(null);
        return consumer;
    }
}
