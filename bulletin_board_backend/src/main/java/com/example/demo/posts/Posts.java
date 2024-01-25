package com.example.demo.posts;


import java.util.Date;
import java.util.List;

import com.example.demo.comments.Comments;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_posts")
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_EXTENSION")
    private String fileExtension;
    @Column(name = "POSTED_DATA")
    private Date postedDate;
    
    @Lob
    @Column(name="IMAGE_DATA",length = 1000)
    private byte[] imageData;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User user;
    
    
    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comments> comments;


}
