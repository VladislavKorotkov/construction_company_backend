package by.bsuir.constructioncompany.models.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("Пользователь"),
    ROLE_ADMIN("Администратор"),
    ROLE_FOREMAN("Бригадир"),
    ROLE_BUILDER("Строитель");

    private final String value;

    Role(String value) {
        this.value = value;
    }

}
