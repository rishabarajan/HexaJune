package com.hexaware.assetmanagement.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String userName;
    private String password;
}
