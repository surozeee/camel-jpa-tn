package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "news_paper")
@Getter
@Setter
public class NewsPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "news_name")
    private String name;
}