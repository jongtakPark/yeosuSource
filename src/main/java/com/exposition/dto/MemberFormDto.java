package com.exposition.dto;

import lombok.Data;

@Data
public class MemberFormDto {

    private String mid;
    private String name;
    private String password;
    private String passwordCheck;
    private String address;
    private String email;
    private String tel;
}