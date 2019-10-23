package com.scraper.web.util;

import lombok.Getter;

import static java.util.Objects.isNull;

@Getter
public class EntityWrapper<T> {

    private T scrapedEntity;

    private T existentEntity;

    private State state;

    public EntityWrapper(T scrapedEntity, T existentEntity) {
        this.scrapedEntity = scrapedEntity;
        this.existentEntity = existentEntity;

        if (isNull(scrapedEntity) || scrapedEntity.equals(existentEntity)) {
            this.state = State.IGNORE;
        } else if (isNull(existentEntity)) {
            this.state = State.CREATE;
        } else if (!scrapedEntity.equals(existentEntity)) {
            this.state = State.UPDATE;
        }
    }

    public static enum State {
        CREATE, UPDATE, IGNORE
    }
}
