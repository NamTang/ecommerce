package com.example.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Slider implements Serializable {

    private static final long serialVersionUID = -1345626380557589113L;

    @Id
    @GeneratedValue
    private Long sliderId;

    private String sliderUpperData;
    @Column(nullable = false)
    private String sliderTitle;
    private String sliderDescription;
    @Column(nullable = false)
    private String sliderLink;

    @Transient
    private MultipartFile sliderImage;

    public Long getSliderId() {
        return sliderId;
    }

    public void setSliderId(Long sliderId) {
        this.sliderId = sliderId;
    }

    public String getSliderUpperData() {
        return sliderUpperData;
    }

    public void setSliderUpperData(String sliderUpperData) {
        this.sliderUpperData = sliderUpperData;
    }

    public String getSliderTitle() {
        return sliderTitle;
    }

    public void setSliderTitle(String sliderTitle) {
        this.sliderTitle = sliderTitle;
    }

    public String getSliderDescription() {
        return sliderDescription;
    }

    public void setSliderDescription(String sliderDescription) {
        this.sliderDescription = sliderDescription;
    }

    public String getSliderLink() {
        return sliderLink;
    }

    public void setSliderLink(String sliderLink) {
        this.sliderLink = sliderLink;
    }

    public MultipartFile getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(MultipartFile sliderImage) {
        this.sliderImage = sliderImage;
    }
}
