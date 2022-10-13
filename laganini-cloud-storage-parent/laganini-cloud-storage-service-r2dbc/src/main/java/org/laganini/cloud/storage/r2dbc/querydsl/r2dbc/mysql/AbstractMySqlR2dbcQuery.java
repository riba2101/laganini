package org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.mysql;

import com.google.common.base.Joiner;
import com.querydsl.core.JoinFlag;
import com.querydsl.core.QueryFlag;
import com.querydsl.core.QueryMetadata;
import com.querydsl.sql.Configuration;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.AbstractR2dbcQuery;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.R2dbcConnectionProvider;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.R2dbcQuery;

import java.io.File;

/**
 * {@link MySqlR2dbcQuery} provides MySQL related extensions to {@link R2dbcQuery}.
 *
 * @param <T> result type
 * @param <C> the concrete subtype
 * @see R2dbcQuery
 */
public abstract class AbstractMySqlR2dbcQuery<T, C extends AbstractMySqlR2dbcQuery<T, C>>
        extends AbstractR2dbcQuery<T, C>
{

    protected static final String WITH_ROLLUP = "\nwith rollup ";

    protected static final String STRAIGHT_JOIN = "straight_join ";

    protected static final String SQL_SMALL_RESULT = "sql_small_result ";

    protected static final String SQL_NO_CACHE = "sql_no_cache ";

    protected static final String LOCK_IN_SHARE_MODE = "\nlock in share mode ";

    protected static final String HIGH_PRIORITY = "high_priority ";

    protected static final String SQL_CALC_FOUND_ROWS = "sql_calc_found_rows ";

    protected static final String SQL_CACHE = "sql_cache ";

    protected static final String SQL_BUFFER_RESULT = "sql_buffer_result ";

    protected static final String SQL_BIG_RESULT = "sql_big_result ";

    protected static final Joiner JOINER = Joiner.on(", ");

    public AbstractMySqlR2dbcQuery(
            R2dbcConnectionProvider connProvider,
            Configuration configuration,
            QueryMetadata metadata
    )
    {
        super(connProvider, configuration, metadata);
    }

    /**
     * For SQL_BIG_RESULT, MySQL directly uses disk-based temporary tables if needed, and prefers
     * sorting to using a temporary table with a key on the GROUP BY elements.
     *
     * @return the current object
     */
    public C bigResult() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, SQL_BIG_RESULT);
    }

    /**
     * SQL_BUFFER_RESULT forces the result to be put into a temporary table. This helps MySQL free
     * the table locks early and helps in cases where it takes a long time to send the result set
     * to the client. This option can be used only for top-level SELECT statements, not for
     * subqueries or following UNION.
     *
     * @return the current object
     */
    public C bufferResult() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, SQL_BUFFER_RESULT);
    }

    /**
     * SQL_CACHE tells MySQL to store the result in the query cache if it is cacheable and the value
     * of the query_cache_type system variable is 2 or DEMAND.
     *
     * @return the current object
     */
    public C cache() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, SQL_CACHE);
    }

    /**
     * SQL_CALC_FOUND_ROWS tells MySQL to calculate how many rows there would be in the result set,
     * disregarding any LIMIT clause. The number of rows can then be retrieved with SELECT FOUND_ROWS().
     *
     * @return the current object
     */
    public C calcFoundRows() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, SQL_CALC_FOUND_ROWS);
    }

    /**
     * HIGH_PRIORITY gives the SELECT higher priority than a statement that updates a table.
     * You should use this only for queries that are very fast and must be done at once.
     *
     * @return the current object
     */
    public C highPriority() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, HIGH_PRIORITY);
    }

    /**
     * SELECT ... INTO var_list selects column values and stores them into variables.
     *
     * @param var variable name
     * @return the current object
     */
    public C into(String var) {
        return addFlag(QueryFlag.Position.END, "\ninto " + var);
    }

    /**
     * SELECT ... INTO DUMPFILE writes a single row to a file without any formatting.
     *
     * @param file file to write to
     * @return the current object
     */
    public C intoDumpfile(File file) {
        return addFlag(QueryFlag.Position.END, "\ninto dumpfile '" + file.getPath() + "'");
    }

    /**
     * SELECT ... INTO OUTFILE writes the selected rows to a file. Column and line terminators c
     * an be specified to produce a specific output format.
     *
     * @param file file to write to
     * @return the current object
     */
    public C intoOutfile(File file) {
        return addFlag(QueryFlag.Position.END, "\ninto outfile '" + file.getPath() + "'");
    }

    /**
     * Using LOCK IN SHARE MODE sets a shared lock that permits other transactions to read the examined
     * rows but not to update or delete them.
     *
     * @return the current object
     */
    public C lockInShareMode() {
        return addFlag(QueryFlag.Position.END, LOCK_IN_SHARE_MODE);
    }

    /**
     * With SQL_NO_CACHE, the server does not use the query cache. It neither checks the query cache
     * to see whether the result is already cached, nor does it cache the query result.
     *
     * @return the current object
     */
    public C noCache() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, SQL_NO_CACHE);
    }

    /**
     * For SQL_SMALL_RESULT, MySQL uses fast temporary tables to store the resulting table instead
     * of using sorting. This should not normally be needed.
     *
     * @return the current object
     */
    public C smallResult() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, SQL_SMALL_RESULT);
    }

    /**
     * STRAIGHT_JOIN forces the optimizer to join the tables in the order in which they are listed
     * in the FROM clause. You can use this to speed up a query if the optimizer joins the tables
     * in nonoptimal order. STRAIGHT_JOIN also can be used in the table_references list.
     *
     * @return the current object
     */
    public C straightJoin() {
        return addFlag(QueryFlag.Position.AFTER_SELECT, STRAIGHT_JOIN);
    }

    /**
     * You can use FORCE INDEX, which acts like USE INDEX (index_list) but with the addition that a
     * table scan is assumed to be very expensive. In other words, a table scan is used only if there
     * is no way to use one of the given indexes to find rows in the table.
     *
     * @param indexes index names
     * @return the current object
     */
    public C forceIndex(String... indexes) {
        return addJoinFlag(" force index (" + JOINER.join(indexes) + ")", JoinFlag.Position.END);
    }

    /**
     * The alternative syntax IGNORE INDEX (index_list) can be used to tell MySQL to not use some
     * particular index or indexes.
     *
     * @param indexes index names
     * @return the current object
     */
    public C ignoreIndex(String... indexes) {
        return addJoinFlag(" ignore index (" + JOINER.join(indexes) + ")", JoinFlag.Position.END);
    }

    /**
     * By specifying USE INDEX (index_list), you can tell MySQL to use only one of the named indexes
     * to find rows in the table.
     *
     * @param indexes index names
     * @return the current object
     */
    public C useIndex(String... indexes) {
        return addJoinFlag(" use index (" + JOINER.join(indexes) + ")", JoinFlag.Position.END);
    }

    /**
     * The GROUP BY clause permits a WITH ROLLUP modifier that causes extra rows to be added to the
     * summary output. These rows represent higher-level (or super-aggregate) summary operations.
     * ROLLUP thus enables you to answer questions at multiple levels of analysis with a single query.
     * It can be used, for example, to provide support for OLAP (Online Analytical Processing) operations.
     *
     * @return the current object
     */
    public C withRollup() {
        return addFlag(QueryFlag.Position.AFTER_GROUP_BY, WITH_ROLLUP);
    }

}
