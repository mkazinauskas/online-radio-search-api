package com.modzo.ors.web.components.common;

import org.springframework.hateoas.PagedModel;

public class EnhancedPageMetadata extends PagedModel.PageMetadata {

    public EnhancedPageMetadata(PagedModel.PageMetadata metadata) {
        super(metadata.getSize(), metadata.getNumber(), metadata.getTotalElements(), metadata.getTotalPages());
    }

    public long previousPage() {
        long current = this.getNumber();
        if (hasPrevious()) {
            return current - 1;
        } else {
            return 0;
        }
    }

    public long nextPage() {
        long current = this.getNumber();
        if (hasNext()) {
            return current + 1;
        } else {
            return current;
        }
    }

    public boolean navigationPossible(){
        return hasPrevious() || hasNext();
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public boolean hasNext() {
        return this.getNumber() < this.getTotalPages() - 1;
    }
}
