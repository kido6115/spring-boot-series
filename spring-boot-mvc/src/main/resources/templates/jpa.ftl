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
                <h1 class="h2">JPA</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group mr-2">
                        <button type="button" class="btn btn-sm btn-outline-secondary">Share</button>
                        <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                    </div>
                    <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="feather feather-calendar">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                        </svg>
                        This week
                    </button>
                </div>
            </div>
            <h2>Repository</h2>
            <p>使用Spring Data Jpa當作主要DAO層, 節省基礎SQL需求</p>
            <pre>
                <code data-language="java">
                public interface PersonRepository extends JpaRepository&lt;Person, String&gt; {
                }
                </code>
            </pre>
            <h2>ManyToOne</h2>
            <p>使用ManyToOne進行外鍵關聯, 透過A關聯至B取得資料</p>
            <pre>
                <code data-language="java">
                @Entity
                @Table
                @Data
                public class Person implements Serializable {
                private static final long serialVersionUID = 1293221476229623790L;

                @Id
                private String id;

                private String username;

                private String password;

                @ManyToOne
                @JoinColumn(name = "department", foreignKey = @ForeignKey(name = "fk_person_department"))
                private Department department;


                }
                </code>
            </pre>
            <div class="table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>帳戶名稱</th>
                        <th>部門名稱</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list persons as item>
                        <tr>
                            <td>${item.username}</td>
                            <td>${item.department.name}</td>
                        </tr>
                    </#list>

                    </tbody>
                </table>
            </div>
            <h2>ManyToMany</h2>
            <p>透過中繼表關聯複數資料, 透過關聯取得所有屬於A的B資料</p>

            <pre>
                <code data-language="java">
                    @Entity
                    @Table
                    @Data
                    public class Person implements Serializable {
                        private static final long serialVersionUID = 1293221476229623790L;

                        @Id
                        private String id;

                        private String username;

                        private String password;

                        @ManyToOne
                        @JoinColumn(name = "department", foreignKey = @ForeignKey(name = "fk_person_department"))
                        private Department department;

                        @ManyToMany
                        @JoinTable(name = "person_role",
                                joinColumns = @JoinColumn(name = "person_id"),
                                inverseJoinColumns = @JoinColumn(name = "role_id"),
                                uniqueConstraints = @UniqueConstraint(columnNames = {"person_id", "role_id"}))
                        private List<Role> roles;

                    }

                </code>
            </pre>
            <div class="table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>帳戶名稱</th>
                        <th>部門名稱</th>
                        <th>身分</th>

                    </tr>
                    </thead>
                    <tbody>
                    <#list persons as item>
                        <tr>
                            <td>${item.username}</td>
                            <td>${item.department.name}</td>
                            <td><#list item.roles as role>
                                    ${role.name}<#sep>, </#sep>
                                </#list>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <h2>OneToMany</h2>
            <p>透過雙向關聯反關聯</p>
            <pre>
                <code data-language="java">
                @Entity
                @Table
                @Data
                public class Department implements Serializable {

                    private static final long serialVersionUID = -6093972142329629381L;

                    @Id
                    private String id;

                    private String name;

                    private String no;

                    @OneToMany(mappedBy = "department")
                    private List<Person> persons;
                }
                </code>
            </pre>
            <div class="table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>部門名稱</th>
                        <th>代號</th>
                        <th>所屬員工</th>

                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${department.name}</td>
                        <td>${department.no}</td>
                        <td><#list department.persons as person>
                                ${person.username}<#sep>, </#sep>
                            </#list>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <h2>Criteria Api</h2>
            <p>若有複雜關聯情況透過Criteria API撰寫語法, 與SQL相比較容易DEBUG及追蹤</p>

            <h2>Pageable</h2>
            <p>Spring Data幾乎有此介面, 有效分頁提升查詢效率</p>
        </main>
    </div>
</div>
</body>

</html>
