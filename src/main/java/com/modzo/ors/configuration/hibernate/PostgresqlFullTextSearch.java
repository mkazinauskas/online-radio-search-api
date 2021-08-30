package com.modzo.ors.configuration.hibernate;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class PostgresqlFullTextSearch implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction("full_text_search",
                new SQLFunctionTemplate(
                        StandardBasicTypes.BOOLEAN,
                        "(?1 ILIKE CONCAT('%',REPLACE(?2,' ','%'),'%'))",
                        true
                )
        );
    }

}
