<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
</head>
<body>
<#include "component/nav.ftl" />

<div class="container-fluid">
    <div class="row">
        <#include "component/sidebar.ftl" />

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div class="chartjs-size-monitor">
                <div class="chartjs-size-monitor-expand">
                    <div class=""></div>
                </div>
                <div class="chartjs-size-monitor-shrink">
                    <div class=""></div>
                </div>
            </div>
            <div class="d-flex  flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Restful/Swagger/SpringDoc</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="dropdown">
                        <a class="btn btn-sm btn-outline-secondary dropdown-toggle" href="#" role="button"
                           id="dropdownMenuLink"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            原始碼
                        </a>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/tree/master/springdoc/src/main/java/com/sungyeh/config">SpringDoc相關</a>
                        </div>
                    </div>
                </div>
            </div>
            <h5>SpringDoc</h5>
            <p>SpringFox接班人, 透過在Controller上進行註解來自動產生OAS及整合Swagger介面
            </p>
            <pre>
                <code data-language="java">
                    @RestController
                    @RequestMapping("/rest/role")
                    @Tag(name = "角色")
                    public class RoleController {

                        @Resource
                        private RoleRepository roleRepository;

                        /**
                         * 根據ID找出角色
                         *
                         * @param id 主鍵
                         * @return 角色
                         * @see Role
                         */
                        @Operation(summary = "根據ID找出角色")
                        @GetMapping("{id}")
                        public Role findByRoleId(
                                @Parameter(
                                        name = "id",
                                        description = "主鍵",
                                        required = true,
                                        schema = @Schema(type = "string"))
                                @PathVariable("id") String id) {
                            return new Role(roleRepository.getReferenceById(id));
                        }

                        /**
                         * 找出全部角色
                         *
                         * @return 角色
                         * @see Role
                         */
                        @Operation(summary = "找出全部角色")
                        @GetMapping
                        public List&lt;Role&gt; findAllRole() {
                            return roleRepository.findAll().stream().map(Role::new).toList();
                        }

                        /**
                         * 新增角色
                         *
                         * @param role 角色
                         * @return 角色
                         * @see Role
                         */
                        @Operation(summary = "新增角色")
                        @PostMapping
                        public Role saveRole(@RequestBody Role role) {
                            role.setId(null);
                            return new Role(roleRepository.save(role.toEntity()));
                        }

                        /**
                         * 更新角色
                         *
                         * @param role 角色
                         * @param id   主鍵
                         * @return 角色
                         * @see Role
                         */
                        @Operation(summary = "更新角色")
                        @PutMapping("{id}")
                        public Role updateRole(@RequestBody Role role,
                                               @Parameter(
                                                       name = "id",
                                                       description = "主鍵",
                                                       required = true,
                                                       schema = @Schema(type = "string"))
                                               @PathVariable("id") String id) {
                            com.sungyeh.domain.Role target = roleRepository.getReferenceById(id);
                            target.setName(role.getName());
                            target.setNo(role.getNo());
                            return new Role(roleRepository.save(target));
                        }

                        /**
                         * 刪除角色
                         *
                         * @param id 主鍵
                         */
                        @Operation(summary = "刪除角色")
                        @DeleteMapping("{id}")
                        public void deleteRole(
                                @Parameter(
                                        name = "id",
                                        description = "主鍵",
                                        required = true,
                                        schema = @Schema(type = "string"))
                                @PathVariable("id") String id) {
                            roleRepository.deleteById(id);
                        }


                    }
                </code>
            </pre>
            <h5>Swagger</h5>
            <p><a target="_blank" href="/swagger-ui/index.html">試用Swagger</a>, 參考<a
                        href="<@spring.url '/auth/authorization' />">
                    Authorization Server</a>取得access token進行Authorize
            </p>
            <h5>OAS</h5>
            <p>透過SpringDoc產生出的OAS文檔</p>
            <ul>
                <li><a href="/v3/api-docs/sungyeh-api" download>JSON</a></li>
                <li><a href="/v3/api-docs.yaml/sungyeh-api" download="sungyeh-api.yml">YAML</a>
                </li>

            </ul>
        </main>
    </div>
</div>
</body>

</html>
