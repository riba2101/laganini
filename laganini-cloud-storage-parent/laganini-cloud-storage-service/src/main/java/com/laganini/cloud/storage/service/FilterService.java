package com.laganini.cloud.storage.service;

import com.laganini.cloud.storage.connector.model.Filter;
import com.laganini.cloud.storage.connector.model.FilteredAndSorted;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface FilterService<ID, ENTITY> {

    Mono<FilteredAndSorted<ENTITY>> filter(Filter filter);

    Flux<ENTITY> findAll(Collection<ID> ids);

    Mono<ENTITY> find(ID id);

}
