package com.modzo.ors.configuration.hibernate;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class PostgresqlILIKE implements MetadataBuilderContributor {

    public static final String ILIKE_TITLE = "ILIKE";

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction(ILIKE_TITLE,
                new SQLFunctionTemplate(
                        StandardBasicTypes.BOOLEAN,
                        "(?1 ILIKE ?2)",
                        true
                )
        );
    }

}
