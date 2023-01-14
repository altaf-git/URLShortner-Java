package com.urlShortner.persistence.URL;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("URL")
public record URL(@Column("uuid") UUID uuid,
                  @Column("longUrl") String longUrl,
                  @Column("shortUrl") String shortUrl,
                  @CreatedBy @Column("created_by") String createdBy,
                  @CreatedDate @Column("created_date") Instant createdDate,
                  @Column("modified_by") String modifiedBy,
                  @Column("modified_date") Instant modifiedDate,
                  @Column("comments") String comments,
                  @Column("is_valid") boolean isValid) {
}
