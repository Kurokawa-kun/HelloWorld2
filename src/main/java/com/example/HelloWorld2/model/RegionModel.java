package com.example.HelloWorld2.model;
import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "helloworld2", name = "region")
public class RegionModel
{
    @Id
    @Column(name = "name")
    private String name;
}
