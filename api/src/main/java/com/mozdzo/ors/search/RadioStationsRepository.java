package com.mozdzo.ors.search;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RadioStationsRepository extends ElasticsearchRepository<RadioStationDocument, String> {

}
