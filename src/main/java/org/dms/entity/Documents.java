package org.dms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dms.enums.DocumentStatus;

import java.util.Date;

@Entity
@Table(name="documents")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;

    @Column(name="upload_date")
    private Date uploadDate;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String documentUrl;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;
}
