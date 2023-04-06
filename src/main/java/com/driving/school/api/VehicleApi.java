package com.driving.school.api;

import com.driving.school.dto.VehicleRequestDto;
import com.driving.school.dto.VehicleUpdateDto;
import com.driving.school.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/vehicles")
public class VehicleApi extends CrudApi<VehicleRequestDto, VehicleUpdateDto> {
    @Autowired
    public VehicleApi(VehicleService service) {
        super(service);
    }

}
