package com.example.HelloWorld2.controller;
import com.example.HelloWorld2.model.*;
import com.example.HelloWorld2.repository.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.test.web.servlet.setup.*;
import org.thymeleaf.templateresolver.*;
import org.thymeleaf.spring6.*;
import org.thymeleaf.context.*;

@SpringBootTest
class HelloWorldControllerTest
{
    @InjectMocks
    private HelloWorldController helloWorldController;
    @Mock
    private FamilyNameRepository familyNameRepository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private Model model;    
    private MockMvc mockMvc;
    
    private static List<FamilyNameModel> listFamilyNameModel;
    private static Optional<List<FamilyNameModel>> listFamilyNames;
    private static final String TEST_REGION1 = "カリフォルニア州";
    private static final String TEST_NAME1 = "佐藤テスト";
    private static final String TEST_NAME2 = "鈴木テスト";
    private static final String TEST_NAME3 = "田中テスト";
    private static final int TEST_POPULATION1 = 500;
    private static final int TEST_POPULATION2 = 400;
    private static final int TEST_POPULATION3 = 300;
    
    @BeforeAll
    public static void setupAll()
    {
        //  テスト全体で使われる定数を作成する
        listFamilyNameModel = new ArrayList<>();
        listFamilyNameModel.add(new FamilyNameModel(TEST_REGION1, TEST_NAME1, 1, TEST_POPULATION1));
        listFamilyNameModel.add(new FamilyNameModel(TEST_REGION1, TEST_NAME2, 2, TEST_POPULATION2));
        listFamilyNameModel.add(new FamilyNameModel(TEST_REGION1, TEST_NAME3, 3, TEST_POPULATION3));
        listFamilyNames = Optional.of(listFamilyNameModel);        
    }
    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloWorldController(familyNameRepository, regionRepository)).build();
    }
    
    @Test
    @DisplayName("期待したURLが返るかテスト:/")
    public void URLtest1() throws Exception
    {
        MockMvcResultMatchers.view().name("/");
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andReturn();
    }
    @Test
    @DisplayName("期待したURLが返るかテスト:/HelloWorld")
    public void URLtest2() throws Exception
    {
        MockMvcResultMatchers.view().name("HelloWorld");
        mockMvc.perform(MockMvcRequestBuilders.post("/HelloWorld").param("familyName", TEST_NAME1)).andExpect(MockMvcResultMatchers.view().name("HelloWorld.html")).andReturn();
    }
    @Test
    @DisplayName("Repositoryのメソッドが呼び出されていることのテスト")
    void RepositoryTest() throws Exception
    {
        Mockito.when(familyNameRepository.findByName(TEST_NAME1)).thenReturn(listFamilyNames);
        Mockito.when(familyNameRepository.getPopulation(TEST_NAME1, TEST_REGION1)).thenReturn(Optional.of(Integer.valueOf(TEST_POPULATION1)));
        
        List<RegionModel> t = new ArrayList<>();
        t.add(new RegionModel(TEST_REGION1));
        Mockito.when(regionRepository.findAll()).thenReturn(t);
        
        //  呼び出し
        helloWorldController.getHelloWorld(TEST_NAME1, model);
        
        //  結果の検証
        Mockito.verify(familyNameRepository, Mockito.times(1)).findByName(TEST_NAME1);
        Mockito.verify(familyNameRepository, Mockito.times(1)).getPopulation(TEST_NAME1, TEST_REGION1);
    }
    @Test
    @DisplayName("Thymeleafを通して正しく出力されているかテスト")
    public void ThymeleafTest() throws Exception
    {
        //  テンプレートへのアクセス
        AbstractConfigurableTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        
        //  テンプレート中の変数に値を代入する
        Context context = new Context();
        context.setVariable("familyName", TEST_NAME1);
        context.setVariable("familyNameAll", listFamilyNames.get());
        
        List<FamilyNameModel> listFamilyName = new ArrayList<>();
        listFamilyName.add(new FamilyNameModel("プロテスタントが多い州", TEST_NAME1, 0, 2000000));
        listFamilyName.add(new FamilyNameModel("WindowsよりMacユーザーが多い州", TEST_NAME1, 0, 1000000));
        context.setVariable("familyNameByGroup", listFamilyName);

        // templates配下のhtmlを読み込む
        String result = templateEngine.process("HelloWorld", context);
        //System.out.println("htmlの内容を表示します。");
        //System.out.println(result);
        
        // ThymeLeafの出力結果を確認する
        Assertions.assertThat(result.contains(String.format("Hello %s!", TEST_NAME1)));
        for (FamilyNameModel e : listFamilyNames.get())
        {
            Assertions.assertThat(result.contains(String.format("%sでは%d番目に多い苗字です。およそ%d人です。", e.getRegion(), e.getRanking(), e.getPopulation())));
        }
        for (FamilyNameModel e : listFamilyName)
        {
            Assertions.assertThat(result.contains(String.format("%sではおよそ%d人です。", e.getRegion(), e.getPopulation())));
        }
    }
}
