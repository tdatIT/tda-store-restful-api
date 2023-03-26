package com.webapp.tdastore.dto;

import com.webapp.tdastore.entities.Category;
import com.webapp.tdastore.entities.Product;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;

@Data
public class CategoryDTO {

    private long categoryId;

    @NotEmpty
    @Length(min = 6)
    private String name;

    private String images;

    @NotEmpty
    @Length(min = 50)
    private String description;

    private Timestamp createDate;

    private Timestamp updateDate;

    private boolean status;

    private List<Product> product;

    private MultipartFile image;

}
