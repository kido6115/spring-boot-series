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
                <h1 class="h2">JUnit</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/tree/master/spring-boot-jpa/src/test/java/com/sungyeh/repository">DAO
                                Test</a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/tree/master/spring-boot-mvc/src/test/java/com/sungyeh/web">Controller
                                Test</a>
                        </div>
                    </div>
                </div>
            </div>
            <p>Java 單元測試框架, 透過與Spring整合, 可以測試Controller, Service, Repository等等
            </p>
            <p>
                透過Jenkins建置執行測試, 確保測試完整性, 並確保程式最小正常運作能力, 搭配Jacoco也可確認測試之涵蓋率
            </p>
            <pre>
                <code data-language="java">
                        @DisplayName("訂房單元測試")
                        @ExtendWith(SpringExtension.class)
                        @SpringBootTest
                        @Transactional
                        public class BookingRepositoryTest {

                            @Resource
                            private BookingRepository bookingRepository;

                            @Test
                            public void testCrud() {
                                // Arrange
                                Booking booking = new Booking();
                                booking.setPeople(1);
                                booking.setDateTime(LocalDateTime.now());
                                booking.setCreateTime(LocalDateTime.now());
                                // Act
                                Booking target = bookingRepository.save(booking);
                                // Assert
                                Assertions.assertNotNull(target.getId());
                                Assertions.assertEquals(booking.getPeople(), target.getPeople());
                                Assertions.assertEquals(booking.getDateTime(), target.getDateTime());
                                Assertions.assertEquals(booking.getCreateTime(), target.getCreateTime());

                            }
                        }
                    </code>
            </pre>
            <br/>
            <h5>Mockito</h5>
            <p>使用Moockito製作模擬對象供我們測試Controller, 除測試API也能測試頁面flow是否正常</p>
            <pre>
                <code data-language="java">
                    @ExtendWith(SpringExtension.class)
                    @SpringBootTest(webEnvironment = MOCK)
                    @AutoConfigureMockMvc
                    @Transactional
                    @ActiveProfiles("dev")
                    public class IndexControllerTest {

                        @Resource
                        private MockMvc mockMvc;

                        @Test
                        public void loginPage() throws Exception {
                            ModelAndView model = mockMvc.perform(
                                    get("/login")
                            ).andExpect(status().isOk()).andReturn().getModelAndView();
                            Assertions.assertNotNull(model);
                            Assertions.assertNotNull(model.getModel().get("site"));
                        }

                        @Test
                        public void acl() throws Exception {
                            mockMvc.perform(
                                            get("/acl/user")
                                                    .accept("application/json")
                                    ).andExpect(status().isOk())
                                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
                        }

                    }
                </code>
            </pre>
        </main>
    </div>
</div>
</body>

</html>
