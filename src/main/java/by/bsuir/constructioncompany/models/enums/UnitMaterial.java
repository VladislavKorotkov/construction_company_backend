package by.bsuir.constructioncompany.models.enums;

public enum UnitMaterial {
    PIECE("шт."),
    KILOGRAM("кг"),
    SQUARE_METER("м2"),
    LINEAR_METER("м пог.");

    private String label;

    UnitMaterial(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
