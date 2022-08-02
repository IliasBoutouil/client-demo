package com.valueIt.demo.services;

import com.valueIt.demo.exceptions.IllegalPageRequestArgumentsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
public class CustomPageRequest {
    private final int page;
    private final int size;

    public CustomPageRequest(int page, int size) {
        if (page >= 0)
            this.page = page;
        else {
            throw new IllegalPageRequestArgumentsException("the page number must be greater than or equal 0");
        }
        if (size >= 1)
            this.size = size;
        else
            throw new IllegalPageRequestArgumentsException("the page size must be greater than or equal 1");
    }

    public Pageable getPageable() {
        return PageRequest.of(this.page, this.size);
    }

}
