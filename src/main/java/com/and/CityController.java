package com.and;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ApplicationConfig applicationConfig;

    @PostConstruct
    public void init() {
        System.out.println(applicationConfig.getName() + " is run");
        System.out.println("Service version: " + applicationConfig.getVersion());
        System.out.println("There are " + applicationConfig.getImplementations() + " implementations in service");
        cityRepository.save(new City("Dublin", "The best city"));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<City> listCity() {
        return cityRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public City getCity(@PathVariable("id") Long id) {
        return cityRepository.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCity(@RequestBody City city) {
        cityRepository.save(new City(city.getName(), city.getDescription()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCity(@PathVariable("id") Long id, @RequestBody City city) {
        City existingCity = cityRepository.findOne(id);
        existingCity.setName(city.getName());
        existingCity.setDescription(city.getDescription());
        cityRepository.save(existingCity);
    }
}
