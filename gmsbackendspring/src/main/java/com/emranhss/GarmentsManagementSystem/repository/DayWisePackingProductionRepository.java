package com.emranhss.GarmentsManagementSystem.repository;

import com.emranhss.GarmentsManagementSystem.entity.DayWisePackingProduction;
import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DayWisePackingProductionRepository extends JpaRepository<DayWisePackingProduction, Long> {

//     All Productions   of One Packing Plan


    List<DayWisePackingProduction> findByPackingPlanIdOrderByDateAsc(
            Long packingPlanId);
//2ta method same hoye gese nicherta use hobe
    @Query("""
select coalesce(sum(d.todayPackedQty),0)
from DayWisePackingProduction d
where d.date=:date
""")
    Integer getTodayPacking(LocalDate date);

    @Query("""
select coalesce(sum(d.todayPackedQty),0)
from DayWisePackingProduction d
where d.date=:date
""")
    Integer getTodayPacked(LocalDate date);

    @Query("""
select coalesce(sum(d.todayRejectQty),0)
from DayWisePackingProduction d
where d.date=:date
""")
    Integer getTodayReject(LocalDate date);

    @Query("""
select d
from DayWisePackingProduction d
where d.date=:date
order by d.styleNo
""")
    List<DayWisePackingProduction> getTodayProductions(LocalDate date);

    @Query("""
select distinct d.packingPlan
from DayWisePackingProduction d
order by d.packingPlan.packingPlanId
""")
    List<PackingPlan> getPackingPlansWithProduction();



    @Query("""
SELECT COALESCE(SUM(d.todayPackedQty),0)
FROM DayWisePackingProduction d
""")
    Long getTotalProduction();
}
