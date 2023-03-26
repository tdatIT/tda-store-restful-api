package com.webapp.tdastore.entities;

import com.webapp.tdastore.payload.ProvinceAPI;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;

@Table(name = "user_address")
@Entity
@Getter
@Setter
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column
    private String phone;

    @Column(length = 500)
    private String detail;

    @Column
    private int province;

    @Column
    private int district;

    @Column
    private int ward;

    @Column
    private String commune;

    @Column
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getAPIName() {
        String url_province = "https://provinces.open-api.vn/api/p/" + province;
        String url_district = "https://provinces.open-api.vn/api/d/" + district;
        String url_ward = "https://provinces.open-api.vn/api/w/" + ward;
        //call api and mapping to object
        RestTemplate apiCall = new RestTemplate();
        ProvinceAPI province_data = apiCall.getForObject(url_province, ProvinceAPI.class);
        ProvinceAPI district_data = apiCall.getForObject(url_district, ProvinceAPI.class);
        ProvinceAPI ward_data = apiCall.getForObject(url_ward, ProvinceAPI.class);
        return province_data.getName() + " - " + district_data.getName() + " - " + ward_data.getName();
    }
}
