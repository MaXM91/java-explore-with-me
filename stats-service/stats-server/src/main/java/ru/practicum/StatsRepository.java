package ru.practicum;

import dto.ViewStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.entity.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;
/**
 * StatsRepository.
 */

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long>, QuerydslPredicateExecutor<EndpointHit> {
    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
           "FROM EndpointHit AS eh " +
           "WHERE eh.uri IN (:uris) AND timestamp BETWEEN :start AND :end " +
           "GROUP BY eh.app, eh.uri " +
           "ORDER BY hits ")
    List<ViewStatsDto> getAllStatsByTime(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) AND timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits ")
    List<ViewStatsDto> getAllStatsByTimeAndDistinct(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits ")
    List<ViewStatsDto> getAllStats(@Param("uris") List<String> uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits ")
    List<ViewStatsDto> getAllStatsDistinct(@Param("uris") List<String> uris);
}
