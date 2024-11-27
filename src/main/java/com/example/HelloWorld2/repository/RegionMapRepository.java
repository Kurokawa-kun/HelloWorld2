package com.example.HelloWorld2.repository;
import com.example.HelloWorld2.model.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

@Repository
public interface RegionMapRepository extends JpaRepository<RegionMapModel, RegionMapModel.RegionMapModelPK>
{
    /**
    * 指定した地域に含まれる都道府県を返却する
    * @param region 対象地域
    * @return 都道府県のリスト
    */
    @Query(value = "SELECT prefecture FROM RegionMapModel t WHERE t.region = :region")
    Optional<List<String>> findByRegion(@Param("region") String region);
}
