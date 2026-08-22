package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.DayWiseSewingProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DayWiseSewingProductionRepository extends JpaRepository<DayWiseSewingProduction, Long> {

//    All production entries of a Sewing Plan
    List<DayWiseSewingProduction>
    findBySewingPlanId(Long sewingPlanId);
//      Used for Line Progress,Achieved So Far, Remaining
    List<DayWiseSewingProduction>
    findBySewingPlanIdAndProductionLineId(
            Long sewingPlanId,
            Long productionLineId
    );

// Date onujaye Details page e dekhano
    List<DayWiseSewingProduction> findBySewingPlanIdOrderByDateAsc(Long sewingPlanId);

    @Query("""
select coalesce(sum(d.achievedQuantity),0)
from DayWiseSewingProduction d
where d.sewingPlan.id=:planId
and d.productionLine.id=:lineId
""")
    Integer getAchievedQty(Long planId, Long lineId);
    @Query("""
select coalesce(sum(d.rejectionQty),0)
from DayWiseSewingProduction d
where d.sewingPlan.id=:planId
and d.productionLine.id=:lineId
""")
    Integer getRejectQty(Long planId, Long lineId);


    @Query("""
select coalesce(sum(d.achievedQuantity),0)
from DayWiseSewingProduction d
where d.date=:date
""")
    Integer getTodaySewing(LocalDate date);

    @Query("""
select coalesce(sum(d.rejectionQty),0)
from DayWiseSewingProduction d
where d.date=:date
""")
    Integer getTodayReject(LocalDate date);

    @Query("""
select d
from DayWiseSewingProduction d
where d.date=:date
order by d.styleNo
""")
    List<DayWiseSewingProduction> getTodayProductions(LocalDate date);

    @Query("""
SELECT COALESCE(SUM(d.achievedQuantity),0)
FROM DayWiseSewingProduction d
""")
    Long getTotalProduction();

}
