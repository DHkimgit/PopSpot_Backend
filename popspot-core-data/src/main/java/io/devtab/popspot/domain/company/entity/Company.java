package io.devtab.popspot.domain.company.entity;

import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SoftDelete;

import io.devtab.popspot.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Employee> members = new ArrayList<>();

    @Column(name = "business_registration_number", unique = true)
    private String businessRegistrationNumber;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @SoftDelete
    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isDeleted;

    @Builder
    public Company(String name, String businessRegistrationNumber, String contactPhone, String contactEmail,
        Boolean isDeleted) {
        this.name = name;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.isDeleted = isDeleted;
    }
}
