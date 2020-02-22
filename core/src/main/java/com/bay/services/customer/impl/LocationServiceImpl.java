package com.bay.services.customer.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bay.common.dto.core.LocationDTO;
import com.bay.common.exceptions.NotSaveException;
import com.bay.entity.core.location.TblLocation;
import com.bay.repositories.customer.LocationRepository;
import com.bay.services.customer.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	@Autowired
	private LocationRepository locationRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LocationDTO save(LocationDTO location) {
		try {
			TblLocation entity = modelMapper.map(location, TblLocation.class);
			entity = locationRepo.save(entity);
			location.setId(entity.getId());
			return location;
		} catch (Exception e) {
			LOGGER.error("ERROR: guardando la localización LocationServiceImpl.save.Exception", e);
			throw new NotSaveException(String.valueOf(location.getIdCustomer()), e);
		}
	}

}
