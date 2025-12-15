package com.jojolaptech.camel.model.postgres.iam;

import com.tendernotice.identityservice.usermodule.enums.ContactTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "google_contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private UUID ownerUserId;  // Who owns these contacts (your user)
    private String ownerUserId;  // Who owns these contacts (your user)

    private String name;
    private String email;
    private String phone;
    private ContactTypeEnum contactType;
}
