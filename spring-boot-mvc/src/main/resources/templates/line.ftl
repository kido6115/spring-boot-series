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
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">MVC</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="dropdown">
                        <a class="btn btn-sm btn-outline-secondary dropdown-toggle" href="#" role="button"
                           id="dropdownMenuLink"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Dropdown link
                        </a>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <a class="dropdown-item" href="#">Something else here</a>
                        </div>
                    </div>
                </div>
            </div>
            <h2>Controller</h2>
            <p>透過SpringMVC註解快速註冊Controller並以Freemarker進行頁面渲染, 選擇Freemarker則是因其支援embedded Tomcat,
                除輕量化在分散式或者容器化上較易應用
            </p>
            <pre>
                <code data-language="java">
                        @Controller
                        @RequestMapping("/auth")
                        public class AuthenticationController {
                            @Resource
                            private DepartmentRepository departmentRepository;

                            @Resource
                            private PersonRepository personRepository;

                            @GetMapping("mvc")
                            public String mvc(Model model) {
                                model.addAttribute("persons", personRepository.findAll());
                                model.addAttribute("department", departmentRepository.findByNo("RD"));
                                return "mvc";
                            }

                        }
                    </code>
            </pre>
            <pre>
                <code data-language="html">
                    <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>帳戶名稱</th>
                        <th>部門名稱</th>
                        <th>身分</th>
                    </tr>
                    </thead>
                    <tbody>
                        ${r"<#list persons as item>"}<tr>
                            <td>${r"${item.username}"}</td>
                            <td>${r"${item.department.name}"}</td>
                            <td>${r"<#list item.roles as role>"}
                                ${r"${role.name}<#sep>, </#sep>"}
                                ${r"</#list>"}
                            </td>
                        </tr> ${r"</#list>"}
                    </tbody>
                </table>
                </code>
            </pre>
        </main>
    </div>
</div>
</body>

</html>
