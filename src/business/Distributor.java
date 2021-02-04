package business;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import strategies.PriceStrategy;
import utils.Constants;

import java.util.LinkedHashSet;

@JsonPropertyOrder({"id", "budget", "isBankrupt", "contracts"})
public class Distributor implements Payer {
    private int id;
    private int budget;
    private boolean isBankrupt;
    private int contractLength;
    private int infrastructureCost;
    private int productionCost;
    private LinkedHashSet<Contract> contracts = new LinkedHashSet<>();
    /**
     * Pretul unei rate lunare
     */
    private int price;

    private PriceStrategy contractPriceStrategy = () -> {
        int profit = (int) Math.round(
                Math.floor(Constants.PROFIT_PERCENTAGE * productionCost));

        int x;
        if (contracts.size() != 0) {
            x = (int) Math.round(
                    Math.floor(infrastructureCost / contracts.size()));

        } else {
            x = (int) Math.round(Math.floor(infrastructureCost));
        }
        return x + profit + productionCost;
    };

    /**
     *  Metoda pentru plata costurilor de infrastructura si de productie
     */
    public void pay() {
        budget -= (infrastructureCost + productionCost * contracts.size());
        if (budget < 0) {
            isBankrupt = true;
        }
    }

    /**
     * Aduna la buget un venit
     */
    public void addIncome(final int sum) {
        budget += sum;
    }

    /**
     * Returneaza pretul contractului oferit de catre distrubuitor
     */
    @JsonIgnore
    public int getMonthlyRate() {
        return price;
    }

    /**
     * Returneaza id-ul distribuitorului
     */
    public int getId() {
        return id;
    }

    /**
     * Se foloseste la initializarea unui distribuitor
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Returneaza perioada de valabilitate a unui contract
     */
    @JsonIgnore
    public int getContractLength() {
        return contractLength;
    }

    /**
     * Initializeaza perioada de valabilitate a unui contract
     */
    @JsonIgnore
    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    /**
     * Returneaza bugetul distribuitorului
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Seteaza bugetul distribuitorului
     */
    public void setBudget(final int budget) {
        this.budget = budget;
    }

    /**
     * Returneaza costul de infrastructura specific distribuitorului
     */
    @JsonIgnore
    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     * Schimba costul de infrastructura al unui distribuitor
     */
    @JsonIgnore
    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * Returneaza costul de productie al unui distribuitor
     */
    @JsonIgnore
    public int getProductionCost() {
        return productionCost;
    }

    /**
     * Actualizeaza costul de productie al unui distribuitor
     */
    @JsonIgnore
    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    /**
     * Returneaza statutul unui distribuitor
     */
    @JsonGetter("isBankrupt")
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * Initializeaza statutul unui distribuitor
     */
    @JsonSetter("isBankrupt")
    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    /**
     * Returneaza lista cu contractele curente ale distribuitorului
     */
    public LinkedHashSet<Contract> getContracts() {
        return contracts;
    }

    /**
     * Actualizeaza lista de contracte a distribuitorului
     */
    public void setContracts(final LinkedHashSet<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     * Actualizeaza pretul unui contract oferit de distribuitor intr-o luna
     */
    @JsonIgnore
    public void setPrice() {
        price = contractPriceStrategy.price();
    }
}
