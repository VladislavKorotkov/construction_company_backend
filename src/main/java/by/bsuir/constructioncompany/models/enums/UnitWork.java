package by.bsuir.constructioncompany.models.enums;

public enum UnitWork {
    SQUARE_METER("м2"),
    LINEAR_METER("м пог.");

    private String label;

    UnitWork(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
