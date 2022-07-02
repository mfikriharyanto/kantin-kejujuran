package com.project.kantinkejujuran.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "balance_box")
public class BalanceBox {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false, updatable = false)
    private Date dateCreated;

    @Column(name = "change", nullable = false)
    private Long change;

    @Column(name = "total", nullable = false)
    private Long total;

    public BalanceBox(Long change, Long total) {
        this.change = change;
        this.total = total;
    }
}
