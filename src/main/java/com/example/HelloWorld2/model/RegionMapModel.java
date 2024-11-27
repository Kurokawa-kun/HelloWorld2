package com.example.HelloWorld2.model;
import java.io.*;
import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "helloworld2", name = "regionmap")
@IdClass(RegionMapModel.RegionMapModelPK.class)
public class RegionMapModel
{
    @Id
    @Column(name = "region")
    private String region;
    @Id
    @Column(name = "prefecture")
    private String prefecture;
    
    //  プライマリキー
    @Data
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class RegionMapModelPK implements Serializable
    {
        @Column(name = "region")
        private String region;
        @Column(name = "prefecture")
        private String prefecture;
    }    
}
