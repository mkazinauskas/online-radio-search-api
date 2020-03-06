package com.modzo.ors.web.web.components.common;

import org.springframework.hateoas.PagedModel;

import java.util.Collection;

public class Paged<T> {

    private final Collection<T> content;

    private final EnhancedPageMetadata metadata;

    public Paged(Collection<T> content, EnhancedPageMetadata metadata) {
        this.content = content;
        this.metadata = metadata;
    }

    public Paged(Collection<T> content, PagedModel.PageMetadata metadata) {
        this.content = content;
        this.metadata = new EnhancedPageMetadata(metadata);
    }

    public Collection<T> getContent() {
        return content;
    }

    public EnhancedPageMetadata getMetadata() {
        return metadata;
    }
}
