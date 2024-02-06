class Equipment {
    private int id;
    private String name;
    private long price;

    public Equipment(int id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice() {
        price = price / 10;
    }

}

