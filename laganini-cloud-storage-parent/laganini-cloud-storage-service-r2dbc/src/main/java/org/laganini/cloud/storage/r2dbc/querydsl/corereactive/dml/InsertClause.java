package org.laganini.cloud.storage.r2dbc.querydsl.corereactive.dml;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.SubQueryExpression;

/**
 * {@code InsertClause} defines a generic interface for Insert clauses
 *
 * @param <C> concrete subtype
 */
public interface InsertClause<C extends InsertClause<C>>
        extends StoreClause<C>
{

    /**
     * Define the columns to be populated
     *
     * @param columns columns to be populated
     * @return the current object
     */
    C columns(Path<?>... columns);

    /**
     * Define the populate via subquery
     *
     * @param subQuery sub query to be used for population
     * @return the current object
     */
    C select(SubQueryExpression<?> subQuery);

    /**
     * Define the value bindings
     *
     * @param v values to be inserted
     * @return the current object
     */
    C values(Object... v);

}

