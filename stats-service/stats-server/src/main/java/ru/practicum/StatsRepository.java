package ru.practicum;

import dto.ViewStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.entity.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * StatsRepository.
 */

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Integer>, JpaSpecificationExecutor<EndpointHit> {
    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) AND timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStartAndEndAndUrisAndUnique(@Param("start") LocalDateTime start,
                                                            @Param("end") LocalDateTime end,
                                                            @Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) AND timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStartAndEndAndUris(@Param("start") LocalDateTime start,
                                                   @Param("end") LocalDateTime end,
                                                   @Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStartAndEndAndUnique(@Param("start") LocalDateTime start,
                                                     @Param("end") LocalDateTime end);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE timestamp BETWEEN :start AND :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStartAndEnd(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllUrisAndUnique(@Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByUris(@Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllUnique();

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> getAll();

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) AND timestamp > :start " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStartAndUrisAndUnique(@Param("start") LocalDateTime start,
                                                      @Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) AND timestamp > :start " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStartAndUris(@Param("start") LocalDateTime start,
                                             @Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE timestamp > :start " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStartAndUnique(@Param("start") LocalDateTime start);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE timestamp > :start " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByStart(@Param("start") LocalDateTime start);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) AND timestamp < :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByEndAndUrisAndUnique(@Param("end") LocalDateTime end,
                                                    @Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri IN (:uris) AND timestamp < :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByEndAndUris(@Param("end") LocalDateTime end,
                                           @Param("uris") String[] uris);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(DISTINCT eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE timestamp < :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByEndAndUnique(@Param("end") LocalDateTime end);

    @Query("SELECT new dto.ViewStatsDto(eh.app AS app, eh.uri AS uri, COUNT(eh.ip) AS hits) " +
            "FROM EndpointHit AS eh " +
            "WHERE timestamp < :end " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY hits DESC ")
    List<ViewStatsDto> findAllByEnd(@Param("end") LocalDateTime end);
}