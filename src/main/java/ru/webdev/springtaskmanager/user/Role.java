package ru.webdev.springtaskmanager.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(1, "Может менять статус карточки и брать ее в работу"),
    MANAGER(2, "Может создавать карточки"),
    ADMIN(3, "Может удалять карточки, назначать роли");

    private final int level;
    private final String description;
}
