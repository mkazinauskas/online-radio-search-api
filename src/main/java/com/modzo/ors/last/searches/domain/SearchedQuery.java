package com.modzo.ors.last.searches.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "searched_queries")
public class SearchedQuery {

    public enum Type {
        SONG("song"), RADIO_STATION("radio_station"), GENRE("genre");

        private final String title;

        Type(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    @Id
    @GeneratedValue(generator = "searched_queries_sequence", strategy = SEQUENCE)
    @SequenceGenerator(
            name = "searched_queries_sequence",
            sequenceName = "searched_queries_sequence",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "created", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now();

    @Column(name = "query", length = 100, unique = true, nullable = false)
    private String query;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    SearchedQuery() {
    }

    public SearchedQuery(String query, Type type) {
        this.query = query;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
