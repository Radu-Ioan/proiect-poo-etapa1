package utils;

import factory.Factory;
import business.Contract;
import business.Consumer;
import business.Distributor;
import input.ConsumerInput;
import input.DistributorInput;
import input.Update;
import input.CostChange;
import input.InitialData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public final class BusinessFlow {
    private final List<Consumer> consumers = new ArrayList<>();
    private final List<Distributor> distributors = new ArrayList<>();
    private final List<Update> monthlyRounds;
    private final HashSet<Integer> bankruptDistributors = new HashSet<>();
    private final Factory factory = Factory.getInstance();
    private static final boolean GAME_OVER = true;

    public BusinessFlow(final InitialData initialData,
                        final List<Update> rounds) {

        for (ConsumerInput data : initialData.getConsumers()) {
            consumers.add(factory.createConsumer(data));
        }

        for (DistributorInput data : initialData.getDistributors()) {
            distributors.add(factory.createDistributor(data));
        }

        monthlyRounds = rounds;
    }

    /**
     * Simuleaza rundele din fiecare luna
     */
    public void play() {
        round0();

        for (Update month : monthlyRounds) {
            if (playRound(month) == GAME_OVER) {
                break;
            }
        }
    }

    private void round0() {
        for (Distributor d : distributors) {
            d.setPrice();
        }
        Distributor chosenDistributor = chooseDistributor();

        for (Consumer consumer : consumers) {
            if (consumer.isBankrupt()) {
                continue;
            }
            consumer.makeContractWith(chosenDistributor);
            consumer.pay();
        }

        for (Distributor distributor : distributors) {
            distributor.pay();
        }
    }

    private Distributor chooseDistributor() {
        int minRate = Integer.MAX_VALUE;
        Distributor seller = null;

        for (Distributor d : distributors) {
            if (!d.isBankrupt() && minRate > d.getMonthlyRate()) {
                seller = d;
                minRate = d.getMonthlyRate();
            }
        }

        return seller;
    }

    private boolean playRound(final Update month) {
        // se citesc update-uri
        updateDistributors(month);
        addConsumers(month.getNewConsumers());

        // se calculeaza preturile pentru noile contracte
        Distributor chosenDistributor = chooseDistributor();

        for (Consumer consumer : consumers) {
            ensureContractForConsumer(consumer, chosenDistributor);

            // consumatorii primesc salariu si platesc rata
            consumer.pay();
        }

        // distribuitorii isi platesc cheltuielile
        boolean allDistributorsAreBankrupt = distributorsPayCosts();
        if (allDistributorsAreBankrupt) {
            return GAME_OVER;
        }

        // Se reziliaza contractele consumatorilor care au dat faliment
        cleanUpContractsOfBankruptConsumers();

        return !GAME_OVER;
    }

    /**
     * Actualizeaza preturile oferite de catre distribuitori la fiecare inceput
     * de luna
     * @param month runda curenta
     */
    private void updateDistributors(final Update month) {
        for (CostChange change : month.getCostsChanges()) {
            Distributor provider = distributors.get(change.getId());
            if (provider == null) {
                continue;
            }

            provider.setInfrastructureCost(change.getInfrastructureCost());
            provider.setProductionCost(change.getProductionCost());
        }

        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                distributor.setPrice();
            }
        }
    }

    private void addConsumers(final List<ConsumerInput> newConsumers) {
        if (newConsumers == null) {
            return;
        }
        for (ConsumerInput data : newConsumers) {
            consumers.add(factory.createConsumer(data));
        }
    }

    /**
     * Asigura existenta unuei surse de energie pentru un consumator
     * @param chosenDistributor referinta catre distribuitorul cu cea mai mica
     *                          rata lunara
     */
    private void ensureContractForConsumer(final Consumer consumer,
                                           final Distributor
                                                   chosenDistributor) {
        if (consumer.isBankrupt()) {
            return;
        }

        // consumatorii aleg contracte
        Contract contract = consumer.getContract();

        if (contract != null
                && contract.getRemainedContractMonths() == 0) {
            contract.getPaymentDestination().getContracts().remove(contract);
            consumer.makeContractWith(chosenDistributor);
        } else if (contract == null) {
            consumer.makeContractWith(chosenDistributor);
        }
    }

    private boolean distributorsPayCosts() {
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                distributor.pay();
            }

            if (distributor.isBankrupt()
                    && !bankruptDistributors.contains(distributor.getId())) {

                bankruptDistributors.add(distributor.getId());
                deleteContracts(distributor.getContracts());

                if (bankruptDistributors.size() == distributors.size()) {
                    return GAME_OVER;
                }
            }
        }
        return !GAME_OVER;
    }

    private void cleanUpContractsOfBankruptConsumers() {
        for (Consumer consumer : consumers) {
            Contract contract = consumer.getContract();

            // In cazul in care vechiul distribuitor a dat faliment, nu
            // mai trebuie reziliat nimic
            if (contract == null) {
                continue;
            }

            if (consumer.isBankrupt()) {
                contract.getPaymentDestination().getContracts()
                        .remove(contract);
            }
        }
    }

    /**
     * Anuleaza contractele consumatorilor cu un distribuitor care a dat
     * faliment
     * @param contracts contractele unui distribuitor care a dat faliment
     */
    private void deleteContracts(final LinkedHashSet<Contract> contracts) {
        for (Contract contract : contracts) {

            Consumer consumer = consumers.get(contract.getConsumerId());

            // Se reziliaza contractele facute cu consumatorii de catre
            // distribuitorul care a dat faliment
            if (!consumer.isBankrupt()) {
                consumer.setContract(null);
            }
        }
    }

    /**
     * Returneaza consumatorii de la finalul jocului
     */
    public List<Consumer> getConsumers() {
        return consumers;
    }

    /**
     * Returneaza distribuitorii de la finalul jocului
     */
    public List<Distributor> getDistributors() {
        return distributors;
    }
}
