package com.laganini.cloud.storage.service;

import com.laganini.cloud.storage.dto.Owner;
import com.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Mono;

public interface OwnerService {

    Mono<Owner> resolve();

    Mono<Owner> resolve(Long ownerId);

    <T> Mono<T> runAs(Owner owner, Mono<T> delegate);

    boolean isSystem(Owner owner);

    boolean isAdmin(Owner owner);

    boolean isOwner(Owner owner, OwnedEntity<?> owned);

}
