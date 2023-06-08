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
                <h1 class="h2">靜態分析</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/blob/master/pom.xml">pom.xml</a>
                        </div>
                    </div>
                </div>
            </div>
            <p>透過靜態分析結合CI/CD工具從日常降低不良程式碼, 減少風險外也能提升品質, 未來若進行各式弱掃、壓測時能夠更快速的修正
            </p>
            <br/>
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
            <p>整合JUnit單元測試報告
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
            <h5>Apache Maven Jacoco Plugin</h5>
            <p>透過單元測試檢查程式測試涵蓋率
            </p>
            <pre>
                <code data-language="xml">
                    <build>
                        <plugins>
                            <plugin>
                                <groupId>org.jacoco</groupId>
                                <artifactId>jacoco-maven-plugin</artifactId>
                                <version>0.8.9</version>
                                <executions>
                                    <execution>
                                        <id>prepare-agent</id>
                                        <goals>
                                            <goal>prepare-agent</goal>
                                        </goals>
                                    </execution>
                                    <execution>
                                        <id>report</id>
                                        <phase>prepare-package</phase>
                                        <goals>
                                            <goal>report</goal>
                                        </goals>
                                    </execution>
                                    <execution>
                                        <id>post-unit-test</id>
                                        <phase>test</phase>
                                        <goals>
                                            <goal>report</goal>
                                        </goals>
                                        <configuration>
                                            <dataFile>target/jacoco.exec</dataFile>
                                            <outputDirectory>target/jacoco-ut</outputDirectory>
                                        </configuration>
                                    </execution>
                                </executions>
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
            <p>將各式靜態報告整理成一份報告, 例如: Checkstyle, Spotbugs, JUnit, Jacoco, Javadoc等
            </p>
            <div class="card" style="width: 50rem;">
                <div class="card-body">
                    <h6 class="card-subtitle mb-2 text-muted">報告下載</h6>
                    <p class="card-text">原始site報告較適合當作報告繳交, 在日常監督使用較不方便, 可以使用Jenkins內部套件進行觀看,
                        對於日常品質要求較為容易, Jenkins的Warnings Next
                        Generation套件能夠整合百種以上的靜態分析報告</p>
                    <a target="_blank"
                       href="https://drive.google.com/drive/folders/1oPArC63bSChcnbkAYtl5ZNVPdcCfzRoB?usp=sharing"
                       class="card-link">site報告</a>
                    <a target="_blank" href="http://104.198.166.226:8080" class="card-link">Jenkins
                        使用user/1111登入</a>
                </div>
            </div>
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
