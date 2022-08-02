package com.valueIt.demo.entities.clients;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AppClient client;
    @CreationTimestamp
    private Date createdAt=new Date();

    public Reclamation(String description,AppClient client) {
        this.description = description;
        this.client = client;
    }

    public Reclamation(long id, String description) {
        this.id = id;
        this.description = description;
    }


}
