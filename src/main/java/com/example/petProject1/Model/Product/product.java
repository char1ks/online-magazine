package com.example.petProject1.Model.Product;

import com.example.petProject1.Model.User.User;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "product")
public class product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nomination")
    private String nomination;

    @Column(name = "price")
    private String price;

    @Column(name = "description")
    private String description;

    @Column(name = "sell_by_concrete_time")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sellBy;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private productType productType;

    @ManyToOne
    @JoinColumn(name = "ownerId",referencedColumnName = "id")
    private User productOwner;

    public product() {}

    public product(String nomination, String price, String description, Date sellBy, com.example.petProject1.Model.Product.productType productType, User productOwner) {
        this.nomination = nomination;
        this.price = price;
        this.description = description;
        this.sellBy = sellBy;
        this.productType = productType;
        this.productOwner = productOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getSellBy() {
        return sellBy;
    }

    public void setSellBy(Date sellBy) {
        this.sellBy = sellBy;
    }

    public com.example.petProject1.Model.Product.productType getProductType() {
        return productType;
    }

    public void setProductType(com.example.petProject1.Model.Product.productType productType) {
        this.productType = productType;
    }

    public User getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(User productOwner) {
        this.productOwner = productOwner;
    }
}
