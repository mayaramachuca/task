package com.task.task.domain.constant;

public enum Status {

    CREATED("created"),
    DOING("doing"),
    FINISHED("finished");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }
}
