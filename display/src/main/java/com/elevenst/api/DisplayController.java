package com.elevenst.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.elevenst.DisplayApplication;
import com.elevenst.service.FeignProductRemoteService;
import com.elevenst.service.ProductRemoteService;

import brave.sampler.Sampler;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/displays")
public class DisplayController {
    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Sampler alwaysSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    private final ProductRemoteService productRemoteService;
    private final FeignProductRemoteService feignProductRemoteService;

    public DisplayController(ProductRemoteService productRemoteService,
                             FeignProductRemoteService feignProductRemoteService) {
        this.productRemoteService = productRemoteService;
        this.feignProductRemoteService = feignProductRemoteService;
    }

    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = DisplayApplication.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 

    @GetMapping(path = "/{displayId}")
    public String getDisplayDetail(@PathVariable String displayId) {
        String productInfo = getProductInfo();
        return String.format("[display id = %s at %s %s ]", displayId, System.currentTimeMillis(), productInfo);
    }

    private String getProductInfo() {
        return feignProductRemoteService.getProductInfo("12345");
    }
}