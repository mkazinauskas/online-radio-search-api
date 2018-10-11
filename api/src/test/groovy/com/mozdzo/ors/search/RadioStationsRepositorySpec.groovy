package com.mozdzo.ors.search

import com.mozdzo.ors.resources.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class RadioStationsRepositorySpec extends IntegrationSpec {
    @Autowired
    RadioStationsRepository repository

    void 'should create radio station document in repository'() {
        when:
            repository.save(new RadioStationDocument(id: 'asasdasd',
                    title: 'title',
                    author: 'author',
                    releaseDate: '1212312'))
        then:
            repository.findById('asasdasd').get().title == 'title'
    }
}
