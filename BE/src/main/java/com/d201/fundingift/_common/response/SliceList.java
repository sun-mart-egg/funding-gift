package com.d201.fundingift._common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class SliceList<T> {

    private List<T> data;
    private int page;
    private int size;
    private boolean hasNext;

    @Builder
    private SliceList(List<T> data, int page, int size, boolean hasNext) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
    }

    public static SliceList of(List<Object> data, int page, int size, boolean hasNext) {
        return builder()
                .data(data)
                .page(page)
                .size(size)
                .hasNext(hasNext)
                .build();
    }

    public static SliceList from(List<Object> data, Pageable pageable, boolean hasNext) {
        return builder()
                .data(data)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .hasNext(hasNext)
                .build();
    }

}
