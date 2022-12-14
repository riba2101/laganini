package org.laganini.cloud.storage.entity;

import java.time.LocalDateTime;

public interface PurgeableEntity<ID>
        extends IdentityEntity<ID>
{

    LocalDateTime getDeletedAt();

    void setDeletedAt(LocalDateTime deletedAt);

}
