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
            <div class="d-flex flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">分析文件</h1>
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
            <h5>Spotbugs</h5>
            <p>Findbugs的後續者, 分析JAVA編譯後程式碼找出潛在風險
            </p>
            <pre>
                <code data-language="xml">
                <build>
                    <plugins>
                        <plugin>
                            <groupId>com.github.spotbugs</groupId>
                            <artifactId>spotbugs-maven-plugin</artifactId>
                            <configuration>
                                <excludeFilterFile>config/findbugs/findbugs-filter.xml</excludeFilterFile>
                             </configuration>
                        </plugin>
                    </plugins>
                </build>
                    </code>
            </pre>
            <h5>Checkstyle</h5>
            <p>程式碼靜態分析, 檢查程式碼是否符合Coding Style, 例如: 縮排, 命名規則, 註解等
                並找出不宜的撰寫方式等等
            </p>
            <pre>
                <code data-language="xml">
                <build>
                    <plugins>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-checkstyle-plugin</artifactId>
                            <configuration>
                                <configLocation>config/checkstyle/checkstyle.xml</configLocation>
                                <includes>com/sungyeh/**/*.java</includes>
                                <includeResources>false</includeResources>
                                <includeTestResources>false</includeTestResources>
                            </configuration>
                        </plugin>
                    </plugins>
                </build>
                    </code>
            </pre>
            <h5>Maven Surefire Report Plugin</h5>
            <p>整合Junit單元測試報告
            </p>
            <pre>
                <code data-language="xml">
                <build>
                    <plugins>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-surefire-plugin</artifactId>
                            <version>3.0.0-M1</version>
                            <configuration>
                                <includes>
                                    <include>**/Test*.java</include>
                                    <include>**/*Test.java</include>
                                    <include>**/*Tests.java</include>
                                    <include>**/*TestCase.java</include>
                                </includes>
                                <excludes>
                                    <exclude>**/dummy/*</exclude>
                                </excludes>
                                <excludedGroups>slow</excludedGroups>
                            </configuration>
                        </plugin>
                    </plugins>
                </build>
                    </code>
            </pre>
            <h5>Apache Maven Javadoc Plugin</h5>
            <p>根據程式Javadoc註解Javadoc API文件
            </p>
            <pre>
                <code data-language="xml">
                <build>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-javadoc-plugin</artifactId>
                        <configuration>
                            <show>private</show>
                        </configuration>
                        <executions>
                            <execution>
                                <id>attach-javadocs</id>
                                <phase>deploy</phase>
                                <goals>
                                    <goal>jar</goal>
                                </goals>
                            </execution>
                            <execution>
                                <id>install-javadocs</id>
                                <phase>install</phase>
                                <goals>
                                    <goal>jar</goal>
                                </goals>
                            </execution>
                        </executions>
                     </plugin>
                </build>
                    </code>
            </pre>
            <h5>Apache Maven Site Plugin</h5>
            <p>將各式靜態報告整理成一份報告, 例如: Checkstyle, Spotbugs, Surefire, Javadoc等
            </p>
            <pre>
                <code data-language="xml">
                <build>
                    <plugins>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-site-plugin</artifactId>
                        </plugin>
                    </plugins>
                </build>
                <reporting>
                    <plugins>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-site-plugin</artifactId>
                            <configuration>
                                <finalName>index</finalName>
                            </configuration>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-checkstyle-plugin</artifactId>
                            <configuration>
                                <configLocation>config/checkstyle/checkstyle.xml</configLocation>
                                <includes>com/sungyeh/**/*.java</includes>
                                <includeResources>false</includeResources>
                                <includeTestResources>false</includeTestResources>
                            </configuration>
                            <reportSets>
                                <reportSet>
                                    <reports>
                                        <report>checkstyle</report>
                                    </reports>
                                </reportSet>
                            </reportSets>
                        </plugin>
                        <plugin>
                            <groupId>com.github.spotbugs</groupId>
                            <artifactId>spotbugs-maven-plugin</artifactId>
                            <configuration>
                                <excludeFilterFile>config/findbugs/findbugs-filter.xml</excludeFilterFile>
                            </configuration>
                        </plugin>
                    </plugins>
                </reporting>
                    </code>
            </pre>
        </main>
    </div>
</div>
</body>

</html>
