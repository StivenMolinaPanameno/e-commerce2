package com.project.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.security.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH, targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;
    @Column(columnDefinition = "DATETIME")
    private Date date;
    private BigDecimal total;
    private Boolean enable=Boolean.TRUE;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();


    public Order(BigDecimal total, Date date, UserEntity client) {
        this.total = total;
        this.date = date;
        this.user = client;
    }
    public Order(BigDecimal total, Date date, UserEntity client, Boolean enabled) {
        this.total = total;
        this.date = date;
        this.user = client;
        this.enable = enabled;
    }
}
