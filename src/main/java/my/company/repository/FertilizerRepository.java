package my.company.repository;

import my.company.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FertilizerRepository extends JpaRepository<Fertilizer, Integer> {
    @Modifying
    @Query("SELECT m FROM fertilizer m WHERE m.user.id=:userId ORDER BY m.localDate")
    List<Fertilizer> findAll(@Param("userId") int userId);

}
