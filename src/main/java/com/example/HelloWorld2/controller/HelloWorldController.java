package com.example.HelloWorld2.controller;
import com.example.HelloWorld2.model.*;
import com.example.HelloWorld2.repository.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HelloWorldController
{
    @Autowired
    private final FamilyNameRepository familyNameRepository;
    @Autowired
    private final RegionRepository regionRepository;
    
    @GetMapping(value = "/")
    public String showIndex(Model model)
    {
        return "index";
    }
    
    @RequestMapping(value = "HelloWorld", method = RequestMethod.POST)
    public String getHelloWorld(@RequestParam("familyName") String familyName, Model model)
    {
        if (!familyName.equals(""))
        {
            //  入力された苗字を代入して"Hello xxxxxxxx"と表示する
            model.addAttribute("familyName", familyName);
            
            //  全国でのランキングを検索する
            Optional<List<FamilyNameModel>> familyNames = familyNameRepository.findByName(familyName);
            if (familyNames.isPresent())
            {
                //  リストの長さを最大6つまでに切り詰める
                familyNames = Optional.ofNullable(familyNames.get().subList(0, Math.min(familyNames.get().size(), 6)));
                //  リストを表示する
                model.addAttribute("familyNameAll", familyNames.get());
            }
            
            //  地域ごとのランキングを検索する
            List<FamilyNameModel> regionAndPopulation = new ArrayList<>();
            for (RegionModel regionModels : regionRepository.findAll())
            {
                Optional<Integer> population = familyNameRepository.getPopulation(familyName, regionModels.getName());
                //  検索結果が存在する、かつ5000人以上の場合は表示する
                if (population.isEmpty() || population.get() < 5000) continue;
                regionAndPopulation.add(new FamilyNameModel(regionModels.getName(), familyName, 0, population.get()));
            }
            //  降順にソート
            Collections.sort(regionAndPopulation, Collections.reverseOrder());
            //  リストを表示する
            model.addAttribute("familyNameByGroup", regionAndPopulation);
        }
        else
        {
            //  名前が入力されていない場合は"Hello World"と表示する
            model.addAttribute("familyName", "World");
        }
        return "HelloWorld.html";
    }
}
