package com.sungyeh.bean;

import lombok.Data;

import java.util.List;

/**
 * RoleAcl
 *
 * @author sungyeh
 */
@Data
public class RoleAcl {

    private List<Acl> acls;

    @Data
    public static class Acl {

        private String role;

        private String url;
    }


}
