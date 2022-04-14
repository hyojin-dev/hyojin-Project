package com.example.janghj.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends Timestamped {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, Start, Arrived

    public Delivery(Order order, Address address) {
        this.order = order;
        if (address == null) {
            this.address = order.getUser().getAddress();
        }
        this.address = address;
        this.status = DeliveryStatus.READY;
    }

    public void setStatus() {
        this.status = DeliveryStatus.READY;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
