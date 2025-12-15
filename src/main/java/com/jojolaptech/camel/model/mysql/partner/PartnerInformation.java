package com.jojolaptech.camel.model.mysql.partner;

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
@Table(name = "partner_information")
@Getter
@Setter
public class PartnerInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_name")
    private String cName;

    @Column(name = "c_address")
    private String cAddress;

    @Column(name = "c_city")
    private String cCity;

    @Column(name = "c_po_box")
    private String cPoBox;

    @Column(name = "c_fax")
    private String cFax;

    @Column(name = "c_email", unique = true, nullable = false)
    private String cEmail;

    @Column(name = "c_telephone")
    private String cTelephone;

    @Column(name = "c_website")
    private String cWebsite;

    @Column(name = "c_main_contact_person")
    private String cMainContactPerson;

    @Column(name = "main_person_telephone")
    private String mainPersonTelephone;

    @Column(name = "main_person_email")
    private String mainPersonEmail;

    @Column(name = "ceo")
    private String ceo;

    @Column(name = "product_manager")
    private String productManager;

    @Column(name = "marketing_manager")
    private String marketingManager;

    @Column(name = "established")
    private Instant established;

    @Column(name = "c_additional_office")
    private String cAdditionalOffice;

    @Column(name = "c_annual_sales")
    private String cAnnualSales;

    @Column(name = "c_main_business")
    private String cMainBusiness;

    @Column(name = "c_projected_revenue_this_year")
    private String cProjectedRevenueThisYear;

    @Column(name = "c_total_employees")
    private Integer cTotalEmployees;

    @Column(name = "membership_reps")
    private String membershipReps;

    @Column(name = "training")
    private String training;

    @Column(name = "membership_reps_tender_notice")
    private String membershipRepsTenderNotice;

    @Column(name = "support_staffs_tender_notice")
    private String supportStaffsTenderNotice;

    @Column(name = "current_providing_products_services")
    private String currentProvidingProductsServices;

    @Column(name = "current_business_relationship")
    private String currentBusinessRelationship;

    @Column(name = "subscriber_for_tender_notice")
    private String subscriberForTenderNotice;

    @Column(name = "cause_to_use_tender")
    private String causeToUseTender;

    @Column(name = "geo_area")
    private String geoArea;

    @Column(name = "customer_reference_client_name")
    private String customerReferenceClientName;

    @Column(name = "customer_reference_address")
    private String customerReferenceAddress;

    @Column(name = "customer_reference_city")
    private String customerReferenceCity;

    @Column(name = "customer_reference_email")
    private String customerReferenceEmail;

    @Column(name = "customer_reference_telephone")
    private String customerReferenceTelephone;

    @Column(name = "created_date")
    private Instant createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "partner_status")
    private PartnerStatus partnerStatus;

    @Column(name = "rule_assign_date")
    private Instant ruleAssignDate;

    @Column(name = "partner_unique_code")
    private String partnerUniqueCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @OneToMany(mappedBy = "partnerInformation")
    private Set<PartnerRule> partnerRules = new HashSet<>();

    @OneToMany(mappedBy = "partnerInformation")
    private Set<PartnerClient> partnerClients = new HashSet<>();
}

