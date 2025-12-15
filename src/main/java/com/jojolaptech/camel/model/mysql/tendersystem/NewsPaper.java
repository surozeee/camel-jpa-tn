package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private String name;
}

