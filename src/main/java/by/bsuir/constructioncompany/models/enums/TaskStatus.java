package by.bsuir.constructioncompany.models.enums;

public enum TaskStatus {
    PENDING("Ожидание"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершено");

    private String label;

    TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
