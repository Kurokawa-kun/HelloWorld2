package com.example.HelloWorld2.repository;
import com.example.HelloWorld2.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface RegionRepository extends JpaRepository<RegionModel, String>
{
}
