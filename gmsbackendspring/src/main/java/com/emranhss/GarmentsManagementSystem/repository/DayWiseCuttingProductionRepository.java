package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingHistoryDetailsResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingHistoryResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingHistorySummaryResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseCuttingProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DayWiseCuttingProductionRepository extends JpaRepository<DayWiseCuttingProduction, Long> {
    List<DayWiseCuttingProduction> findByCuttingPlanId(Long cuttingPlanId);
    Integer countByCuttingPlanId(Long cuttingPlanId);
    List<DayWiseCuttingProduction> findByCuttingPlanIdOrderByDateAsc(Long cuttingPlanId);

    @Query("""
select coalesce(sum(d.actualCutPieces),0)
from DayWiseCuttingProduction d
where d.cuttingPlan.id=:planId
""")
    Integer getTotalActualCut(Long planId);
    @Query("""
select coalesce(sum(d.rejectPieces),0)
from DayWiseCuttingProduction d
where d.cuttingPlan.id=:planId
""")
    Integer getTotalReject(Long planId);


    @Query("""
select coalesce(sum(d.actualCutPieces),0)
from DayWiseCuttingProduction d
where d.date=:date
""")
    Integer getTodayCutting(LocalDate date);


    @Query("""
select coalesce(sum(d.rejectPieces),0)
from DayWiseCuttingProduction d
where d.date=:date
""")
    Integer getTodayReject(LocalDate date);

    @Query("""
select d
from DayWiseCuttingProduction d
where d.date=:date
order by d.styleNo
""")
    List<DayWiseCuttingProduction> getTodayProductions(LocalDate date);



    @Query("""
SELECT COALESCE(SUM(d.actualCutPieces),0)
FROM DayWiseCuttingProduction d
""")
    Long getTotalProduction();



//Query for Android
    @Query("""
SELECT new com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingHistoryResponseDto(

    cp.id,
    cp.cuttingPlanId,
    d.styleNo,
    d.date,
    SUM(d.actualCutPieces),
    SUM(d.rejectPieces),
    COUNT(d)

)

FROM DayWiseCuttingProduction d

JOIN d.cuttingPlan cp

GROUP BY
cp.id,
cp.cuttingPlanId,
d.styleNo,
d.date

ORDER BY d.date DESC,
MAX(d.createdAt) DESC
""")
    List<DayWiseCuttingHistoryResponseDto> getHistory();


    @Query("""
SELECT new com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingHistoryResponseDto(

    cp.id,
    cp.cuttingPlanId,
    d.styleNo,
    d.date,
    SUM(d.actualCutPieces),
    SUM(d.rejectPieces),
    COUNT(d)

)

FROM DayWiseCuttingProduction d

JOIN d.cuttingPlan cp

WHERE d.date = :date

GROUP BY
cp.id,
cp.cuttingPlanId,
d.styleNo,
d.date

ORDER BY MAX(d.createdAt) DESC
""")
    List<DayWiseCuttingHistoryResponseDto> getHistoryByDate(LocalDate date);




    @Query("""
SELECT new com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingHistoryDetailsResponseDto(

    d.id,
    d.createdAt,
    d.actualCutPieces,
    d.rejectPieces

)

FROM DayWiseCuttingProduction d

WHERE d.cuttingPlan.id = :cuttingPlanId
AND d.date = :date

ORDER BY d.createdAt ASC
""")
    List<DayWiseCuttingHistoryDetailsResponseDto> getHistoryDetails(
            Long cuttingPlanId,
            LocalDate date
    );


    @Query("""
SELECT new com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingHistorySummaryResponseDto(

    COALESCE(SUM(d.actualCutPieces), 0),

    COALESCE(SUM(d.rejectPieces), 0),

    COUNT(d),

    MAX(d.createdAt)

)

FROM DayWiseCuttingProduction d

WHERE d.cuttingPlan.id = :cuttingPlanId
AND d.date = :date
""")
    DayWiseCuttingHistorySummaryResponseDto getHistorySummary(
            @Param("cuttingPlanId") Long cuttingPlanId,
            @Param("date") LocalDate date
    );
}


