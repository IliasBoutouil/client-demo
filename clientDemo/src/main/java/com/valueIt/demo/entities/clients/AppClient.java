package com.valueIt.demo.entities.clients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name="clients")
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class AppClient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotNull
    private String fullName;

    @Column(unique = true)
    @NotNull
    @Email
    private String email;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDay;
    @Column(name = "type")
    private ClientType clientType;
    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    private Set<Reclamation> reclamations=new HashSet<>();
    @CreationTimestamp
    private Date createdAt=new Date();

    public AppClient(long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }
    public AppClient(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }


}
