package com.laganini.cloud.storage.connector.service;

import com.laganini.cloud.storage.connector.model.Fetchable;
import com.laganini.cloud.validation.group.Create;
import com.laganini.cloud.validation.group.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PersistableEndpoint<ID, IN extends Fetchable<ID>, OUT extends Fetchable<ID>> {

    @Validated(Create.class)
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    Mono<OUT> create(@RequestBody @Valid @NotNull IN model);

    @Validated(Update.class)
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    Mono<OUT> update(@RequestBody @Valid @NotNull IN model);

}
