package io.github.xausky.cfmpe;

import org.springframework.data.jpa.repository.Query;

/**
 * Created by xausky on 11/4/16.
 */
public interface LogExtRepository extends LogRepository {
    @Query("select i from Log i where i.owner=?1")
    public Iterable<Log> findByOwner(String owner);
}
