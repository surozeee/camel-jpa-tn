package com.jojolaptech.camel.model.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notice")
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notice_provider")
    private String noticeProvider;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "publish_date")
    private Instant publishDate;

    @Column(name = "last_date")
    private Instant lastDate;

    @Column
    private String remark;

    @Column(name = "imgname")
    private String imgName;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "post_time")
    private Instant postTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newspaper_id")
    private NewsPaper newsPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SecUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_service_id")
    private ProductService productService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tender_classification_id")
    private TenderClassification tenderClassification;

    @OneToMany(mappedBy = "notice")
    private Set<Pin> pins = new HashSet<>();

    @OneToMany(mappedBy = "notice")
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "notice")
    private Set<UserNotes> userNotes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "mail_flag")
    private String mailFlag;

    @Column(name = "mail_time")
    private Instant mailTime;

    @Column(name = "image_location")
    private String imageLocation;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_notice_id")
    private Notice linkedNotice;

    @Column
    private String document;
}
package com.jojolaptech.camel.model.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notice")
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notice_provider")
    private String noticeProvider;

    @Column(length = 5000)
    private String description;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "last_date")
    private Date lastDate;

    @Column(length = 2000)
    private String remark;

    @Column(name = "imgname")
    private String imgname;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "post_time")
    private Date postTime;

    @Column(name = "mail_flag")
    private String mailFlag;

    @Column(name = "mail_time")
    private Date mailTime;

    @Column(name = "image_location")
    private String imageLocation;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @Column(name = "document")
    private String document;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_paper_id")
    private NewsPaper newsPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SecUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_service_id")
    private ProductService productService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tender_classification_id")
    private TenderClassification tenderClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_notice_id")
    private Notice linkedNotice;

    @OneToMany(mappedBy = "notice")
    private Set<Pin> pins = new HashSet<>();

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "notice")
    private Set<UserNotes> userNotes = new HashSet<>();
}

