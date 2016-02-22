package com.exteso.bruno.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.exteso.bruno.web.ControllersMarker;

@Configuration
@ComponentScan(basePackageClasses = {ControllersMarker.class})
public class WebMvcConfiguration {

}
