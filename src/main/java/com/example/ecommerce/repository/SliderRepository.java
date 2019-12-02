package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Slider;

@Repository
public interface SliderRepository extends CrudRepository<Slider, Long> {

}
