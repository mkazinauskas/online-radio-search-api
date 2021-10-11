package com.modzo.ors.last.searches.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchedQueries extends JpaRepository<SearchedQuery, Long> {

    void deleteAllByQueryAndType(String query, SearchedQuery.Type type);

}
