package com.webapp.tdastore.entities.generator;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Data
@Component
public class GeneratorCode {
    public String generator(String id) {
        String uuid_str = UUID.randomUUID().toString();
        return uuid_str.substring(0, 4) + "i" + id;
    }

}
