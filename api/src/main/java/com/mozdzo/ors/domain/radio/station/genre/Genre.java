package com.mozdzo.ors.domain.radio.station.genre;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(generator = "genres_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "genres_sequence", sequenceName = "genres_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 100, unique = true, nullable = false)
    private String title;

    Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
