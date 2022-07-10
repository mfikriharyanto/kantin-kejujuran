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
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Lob
    @Column(name = "image", nullable = false)
    private String image;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false, updatable = false)
    private Date dateCreated;

    public Product(String name, String description, Integer price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

}
