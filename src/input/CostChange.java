package input;

public final class CostChange {
    private int id;
    private int infrastructureCost;
    private int productionCost;

    /**
     * id-ul distribuitorului ale carui costuri trebuie modificate
     */
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    /**
     * noul cost pentru infrastructura
     */
    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * noul cost pentru productie
     */
    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }
}
