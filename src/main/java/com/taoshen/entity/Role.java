package com.taoshen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String id;
    private String name;

    private List<Permission> permissions;
}
