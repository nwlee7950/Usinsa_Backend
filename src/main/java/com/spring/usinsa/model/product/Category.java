package com.spring.usinsa.model.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Builder.Default
    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
    private List<SubCategory> subCategoryList = new ArrayList<SubCategory>();

    public void addSubCategory(SubCategory subCategory) {
        subCategoryList.add(subCategory);
        subCategory.setCategory(this);
    }
}
