package com.example.hyupup_tool.entity.dto;

import com.example.hyupup_tool.util.PurchasePlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private Long userId;
    private String email;
    private PurchasePlan purchasePlan;
}
