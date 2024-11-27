package com.example.HelloWorld2.model;
import java.io.*;
import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "helloworld2", name = "familyname")
@IdClass(FamilyNameModel.FamilyNameModelPK.class)
public class FamilyNameModel implements Comparable<FamilyNameModel>
{
    @Id
    @Column(name = "region")
    private String region;
    @Id
    @Column(name = "name", nullable=false)
    private String name;
    @Column(name = "ranking")
    private int ranking;
    @Column(name = "population")
    private int population;
    
    @Data
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class FamilyNameModelPK implements Serializable
    {
        @Column(name = "region")
        private String region;
        @Column(name = "name")
        private String name;
    }
    
    @Override
    public int compareTo(FamilyNameModel familyNameModel)
    {
        return Integer.compare(population, familyNameModel.population);
    }
}
