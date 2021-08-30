package com.modzo.ors.configuration.hibernate;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.BooleanType;
import org.hibernate.type.StandardBasicTypes;

public class TsVectorMetaBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction("fts",
                new SQLFunctionTemplate(
                        StandardBasicTypes.BOOLEAN,
                        "(?1 ILIKE ?2)",
                        true
                )
        );
    }

}
