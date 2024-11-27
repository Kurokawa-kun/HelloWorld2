package com.example.HelloWorld2.repository;
import com.example.HelloWorld2.model.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

@Repository
public interface FamilyNameRepository extends JpaRepository<FamilyNameModel, FamilyNameModel.FamilyNameModelPK>
{
    /**
    * 全国ランキングから指定した苗字の順位と人口を返却する（全国と全都道府県が対象）。
    * @param name 苗字
    * @return 人口と順位
    */
    @Query(value = "SELECT t FROM FamilyNameModel t WHERE t.name = :name ORDER BY t.population DESC")
    Optional<List<FamilyNameModel>> findByName(@Param("name") String name);
    
    /**
    * 指定した苗字の人口の総和を返却する（指定した地域に含まれる都道府県のみ）。
    * @param name 苗字
    * @param region 対象地域
    * @return 人口
    */
    @Query(value = "SELECT SUM(population) FROM FamilyNameModel t JOIN RegionMapModel r ON t.region = r.prefecture WHERE t.name IN :name AND r.region = :region")
    Optional<Integer> getPopulation(@Param("name") String name, @Param("region") String region);
}
